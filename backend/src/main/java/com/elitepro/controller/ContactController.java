package com.elitepro.controller;

import com.elitepro.dto.ContactDto;
import com.elitepro.model.Contact;
import com.elitepro.model.Utilisateur;
import com.elitepro.service.ContactService;
import com.elitepro.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController //cette classe expose une api rest
@RequestMapping("/api/contacts") //toutes les routes commenceront par ce lien
public class ContactController {
    private final ContactService contactService;
    private final UtilisateurService utilisateurService;

    public ContactController(ContactService contactService, UtilisateurService utilisateurService) {
        this.contactService = contactService;
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Contact> all() {
        return contactService.findAll();
    }

    @GetMapping("/{id}") // spring reconnait l'id comme une variable dans l'url @RequestMapping
    public ResponseEntity<Contact> get(@PathVariable Long id) {
        return contactService.findById(id)
                .map(ResponseEntity::ok)  //si le contact existe, on le renvoie avec un code 200 ok
                .orElse(ResponseEntity.notFound().build()); // si le contact n'existe pas on renvoie une erreur 404 notFound
    }

    @GetMapping("/search")
    public List<Contact> search(@RequestParam String q) {
        return contactService.searchByNom(q);
    }

    @PostMapping //on attend une requête http post avec un corps json
    public ResponseEntity<Contact> create(@RequestBody ContactDto dto) { /*l'anotation demande à spring de
    convertir automatiquement le json reçu en objet contact dto*/
        // 1) on retrouve l’utilisateur propriétaire
        Utilisateur owner = utilisateurService.findById(dto.utilisateurId) /*on récupère l'utilisateur propriétaire du contact
        pour le stocker dans la variable de type utilisateur (owner)*/
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
        // 2) on mappe le DTO -> Entity
        Contact c = new Contact();
        c.setNom(dto.nom);
        c.setPrenom(dto.prenom);
        c.setTelephone(dto.telephone);
        c.setEmail(dto.email);
        c.setPoste(dto.intitulePoste);
        c.setDirection(dto.direction);
        c.setBureau(dto.bureau);
        c.setUtilisateur(owner);

        Contact created = contactService.create(c);
        return ResponseEntity.created(URI.create("/api/contacts/" + created.getId())).body(created);/*on
        retourne une reponse de l'utilisateur crée avec l'url de la ressource créée et incluant l'objet
        complet dans la reponse*/
    }

    @PutMapping("/{id}") // spring reconnait l'id comme une variable dans l'url @RequestMapping
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody ContactDto dto) {
        return contactService.findById(id)
                .map(existing -> {   //si l'id existe, on met à jour champ par champ avec les valeurs du dto
                    existing.setNom(dto.nom);
                    existing.setPrenom(dto.prenom);
                    existing.setTelephone(dto.telephone);
                    existing.setEmail(dto.email);
                    existing.setPoste(dto.intitulePoste);
                    existing.setDirection(dto.direction);
                    existing.setBureau(dto.bureau);

                    // si utilisateur id existe, on peut (possibilité) de changer de propriétaire :
                    if (dto.utilisateurId != null) {
                        Utilisateur owner = utilisateurService.findById(dto.utilisateurId)
                                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
                        existing.setUtilisateur(owner);
                    }
                    return ResponseEntity.ok(contactService.update(id, existing));
                })
                .orElse(ResponseEntity.notFound().build());

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        if (contactService.findById(id).isEmpty())
            return ResponseEntity.notFound().build();
            contactService.delete(id);
            return ResponseEntity.noContent().build();
    }
}

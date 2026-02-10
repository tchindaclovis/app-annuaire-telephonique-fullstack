package com.elitepro.controller;

import com.elitepro.model.Contact;
import com.elitepro.model.Utilisateur;
import com.elitepro.service.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController  //cette classe expose une api rest
@RequestMapping("/api/utilisateurs") //toutes les routes de la classe commenceront par ce lien
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping
    public List<Utilisateur> all(){
        return utilisateurService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> get(@PathVariable Long id){
        return utilisateurService.findById(id)
                .map(ResponseEntity::ok)  //si l'utilisateur existe, on le renvoie avec un code 200 ok
                .orElse(ResponseEntity.notFound().build()); // si l'utilisateur n'existe pas on renvoie une erreur 404 notFound
    }


    @PostMapping //on attend une requête http post avec un corps json
    public ResponseEntity<Utilisateur> create(@RequestBody Utilisateur utilisateur) throws IllegalAccessException {
        Utilisateur created = utilisateurService.create(utilisateur);

        return ResponseEntity.created(URI.create("/api/utilisateurs/" + created.getId())).body(created); /*on renvoi
        le code http 201(created), on précise l'url de l'utilisateur créé et inclu l'objet complet dans la reponse*/
    }



}

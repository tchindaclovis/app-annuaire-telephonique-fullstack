package com.elitepro.controller;

import com.elitepro.model.Utilisateur;
import com.elitepro.repository.UtilisateurRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final UtilisateurRepository utilisateurRepository;

    public TestController(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @GetMapping("/api/test")
    public String testDatabase(){
        Utilisateur u = new Utilisateur("elitepro", "elitepro@test.com", "1234");
        utilisateurRepository.save(u);
        return "Utilisateur sauvegardé avec ID: " + u.getId() + "///   Le nom: " + u.getNom()
                + "///   L'email: " + u.getEmail() + "///   Le mot de passe: " + u.getMotDePasse();
    }
}

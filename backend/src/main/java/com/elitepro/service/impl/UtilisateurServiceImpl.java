package com.elitepro.service.impl;

import com.elitepro.model.Utilisateur;
import com.elitepro.repository.UtilisateurRepository;
import com.elitepro.service.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository; /*final veut dire qu'on ne
    pourra pas changer sa valeur après l'avoir initialisé*/

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Optional<Utilisateur> findById(Long id) { /*le mot clé Optional veut dire que
    la méthode peut renvoyer soit un utilisateur soit rien du tout si l'id n'existe pas*/
        return utilisateurRepository.findById(id);
    }

    @Override
    public Utilisateur create(Utilisateur utilisateur) throws IllegalAccessException {
        //éviter les doublons d'email en vérifiant s'il n'existe pas dans la base
        if(utilisateurRepository.existsByEmail(utilisateur.getEmail())){
            throw new IllegalAccessException("Email déjà utilisé");
        }
        return utilisateurRepository.save(utilisateur);
    }
}

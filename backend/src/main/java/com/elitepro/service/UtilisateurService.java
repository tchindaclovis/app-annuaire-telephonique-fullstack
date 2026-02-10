package com.elitepro.service;

import com.elitepro.model.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurService {
    List<Utilisateur> findAll();
    Optional<Utilisateur> findById(Long id);
    Utilisateur create(Utilisateur utilisateur) throws IllegalAccessException;
}

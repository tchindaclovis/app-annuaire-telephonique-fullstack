package com.elitepro.repository;

import com.elitepro.model.Contact;
import com.elitepro.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository //va faire le lien entre la base de données et l'application
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByUtilisateur(Utilisateur utilisateur); //tous les contacts d'un utilisateur donné

    List<Contact> findByNomContainingIgnoreCase(String nom); //contacts dont le nom contient une partie d'un mot en ignorant la casse
}

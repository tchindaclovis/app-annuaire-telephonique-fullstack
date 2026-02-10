package com.elitepro.service;

import com.elitepro.model.Contact;
import java.util.List;
import java.util.Optional;

public interface ContactService {
    List<Contact> findAll();
    Optional<Contact> findById(Long id);
    Contact create(Contact contact);
    Contact update(Long id, Contact contact);
    void delete(Long id);
    List<Contact> searchByNom(String nom);
}

package com.elitepro.service.impl;

import com.elitepro.model.Contact;
import com.elitepro.repository.ContactRepository;
import com.elitepro.service.ContactService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository; // l'implémentation du service a besoin du repository

    public ContactServiceImpl(ContactRepository contactRepository) { /*ce constructeur sert à injecter
    automatiquement contactRepository dans le service*/
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Override
    public Optional<Contact> findById(Long id) {
        return contactRepository.findById(id);
    }

    @Override
    public Contact create(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Long id, Contact contact) {
        //on s'assure que l'ID correspond à la ressource qu'on met à jour
        contact.setId(id);
        return contactRepository.save(contact);
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<Contact> searchByNom(String nom) {
        return contactRepository.findByNomContainingIgnoreCase(nom);
    }
}

package com.elitepro.service;

import com.elitepro.model.Contact;
import com.elitepro.repository.ContactRepository;
import com.elitepro.service.impl.ContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// Test unitaire (logique métier) du service ContactServiceImpl
public class ContactServiceImplTest {

    @Test  //identifier la methode comme une méthode de test
    void create_shouldSaveContact() throws Exception {
        ContactRepository repo = mock(ContactRepository.class); /*on mock le
        contactRepo ie qu'on cré un faux objet repo. Mocker c'est simuler le comportement d'une dépendance*/
        ContactService service = new ContactServiceImpl(repo);/* on injecte ce
        mock dans le service qu'on cré. Mocker c'est simuler le comportement d'une dépendance*/

        /*on prépare un contact de test*/
        Contact c = new Contact();
        c.setNom("Dupond");
        c.setPrenom("Jean");
        c.setTelephone("0600000000");

        /*on programme le comportement du mock ie ce qu'on va faire
        lorsqu'on va appeler la methode save*/
        when(repo.save(any(Contact.class))).thenAnswer(inv -> {
            Contact saved = inv.getArgument(0); //on récupère l'argument transmis ie "contact"
            var idField = Contact.class.getDeclaredField("id");/*on simule la génération d'id
            puisqu'il n'y a pas de base de données dans le test*/
            idField.setAccessible(true);
            idField.set(saved, 1L);
            return saved; //on retourne l'objet
        });

        /*on appelle la méthode create du service attesté*/
        Contact created = service.create(c);

        /*on capture l'argument passé à la methode save*/
        ArgumentCaptor<Contact> captor = ArgumentCaptor.forClass(Contact.class);

        /*on vérifie que l'argument porte bien le nom "xxx" et que l'id a bien été attribuée*/
        verify(repo).save(captor.capture());
        assertThat(captor.getValue().getNom()).isEqualTo("Dupond");
        assertThat(created.getId()).isEqualTo(1L);
    }

    @Test
    void findAll_shouldReturnListFromRepo() {
        ContactRepository repo = mock(ContactRepository.class);
        ContactService service = new ContactServiceImpl(repo);

        when(repo.findAll()).thenReturn(List.of(new Contact(), new Contact()));
        assertThat(service.findAll()).hasSize(2);
    }

    @Test
    void findById_shouldReturnOptional() {
        ContactRepository repo = mock(ContactRepository.class);
        ContactService service = new ContactServiceImpl(repo);

        Contact c = new Contact();
        when(repo.findById(1L)).thenReturn(Optional.of(c));

        assertThat(service.findById(1L)).isPresent();
    }
}


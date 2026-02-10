package com.elitepro.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)  //champ obligatoire
    private String nom;
    @Column(nullable = false, unique = true)  //champ obligatoire et pas de doublons
    private String email;
    @Column(nullable = false)  //champ obligatoire
    private String motDePasse;
    @OneToMany(mappedBy = "utilisateur", cascade =CascadeType.ALL,orphanRemoval = true)
    private List<Contact> contacts = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(String nom, String email, String motDePasse) {
        this.nom = nom;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public List<Contact> getContacts() {
        return contacts;
    }
    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }
}

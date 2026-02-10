package com.elitepro.model;

import jakarta.persistence.*;

@Entity
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)  //champ obligatoire
    private String nom;
    @Column(nullable = false)  //champ obligatoire
    private String prenom;
    @Column(nullable = false)  //champ obligatoire
    private String telephone;
    @Column(nullable = false)  //champ obligatoire
    private String email;
    private String poste;
    private String direction;
    private String bureau;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id")
    private Utilisateur utilisateur;

    public Contact() {
    }

    public Contact(String nom,String prenom, String telephone, String email,
                   String poste, String direction, String bureau) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.poste = poste;
        this.direction = direction;
        this.bureau = bureau;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoste() {
        return poste;
    }
    public void setPoste(String poste) {
        this.poste = poste;
    }

    public String getDirection() {
        return direction;
    }
    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getBureau() {
        return bureau;
    }
    public void setBureau(String bureau) {
        this.bureau = bureau;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}

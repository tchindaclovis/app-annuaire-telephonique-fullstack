package com.elitepro.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class ContactDto {

    public String nom;
    public String prenom;
    public String telephone;
    public String email;
    public String intitulePoste;
    public String direction;
    public String bureau;
    public Long utilisateurId;
}

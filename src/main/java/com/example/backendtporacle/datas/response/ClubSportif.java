package com.example.backendtporacle.datas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

//Data va cree tous les getters et setters
@Data
// NoArgsConstructor va creer un constructeur sans parametre
@NoArgsConstructor
// AllArgsConstructor va creer un constructeur avec tous les parametres
@AllArgsConstructor
public class ClubSportif {
    String CodeClub;
    String NomClub;
    Date DateCreation;
    String Dirigeant;
    String Ville;
    Integer Region;
}

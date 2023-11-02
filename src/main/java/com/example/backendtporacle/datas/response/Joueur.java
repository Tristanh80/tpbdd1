package com.example.backendtporacle.datas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Joueur {
    String Code;
    String Nom;
    String Prenom;
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date DateNaissance;
    String Nationalite;
    Float Poids;
    Float Taille;
    String Classe;
}

package com.example.backendtporacle.datas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Joueur {
    String Code;
    String Nom;
    String Prenom;
    Date DateNaissance;
    String Nationalite;
    Float Poids;
    Float Taille;
    String Classe;
}

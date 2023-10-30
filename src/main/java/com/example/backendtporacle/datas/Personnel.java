package com.example.backendtporacle.datas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Personnel {
    Integer Code;
    String Nom;
    String Prenom;
    Date DateDeNaissance;
    String Fonction;
    Integer Region;
    String Ville;
}

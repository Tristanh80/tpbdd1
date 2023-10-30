package com.example.backendtporacle.datas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipe {
    String id;
    String CodeClub;
    String CodeJoueur;
    Date DateDebutContrat;
    Date DateFinContrat;
    Integer NumeroMaillot;
    String Poste;
}

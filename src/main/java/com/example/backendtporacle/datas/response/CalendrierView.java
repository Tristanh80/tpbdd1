package com.example.backendtporacle.datas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CalendrierView {
    String ClubA_Nom;
    String ClubB_Nom;
    Date DateMatch;
    Timestamp Heure;
    String Stade;
    String Code_ClubB;
    String Code_ClubA;
    Integer Calendrier_id;
    String CodeMatch;
}

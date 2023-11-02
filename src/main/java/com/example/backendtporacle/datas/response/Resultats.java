package com.example.backendtporacle.datas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resultats {
    String CodeMatch;
    String ClubA_Nom;
    String ClubB_Nom;
    Integer ScoreA;
    Integer ScoreB;
    Date DateMatch;
    Timestamp Heure;
    String Stade;
    Integer NbreSpectateurs;
}

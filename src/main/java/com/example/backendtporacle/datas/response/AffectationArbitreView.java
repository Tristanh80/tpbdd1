package com.example.backendtporacle.datas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AffectationArbitreView {
    String Nom_Arbitre;
    String Club_A;
    String Club_B;
    String CodeMatch;
    String Stade;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date DateMatch;
    Timestamp Heure;
}

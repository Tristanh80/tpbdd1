package com.example.backendtporacle.datas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Arbitre {
    String Code;
    String Nom;
    String Prenom;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date DateDeNaissance;
    Integer Region;
    String ClubPrefere;
}

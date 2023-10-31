package com.example.backendtporacle.datas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date DateDebutContrat;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date DateFinContrat;
    Integer NumeroMaillot;
    String Poste;
}

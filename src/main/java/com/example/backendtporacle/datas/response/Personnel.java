package com.example.backendtporacle.datas.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date DateDeNaissance;
    String Fonction;
    Integer Region;
    String Ville;
}

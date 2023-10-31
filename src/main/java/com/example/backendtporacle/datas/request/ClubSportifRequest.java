package com.example.backendtporacle.datas.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubSportifRequest {
    String NomClub;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date DateCreation;
    String Dirigeant;
    String Ville;
    Integer Region;
}

package com.example.backendtporacle.datas.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubSportifRequest {
    String NomClub;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date DateCreation;
    String Dirigeant;
    String Ville;
    Integer Region;
}

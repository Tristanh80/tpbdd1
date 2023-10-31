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
public class Calendrier {
    Integer id;
    String CodeMatch;
    @JsonFormat(pattern = "dd/MM/yyyy")
    Date DateMatch;
    Timestamp Heure;
    String ClubA;
    String ClubB;
    String Stade;
}

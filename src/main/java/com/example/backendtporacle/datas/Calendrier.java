package com.example.backendtporacle.datas;

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
    Date DateMatch;
    Timestamp Heure;
    String CLubA;
    String CLubB;
    String Stade;
}
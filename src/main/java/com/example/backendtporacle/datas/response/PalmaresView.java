package com.example.backendtporacle.datas.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PalmaresView {
    Integer Palmares_id;
    String NomClub;
    Integer Annee;
    String Trophee;
    Integer NbreMatchsGagnes;
    Integer NbreMatchsPerdus;
    String CodeClub;
}

package com.example.backendtporacle.datas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Palmares {
    Integer id;
    String CodeCLub;
    Integer Annee;
    String Trophee;
    Integer NbreMatchsGagnes;
    Integer NbreMatchsPerdus;
}

package com.example.backendtporacle.datas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dirigeant {
    String Code;
    String Nom;
    String Prenom;
    String Profession;
}

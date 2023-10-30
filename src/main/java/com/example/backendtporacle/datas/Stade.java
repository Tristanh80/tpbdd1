package com.example.backendtporacle.datas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stade {
    String Code;
    String Nom;
    String Ville;
    Integer Region;
    Integer Capacite;
}

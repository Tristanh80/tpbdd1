package com.example.backendtporacle.datas.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StadeRequest {
    String Nom;
    String Ville;
    Integer Region;
    Integer Capacite;
}

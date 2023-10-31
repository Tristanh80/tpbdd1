package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.response.Arbitre;
import com.example.backendtporacle.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/arbitre")
public class ArbitreController {
     //    Ici on va faire un endpoint qui va nous permettre de recuperer tous les arbitres
    @GetMapping("")
    public ResponseEntity<List<Arbitre>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Arbitre> arbitres = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Arbitre";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Arbitre arbitre = new Arbitre();
                arbitre.setCode(resultSet.getString("Code"));
                arbitre.setNom(resultSet.getString("Nom"));
                arbitre.setPrenom(resultSet.getString("Prenom"));
                arbitre.setDateDeNaissance(resultSet.getDate("DateNaissance"));
                arbitre.setRegion(resultSet.getInt("Region"));
                arbitre.setClubPrefere(resultSet.getString("ClubPrefere"));
                arbitres.add(arbitre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(arbitres);
    }
}
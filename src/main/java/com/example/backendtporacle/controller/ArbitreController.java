package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.response.AffectationArbitreView;
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
    public ResponseEntity<List<AffectationArbitreView>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<AffectationArbitreView> arbitres = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Affectation_Arbitre_MV";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                AffectationArbitreView arbitre = new AffectationArbitreView();
                arbitre.setNom_Arbitre(resultSet.getString("Nom_Arbitre"));
                arbitre.setClub_A(resultSet.getString("Club_A"));
                arbitre.setClub_B(resultSet.getString("Club_B"));
                arbitre.setCodeMatch(resultSet.getString("CodeMatch"));
                arbitre.setStade(resultSet.getString("Stade"));
                arbitre.setDateMatch(resultSet.getDate("DateMatch"));
                arbitre.setHeure(resultSet.getTimestamp("Heure"));
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
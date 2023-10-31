package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.response.AffectationArbitreView;
import com.example.backendtporacle.datas.response.Arbitre;
import com.example.backendtporacle.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/arbitre")
public class ArbitreController {
     //    Ici on va faire un endpoint qui va nous permettre de recuperer tous les arbitres
    @GetMapping("/affectations")
    public ResponseEntity<List<AffectationArbitreView>> getAffectations(HttpServletRequest request) {
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

    @GetMapping("")
    public ResponseEntity<List<Arbitre>> getArbitres(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Arbitre> arbitres = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM arbitre";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Arbitre arbitre = new Arbitre();
                arbitre.setCode(resultSet.getString("Code"));
                arbitre.setNom(resultSet.getString("Nom"));
                arbitre.setPrenom(resultSet.getString("Prenom"));
                arbitre.setDateDeNaissance(resultSet.getDate("DateDeNaissance"));
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

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request,
                                          @RequestBody Arbitre arbitre) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String uuid = Utils.generateUUID();
            String sql =
                    "INSERT INTO arbitre" +  " (Code, Nom, Prenom, DateDeNaissance, Region, ClubPrefere) " +
                            " VALUES " +
                            "('"  + uuid + "', '" + arbitre.getNom() + "', '" + arbitre.getPrenom() + "', " + Utils.transformDate(arbitre.getDateDeNaissance()) + ", " + arbitre.getRegion() + ", '" + arbitre.getClubPrefere() + "')";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        } finally {
            // Fermez les ressources
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(true);
    }
}
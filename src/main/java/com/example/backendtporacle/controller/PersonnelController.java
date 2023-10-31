package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
import com.example.backendtporacle.datas.response.Personnel;
import com.example.backendtporacle.datas.response.Stade;
import com.example.backendtporacle.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/personnel")
public class PersonnelController {

    @GetMapping("")
    public ResponseEntity<List<?>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Personnel> personnels = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Personnel_" + region;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Personnel personnel = new Personnel();
                personnel.setCode(resultSet.getInt("Code"));
                personnel.setNom(resultSet.getString("Nom"));
                personnel.setPrenom(resultSet.getString("Prenom"));
                personnel.setDateDeNaissance(resultSet.getDate("DateDeNaissance"));
                personnel.setFonction(resultSet.getString("Fonction"));
                personnel.setRegion(resultSet.getInt("Region"));
                personnel.setVille(resultSet.getString("Ville"));
                personnels.add(personnel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(personnels);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request,
                                          @RequestBody Personnel personnel) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String uuid = Utils.generateRandomNumber();
            String sql =
                    "INSERT INTO Personnel_" + region +  " (Code, Nom, Prenom, DateDeNaissance, Fonction, Region, " +
                            "Ville) " +
                            " VALUES " +
                            "('"  + uuid + "', '" + personnel.getNom() + "', '" + personnel.getPrenom() + "', " + Utils.transformDate(personnel.getDateDeNaissance()) + ", '" + personnel.getFonction() + "', " + personnel.getRegion() + ", '" + personnel.getVille() + "')";
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

    @PutMapping("")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Personnel personnel) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE personnel_" + region + " SET Nom = '" + personnel.getNom() + "', Prenom = '" + personnel.getPrenom() + "', DateDeNaissance = " + Utils.transformDate(personnel.getDateDeNaissance()) + ", Fonction = '" + personnel.getFonction() + "', Region = " + personnel.getRegion() + ", Ville = '" + personnel.getVille() + "' WHERE Code = '" + personnel.getCode() + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(true);
    }

    @DeleteMapping("")
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody Personnel personnel) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "DELETE FROM personnel_" + region + " WHERE Code = '" + personnel.getCode() + "'";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(true);
    }
}

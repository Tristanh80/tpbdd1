package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
import com.example.backendtporacle.datas.response.Stade;
import com.example.backendtporacle.datas.response.StaffTechnique;
import com.example.backendtporacle.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.execution.Util;
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
@RequestMapping("/stafftechnique")
public class StaffTechniqueController {

    @GetMapping("")
    public ResponseEntity<List<?>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<StaffTechnique> staffTechniques = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM StaffTechnique_" + region;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                StaffTechnique staffTechnique = new StaffTechnique();
                staffTechnique.setCode(resultSet.getString("Code"));
                staffTechnique.setNom(resultSet.getString("Nom"));
                staffTechnique.setCodeClub(resultSet.getString("CodeClub"));
                staffTechnique.setFonction(resultSet.getString("Fonction"));
                staffTechniques.add(staffTechnique);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(staffTechniques);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request,
                                          @RequestBody StaffTechnique staffTechnique) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String random = Utils.generateRandomNumber();
            String sql =
                    "INSERT INTO StaffTechnique_" + region +  " (Code, Nom, CodeClub, Fonction) " +
                            " VALUES " +
                            "('"  + random + "', '" + staffTechnique.getNom() + "', '" + staffTechnique.getCodeClub() + "', '" + staffTechnique.getFonction() + "')";
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
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody StaffTechnique StaffTechnique) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE StaffTechnique_" + region + " SET Nom = '" + StaffTechnique.getNom() + "', CodeClub = '" + StaffTechnique.getCodeClub() + "', Fonction = '" + StaffTechnique.getFonction() + "' WHERE Code = '" + StaffTechnique.getCode() + "'";
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
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody StaffTechnique staffTechnique) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "DELETE FROM StaffTechnique_" + region + " WHERE Code = '" + staffTechnique.getCode() + "'";
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

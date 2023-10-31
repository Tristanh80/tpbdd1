package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.DirigeantRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
import com.example.backendtporacle.datas.response.Dirigeant;
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
@RequestMapping("/dirigeant")
public class DirigeantController {

    @GetMapping("")
    public ResponseEntity<List<Dirigeant>> getAll() {
        List<Dirigeant> dirigeants = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection("Nord", "Nord");
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Dirigeant_Nord";
            resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Dirigeant dirigeant = new Dirigeant();
                dirigeant.setCode(resultSet.getString("Code"));
                dirigeant.setNom(resultSet.getString("Nom"));
                dirigeant.setPrenom(resultSet.getString("Prenom"));
                dirigeant.setProfession(resultSet.getString("Profession"));
                dirigeants.add(dirigeant);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return ResponseEntity.ok(dirigeants);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request, @RequestBody DirigeantRequest dirigeant) {
        Connection connection = DatabaseConnection.getConnection("Nord", "Nord");
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String region = Utils.obtenirCookieRegion(request);
            String uuid = Utils.generateUUID();
            String sql =
                    "INSERT INTO Dirigeant_" + region + " VALUES ('" + uuid + "', '" + dirigeant.getNom() + "', '" + dirigeant.getPrenom() + "', '" + dirigeant.getProfession() + "')";
            resultSet = statement.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return ResponseEntity.ok(true);
    }

    @PutMapping("")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Dirigeant dirigeant) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE Dirigeant_" + region + " SET Nom = '" + dirigeant.getNom() + "', Prenom = '" + dirigeant.getPrenom() + "', Profession = '" + dirigeant.getProfession() + "' WHERE Code = '" + dirigeant.getCode() + "'";
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
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody Dirigeant dirigeant) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "DELETE FROM Dirigeant_" + region + " WHERE Code = '" + dirigeant.getCode() + "'";
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

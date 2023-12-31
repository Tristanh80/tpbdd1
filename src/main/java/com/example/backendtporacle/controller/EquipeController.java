package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
import com.example.backendtporacle.datas.response.Equipe;
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
@RequestMapping("/equipe")
public class EquipeController {
    @GetMapping("")
    public ResponseEntity<List<?>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Equipe> equipes = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Equipe_" + region;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(resultSet.getString("Id"));
                equipe.setCodeClub(resultSet.getString("CodeClub"));
                equipe.setCodeJoueur(resultSet.getString("CodeJoueur"));
                equipe.setDateDebutContrat(resultSet.getDate("DateDebutContrat"));
                equipe.setDateFinContrat(resultSet.getDate("DateFinContrat"));
                equipe.setNumeroMaillot(resultSet.getInt("NumeroMaillot"));
                equipe.setPoste(resultSet.getString("Poste"));
                equipes.add(equipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(equipes);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request,
                                          @RequestBody Equipe equipe) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String random = Utils.generateUUID();
            String sql =
                    "INSERT INTO Equipe_" + region +  " (id, CodeClub, CodeJoueur, DateDebutContrat, DateFinContrat, " +
                            "NumeroMaillot, Poste" +
                            ") " +
                            " VALUES " +
                            "('"  + random + "', '" + equipe.getCodeClub() + "', '" + equipe.getCodeJoueur() + "', " + Utils.transformDate(equipe.getDateDebutContrat()) + ", " + Utils.transformDate(equipe.getDateFinContrat()) + ", " + equipe.getNumeroMaillot() + ", '" + equipe.getPoste() + "')";
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
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Equipe equipe) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE equipe_" + region + " SET CodeClub = '" + equipe.getCodeClub() + "', CodeJoueur = '" + equipe.getCodeJoueur() + "', DateDebutContrat = " + Utils.transformDate(equipe.getDateDebutContrat()) + ", DateFinContrat = " + Utils.transformDate(equipe.getDateFinContrat()) + ", NumeroMaillot = " + equipe.getNumeroMaillot() + ", Poste = '" + equipe.getPoste() + "' WHERE Id = '" + equipe.getId() + "'";
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
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody Equipe equipe) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "DELETE FROM TATABLE_" + region + " WHERE Id = '" + equipe.getId() + "'";
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

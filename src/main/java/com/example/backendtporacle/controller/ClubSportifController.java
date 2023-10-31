package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
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

// Ici ca va etre tous les endpoints a faire pour le front end

// Il faut avoir des endpoints pour toutes les donnees a afficher sur le front end (Pour toutes les tables)

@RestController
@RequestMapping("/clubsportif")
public class ClubSportifController {
//    Ici on va faire un endpoint qui va nous permettre de recuperer tous les clubs sportifs
    @GetMapping("")
    public ResponseEntity<List<ClubSportif>> get(HttpServletRequest request) {
        List<ClubSportif> clubSportifs = new ArrayList<>();
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
                statement = connection.createStatement();
                String sql = "SELECT * FROM ClubSportif_" + region;
                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    // Traitez les résultats ici
                    ClubSportif clubSportif = new ClubSportif();
                    clubSportif.setCodeClub(resultSet.getString("CodeClub"));
                    clubSportif.setNomClub(resultSet.getString("NomClub"));
                    clubSportif.setDateCreation(resultSet.getDate("DateCreation"));
                    clubSportif.setDirigeant(resultSet.getString("Dirigeant"));
                    clubSportif.setVille(resultSet.getString("Ville"));
                    clubSportif.setRegion(resultSet.getInt("Region"));
                    clubSportifs.add(clubSportif);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Fermez les ressources
                DatabaseConnection.closeConnection(connection);
            }

        return ResponseEntity.ok(clubSportifs);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createClub(HttpServletRequest request,
                                                  @RequestBody ClubSportifRequest clubSportifRequest) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String uuid = Utils.generateUUID();
            String sql =
                    "INSERT INTO ClubSportif_" + region + " (CodeClub, NomClub, DateCreation, Dirigeant, Ville, " +
                            "Region) VALUES " +
                            "('" + uuid + "', '" + clubSportifRequest.getNomClub() + "', " + Utils.transformDate(clubSportifRequest.getDateCreation()) + ", '" + clubSportifRequest.getDirigeant() + "', '" + clubSportifRequest.getVille() + "', " + clubSportifRequest.getRegion() + ")";
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
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody ClubSportif clubSportifRequest) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE ClubSportif_" + region + " SET NomClub = '" + clubSportifRequest.getNomClub() + "' " +
                    "WHERE CodeClub = '" + clubSportifRequest.getCodeClub() + "'";
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
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody ClubSportif clubSportifRequest) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql =
                    "DELETE FROM ClubSportif_" + region + " WHERE CodeClub = '" + clubSportifRequest.getCodeClub() +
                            "'";
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

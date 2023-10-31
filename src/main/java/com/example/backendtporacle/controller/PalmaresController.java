package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
import com.example.backendtporacle.datas.response.Palmares;
import com.example.backendtporacle.datas.response.PalmaresView;
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
@RequestMapping("/palmares")
public class PalmaresController {

    @GetMapping("")
    public ResponseEntity<List<?>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<PalmaresView> palmares = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM palmares_" + region + "_MV";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                PalmaresView palmares_solo = new PalmaresView();
                palmares_solo.setPalmares_id(resultSet.getInt("Palmares_id"));
                palmares_solo.setNomClub(resultSet.getString("NomClub"));
                palmares_solo.setAnnee(resultSet.getInt("Annee"));
                palmares_solo.setTrophee(resultSet.getString("Trophee"));
                palmares_solo.setNbreMatchsGagnes(resultSet.getInt("NbreMatchsGagnes"));
                palmares_solo.setNbreMatchsPerdus(resultSet.getInt("NbreMatchsPerdus"));
                palmares_solo.setCodeClub(resultSet.getString("CodeClub"));
                palmares.add(palmares_solo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(palmares);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request,
                                          @RequestBody Palmares palmares) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String uuid = Utils.generateRandomNumber();
            String sql =
                    "INSERT INTO palmares" +  " (id, CodeClub, Annee, Trophee, NbreMatchsGagnes, " +
                            "NbreMatchsPerdus) " +
                            " VALUES " +
                            "('"  + uuid + "', '" + palmares.getCodeClub() + "', '" + palmares.getAnnee() + "', '" + palmares.getTrophee() + "', '" + palmares.getNbreMatchsGagnes() + "', '" + palmares.getNbreMatchsPerdus() + "')";
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
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Palmares palmares) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE palmares" + " SET CodeClub = '" + palmares.getCodeClub() + "', Annee = '" + palmares.getAnnee() + "', Trophee = '" + palmares.getTrophee() + "', NbreMatchsGagnes = '" + palmares.getNbreMatchsGagnes() + "', NbreMatchsPerdus = '" + palmares.getNbreMatchsPerdus() + "' " +
                    "WHERE id = " + palmares.getId();
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
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody Palmares palmares) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "DELETE FROM palmares" + " WHERE id = " + palmares.getId();
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

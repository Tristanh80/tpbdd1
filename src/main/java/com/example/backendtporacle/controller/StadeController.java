package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.request.StadeRequest;
import com.example.backendtporacle.datas.response.Stade;
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
@RequestMapping("/stade")
public class StadeController {

    @GetMapping("")
    public ResponseEntity<List<Stade>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Stade> stades = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Stade_" + region;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Stade stade = new Stade();
                stade.setCode(resultSet.getString("Code"));
                stade.setNom(resultSet.getString("Nom"));
                stade.setVille(resultSet.getString("Ville"));
                stade.setCapacite(resultSet.getInt("Capacite"));
                stade.setRegion(resultSet.getInt("Region"));
                stades.add(stade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(stades);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request,
                                          @RequestBody StadeRequest stadeRequest) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String uuid = Utils.generateUUID();
            String sql =
                    "INSERT INTO Stade_" + region +  " (Code, Nom, Ville, Region, Capacite) " +
                            " VALUES " +
                            "('"  + uuid + "', '" + stadeRequest.getNom() +  "', '" + stadeRequest.getVille() +"', " + stadeRequest.getRegion() +", " + stadeRequest.getCapacite() +")";
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

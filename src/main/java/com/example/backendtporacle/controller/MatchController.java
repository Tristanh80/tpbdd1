package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
import com.example.backendtporacle.datas.response.Match;
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
@RequestMapping("/match")
public class MatchController {

    @GetMapping("")
    public ResponseEntity<List<?>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Match> matchs = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM match_" + region;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Match match = new Match();
                match.setCodeMatch(resultSet.getString("CodeMatch"));
                match.setNbreButsClubA(resultSet.getInt("NbreButsClubA"));
                match.setNbreButsClubB(resultSet.getInt("NbreButsClubB"));
                match.setNbreSpectateurs(resultSet.getInt("NbreSpectateurs"));
                match.setCodeArbitre(resultSet.getString("CodeArbitre"));
                match.setCodeStade(resultSet.getString("CodeStade"));
                matchs.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(matchs);
    }

//    @PostMapping("")
//    public ResponseEntity<Boolean> create(HttpServletRequest request,
//                                          @RequestBody ClubSportifRequest clubSportifRequest) {
//        String region = Utils.obtenirCookieRegion(request);
//        Connection connection = DatabaseConnection.getConnection(region, region);
//
//        try {
//            Statement statement = null;
//            ResultSet resultSet = null;
//            statement = connection.createStatement();
//            String uuid = Utils.generateUUID();
//            String sql =
//                    "INSERT INTO TATABLE_" + region +  " (CodeClub, NomClub) " +
//                            " VALUES " +
//                            "('"  + uuid + "', '" + clubSportifRequest.getNomClub() +  ")";
//            statement.executeUpdate(sql);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return ResponseEntity.ok(false);
//        } finally {
//            // Fermez les ressources
//            DatabaseConnection.closeConnection(connection);
//        }
//        return ResponseEntity.ok(true);
//    }

    @PutMapping("")
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Match match) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE match_" + region + " SET NbreButsClubA = " + match.getNbreButsClubA() + ", NbreButsClubB = " + match.getNbreButsClubB() + ", NbreSpectateurs = " + match.getNbreSpectateurs() + ", CodeArbitre = '" + match.getCodeArbitre() + "', CodeStade = '" + match.getCodeStade() + "' WHERE CodeMatch = '" + match.getCodeMatch() + "'";
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
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody Match match) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "DELETE FROM match_" + region + " WHERE CodeMatch = '" + match.getCodeMatch() + "'";
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

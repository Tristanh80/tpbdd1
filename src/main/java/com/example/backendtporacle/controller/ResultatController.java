package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.response.CalendrierView;
import com.example.backendtporacle.datas.response.Resultats;
import com.example.backendtporacle.datas.response.Stade;
import com.example.backendtporacle.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/resultats")
public class ResultatController {

    @GetMapping("")
    public ResponseEntity<List<Resultats>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<CalendrierView> calendriers = new ArrayList<>();
        List<Resultats> resultats = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Calendrier_" + region + "_MV";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                CalendrierView calendrier = new CalendrierView();
                calendrier.setClubA_Nom(resultSet.getString("ClubA_Nom"));
                calendrier.setClubB_Nom(resultSet.getString("ClubB_Nom"));
                calendrier.setDateMatch(resultSet.getDate("DateMatch"));
                calendrier.setHeure(resultSet.getTimestamp("Heure"));
                calendrier.setStade(resultSet.getString("Stade"));
                calendrier.setCode_ClubB(resultSet.getString("Code_ClubB"));
                calendrier.setCode_ClubA(resultSet.getString("Code_ClubA"));
                calendrier.setCalendrier_id(resultSet.getInt("Calendrier_id"));
                calendrier.setCodeMatch(resultSet.getString("CodeMatch"));
                calendriers.add(calendrier);
            }
            sql = "SELECT * FROM Match_" + region;
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Resultats resultat = new Resultats();
                resultat.setCodeMatch(resultSet.getString("CodeMatch"));
                resultat.setScoreA(resultSet.getInt("NbreButsClubA"));
                resultat.setScoreB(resultSet.getInt("NbreButsClubB"));
                resultat.setNbreSpectateurs(resultSet.getInt("NbreSpectateurs"));
                resultats.add(resultat);
            }
            for (Resultats resultat : resultats) {
                for (CalendrierView calendrier : calendriers) {
                    if (calendrier.getCodeMatch().equals(resultat.getCodeMatch())) {
                        resultat.setHeure(calendrier.getHeure());
                        resultat.setDateMatch(calendrier.getDateMatch());
                        resultat.setStade(calendrier.getStade());
                        resultat.setClubA_Nom(calendrier.getClubA_Nom());
                        resultat.setClubB_Nom(calendrier.getClubB_Nom());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return ResponseEntity.ok(resultats);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Resultats>> getAll(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<CalendrierView> calendriers = new ArrayList<>();
        List<Resultats> resultats = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Calendrier_All_MV";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                CalendrierView calendrier = new CalendrierView();
                calendrier.setClubA_Nom(resultSet.getString("ClubA_Nom"));
                calendrier.setClubB_Nom(resultSet.getString("ClubB_Nom"));
                calendrier.setDateMatch(resultSet.getDate("DateMatch"));
                calendrier.setHeure(resultSet.getTimestamp("Heure"));
                calendrier.setStade(resultSet.getString("Stade"));
                calendrier.setCode_ClubB(resultSet.getString("Code_ClubB"));
                calendrier.setCode_ClubA(resultSet.getString("Code_ClubA"));
                calendrier.setCalendrier_id(resultSet.getInt("Calendrier_id"));
                calendrier.setCodeMatch(resultSet.getString("CodeMatch"));
                calendriers.add(calendrier);
            }
            sql = "SELECT * FROM Matchcentral";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Resultats resultat = new Resultats();
                resultat.setCodeMatch(resultSet.getString("CodeMatch"));
                resultat.setScoreA(resultSet.getInt("NbreButsClubA"));
                resultat.setScoreB(resultSet.getInt("NbreButsClubB"));
                resultat.setNbreSpectateurs(resultSet.getInt("NbreSpectateurs"));
                resultats.add(resultat);
            }
            for (Resultats resultat : resultats) {
                for (CalendrierView calendrier : calendriers) {
                    if (calendrier.getCodeMatch().equals(resultat.getCodeMatch())) {
                        resultat.setHeure(calendrier.getHeure());
                        resultat.setDateMatch(calendrier.getDateMatch());
                        resultat.setStade(calendrier.getStade());
                        resultat.setClubA_Nom(calendrier.getClubA_Nom());
                        resultat.setClubB_Nom(calendrier.getClubB_Nom());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return ResponseEntity.ok(resultats);
    }

}

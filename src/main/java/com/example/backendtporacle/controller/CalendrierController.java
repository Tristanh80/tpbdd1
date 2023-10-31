package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.response.Calendrier;
import com.example.backendtporacle.datas.response.CalendrierView;
import com.example.backendtporacle.util.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/calendrier")
public class CalendrierController {
     //    Ici on va faire un endpoint qui va nous permettre de recuperer tous les calendriers
    @GetMapping("")
    public ResponseEntity<List<CalendrierView>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<CalendrierView> calendriers = new ArrayList<>();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(calendriers);
    }
}

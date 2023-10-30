package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.ClubSportif;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
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
    public ResponseEntity<List<ClubSportif>> get() {
        List<ClubSportif> clubSportifs = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection("Nord", "Nord");
        try {
            Statement statement = null;
            ResultSet resultSet = null;
                statement = connection.createStatement();
                String sql = "SELECT * FROM ClubSportifCentral";
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
}

package com.example.backendtporacle.controller;

import com.example.backendtporacle.databaseconnection.DatabaseConnection;
import com.example.backendtporacle.datas.request.ClubSportifRequest;
import com.example.backendtporacle.datas.response.ClubSportif;
import com.example.backendtporacle.datas.response.Joueur;
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
@RequestMapping("/joueur")
public class JoueurController {

    @GetMapping("")
    public ResponseEntity<List<?>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Joueur> joueurs = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Joueur";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Joueur joueur = new Joueur();
                joueur.setCode(resultSet.getString("Code"));
                joueur.setNom(resultSet.getString("Nom"));
                joueur.setPrenom(resultSet.getString("Prenom"));
                joueur.setDateNaissance(resultSet.getDate("DateNaissance"));
                joueur.setNationalite(resultSet.getString("Nationnalite"));
                joueur.setPoids(resultSet.getFloat("Poids"));
                joueur.setTaille(resultSet.getFloat("Taille"));
                joueur.setClasse(resultSet.getString("Classe"));
                joueurs.add(joueur);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(joueurs);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> create(HttpServletRequest request,
                                          @RequestBody Joueur joueur) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);

        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String uuid = Utils.generateUUID();
            String sql =
                    "INSERT INTO Joueur" +  " (Code, Nom, Prenom, DateNaissance, Nationnalite, Poids, Taille, Classe)" +
                            " " +
                            "VALUES ('" + uuid + "', '" + joueur.getNom() + "', '" + joueur.getPrenom() + "', " + Utils.transformDate(joueur.getDateNaissance()) + ", '" + joueur.getNationalite() + "', " + joueur.getPoids() + ", " + joueur.getTaille() + ", '" + joueur.getClasse() + "')";
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
    public ResponseEntity<?> update(HttpServletRequest request, @RequestBody Joueur joueur) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "UPDATE Joueur" + " SET Nom = '" + joueur.getNom() + "', Prenom = '" + joueur.getPrenom() + "', DateNaissance = " + Utils.transformDate(joueur.getDateNaissance()) + ", Nationnalite = '" + joueur.getNationalite() + "', Poids = " + joueur.getPoids() + ", Taille = " + joueur.getTaille() + ", Classe = '" + joueur.getClasse() + "' WHERE Code = '" + joueur.getCode() + "'";
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
    public ResponseEntity<?> delete(HttpServletRequest request, @RequestBody Joueur joueur) {
        String region = Utils.obtenirCookieRegion(request);
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "DELETE FROM Joueur" + " WHERE Code = '" + joueur.getCode() + "'";
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

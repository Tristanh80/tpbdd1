package com.example.backendtporacle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arbitre")
public class ArbitreController {
     //    Ici on va faire un endpoint qui va nous permettre de recuperer tous les arbitres
    @GetMapping("/listeArbitres")
    public ResponseEntity<List<Arbitre>> get(HttpServletRequest request) {
        String region = Utils.obtenirCookieRegion(request);
        List<Arbitre> arbitres = new ArrayList<>();
        Connection connection = DatabaseConnection.getConnection(region, region);
        try {
            Statement statement = null;
            ResultSet resultSet = null;
            statement = connection.createStatement();
            String sql = "SELECT * FROM Arbitre";
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Arbitre arbitre = new Arbitre();
                arbitre.setCodeClub(resultSet.getString("Code"));
                arbitre.setNomClub(resultSet.getString("Nom"));
                arbitre.setDateCreation(resultSet.getDate("Prenom"));
                arbitre.setDirigeant(resultSet.getString("DateNaissance"));
                arbitre.setVille(resultSet.getString("Region"));
                arbitre.setRegion(resultSet.getInt("ClubPrefere"));
                arbitres.add(arbitre);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return ResponseEntity.ok(arbitres);
    }
}
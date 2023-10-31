package com.example.backendtporacle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arbitre")
public class ArbitreController {
     //    Ici on va faire un endpoint qui va nous permettre de recuperer tous les arbitres
    @GetMapping("/listeArbitres")
    public ResponseEntity<List<Arbitre>> get() {
        List<Arbitre> arbitres = new ArrayList<>();
        Connection connection = null;
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String username = "Nord";
        String password = "Nord";
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful");
            Statement statement = null;
            ResultSet resultSet = null;

            try {
                statement = connection.createStatement();
                String sql = "SELECT * FROM Arbitre";
                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    // Traitez les résultats ici
                    Arbitre arbitre = new Arbitre();
                    arbitre.setCodeClub(resultSet.getString("Code"));
                    arbitre.setNomClub(resultSet.getString("Nom"));
                    arbitre.setDateCreation(resultSet.getDate("Prenom"));
                    arbitre.setDirigeant(resultSet.getString("DateNaissance"));
                    arbitre.setVille(resultSet.getString("Region"));
                    arbitre.setRegion(resultSet.getInt("ClubPrefere"));
                    arbitre.add(arbitres);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                // Fermez les ressources
                try {
                    if (resultSet != null) resultSet.close();
                    if (statement != null) statement.close();
                    if (connection != null) connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println("Connection unsuccessful");
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ResponseEntity.ok(arbitres);
    }
}
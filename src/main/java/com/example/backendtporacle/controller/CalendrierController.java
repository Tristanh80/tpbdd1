package com.example.backendtporacle.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calendrier")
public class CalendrierController {
     //    Ici on va faire un endpoint qui va nous permettre de recuperer tous les calendriers
    @GetMapping("/listeCalendriers")
    public ResponseEntity<List<Calendrier>> get() {
        List<Calendrier> calendriers = new ArrayList<>();
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
                String sql = "SELECT * FROM Calendrier_Nord_MV";
                resultSet = statement.executeQuery(sql);

                while (resultSet.next()) {
                    // Traitez les résultats ici
                    Calendrier calendrier = new Calendrier();
                    calendrier.setCodeClub(resultSet.getString("id"));
                    calendrier.setNomClub(resultSet.getString("CodeMatch"));
                    calendrier.setDateCreation(resultSet.getDate("DateMatch"));
                    calendrier.setDirigeant(resultSet.getString("Heure"));
                    calendrier.setVille(resultSet.getString("ClubA"));
                    calendrier.setRegion(resultSet.getInt("ClubB"));
                    calendrier.setRegion(resultSet.getInt("Stade"));
                    calendrier.add(calendriers);
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
        return ResponseEntity.ok(calendriers);
    }
}

package P2;

import P2.DAO.AdresDAO;
import P2.DAO.AdresDAOsql;
import P2.DAO.ReizigerDAO;
import P2.DAO.ReizigerDAOsql;
import P2.Domain.Adres;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:postgresql://127.0.0.1:5432/P1DataBase";
        Connection conn = DriverManager.getConnection(url,"postgres","1234");
        ReizigerDAO RDAO = new ReizigerDAOsql(conn);
        testReizigerDAO(RDAO);
        AdresDAO ADAO = new AdresDAOsql(conn);
        testAdresDAO(ADAO);
    }

    private void getConnection(){
    }

    private void closeConnection(){
    }

    private static void testReizigerDAO(ReizigerDAO dao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = dao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        dao.save(sietske);
        reizigers = dao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }

    private static void testAdresDAO(AdresDAO dao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle reizigers op uit de database
        List<Adres> adresList = dao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres r : adresList) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sumbo = new Reiziger(88, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres    cookie  = new Adres   (88,"7777XD", "77ABC", "cookieStraat","SESAME",sumbo);
        System.out.print("[Test] Eerst " + adresList.size() + " adresList, na AdresDAO.save() ");
        dao.save(cookie);
        adresList = dao.findAll();
        System.out.println(adresList.size() + " adresList\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
    }
}

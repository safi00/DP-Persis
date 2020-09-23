package P2;

import P2.DAO.*;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ReizigerMain {
    public static void main(String[] args) throws SQLException {
//        String url = "jdbc:postgresql://127.0.0.1:5432/P1DataBase";
        String url = "jdbc:postgresql://127.0.0.1:5432/ovchip";
        Connection conn = DriverManager.getConnection(url,"postgres","1234");
        ReizigerDAO DAO = new ReizigerDAOsql(conn);
        testReizigerDAO(DAO);
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
        Reiziger deleteTest = new Reiziger(207, "S", "", "Deleted", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        dao.save(sietske);
        dao.save(deleteTest);
        dao.delete(deleteTest);
        reizigers = dao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("update test");
        Reiziger updateTest = new Reiziger(77, "S", "DE", "Boers", java.sql.Date.valueOf(gbdatum));
        dao.update(updateTest);
        System.out.println("update checking if it worked:");
        System.out.println("finding the reiziger with ID #" + updateTest.getIdNummer() + ": " + dao.findById(updateTest.getIdNummer()));
        System.out.println("delete test:");
        System.out.println(dao.findByGbdatum(gbdatum));
        dao.delete(deleteTest);
        System.out.println(reizigers.size() + " reizigers\n");
    }
}
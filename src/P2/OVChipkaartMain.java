package P2;

import P2.DAO.*;
import P2.Domain.OVChipkaart;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class OVChipkaartMain {
    public static void main(String[] args) throws SQLException {
//        String url = "jdbc:postgresql://127.0.0.1:5432/P1DataBase";
        String url = "jdbc:postgresql://127.0.0.1:5432/ovchip";
        Connection conn = DriverManager.getConnection(url,"postgres","1234");
        OVChipkaartDAO DAO = new OVChipkaartDAOsql(conn);
        testOVChipkaartDAO(DAO);
    }
    private static void testOVChipkaartDAO(OVChipkaartDAO dao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle kaarten op uit de database
        List<OVChipkaart> kaartList = dao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:");
        for (OVChipkaart r : kaartList) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        String gtot = "2025-03-14";
        Reiziger testreiz = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        OVChipkaart testkaart  = new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 2, 100.50,testreiz);
        System.out.print("[Test] Eerst " + kaartList.size() + " kaartList, na ovChipkaartDAO.save() \n");
        dao.save(testkaart);
        kaartList = dao.findAll();
        System.out.println(kaartList.size() + " kaarten in de List\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("update test");
        dao.update(new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 1, 155.50,testreiz));
        System.out.println("update checking if it worked:");
        System.out.println("finding the kaartnummer with ID #" + testkaart.getKaart_nummer() + ":\n" + dao.findById(testkaart.getKaart_nummer()));
        System.out.println();
        System.out.println("delete test:");
        System.out.println("alle kaarten van "+ testreiz.getNaam() + "\n:" + dao.findByReiziger(testreiz));
        System.out.println();
        System.out.println("Deleting :\n" + dao.findByReiziger(testreiz));
        dao.delete(testkaart);
    }
}

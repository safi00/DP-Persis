package P2;

import P2.DAO.*;
import P2.Domain.Adres;
import P2.Domain.OVChipkaart;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
//        String url = "jdbc:postgresql://127.0.0.1:5432/P1DataBase";
        String url = "jdbc:postgresql://127.0.0.1:5432/ovchip";
        Connection conn = DriverManager.getConnection(url,"postgres","1234");
        ReizigerDAO RDAO = new ReizigerDAOsql(conn);
        testReizigerDAO(RDAO);
        AdresDAO ADAO = new AdresDAOsql(conn);
        testAdresDAO(ADAO);
        OVChipkaartDAO OVDAO = new OVChipkaartDAOsql(conn);
        testOVChipkaartDAO(OVDAO);
//        ProductDAO pDAO = new OVChipkaartDAOsql(conn);
//        testProductDAO(pDAO);
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

    private static void testAdresDAO(AdresDAO dao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adresList = dao.findAll();
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres r : adresList) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger testReiz = new Reiziger(77, "S", "DE", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres    testadres  = new Adres   (88,"7777XD", "77ABC", "cookieStraat","SESAME",testReiz);
        System.out.print("[Test] Eerst " + adresList.size() + " adresList, na AdresDAO.save() ");
        dao.save(testadres);
        adresList = dao.findAll();
        System.out.println(adresList.size() + " adresList\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("update test");
        dao.update(new Adres(88,"7888XD", "77ABC", "cookieStraat","SESAME",testReiz));
        System.out.println("update checking if it worked:");
        System.out.println("finding the Adres with ID #" + testadres.getAdresID() + ":\n" + dao.findById(testadres.getAdresID()));
        System.out.println();
        System.out.println("delete test:");
        System.out.println(dao.findByWoonplaats(testadres.getWoonplaats()));
        System.out.println();
        System.out.println("Deleting :\n" + dao.findByReiziger(testReiz));
        dao.delete(testadres);
        System.out.println(adresList.size() + " adresList\n");
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
        Reiziger testReiz = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        OVChipkaart testkaart  = new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 2, 100.50,testReiz);
        System.out.print("[Test] Eerst " + kaartList.size() + " kaartList, na ovChipkaartDAO.save() \n");
        dao.save(testkaart);
        kaartList = dao.findAll();
        System.out.println(kaartList.size() + " kaarten in de List\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("update test");
        dao.update(new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 1, 155.50,testReiz));
        System.out.println("update checking if it worked:");
        System.out.println("finding the kaartnummer with ID #" + testkaart.getKaart_nummer() + ":\n" + dao.findById(testkaart.getKaart_nummer()));
        System.out.println();
        System.out.println("delete test:");
        System.out.println("alle kaarten van "+ testReiz.getNaam() + "\n:" + dao.findByReiziger(testReiz));
        System.out.println();
        System.out.println("Deleting :\n" + dao.findByReiziger(testReiz));
        dao.delete(testkaart);
    }
}

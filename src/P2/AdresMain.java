package P2;

import P2.DAO.*;
import P2.Domain.Adres;
import P2.Domain.OVChipkaart;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class AdresMain {
    public static void main(String[] args) throws SQLException {
//        String url = "jdbc:postgresql://127.0.0.1:5432/P1DataBase";
        String url = "jdbc:postgresql://127.0.0.1:5432/ovchip";
        Connection conn = DriverManager.getConnection(url,"postgres","1234");
        AdresDAO DAO = new AdresDAOsql(conn);
        testAdresDAO(DAO);
    }

    private static void testAdresDAO(AdresDAO dao) throws SQLException {
        System.out.println("\n----------- Test AdresDAO --------------");

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
        Reiziger testreiz = new Reiziger(77, "S", "DE", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres testadres = new Adres(88, "7777XD", "77ABC", "cookieStraat", "SESAME", testreiz);
        System.out.print("[Test] Eerst " + adresList.size() + " adresList, na AdresDAO.save() ");
        dao.save(testadres);
        adresList = dao.findAll();
        System.out.println(adresList.size() + " adresList\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("update test");
        dao.update(new Adres(88, "7888XD", "77ABC", "cookieStraat", "SESAME", testreiz));
        System.out.println("update checking if it worked:");
        System.out.println("finding the Adres with ID #" + testadres.getAdresID() + ":\n" + dao.findById(testadres.getAdresID()));
        System.out.println();
        System.out.println("delete test:");
        System.out.println(dao.findByWoonplaats(testadres.getWoonplaats()));
        System.out.println();
        System.out.println("Deleting :\n" + dao.findByReiziger(testreiz));
        dao.delete(testadres);
        System.out.println(adresList.size() + " adresList\n");
    }
}

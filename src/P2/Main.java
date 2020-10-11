package P2;

import P2.DAO.*;
import P2.Domain.Adres;
import P2.Domain.OVChipkaart;
import P2.Domain.Product;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    private static Connection conn;

    public static void main(String[] args) {
        try {
            // Dao's
            ReizigerDAOsql     RDAO = new ReizigerDAOsql(getConnection());
            AdresDAOsql        ADAO = new AdresDAOsql(getConnection());
            OVChipkaartDAOsql  ODAO = new OVChipkaartDAOsql(getConnection());
            ProductDAOsql      PDAO = new ProductDAOsql(getConnection());

            // Daos relations
            RDAO.setAdresDao(ADAO);
            RDAO.setOVCDao  (ODAO);
            ADAO.setRdao    (RDAO);
            ODAO.setRdao    (RDAO);
            ODAO.setPdao    (PDAO);
            PDAO.setOVCDao  (ODAO);

            //tests
            testReizigerDAO(RDAO);
            testAdresDAO(ADAO);
            testOVChipkaartDAO(ODAO);
            testProductDAO(PDAO);
        }catch (Exception e){
            e.printStackTrace();
            closeConnection();
        }
    }

    private static Connection getConnection(){
        String url = "jdbc:postgresql://localhost:5432/ovchip";
        try {
            conn = DriverManager.getConnection(url, "postgres", "1234");
        } catch (SQLException e){
            System.err.println("Bad Code Alert!\n" + e.getMessage());
        }
        return conn;
    }

    private static void closeConnection(){
        try {
            conn.close();
        }catch (SQLException e){
            System.err.println("Bad Code Alert!\n" + e.getMessage());
        }
    }

    private static void testReizigerDAO(ReizigerDAO dao) throws SQLException {
        System.out.println("---------- Test ReizigerDAO -------------");
        System.out.println();

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = dao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(177, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        Reiziger deleteTest = new Reiziger(207, "S", "", "Deleted", java.sql.Date.valueOf(gbdatum));
        dao.delete(deleteTest);
        dao.delete(sietske);
        System.out.println();
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, Before saving and deleting \n");
        System.out.println();
        dao.delete(sietske);
        System.out.println("[Test] " + dao.findAll().size() + " reizigers\n");
        dao.save(sietske);
        System.out.println("[Test] " + dao.findAll().size() + " reizigers\n");
        dao.save(deleteTest);
        System.out.println("[Test] " + dao.findAll().size() + " reizigers\n");
        dao.delete(deleteTest);
        System.out.println("[Test] " + dao.findAll().size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("[Test] update & find by ID\n");
        Reiziger updateTest    = new Reiziger(77, "G", "Van", "algemeen", java.sql.Date.valueOf(gbdatum));
        Reiziger beforeTheTest = dao.findById(updateTest.getIdNummer());
        System.out.println("finding the reiziger with ID #" + updateTest.getIdNummer() + ":\n" + dao.findById(updateTest.getIdNummer()));
        dao.update(updateTest);
        System.out.println("\nupdated to:");
        System.out.println(dao.findById(updateTest.getIdNummer()));
        System.out.println("reversing update by updating again");
        dao.update(beforeTheTest);
        System.out.println("\n[Test] find by Gbdatum");
        List<Reiziger> reizGBD = dao.findByGbdatum(gbdatum);
        for (Reiziger rgbd : reizGBD) {
            System.out.println(rgbd);
        }
        System.out.println(dao.findAll().size() + " reizigers");

        Reiziger testReiziger = new Reiziger(777,"T","EST","connDELETE", java.sql.Date.valueOf("1999-03-14"));
        Adres    testResAdres = new Adres   (88,"7777XD", "77ABC", "cookieStraat","SESAME",testReiziger);
        testReiziger.setHuisadres(testResAdres);
//        dao.save(testReiziger);
//        dao.delete(dao.findById(777));
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
        Reiziger testReiz   = new Reiziger(77, "S", "DE", "Boers", java.sql.Date.valueOf(gbdatum));
        Adres    testadres  = new Adres   (888,"7777XD", "77ABC", "cookieStraat","SESAME",testReiz);
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
        System.out.println("delete adres test:");
        System.out.println(dao.findByWoonplaats(testadres.getWoonplaats()));
        System.out.println();
        System.out.println("Deleting :\n" + dao.findByReiziger(testReiz));
        dao.delete(testadres);
        System.out.println(adresList.size() + " adresList\n");

//        Reiziger testReiziger = new Reiziger(777,"T","EST","connDELETE", java.sql.Date.valueOf("1999-03-14"));
//        Adres    testResAdres = new Adres   (88,"7777XD", "77ABC", "cookieStraat","SESAME",testReiziger);
//        dao.save(testResAdres);
    }

    private static void testOVChipkaartDAO(OVChipkaartDAO dao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle kaarten op uit de database
        List<OVChipkaart> kaartList = dao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:\n");
        for (OVChipkaart r : kaartList) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        String gtot = "2025-03-14";
        Reiziger testReiz = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        OVChipkaart testkaart   = new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 2, 100.50,testReiz);
        System.out.print("[Test] Eerst " + kaartList.size() + " kaartList, na ovChipkaartDAO.save() \n");
        dao.delete(testkaart);
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


//        Reiziger testReiziger = new Reiziger(777,"T","EST","connDELETE", java.sql.Date.valueOf("1999-03-14"));
//        OVChipkaart testkaart1  = new OVChipkaart (344445555, java.sql.Date.valueOf("2025-05-10"), 2, 100.50,testReiziger);
//        OVChipkaart testkaart2  = new OVChipkaart (455556666, java.sql.Date.valueOf("2025-05-22"), 1, 44.50,testReiziger);
//        OVChipkaart testkaart3  = new OVChipkaart (566667777, java.sql.Date.valueOf("2025-05-28"), 2, 27.66,testReiziger);
//        dao.save(testkaart1);
//        dao.save(testkaart2);
//        dao.save(testkaart3);
    }

    private static void testProductDAO(ProductDAO dao) throws SQLException {
        System.out.println("\n---------- Test ProductDAO -------------\n");

        // Haal alle kaarten op uit de database
        List<Product> productenlist = dao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:\n");
        for (Product r : productenlist) {
            System.out.println(r);
            System.out.println();
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        String gtot = "2025-03-14";

        Reiziger    testReiz     = new Reiziger(87, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        OVChipkaart testkaart    = new OVChipkaart (233334444, java.sql.Date.valueOf(gtot), 2, 100.50,testReiz);
        Product     testproduct  = new Product(22,"peanutbutter", "snack on the train",2.50);
        Product     testproduct2 = new Product(66,"jelly","snack on the train",0.50);

        System.out.print("[Test] Eerst " + productenlist.size() + " productenlist, na ovChipkaartDAO.save() \n");

        dao.delete(testproduct);
        dao.delete(testproduct2);

        dao.save(testproduct);
        dao.save(testproduct2);

        testkaart.addOVProduct(testproduct);
        testkaart.addOVProduct(testproduct2);

//        Reiziger x = new Reiziger(777,"T","EST","connDELETE", java.sql.Date.valueOf(gbdatum));
//        OVChipkaart testkaart1  = new OVChipkaart (344445555, java.sql.Date.valueOf(gtot), 2, 100.50,x);
//        OVChipkaart testkaart2  = new OVChipkaart (455556666, java.sql.Date.valueOf(gtot), 1, 44.50,x);
//        OVChipkaart testkaart3  = new OVChipkaart (566667777, java.sql.Date.valueOf(gtot), 2, 27.66,x);

        dao.addOVChipkaart(testkaart,  testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
        dao.addOVChipkaart(testkaart,  testproduct2,  "actief",java.sql.Date.valueOf(gbdatum));
//        dao.addOVChipkaart(testkaart1, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//        dao.addOVChipkaart(testkaart2, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//        dao.addOVChipkaart(testkaart3, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//        dao.addOVChipkaart(testkaart2, testproduct2, "actief",java.sql.Date.valueOf(gbdatum));

        productenlist = dao.findAll();
        System.out.println();
        System.out.println(productenlist.size() + " kaarten in de List\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.
        System.out.println("[Test] update test\n");

        System.out.println("ovID #" + testkaart.getKaart_nummer() + " products before the update:");
        for (Product r : dao.findByOVChipKaart(testkaart.getKaart_nummer())) {
            System.out.println(r);
        }

        dao.update(new Product(22,"chocola","snack after the train ride",1.25));
        dao.addOVChipkaart(testkaart,  testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
        System.out.println("\nchecking if the update worked:\n");
        System.out.println("ovID #" + testkaart.getKaart_nummer() + " products after the update:");

        for (Product r : dao.findByOVChipKaart(testkaart.getKaart_nummer())) {
            System.out.println(r);
        }
        System.out.println();
        System.out.println("delete test: " + testproduct2);
        dao.delete(testproduct2);

        System.out.println("checking if product " + testproduct2.getNaam() + " that belongs to ovID #" + testkaart.getKaart_nummer() + "\nis still in the list:\n");

        for (Product r : dao.findByOVChipKaart(testkaart.getKaart_nummer())) {
            System.out.println(r);
            System.out.println();
        }

        System.out.println("alle producten van "+ testkaart.getKaart_nummer() + ":");

        List<Product> productentestlist = dao.findByOVChipKaart(testkaart.getKaart_nummer());

        for (Product r : productentestlist) {
            System.out.println(r);
        }
        System.out.println();
        System.out.println("Deleting test: " + testproduct);
        dao.delete(testproduct);
    }
}

//    INSERT INTO adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (6,'2755XD','7C','Chickenstraat','Den Haag',777);

//  dao.addOVChipkaart(testkaart1, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//          dao.addOVChipkaart(testkaart2, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//          dao.addOVChipkaart(testkaart3, testproduct,  "actief",java.sql.Date.valueOf(gbdatum));
//          dao.addOVChipkaart(testkaart2, testproduct2, "actief",java.sql.Date.valueOf(gbdatum));



//    select r.reiziger_id, op.*, p.*
//        from reiziger r
//        left join ov_chipkaart o on r.reiziger_id = o.reiziger_id
//        left join ov_chipkaart_product op on o.kaart_nummer = op.kaart_nummer
//        left join product p on op.product_nummer = p.product_nummer
//        order by r.reiziger_id
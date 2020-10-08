package P2.Tests;

import P2.Domain.Adres;
import P2.Domain.OVChipkaart;
import P2.Domain.Product;
import P2.Domain.Reiziger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

class DomainTest {
    private Reiziger    testReiziger;
    private Adres       testAdres;
    private OVChipkaart testOVCK1;
    private OVChipkaart testOVCK2;
    private Product     testProduct;

    @BeforeEach
    public void init(){
        testReiziger = new Reiziger(0, "X","","Martina", Date.valueOf("1997-10-27"));

        testAdres    = new Adres(0,"","","","",testReiziger);

        testOVCK1     = new OVChipkaart(233334444, java.sql.Date.valueOf("2030-06-17"), 0, 0.50, testReiziger);
        testOVCK2     = new OVChipkaart(233334445, java.sql.Date.valueOf("2030-06-17"), 1, 27.50, testReiziger);

        testProduct  = new Product(12345,"","",10.50);

        testAdres.setAdresID(37);
        testAdres.setPostcode("4206XD");
        testAdres.setHuisnummer("37");
        testAdres.setStraat("CookieStraat");
        testAdres.setWoonplaats("Cookie Town");
        testAdres.setReizigerID(testReiziger);

        testOVCK1.setGeldig_tot(java.sql.Date.valueOf("2025-03-14"));
        testOVCK1.setKlasse(2);
        testOVCK1.setSaldo(100.50);

        testReiziger.setHuisadres(testAdres);

        testProduct.setNaam("Product");
        testProduct.setBeschrijving("ProductBeschrijving");
        testProduct.setPrijs(10.50);
    }

    @Test
    public void TestReiziger(){
        assertEquals(testReiziger.toString(), "Reiziger{#0 X  Martina, geb.1997-10-27} " +
                "\nAdres {#37 4206XD Cookie Town-37} " +
                "\nOVKaarten { deze reiziger heeft geen OVChipKaarten. }");
    }

    @Test
    public void TestReizigerSetID(){
        testReiziger.setIdNummer(1);
        assertEquals(testReiziger.getIdNummer(), 1);
    }

    @Test
    public void TestReizigerSetVletters(){
        testReiziger.setVoorletters("X.R.A");
        assertEquals(testReiziger.getVoorletters(), "X.R.A");
    }

    @Test
    public void TestReizigerSetTussenvoegsel(){
        testReiziger.setTussenvoegsel("de");
        assertEquals(testReiziger.getTussenvoegsel(), "de");
    }

    @Test
    public void TestReizigerSetAchternaam(){
        testReiziger.setAchternaam("Jong");
        assertEquals(testReiziger.getAchternaam(), "Jong");
    }

    @Test
    public void TestReizigerSetGeboortedatum(){
        testReiziger.setGeboortedatum("1420-10-27");
        assertEquals(testReiziger.getGeboortedatum().toString(), "1420-10-27");
    }

    @Test
    public void TestReizigerNaam(){
        assertEquals(testReiziger.getNaam(), "X  Martina");
    }

    @Test
    public void TestAdres(){
        assertEquals(testAdres.toString(), "Adres {adresID: 37, 4206XD, CookieStraat #37, woonplaats: Cookie Town,\n" +
                "reiziger: #0 X  Martina}");
    }

    @Test
    public void TestGetAdres(){
        assertEquals(testReiziger.getHuisAdres().toString(), "Adres {adresID: 37, 4206XD, CookieStraat #37, woonplaats: Cookie Town,\n" +
                "reiziger: #0 X  Martina}");
    }

    @Test
    public void TestReizigerZonderOVKaart(){
        assertEquals(testReiziger.getOVKaarten().toString(),"[]");
    }

    @Test
    public void TestReizigerMetOVKaarten(){
        testReiziger.addOVKaart(testOVCK1);
        testReiziger.addOVKaart(testOVCK2);
        assertEquals(testReiziger.getOVKaarten().toString(),"[OVChipkaart{kaart #233334444, is geldig tot: 2025-03-14 2de  klass , met een saldo van €100.5,\n" +
                "deze kaart is van reiziger: #0 X  Martina}, OVChipkaart{kaart #233334445, is geldig tot: 2030-06-17 1ste klass , met een saldo van €27.5,\n" +
                "deze kaart is van reiziger: #0 X  Martina}]");
    }

    @Test
    public void TestProduct(){
        assertEquals(testProduct.toString(),"Product{#12345, Product, ProductBeschrijving, €10.5}");
    }
}
package P2.Tests;

import P2.Domain.Adres;
import P2.Domain.Reiziger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DomainTest {
    private Reiziger testReiziger;
    private Adres testAdres;

    @BeforeEach
    public void init(){
        testReiziger = new Reiziger(0, "X","","Martina","1997-10-27");
        testAdres = new Adres();
        testAdres.setAdresID(37);
        testAdres.setPostcode("4206XD");
        testAdres.setHuisnummer("37");
        testAdres.setStraat("cookiestraat");
        testAdres.setWoonplaats("Cookie Town");
        testAdres.setReizigerID(testReiziger);
    }

    @Test
    public void TestReiziger(){
        assertEquals(testReiziger.toString(), "De Reiziger id: 0.\n" +
                "De Reiziger's voorletters: X.\n" +
                "De Reiziger's tussenvoegsel: .\n" +
                "De Reiziger's lastname: Martina.\n" +
                "De Reiziger was born on: 1997-10-27");
    }

    @Test
    public void TestReizigerSetID(){
        testReiziger.setIdnummer(1);
        assertEquals(testReiziger.getIdnummer(), 1);
    }

    @Test
    public void TestReizigerSetVletters(){
        testReiziger.setVoorletters("X.R.A");
        assertEquals(testReiziger.getVoorletters(), "X.R.A");
    }

    @Test
    public void TestReizigerSetTuseenvoegsel(){
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
        assertEquals(testAdres.toString(), "Adres{" + "adresID: " + testAdres.getAdresID() +
                ",\n postcode: '"   + testAdres.getPostcode() + '\'' +
                ",\n huisnummer: '" + testAdres.getHuisnummer() + '\'' +
                ",\n straat: '"     + testAdres.getStraat() + '\'' +
                ",\n woonplaats: '" + testAdres.getWoonplaats() + '\'' +
                ",\n reizigerID: "  + testAdres.getReizigerID() +'}');
    }
}
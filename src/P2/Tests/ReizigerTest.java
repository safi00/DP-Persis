package P2.Tests;

import P2.Domain.Reiziger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReizigerTest {
    private Reiziger testReiziger;

    @BeforeEach
    public void init(){
        testReiziger = new Reiziger(0, "X","","Martina","1997-10-27");
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
}
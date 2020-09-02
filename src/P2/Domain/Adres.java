package P2.Domain;

public class Adres {
    private int    adresID;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int    reizigerID;

    public Adres(){}


    public int getAdresID() {
        return adresID;
    }
    public String getPostcode() {
        return postcode;
    }
    public String getHuisnummer() {
        return huisnummer;
    }
    public String getStraat() {
        return straat;
    }
    public String getWoonplaats() {
        return woonplaats;
    }
    public int getReizigerID() {
        return reizigerID;
    }

    public void setAdresID(int adresID) {
        this.adresID = adresID;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    public void setStraat(String straat) {
        this.straat = straat;
    }
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    public void setReizigerID(Reiziger reiziger) {
        this.reizigerID = reiziger.getIdnummer();
    }

    public String toString() {
        return "Adres{" + "adresID: " + adresID +
                  ",\n postcode: '"   + postcode + '\'' +
                  ",\n huisnummer: '" + huisnummer + '\'' +
                  ",\n straat: '"     + straat + '\'' +
                  ",\n woonplaats: '" + woonplaats + '\'' +
                  ",\n reizigerID: "  + reizigerID +'}';
    }
}

package P2.Domain;

public class Adres {
    private int      adresID;
    private String   postcode;
    private String   huisnummer;
    private String   straat;
    private String   woonplaats;
    private Reiziger wooner;

    public Adres(int aID, String pcode, String hNummer, String strt, String wPlaats, Reiziger reiz){
        adresID    = aID;
        postcode   = pcode;
        huisnummer = hNummer;
        straat     = strt;
        woonplaats = wPlaats;
        wooner     = reiz;
    }

    public int      getAdresID() {
        return adresID;
    }
    public String   getPostcode() {
        return postcode;
    }
    public String   getHuisnummer() {
        return huisnummer;
    }
    public String   getStraat() {
        return straat;
    }
    public String   getWoonplaats() {
        return woonplaats;
    }
    public Reiziger getReiziger() {
        return wooner;
    }

    public void setAdresID   (int adresID) {
        this.adresID = adresID;
    }
    public void setPostcode  (String postcode) {
        this.postcode = postcode;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }
    public void setStraat    (String straat) {
        this.straat = straat;
    }
    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }
    public void setReizigerID(Reiziger reiziger) {
        this.wooner = reiziger;
    }

    public String toString() {
        return  "Adres {" + "adresID: " + adresID + ", " +
                postcode + ", " + straat + " #" + huisnummer +
                ", woonplaats: " + woonplaats +
                ", reiziger: #" + wooner.getIdNummer() + '}';
    }
}

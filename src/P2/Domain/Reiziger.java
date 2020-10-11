package P2.Domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int                      idNummer;
    private String                   voorletters;
    private String                   tussenvoegsel;
    private String                   achternaam;
    private Date                     geboortedatum;
    private Adres                    huisadres;
    private final List<OVChipkaart>  oVKaarten = new ArrayList<>();

    public Reiziger(int idNum, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.idNummer      = idNum;
        this.voorletters   = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam    = achternaam;
        this.geboortedatum = geboortedatum;
    }


    public int               getIdNummer(){
        return idNummer;
    }
    public String            getVoorletters(){
        return voorletters;
    }
    public String            getTussenvoegsel(){
        return tussenvoegsel;
    }
    public String            getAchternaam(){
        return achternaam;
    }
    public Date              getGeboortedatum(){
        return geboortedatum;
    }
    public Adres             getHuisAdres(){
        return huisadres;
    }
    public List<OVChipkaart> getOVKaarten() {
        return oVKaarten;
    }

    public void setIdNummer(int id){
        idNummer = id;
    }
    public void setVoorletters(String string){
        voorletters = string;
    }
    public void setTussenvoegsel(String string){
        tussenvoegsel = string;
    }
    public void setAchternaam(String string){
        achternaam = string;
    }
    public void setGeboortedatum(String datum){
        geboortedatum = Date.valueOf(datum);
    }
    public void setHuisadres(Adres ad) {
        huisadres = ad;
    }

    public void addOVKaart(OVChipkaart OVKaart) {
        oVKaarten.add(OVKaart);
    }
    public void removeOVKaart(OVChipkaart ov) {
       oVKaarten.remove(ov);
    }

    public String getNaam(){
        String naam = voorletters + " ";
        if (tussenvoegsel != null){
            naam = naam + tussenvoegsel + " ";
        }
        naam = naam + achternaam;
        return naam;
    }

    public String toString() {
        String returnString1 = "Reiziger{#"+ idNummer + " " + voorletters + " ";
        StringBuilder returnString2 = new StringBuilder(achternaam + ", geb." + geboortedatum + "} ");
        if (voorletters != null){
            returnString1 = returnString1 + tussenvoegsel + " ";
        }
        if (huisadres != null){
            returnString2.append(", Adres {#").append(huisadres.getAdresID()).append(" ").append(huisadres.getPostcode()).append(" ").append(huisadres.getWoonplaats()).append("-").append(huisadres.getHuisnummer()).append("} ");
        }
        if (!oVKaarten.isEmpty()){
            returnString2.append("\nmet OVChipKaarten : ");
            for (OVChipkaart ov : oVKaarten) {
                returnString2.append("\n{#").append(ov.getKaart_nummer()).append(", expires on ").append(ov.getGeldig_tot()).append(", klasse ").append(ov.getKlasse()).append(", saldo van €").append(ov.getSaldo()).append("} ");

            }
        } else {
            returnString2.append("\nOVKaarten { deze reiziger heeft geen OVChipKaarten. }");
        }
        return  returnString1 + returnString2 + "\n";
    }
}

package P2.Domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    private int                idNummer;
    private String             voorletters;
    private String             tussenvoegsel;
    private String             achternaam;
    private Date               geboortedatum;
    private Adres              huisadres;
    private List<OVChipkaart>  OVKaarten = new ArrayList<>();

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
        return OVKaarten;
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
        OVKaarten.add(OVKaart);
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
        String returnString2 = achternaam    + ", geb." + geboortedatum + "} ";
        if (voorletters != null){
            returnString1 = returnString1 + tussenvoegsel + " ";
        }
        if (huisadres != null){
            returnString2 = returnString2 + "\nAdres {#" + huisadres.getAdresID() + " " + huisadres.getPostcode() + " " + huisadres.getWoonplaats() + "-" + huisadres.getHuisnummer() + "} ";
        }
        if (!OVKaarten.isEmpty()){
            returnString2 = returnString2 + "\nmet OVChipKaarten : ";
            for (OVChipkaart ov : OVKaarten) {
                returnString2 = returnString2 + "\n{#" + ov.getKaart_nummer() + ", expires on " + ov.getGeldig_tot() + ", klasse " + ov.getKlasse() + ", saldo van" + ov.getSaldo() + "} ";

            }
        } else {
            returnString2 = returnString2 + "\nOVKaarten { deze reiziger heeft geen OVChipKaarten. }";
        }
        return  returnString1 + returnString2;
    }
}

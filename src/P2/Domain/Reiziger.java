package P2.Domain;

import java.sql.Date;

public class Reiziger {
    private int    idnummer;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date   geboortedatum;

    public Reiziger(int idnummer, String voorletters, String tussenvoegsel, String achternaam, String geboortedatum) {
        this.idnummer      = idnummer;
        this.voorletters   = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam    = achternaam;
        this.geboortedatum = Date.valueOf(geboortedatum);
    }


    public int getIdnummer(){
        return idnummer;
    }
    public String getVoorletters(){
        return voorletters;
    }
    public String getTussenvoegsel(){
        return tussenvoegsel;
    }
    public String getAchternaam(){
        return achternaam;
    }
    public Date getGeboortedatum(){
        return geboortedatum;
    }

    public void setIdnummer(int id){
        idnummer = id;
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

    public String getNaam(){
        String naam = voorletters + " ";
        if (tussenvoegsel != null){
            naam = naam + tussenvoegsel + " ";
        }
        naam = naam + achternaam;
        return naam;
    }

    public String toString(){
        return  "De Reiziger id: "              + idnummer +      ".\n" +
                "De Reiziger's voorletters: "   + voorletters +   ".\n" +
                "De Reiziger's tussenvoegsel: " + tussenvoegsel + ".\n" +
                "De Reiziger's lastname: "      + achternaam +    ".\n" +
                "De Reiziger was born on: "     + geboortedatum;
    }
}

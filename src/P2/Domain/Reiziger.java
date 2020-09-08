package P2.Domain;

import java.sql.Date;

public class Reiziger {
    private int    idNummer;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date   geboortedatum;
    private Adres  huisadres;

    public Reiziger(int idNum, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.idNummer      = idNum;
        this.voorletters   = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam    = achternaam;
        this.geboortedatum = geboortedatum;
    }


    public int getIdNummer(){
        return idNummer;
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

    public String getNaam(){
        String naam = voorletters + " ";
        if (tussenvoegsel != null){
            naam = naam + tussenvoegsel + " ";
        }
        naam = naam + achternaam;
        return naam;
    }

    @Override
    public String toString() {
        return "Reiziger{#"     + idNummer + " "
                + voorletters   + ". "
                + tussenvoegsel + " "
                + achternaam    + ", geb."
                + geboortedatum +
                '}';
    }
}

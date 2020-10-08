package P2.Domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private final long     kaart_nummer;
    private Date           geldig_tot;
    private int            klasse;
    private Double         saldo;
    private final Reiziger reiziger;
    private List<Product>  ovProduct = new ArrayList<>();

    public OVChipkaart(long kNummer, Date gTot, int klas, Double sal, Reiziger reiz){
        kaart_nummer = kNummer;
        geldig_tot   = gTot;
        klasse       = klas;
        saldo        = sal;
        reiziger     = reiz;
    }

    public long     getKaart_nummer(){return kaart_nummer;}
    public Date     getGeldig_tot(){return geldig_tot;}
    public int      getKlasse(){return klasse;}
    public Double   getSaldo(){return saldo;}
    public Reiziger getReiziger(){return reiziger;}

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }
    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }
    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void addOVProduct(Product pro){
        this.ovProduct.add(pro);
    }

    @Override
    public String toString() {
        String returnString1 = "OVChipkaart{" + "kaart #" + kaart_nummer + ", is geldig tot: " + geldig_tot + " ";
        StringBuilder returnString2 = new StringBuilder( ", met een saldo van €" + saldo + ",\ndeze kaart is van reiziger: #" + reiziger.getIdNummer() + " " + reiziger.getNaam() + '}');
        if (klasse == 2){
            returnString1 = returnString1 + klasse + "de  klass ";
        }else {
            returnString1 = returnString1 + klasse + "ste klass ";
        }
        if (!ovProduct.isEmpty()){
            returnString2.append("\nmet ovProducten : ");
        for (Product pro : ovProduct) {
            returnString2.append("\n{#").append(pro.getProduct_nummer()).append(", ").append(pro.getNaam()).append(",  ").append(pro.getBeschrijving());
        }
    } else {
        returnString2.append("\novProducten { deze reiziger heeft geen ovProducten. }");
    }
        return returnString1 + returnString2;
    }
}

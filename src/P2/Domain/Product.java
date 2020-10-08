package P2.Domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private final long         product_nummer;
    private String             naam;
    private String             beschrijving;
    private Double             prijs;
    private List<Product>      OVProduct   = new ArrayList<>();

    public Product(long nummer, String productNaam, String productBeschrijving, Double productPrijs){
        product_nummer = nummer;
        naam           = productNaam;
        beschrijving   = productBeschrijving;
        prijs          = productPrijs;
    }

    public long getProduct_nummer() {
        return product_nummer;
    }
    public String getNaam() {
        return naam;
    }
    public String getBeschrijving() {
        return beschrijving;
    }
    public Double getPrijs() {
        return prijs;
    }
    public List<Product> getOVProduct() {
        return OVProduct;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }
    public void addOVProduct(Product OVProduct) {
        this.OVProduct.add(OVProduct);
    }

    public String toString() {
        return "Product{#" + product_nummer + ", " + naam + ", " + beschrijving +
                ", €" + prijs + "}";
    }
}

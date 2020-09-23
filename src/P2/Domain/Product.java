package P2.Domain;

public class Product {
    private long               product_nummer;
    private String             naam;
    private String             beschrijving;
    private Double             prijs;
//    private OVChipkaartProduct OVProduct;

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
//    public OVChipkaartProduct getOVProduct() {
//        return OVProduct;
//    }

    public void setNaam(String naam) {
        this.naam = naam;
    }
    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }
    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }
//    public void setOVProduct(OVChipkaartProduct OVProduct) {
//        this.OVProduct = OVProduct;
//    }
}

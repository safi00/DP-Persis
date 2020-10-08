package P2.DAO;

import P2.Domain.OVChipkaart;
import P2.Domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class      ProductDAOsql implements ProductDAO {
    private final Connection conn;
    private       OVChipkaartDAO ovChipD;
    public        ProductDAOsql(Connection conn){
        this.conn = conn;
    }
    public void   setOVCDao(OVChipkaartDAO dao) {
        this.ovChipD = dao;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        String query = "INSERT INTO product(product_nummer, naam, beschrijving, prijs) VALUES (?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong   (1,product.getProduct_nummer());
        ps.setString (2,product.getNaam());
        ps.setString (3,product.getBeschrijving());
        ps.setDouble (4,product.getPrijs());
        ps.executeUpdate();
        System.out.println();
        System.out.println("Product saved.");
        return true;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        String query = "UPDATE product SET naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString (1,product.getNaam());
        ps.setString (2,product.getBeschrijving());
        ps.setDouble (3,product.getPrijs());
        ps.setLong   (4,product.getProduct_nummer());
        ps.executeUpdate();

        System.out.println();
        System.out.println("Product updated.");
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        String query = "DELETE FROM ov_chipkaart_product WHERE product_nummer = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1,product.getProduct_nummer());
        ps.executeUpdate();
        query = "DELETE FROM product WHERE product_nummer = ?";
        ps = conn.prepareStatement(query);
        ps.setLong(1,product.getProduct_nummer());
        ps.executeUpdate();
        System.out.println();
        System.out.println("Product deleted.");
        return true;
    }

    @Override
    public List<Product> findByOVChipKaart(long ov) throws SQLException {
        List<Product> returnValue = new ArrayList<>();
        String query = "SELECT pro.* FROM product pro LEFT JOIN ov_chipkaart_product ocp on pro.product_nummer = ocp.product_nummer where kaart_nummer = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1, ov);
        ResultSet rs = ps.executeQuery();
        System.out.println();
        System.out.println("all Products from ov #" + ov +" :");
        while (rs.next()){
            Product p = new Product(rs.getLong("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getDouble("prijs"));
            returnValue.add(p);
            System.out.println(p);
        }
        return returnValue;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> returnValue = new ArrayList<>();
        String query = "SELECT * FROM product";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        System.out.println();
        System.out.println("all Products :");
        while (rs.next()){

            returnValue.add(new Product(rs.getLong("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getDouble("prijs")));
        }
        return returnValue;
    }

}

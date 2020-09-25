package P2.DAO;

import P2.Domain.Adres;
import P2.Domain.OVChipkaart;
import P2.Domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save  (Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    List<Product> findByOVChipKaart(OVChipkaart ov) throws SQLException;
    List<Product> findAll() throws SQLException;
}

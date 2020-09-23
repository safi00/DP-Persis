package P2.DAO;

import P2.Domain.Adres;
import P2.Domain.Product;

import java.sql.SQLException;

public interface ProductDAO {
    boolean save  (Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
}

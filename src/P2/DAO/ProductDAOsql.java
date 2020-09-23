package P2.DAO;

import P2.Domain.Product;

import java.sql.Connection;
import java.sql.SQLException;

public class ProductDAOsql implements ProductDAO {
    private  Connection conn;
    public   ProductDAOsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        return false;
    }
}

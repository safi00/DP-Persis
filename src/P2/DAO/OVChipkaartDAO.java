package P2.DAO;

import P2.Domain.OVChipkaart;
import P2.Domain.Product;
import P2.Domain.Reiziger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    boolean save      (OVChipkaart ovChipkaart) throws SQLException;
    boolean addProduct(OVChipkaart ovChipkaart, Product product, String status, Date lastUpdate) throws SQLException;
    boolean update    (OVChipkaart ovChipkaart) throws SQLException;
    boolean delete    (OVChipkaart ovChipkaart) throws SQLException;
    OVChipkaart findById(long kaartnummer) throws SQLException;
    List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    List<OVChipkaart> findByProduct(Product product) throws SQLException;
    List<OVChipkaart> findAll() throws SQLException;
}

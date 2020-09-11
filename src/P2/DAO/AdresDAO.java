package P2.DAO;

import P2.Domain.Adres;
import P2.Domain.Reiziger;
import java.sql.SQLException;
import java.util.List;

public interface AdresDAO {
    boolean save(Adres adres) throws SQLException;
    boolean update(Adres adres) throws SQLException;
    boolean delete(Adres adres) throws SQLException;
    Adres findById(int id) throws SQLException;
    List<Adres> findByWoonplaats(String woonplaats) throws SQLException;
    List<Adres> findByReiziger(Reiziger reiziger) throws SQLException;
    List<Adres> findAll() throws SQLException;
}

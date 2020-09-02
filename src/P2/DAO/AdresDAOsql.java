package P2.DAO;

import P2.Domain.Adres;

import java.sql.Connection;
import java.util.List;

public class AdresDAOsql implements AdresDAO {
    private Connection conn;

    public AdresDAOsql(Connection conn){

    }

    @Override
    public boolean save(Adres adres) {
        return false;
    }

    @Override
    public boolean update(Adres adres) {
        return false;
    }

    @Override
    public boolean delete(Adres adres) {
        return false;
    }

    @Override
    public Adres findById(int id) {
        return null;
    }

    @Override
    public Adres findByReizigerId(int id) {
        return null;
    }

    @Override
    public List<Adres> findByWoonplaats(String woonplaats) {
        return null;
    }

    @Override
    public List<Adres> findAll() {
        return null;
    }
}

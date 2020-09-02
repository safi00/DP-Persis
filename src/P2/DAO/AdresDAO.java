package P2.DAO;

import P2.Domain.Adres;
import java.util.List;

public interface AdresDAO {
    public boolean     save(Adres adres){}
    public boolean     update(Adres adres){}
    public boolean     delete(Adres adres){}
    public Adres       findById(int id){}
    public Adres       findByReizigerId(int id){}
    public List<Adres> findByWoonplaats(String woonplaats){}
    public List<Adres> findAll(){}
}

package P2.DAO;

import P2.Domain.Adres;
import java.util.List;

public interface AdresDAO {
    public static boolean     save(Adres adres){
        return true;
    }
    public static boolean     update(Adres adres){
        return true;
    }
    public static boolean     delete(Adres adres){
        return true;
    }
    public static Adres       findById(int id){
        Adres returnValue = null;
        return returnValue;
    }
    public static Adres       findByReizigerId(int id){
        Adres returnValue = null;
        return returnValue;
    }
    public static List<Adres> findByWoonplaats(String woonplaats){
        List<Adres> returnValue = null;
        return returnValue;
    }
    public static List<Adres> findAll(){
        List<Adres> returnValue = null;
        return returnValue;
    }
}

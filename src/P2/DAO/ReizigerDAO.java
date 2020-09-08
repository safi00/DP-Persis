package P2.DAO;

import P2.Domain.Reiziger;

import java.sql.PreparedStatement;
import java.util.List;

public interface ReizigerDAO {
    public static boolean        save(Reiziger reiziger){

        boolean returnvalue = true;
        return  returnvalue;
    }
    public static boolean        update(Reiziger reiziger){
        boolean returnvalue = true;
        return  returnvalue;
    }
    public static boolean        delete(Reiziger reiziger){
        boolean returnvalue = true;
        return  returnvalue;
    }
    public static Reiziger       findById(int id){
        Reiziger returnvalue = null;
        return  returnvalue;
    }
    public static List<Reiziger> findByGbdatum(String datum){
        List<Reiziger> returnvalue = null;
        return  returnvalue;
    }
    public static List<Reiziger> findAll(){
        List<Reiziger> returnvalue = null;

        return  returnvalue;
    }
}

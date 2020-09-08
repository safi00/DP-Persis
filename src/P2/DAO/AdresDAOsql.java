package P2.DAO;

import P2.Domain.Adres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class AdresDAOsql implements AdresDAO {
    private Connection conn;

    public AdresDAOsql(Connection conn){

    }
    
    public boolean save(Adres adres) {
        try {
            String query = "INSERT INTO adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt   (1,adres.getAdresID());
            ps.setString(2,adres.getPostcode());
            ps.setString(3,adres.getHuisnummer());
            ps.setString(4,adres.getStraat());
            ps.setString(5,adres.getWoonplaats());
            ps.setInt   (6,adres.getReiziger().getIdNummer());
            ResultSet rs = ps.executeQuery();
            System.out.println("adres saved.");
            while (rs.next()){
                System.out.println(
                          "#" + rs.getString("adres_id")     + ": "+
                                rs.getString("postcode")     + ". "+
                                rs.getString("huisnummer")   + " " +
                                "#" + rs.getString("straat") + " " +
                                rs.getString("woonplaats")   + " " +
                                  rs.getString("reiziger_id"));}
        }catch (Exception e){
            System.out.println("saving had an error");
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean update(Adres adres) {
        return true;
    }

    
    public boolean delete(Adres adres) {
        return false;
    }

    public Adres findById(int id) {
        return null;
    }
    
    public Adres findByAdresId(int id) {
        return null;
    }
    
    public List<Adres> findByWoonplaats(String woonplaats) {
        return null;
    }
    
    public List<Adres> findAll() {
        return null;
    }
}

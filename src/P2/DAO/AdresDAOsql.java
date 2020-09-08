package P2.DAO;

import P2.Domain.Adres;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOsql implements AdresDAO {
    private Connection conn;
    private ReizigerDAO reizD;

    public AdresDAOsql(Connection conn){
        this.conn = conn;
        this.reizD = new ReizigerDAOsql(conn);
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
            System.out.println();
            System.out.println("adres saved.");
            while (rs.next()){
                System.out.println(
                                  "#" + rs.getString("adres_id") + ": "+
                                  rs.getString("postcode")       + ". "+
                                  rs.getString("huisnummer")     + " " +
                                  "#" + rs.getString("straat")   + " " +
                                  rs.getString("woonplaats")     + " " +
                                  rs.getString("reiziger_id"));}
        }catch (Exception e){
            System.out.println();
            System.out.println("saving had an error");
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean update(Adres adres) {
        try {
            String query = "UPDATE adres SET postcode = " + adres.getPostcode() + ", huisnummer = " + adres.getHuisnummer() + ", straat = " + adres.getStraat() + ", woonplaats = " + adres.getWoonplaats() + ", reiziger_id = " + adres.getReiziger().getIdNummer() + " WHERE adres_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,adres.getAdresID());
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("adres updated.");
            while (rs.next()){
                System.out.println(
                                "#" + rs.getString("adres_id") + ": "+
                                rs.getString("postcode")       + ". "+
                                rs.getString("huisnummer")     + " " +
                                "#" + rs.getString("straat")   + " " +
                                rs.getString("woonplaats")     + " " +
                                rs.getString("reiziger_id"));}
        }catch (Exception e){
            System.out.println();
            System.out.println("updating had an error");
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean delete(Adres adres) {
        try {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,adres.getAdresID());
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("adres deleted.");
            while (rs.next()){
                System.out.println(
                                "#" + rs.getString("adres_id") + ": "+
                                rs.getString("postcode")       + ". "+
                                rs.getString("huisnummer")     + " " +
                                "#" + rs.getString("straat")   + " " +
                                rs.getString("woonplaats")     + " " +
                                rs.getString("reiziger_id"));}
        }catch (Exception e){
            System.out.println();
            System.out.println("deleted had an error");
            e.printStackTrace();
        }
        return true;
    }

    public Adres findById(int id) {
        Adres returnValue = null;
        try {
            String query = "SELECT * FROM adres Where adres_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("adres met de id " + id + " : ");
            while (rs.next()){
                System.out.println(
                                "#" + rs.getString("adres_id") + ": "+
                                rs.getString("postcode")       + ". "+
                                rs.getString("huisnummer")     + " " +
                                "#" + rs.getString("straat")   + " " +
                                rs.getString("woonplaats")     + " " +
                                rs.getString("reiziger_id"));
                returnValue = new Adres(rs.getInt("adres_id"), rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"), reizD.findById(rs.getInt("reiziger_id")));
            }
        }catch (Exception e){
            System.out.println();
            System.out.println("had an error finding your results");
            e.printStackTrace();
        }
        return returnValue;
    }

    @Override
    public List<Adres> findByReiziger(Reiziger reiziger){
        List<Adres> returnValue = new ArrayList<>();
        try {
            String query = "SELECT * FROM adres Where reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,reiziger.getIdNummer());
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("adres by reiziger " + reiziger.getNaam() + " : ");
            findPrinter(returnValue, rs);
        }catch (Exception e){
            System.out.println();
            System.out.println("had an error finding your results");
            e.printStackTrace();
        }
        return returnValue;
    }
    
    public List<Adres> findByWoonplaats(String woonplaats) {
        List<Adres> returnValue = new ArrayList<>();
        try {
            String query = "SELECT * FROM adres Where woonplaats = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,woonplaats);
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("Alle adressen by woonplaats " + woonplaats + " : ");
            findPrinter(returnValue, rs);
        }catch (Exception e){
            System.out.println();
            System.out.println("had an error finding your results");
            e.printStackTrace();
        }
        return returnValue;
    }
    
    public List<Adres> findAll() {
        List<Adres> returnValue = new ArrayList<>();
        try {
            String query = "SELECT * FROM adres";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("Alle adressen : ");
            findPrinter(returnValue, rs);
        }catch (Exception e){
            System.out.println();
            System.out.println("had an error finding your results");
            e.printStackTrace();
        }
        return returnValue;
    }

    private void findPrinter(List<Adres> returnValue, ResultSet rs) throws SQLException {
        while (rs.next()){
            System.out.println(
                            "#" + rs.getString("adres_id") + ": "+
                            rs.getString("postcode")       + ". "+
                            rs.getString("huisnummer")     + " " +
                            "#" + rs.getString("straat")   + " " +
                            rs.getString("woonplaats")     + " " +
                            rs.getString("reiziger_id"));}
//                            rs.getString("adres_id")
//                            rs.getString("postcode")
//                            rs.getString("huisnummer")
//                            rs.getString("straat")
//                            rs.getString("woonplaats")
//                            rs.getString("reiziger_id")
            returnValue.add(new Adres(rs.getInt("adres_id"), rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"), reizD.findById(rs.getInt("reiziger_id"))));
        }
}

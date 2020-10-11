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
    private final Connection conn;
    private       ReizigerDAO reizD;
    public        AdresDAOsql(Connection conn){
        this.conn = conn;
    }
    public void   setRdao(ReizigerDAO dao) {
        this.reizD = dao;
    }

    @Override
    public boolean save(Adres adres) throws SQLException {
            String query = "INSERT INTO adres(adres_id,postcode,huisnummer,straat,woonplaats,reiziger_id) VALUES (?,?,?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt   (1,adres.getAdresID());
            ps.setString(2,adres.getPostcode());
            ps.setString(3,adres.getHuisnummer());
            ps.setString(4,adres.getStraat());
            ps.setString(5,adres.getWoonplaats());
            ps.setInt   (6,adres.getReiziger().getIdNummer());
            ps.executeUpdate();
            System.out.println();
            System.out.println("adres saved.");
            adres.getReiziger().setHuisadres(adres);
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ? WHERE adres_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1,adres.getPostcode());
        ps.setString(2,adres.getHuisnummer());
        ps.setString(3,adres.getStraat());
        ps.setString(4,adres.getWoonplaats());
        ps.setInt   (5,adres.getAdresID());
        ps.executeUpdate();
        System.out.println("adres updated.");
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
            String query = "DELETE FROM adres WHERE adres_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,adres.getAdresID());
            ps.executeUpdate();
            System.out.println();
            System.out.println("adres deleted.");
        return true;
    }

    @Override
    public Adres findById(int id)  throws SQLException {
        Adres returnValue = null;
            String query = "SELECT * FROM adres Where adres_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("adres met de id " + id + " : ");
            while (rs.next()){
                returnValue = new Adres(rs.getInt("adres_id"), rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"), reizD.findById(rs.getInt("reiziger_id")));
            }
        return returnValue;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Adres returnValue =  null;
            String query = "SELECT * FROM adres Where reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, reiziger.getIdNummer());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            returnValue = new Adres(rs.getInt("adres_id"), rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"), reiziger);
        }
        return returnValue;
    }

    @Override
    public List<Adres> findByWoonplaats(String woonplaats) throws SQLException {
        List<Adres> returnValue = new ArrayList<>();
            String query = "SELECT * FROM adres Where woonplaats = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,woonplaats);
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("Alle adressen by woonplaats " + woonplaats + " : ");
        while (rs.next()){
            returnValue.add(new Adres(rs.getInt("adres_id"), rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"), reizD.findById(rs.getInt("reiziger_id"))));
        }
        return returnValue;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        List<Adres> returnValue = new ArrayList<>();
            String query = "SELECT * FROM adres";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println();
        while (rs.next()){
            returnValue.add(new Adres(rs.getInt("adres_id"), rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"), reizD.findById(rs.getInt("reiziger_id"))));
        }
        return returnValue;
    }
}

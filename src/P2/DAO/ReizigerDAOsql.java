package P2.DAO;

import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO{
    private Connection conn;

    public ReizigerDAOsql(Connection conn){
        this.conn = conn;
    }


    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        String query = "INSERT INTO reiziger(reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt   (1,reiziger.getIdNummer());
        ps.setString(2,reiziger.getVoorletters());
        ps.setString(3,reiziger.getTussenvoegsel());
        ps.setString(4,reiziger.getAchternaam());
        ps.setDate  (5,reiziger.getGeboortedatum());
        ps.executeUpdate();
        System.out.println();
        System.out.println("reiziger saved.");
        return true;
    }

    @Override
    public boolean  update(Reiziger reiziger) throws SQLException {
            String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,reiziger.getVoorletters());
            ps.setString(2,reiziger.getTussenvoegsel());
            ps.setString(3,reiziger.getAchternaam());
            ps.setDate  (4,reiziger.getGeboortedatum());
            ps.setInt   (5,reiziger.getIdNummer());
            ps.executeUpdate();
            System.out.println();
            System.out.println("reiziger updated.");
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,reiziger.getIdNummer());
            ps.executeUpdate();
            System.out.println();
            System.out.println("reiziger deleted.");
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Reiziger returnValue = null;
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                returnValue = new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum")); }
        return returnValue;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        List<Reiziger> returnValue = new ArrayList<>();
            String query = "SELECT * FROM reiziger WHERE geboortedatum = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1,java.sql.Date.valueOf(datum));
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("Alle reizigers met geboortedatum (" + datum + ") : ");
//            testPrinter(returnValue, rs);
        while (rs.next()){
        returnValue.add(new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum")));}
        return returnValue;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        List<Reiziger> returnValue = new ArrayList<>();
            String query = "SELECT * FROM reiziger";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            System.out.println();
            System.out.println("Alle reizigers : ");
//            testPrinter(returnValue, rs);
        while (rs.next()){
        returnValue.add(new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum")));}
        return returnValue;
    }

    private void testPrinter(List<Reiziger> returnValue, ResultSet rs) throws SQLException {
        while (rs.next()){
            System.out.println(
                    "#" + rs.getString("reiziger_id")   + ": " +
                            rs.getString("voorletters")   + ". " +
                            rs.getString("tussenvoegsel") + " " +
                            rs.getString("achternaam")    + " " +
                            "(" + rs.getString("geboortedatum") + ")");
        }
    }
}

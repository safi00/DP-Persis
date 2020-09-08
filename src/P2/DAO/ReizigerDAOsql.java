package P2.DAO;

import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class ReizigerDAOsql implements ReizigerDAO{
    private Connection conn;

    public ReizigerDAOsql(Connection conn){
        this.conn = conn;
    }


    public boolean save(Reiziger reiziger) {
        try {
        String query = "INSERT INTO reiziger(reiziger_id,voorletters,tussenvoegsel,achternaam,geboortedatum) VALUES (?,?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt   (1,reiziger.getIdNummer());
        ps.setString(2,reiziger.getVoorletters());
        ps.setString(3,reiziger.getTussenvoegsel());
        ps.setString(4,reiziger.getAchternaam());
        ps.setDate  (5,reiziger.getGeboortedatum());
        ResultSet rs = ps.executeQuery();
        System.out.println("reiziger saved.");
        while (rs.next()){
                System.out.println(
                          "#" + rs.getString("reiziger_id")   + ": " +
                                rs.getString("voorletters")   + ". " +
                                rs.getString("tussenvoegsel") + " " +
                                rs.getString("achternaam")    + " " +
                                "(" + rs.getString("geboortedatum") + ")");}
        }catch (Exception e){
            System.out.println("saving had an error");
            e.printStackTrace();
        }
        return true;
    }

    public boolean update(Reiziger reiziger) {
        try {
            String query = "UPDATE reiziger SET voorletters = " + reiziger.getVoorletters() + ",tussenvoegsel = " + reiziger.getTussenvoegsel() + " ,achternaam = + reiziger.getAchternaam() + ,geboortedatum = " + reiziger.getGeboortedatum() + " + ) WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,reiziger.getIdNummer());
            ResultSet rs = ps.executeQuery();
            System.out.println("reiziger updated.");
            while (rs.next()){
                System.out.println(
                          "#" + rs.getString("reiziger_id")   + ": " +
                                rs.getString("voorletters")   + ". " +
                                rs.getString("tussenvoegsel") + " " +
                                rs.getString("achternaam")    + " " +
                                "(" + rs.getString("geboortedatum") + ")");}
        }catch (Exception e){
            System.out.println("updating had an error");
            e.printStackTrace();
        }
        return true;
    }

    public boolean delete(Reiziger reiziger) {
        try {
            String query = "DELETE FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,reiziger.getIdNummer());
            ResultSet rs = ps.executeQuery();
            System.out.println("reiziger deleted.");
            while (rs.next()){
                System.out.println(
                          "#" + rs.getString("reiziger_id")   + ": " +
                                rs.getString("voorletters")   + ". " +
                                rs.getString("tussenvoegsel") + " " +
                                rs.getString("achternaam")    + " " +
                                "(" + rs.getString("geboortedatum") + ")");}
        }catch (Exception e){
            System.out.println("updating had an error");
            e.printStackTrace();
        }
        return true;
    }

    public Reiziger findById(int id) {
        try {
            Reiziger returnValue = null;
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            System.out.println("Alle reizigers met ID #" + id + ": ");
            while (rs.next())
            {
                System.out.println(
                        // #1: A. Donk (1968-07-19)
                          "#" + rs.getString("reiziger_id")   + ": " +
                                rs.getString("voorletters")   + ". " +
                                rs.getString("tussenvoegsel") + " " +
                                rs.getString("achternaam")    + " " +
                                "(" + rs.getString("geboortedatum") + ")"

                );
            }
            return returnValue;
        }catch (Exception e){
            System.out.println("updating had an error");
            e.printStackTrace();
        }
        return null;
    }

    public List<Reiziger> findByGbdatum(String datum) {
        try {
            String query = "SELECT * FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1,java.sql.Date.valueOf(datum));
            ResultSet rs = ps.executeQuery();
            System.out.println("Alle reizigers met geboortedatum (" + datum + ") : ");
            while (rs.next())
            {
                System.out.println(
                        // #1: A. Donk (1968-07-19)
                        "#" + rs.getString("reiziger_id")   + ": " +
                                rs.getString("voorletters")   + ". " +
                                rs.getString("tussenvoegsel") + " " +
                                rs.getString("achternaam")    + " " +
                                "(" + rs.getString("geboortedatum") + ")"

                );
            }
        }catch (Exception e){
            System.out.println("updating had an error");
            e.printStackTrace();
        }
        return null;
    }

    public List<Reiziger> findAll() {
        return null;
    }
}

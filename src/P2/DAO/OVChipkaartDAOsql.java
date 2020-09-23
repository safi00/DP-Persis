package P2.DAO;

import P2.Domain.OVChipkaart;
import P2.Domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OVChipkaartDAOsql implements OVChipkaartDAO {
    private  Connection conn;
    private  ReizigerDAO reizD;
    public   OVChipkaartDAOsql(Connection conn){
        this.conn = conn;
        this.reizD = new ReizigerDAOsql(conn);
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        String query = "INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong   (1,ovChipkaart.getKaart_nummer());
        ps.setDate   (2,ovChipkaart.getGeldig_tot());
        ps.setInt    (3,ovChipkaart.getKlasse());
        ps.setDouble (4,ovChipkaart.getSaldo());
        ps.setInt    (5,ovChipkaart.getReiziger().getIdNummer());
        ps.executeUpdate();
        System.out.println();
        System.out.println("ovKaart saved.");
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        String query = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ? WHERE kaart_nummer = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setDate   (1,ovChipkaart.getGeldig_tot());
        ps.setInt    (2,ovChipkaart.getKlasse());
        ps.setDouble (3,ovChipkaart.getSaldo());
        ps.setLong   (4,ovChipkaart.getKaart_nummer());
        ps.executeUpdate();
        System.out.println();
        System.out.println("ovKaart updated.");
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1,ovChipkaart.getKaart_nummer());
        ps.executeUpdate();
        System.out.println();
        System.out.println("ovKaart deleted.");
        return true;
    }

    @Override
    public OVChipkaart findById(long kaartnummer) throws SQLException {
        OVChipkaart returnValue = null;
            String query = "SELECT * FROM ov_chipkaart Where kaart_nummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1,kaartnummer);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                returnValue = new OVChipkaart(rs.getLong("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getDouble("saldo"), reizD.findById(rs.getInt("reiziger_id")));}
        return returnValue;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> returnValue = new ArrayList<>();
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setLong(1,reiziger.getIdNummer());
            ResultSet rs = ps.executeQuery();
        while (rs.next()){
            returnValue.add(new OVChipkaart(rs.getLong("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getDouble("saldo"), reiziger));
        }
//            testPrinter(returnValue, rs);
        return returnValue;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> returnValue = new ArrayList<>();
            String query = "SELECT * FROM ov_chipkaart";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                returnValue.add(new OVChipkaart(rs.getLong("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getDouble("saldo"), reizD.findById(rs.getInt("reiziger_id"))));
            }
//            testPrinter(returnValue, rs);
        return returnValue;
    }

    private void testPrinter(List<OVChipkaart> returnValue, ResultSet rs) throws SQLException {
        while (rs.next()){
            System.out.println(
                    "#" + rs.getString("adres_id") + ": "+
                            rs.getString("postcode")       + ". "+
                            rs.getString("huisnummer")     + " " +
                            "#" + rs.getString("straat")   + " " +
                            rs.getString("woonplaats")     + " " +
                            rs.getString("reiziger_id"));}
        returnValue.add(new OVChipkaart(rs.getLong("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getDouble("saldo"), reizD.findById(rs.getInt("reiziger_id"))));
    }
}

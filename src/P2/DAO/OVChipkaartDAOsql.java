package P2.DAO;

import P2.Domain.OVChipkaart;
import P2.Domain.Product;
import P2.Domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OVChipkaartDAOsql implements OVChipkaartDAO {
    private final Connection  conn;
    private       ReizigerDAO reizD;
    private       ProductDAO  prodD;
    public        OVChipkaartDAOsql(Connection conn){
        this.conn = conn;
    }

    public void   setRdao(ReizigerDAO dao) {
        this.reizD = dao;
    }
    public void   setPdao(ProductDAO  dao) { this.prodD = dao;}

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
    public boolean addProduct(OVChipkaart ovChipkaart, Product product, String status, Date lastUpdate) throws SQLException {
        String query = "INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer, status, last_update) VALUES (?,?,?,?);";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong   (1,ovChipkaart.getKaart_nummer());
        ps.setLong   (2,product.getProduct_nummer());
        ps.setString (3,status);
        ps.setDate   (4,lastUpdate);
        ps.executeUpdate();
        System.out.println();
        System.out.println("ovKaart product saved.");
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
        relationDelete(ovChipkaart);
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
                if(!prodD.findByOVChipKaart(kaartnummer).isEmpty()) {
                    for (Product r : prodD.findByOVChipKaart(kaartnummer)) {
                        if (returnValue != null) {
                            returnValue.addOVProduct(r);
                        }
                    }
                }
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
            OVChipkaart listValue = new OVChipkaart(rs.getLong("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getDouble("saldo"), reiziger);
            for (Product r : prodD.findByOVChipKaart(listValue.getKaart_nummer())) {
                listValue.addOVProduct(r);
            }
            returnValue.add(listValue);
        }
        return returnValue;
    }

    @Override
    public List<OVChipkaart> findByProduct(Product product) throws SQLException {
        List<OVChipkaart> returnValue = new ArrayList<>();
        String query = "SELECT ov.* FROM ov_chipkaart ov LEFT JOIN ov_chipkaart_product ocp on ov.kaart_nummer = ocp.kaart_nummer where product_nummer = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1,product.getProduct_nummer());
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            OVChipkaart listValue = new OVChipkaart(rs.getLong("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getDouble("saldo"), reizD.findById(rs.getInt("reiziger_id")));
            for (Product r : prodD.findByOVChipKaart(listValue.getKaart_nummer())) {
                listValue.addOVProduct(r);
            }
            returnValue.add(listValue);
        }
        return returnValue;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> returnValue = new ArrayList<>();
            String query = "SELECT * FROM ov_chipkaart";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                OVChipkaart listValue = new OVChipkaart(rs.getLong("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getDouble("saldo"), reizD.findById(rs.getInt("reiziger_id")));
                for (Product r : prodD.findByOVChipKaart(listValue.getKaart_nummer())) {
                    listValue.addOVProduct(r);
                }
                returnValue.add(listValue);
            }
        return returnValue;
    }
    public void relationDelete(OVChipkaart ovChipkaart) throws SQLException {
        String query = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setLong(1,ovChipkaart.getKaart_nummer());
        ps.executeUpdate();
    }
}

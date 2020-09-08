package P1;

import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
            // get a con http://127.0.0.1:56588/?key=1a35769b-dd07-4ffc-a688-9ac6839d915d
            String url = "jdbc:postgresql://127.0.0.1:5432/P1DataBase";
            Connection conn = DriverManager.getConnection(url,"postgres","1234");

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * From reiziger;");
            System.out.println("Alle reizigers: ");
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
            rs.close();
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


package skandilotto;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


class DB {
    String JDBC_DRIVER = "org.apache.derby.jdbc.embeddedDriver";
    String URL = "jdbc:derby:sampleDB; create = true";
    String USERNAME = "";
    String PASSWORD ="";
    // Kapcsolat l�trehoz�sa oszt�lyv�ltoz�k�nt
    Connection kapcsolat;
    // K�sz�t�nk egy magpakolhat� teheraut�t oszt�lyv�ltoz�k�nt
    Statement teherauto = null;
    
    public DB(){
        
        try {
            // Kapcsolat �letre kelt�se (TRY �s CATCH �g fontos!!!)
            kapcsolat = DriverManager.getConnection(URL);
            System.out.println("A h�d l�trej�tt!");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a h�d l�trehoz�s�n�l!" + ex);
        }
        
        // Ha a kapcsolat l�trej�tt, elk�sz�tj�k a teheraut�t
        if (kapcsolat != null){
            try {
                teherauto = kapcsolat.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van a create statement l�trehoz�s�n�l!" + ex);            
            }
        }
        
        //Adatb�zis l�trehoz�sa
        DatabaseMetaData dbmd = null;
        //Megn�zz�k, hogy �res-e az adatb�zis. Megn�zz�k l�tezik-e az adott adatb�zis
        try {
            dbmd = kapcsolat.getMetaData();
        } catch (SQLException ex) {
                System.out.println("Valami baj van a DataBaseMetaData l�trehoz�sakor!" + ex);            
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", "NUMBERS", null);
            if(!rs.next())
            {
            teherauto.execute("create table numbers(num1 varchar(20), num2 varchar(20), num3 varchar(20), num4 varchar(20), num5 varchar(20), num6 varchar(20), num7 varchar(20))");
            //teherauto.execute("create table numbers(sorsolt varchar(20), tippelt varchar(20))");
            }
        } 
        catch (SQLException ex) {
                System.out.println("Valami baj van az adatb�zis l�trehoz�sakor!" + ex);            
        }
        
    }
    
    public void addNumbers(String sql, int num1, int num2, int num3, int num4, int num5, int num6, int num7){
        try {
//            String sql = "insert into users values ('" + name  + "', '" + addres  + "')"; 
//            createStatement.execute(sql);
            PreparedStatement superteherauto = kapcsolat.prepareStatement(sql);
            superteherauto.setInt(1, num1);
            superteherauto.setInt(2, num2);
            superteherauto.setInt(3, num3);
            superteherauto.setInt(4, num4);
            superteherauto.setInt(5, num5);
            superteherauto.setInt(6, num6);
            superteherauto.setInt(7, num7);

            superteherauto.execute();
        } catch (SQLException ex) {
                System.out.println("Valami baj van a user hozz�ad�sakor!" + ex);            
        }
        
    }
    public void showAllNumbers(String sql){
         
        try {
            ResultSet rs = teherauto.executeQuery(sql);
            while (rs.next()){
                String num1 = rs.getString("num1");
                String num2 = rs.getString("num2");
                String num3 = rs.getString("num3");
                String num4 = rs.getString("num4");
                String num5 = rs.getString("num5");
                String num6 = rs.getString("num6");
                String num7 = rs.getString("num7");
                System.out.println(num1 + " | " + num2 + " | " + num3 + " | " + num4 + " | " + num5 + " | " + num6 + " | " + num7);
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a lek�rdez�skor!" + ex);
        }
    }
    
    public void showNumbersMeta(String sql){
        //String sql = "select * from numbers";
        ResultSet rs = null;
        try {
            rs = teherauto.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            
            for (int x = 1; x<= columnCount; x++){
                System.out.print(rsmd.getColumnName(x) + " | ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteTable(String sql){
    
        try {
            teherauto.execute(sql);
        } catch (SQLException ex) {
            System.out.println("Valami baj van a t�rl�skor!" + ex);
        }
}
    
    public void addDataTable(String sql, String tableName){
    //Adatb�zis l�trehoz�sa
        DatabaseMetaData dbmd = null;
        //Megn�zz�k, hogy �res-e az adatb�zis. Megn�zz�k l�tezik-e az adott adatb�zis
        try {
            dbmd = kapcsolat.getMetaData();
        } catch (SQLException ex) {
                System.out.println("Valami baj van a DataBaseMetaData l�trehoz�sakor!" + ex);            
        }
        
        try {
            ResultSet rs = dbmd.getTables(null, "APP", tableName, null);
            if(!rs.next())
            {
            teherauto.execute(sql);
            //teherauto.execute("create table numbers(sorsolt varchar(20), tippelt varchar(20))");
            }
        } 
        catch (SQLException ex) {
                System.out.println("Valami baj van az adatb�zis l�trehoz�sakor!" + ex);            
        }
    
    }
    
}

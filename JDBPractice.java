/*
 * Resources at https://db.apache.org/derby/papers/DerbyTut/index.html
 * and the sample source files SimpleApp.java at
 * https://svm.apache.org/repos/asf/db/derby/code/tags/10.0.2.1/java/demo/simple/SimpleApp.java
 * have proved invaluable in constructing this class.
 */

// Interface to allow connection with a specific database
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Properties;

public class JDBPractice {

    public String framework = "embedded";
    public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    public String protocol = "jdbc:derby:";

    public static void main(String[] args) {
        new JDBPractice().go();
    }

    void go() {
        System.out.println("JDBPractice starting in " + framework + " mode.");

        try {
            Class.forName(driver).newInstance();
            System.out.println("Loaded the appropriate driver.");

            Connection conn = null;
            Properties props = new Properties();
            props.put("user", "user1");
            props.put("password", "user1");

            conn = DriverManager.getConnection(protocol + "derbyDB;create=true", props);

            System.out.println("Created and connected to database derbyDB");
            conn.setAutoCommit(false);

            Statement s = conn.createStatement();

            s.execute("create table derbyDB(num int, addr varchar(40))");
            System.out.println("created table derbyDB");
            s.execute("insert into derbyDB values (1956,'Webster St.')");
            System.out.println("inserted 1956, webster");
            s.execute("insert into derbyDB values (1910, 'Union St.')");
            System.out.println("Inserted 1910 Union");

            ResultSet rs = s.executeQuery("SELECT num, addr FROM derbyDB ORDER BY num");

            if (!rs.next()) {
                throw new Exception("Wrong number of rows");
            }
            
            if (rs.getInt(1) != 300) {
                throw new Exception("Wrong row returned");
            }

            if (!rs.next()) {
                throw new Exception("Wrong number of rows");
            }

            if (rs.getInt(1) != 1910) {
                throw new Exception("Wrong row returned");
            }

            if (rs.next()) {
                throw new Exception("Wrong number of rows");
            }                

            System.out.println("Verified the rows");

            s.execute("drop table derbyDB");
            System.out.println("Dropped table derbyDB");

            
            rs.close();
            s.close();
            System.out.println("Closed result set and statement");

            conn.commit();
            conn.close();
            System.out.println("Committed transaction and closed connection");

            boolean gotSQLExc = false;

            if (framework.equals("embedded")) {
                try {
                    DriverManager.getConnection("jdbc:derby:;shutdown=true");
                }
                catch(SQLException se) {
                    gotSQLExc = true;
                }
                
                if (!gotSQLExc) {
                    System.out.println("Database did not shut down normally");
                }
                else {
                    System.out.println("Database shut down normally");
                }

            }
        }
        catch (Exception e) {
        }
    }
}

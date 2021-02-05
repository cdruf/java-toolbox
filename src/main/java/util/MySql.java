package util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MySql {

    public static int getNRows(ResultSet rs) {
        try {
            if (rs == null) return 0;
            rs.last();
            int ret = rs.getRow();
            rs.beforeFirst();
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    private String     user   = "root";
    private String     pw     = "";
    private String     driver = "com.mysql.jdbc.Driver";
    private String     url    = "";
    private Connection connection;
    private Statement  stmt;

    public MySql(String user, String pass, String host) {
        this.user = user;
        this.pw = pass;
        url = "jdbc:mysql://" + host + ":3306/?useSSL=false";
    }

    public MySql connect() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pw);
            stmt = connection.createStatement();
            System.out.println("Connected to " + url);
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error Connecting with User:" + user + " and Password:" + pw);
            return null;
        }
    }

    public void connect(String database) {
        try {
            Class.forName(this.driver);
            url += database;
            this.connection = DriverManager.getConnection(url, user, pw);
            stmt = connection.createStatement();
            System.out.println("Connected to " + url);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error Connecting with User:" + user + " and Password:" + pw);
        }
    }

    public void close() {
        if (connection != null) {
            try {
                stmt.close();
                connection.close();
                System.out.println("Connection closed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        try {
            ResultSet rs = returnQuery("SELECT 1;");
            if (rs == null) {
                return false;
            }
            if (rs.next()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public ResultSet returnQuery(String query) {
        try {
            ResultSet rs = stmt.executeQuery(query);
            return rs;
        } catch (SQLException e) {
            System.err.println(e.toString());
            return null;
        }
    }

    public int[] returnInts(String query) {
        try {
            ResultSet rs = stmt.executeQuery(query);
            rs.last();
            int[] ret = new int[rs.getRow()];
            rs.beforeFirst();
            while (rs.next()) {
                ret[rs.getRow()] = rs.getInt(1);
            }
            return ret;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public boolean runQuery(String query) {
        try {
            boolean ret = stmt.execute(query);
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    /**
     * Executes the SQL-script on the current connection. SELECT-statements are not allowed.
     * 
     * @param f
     *            script
     */
    public void executeScriptFile(File f) {
        Scanner s = null;
        try {
            s = new Scanner(f);
            s.useDelimiter(";");
            while (s.hasNext()) {

                int k = stmt.executeUpdate(s.next());
                if (k > -1) {
                    System.out.print(".");
                } else {
                    System.out.println("FAIL");
                    throw new Error();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new Error();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Error();
        } finally {
            if (s != null) s.close();
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
package dao.db;

import dao.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Eugen on 31.08.2017.
 */
public class SQLiteCreate_Product_Table {

    private static final Logger logger = LogManager.getLogger(SQLiteCreate_Product_Table.class);
    private Connection con;
    private Statement stmt;

    public SQLiteCreate_Product_Table(Connection con){
        this.con = con;
    }

    public void createProductTable() throws DAOException{
        try{
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Product" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    " name TEXT, " +
                    " version TEXT) ";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }
}
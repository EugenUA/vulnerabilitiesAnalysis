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
public class SQLiteCreate_Description_Table {

    private static final Logger logger = LogManager.getLogger(SQLiteCreate_Description_Table.class);
    private Connection con = null;
    private Statement stmt = null;

    public SQLiteCreate_Description_Table(Connection con){
        this.con = con;
    }

    public void createDescriptionTable() throws DAOException{
        try{
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Description" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " vulnerability_id INTEGER, " +
                    " short_description TEXT, " +
                    " long_description TEXT, " +
                    " preprocessed_short_description TEXT, " +
                    " preprocessed_long_description TEXT, " +
                    " FOREIGN KEY(vulnerability_id) REFERENCES Vulnerability(id));";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }
}
package dao.db;

import dao.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.LogManager;

/**
 * Created by Eugen on 23.08.2017.
 */
public class SQLiteCreate_User_Table {

    private static final Logger logger = LogManager.getLogger(SQLiteCreate_User_Table.class);
    Connection con = null;
    Statement stmt = null;

    public SQLiteCreate_User_Table(Connection con){
        this.con = con;
    }

    public void createUserTable() throws DAOException{
        //logger.info("Creating of User Table");
        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS User" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " name TEXT NOT NULL," +
                    " password TEXT NOT NULL,"+
                    " email TEXT NOT NULL," +
                    " deleted INTEGER NOT NULL CHECK (deleted IN (0,1)))";
            stmt.executeUpdate(sql);
            stmt.close();
            //logger.info("User table successfully created");
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }

    }
}
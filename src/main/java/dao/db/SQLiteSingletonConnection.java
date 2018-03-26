package dao.db;

/**
 * @name SQLiteSingletonConnection
 * @author Gruzdev Eugen
 * @date 07.08.2017
 */

import dao.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;

/**
 * A singleton class handling the connection to the database
 */
public class SQLiteSingletonConnection {

    private static Connection con = null;
    private Statement statement = null;
    private static final Logger logger = LogManager.getLogger(SQLiteSingletonConnection.class);

    private SQLiteCreate_User_Table createUserTable;
    private SQLiteCreate_Vulnerabilities_Table createVulnerabilitiesTable;
    private SQLiteCreate_Description_Table createDescriptionTable;
    private SQLiteCreate_Product_Table createProductTable;
    private SQLiteCreate_VulnerabilitiesSources_Table createVulnerabilitiesSourcesTable;

    private SQLiteSingletonConnection() throws DAOException {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:mydb.db");

            /* Creation of user table */
            createUserTable = new SQLiteCreate_User_Table(con);
            createUserTable.createUserTable();

            /* Creation of vulnerability table */
            createVulnerabilitiesTable = new SQLiteCreate_Vulnerabilities_Table(con);
            createVulnerabilitiesTable.createVulnerabilitiesTable();

            /* Creation of vulnerability description table */
            createDescriptionTable = new SQLiteCreate_Description_Table(con);
            createDescriptionTable.createDescriptionTable();

            /* Creation of product table */
            createProductTable = new SQLiteCreate_Product_Table(con);
            createProductTable.createProductTable();

            /* Creation of Vulnerabilities Sources table */
            createVulnerabilitiesSourcesTable = new SQLiteCreate_VulnerabilitiesSources_Table(con);
            createVulnerabilitiesSourcesTable.createVulnerabilitiesSourcesTable();

            con.setAutoCommit(false);
        } catch (ClassNotFoundException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException("Could not connect to the database. Please, try again!");
        }
    }

    /**
     * @return the only possible connection to the database in this application.
     * @throws DAOException if could not establish this connection.
     */
    public static Connection getConnection() throws DAOException {
        if (con == null) {
            new SQLiteSingletonConnection();
            //logger.info("Connection to the database established.");
        }
        return con;
    }

    /**
     * Closes the connection to the database.
     *
     * @throws DAOException if it could not close this connection.
     */
    public static void closeConnection() throws DAOException {
        if (con != null) {
            try {
                con.close();
                logger.info("Connection to the database closed successfully.");
            } catch (SQLException e) {
                logger.warn(e.getMessage());
                throw new DAOException(e.getMessage());
            }
        }
    }

    /**
     * Tries reconnecting if the connection has been lost.
     * @return the connection to the database
     * @throws DAOException
     */
    public static Connection reconnectIfConnectionToDatabaseLost() throws DAOException {
        try {
            if(con!=null & ! con.isValid(3)){
                con = null; //set con to null so the getConnection() will try to connect to the database when called again.
                logger.fatal("Connection to database lost.");
            }
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        con = SQLiteSingletonConnection.getConnection();
        return con;
    }
}
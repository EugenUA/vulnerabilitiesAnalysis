package dao.db;


import dao.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteCreate_Vulnerabilities_Table {

    private static final Logger logger = LogManager.getLogger(SQLiteCreate_Vulnerabilities_Table.class);
    Connection con = null;
    Statement stmt = null;

    public SQLiteCreate_Vulnerabilities_Table(Connection connection){
        this.con = connection;
    }

    public void createVulnerabilitiesTable() throws DAOException{
        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Vulnerability" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " name TEXT, " +
                    " cve TEXT, " +
                    " cvss TEXT, " +
                    " date TEXT, " +
                    " source TEXT NOT NULL, " +
                    " source_type TEXT NOT NULL)";
            stmt.execute(sql);
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

}

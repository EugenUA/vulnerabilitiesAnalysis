package dao.db;

import dao.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteCreate_VulnerabilitiesSources_Table {

    private static final Logger logger = LogManager.getLogger(SQLiteCreate_VulnerabilitiesSources_Table.class);
    Connection con = null;
    Statement stmt = null;

    public SQLiteCreate_VulnerabilitiesSources_Table(Connection connection){
        this.con = connection;
    }

    public void createVulnerabilitiesSourcesTable() throws DAOException {
        try {
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS VulnerabilitiesSources" +
                    "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " source_name TEXT, " +
                    " source_type TEXT, " +
                    " link TEXT NOT NULL, " +
                    " last_access TEXT, " +
                    " notes TEXT)";
            stmt.execute(sql);
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

}
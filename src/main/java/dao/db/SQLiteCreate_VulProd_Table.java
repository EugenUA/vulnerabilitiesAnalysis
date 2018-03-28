package dao.db;

import dao.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteCreate_VulProd_Table {

    private static final Logger logger = LogManager.getLogger(SQLiteCreate_VulProd_Table.class);
    private Connection con;
    private Statement stmt;

    public SQLiteCreate_VulProd_Table(Connection con){
        this.con = con;
    }

    public void createVulProdTable() throws DAOException {
        try{
            stmt = con.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS VulProd" +
                    "(vulnerability_id INTEGER, " +
                    " product_id INTEGER, " +
                    " FOREIGN KEY (vulnerability_id) REFERENCES Vulnerability(id), " +
                    " FOREIGN KEY (product_id) REFERENCES Product(id))";
            stmt.execute(sql);
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

}

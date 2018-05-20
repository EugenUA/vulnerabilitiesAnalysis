package dao.SQLiteWorkingPackage;

import dao.DAOException;
import dao.db.SQLiteSingletonConnection;
import dao.interfaces.VulProdDAO;
import entities.dbEntities.VulProd;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteVulProdDAO implements VulProdDAO {

    private static final Logger logger = Logger.getLogger(SQLiteVulnerabilityDAO.class);
    private Connection con = null;

    public SQLiteVulProdDAO() throws DAOException {
        con = SQLiteSingletonConnection.getConnection();
        //logger.debug("Successfully connected to the database");
    }

    @Override
    public VulProd createVulProd(VulProd vulProd) throws DAOException {
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO VulProd(vulnerability_id, product_id) VALUES (?,?)");
            stmt.setInt(1, vulProd.getVulnerability_id());
            stmt.setInt(2, vulProd.getProduct_id());
            stmt.executeUpdate();
            con.commit();
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return vulProd;
    }
}

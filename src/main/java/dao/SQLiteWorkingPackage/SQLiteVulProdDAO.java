package dao.SQLiteWorkingPackage;

import dao.DAOException;
import dao.db.SQLiteSingletonConnection;
import dao.interfaces.VulProdDAO;
import entities.dbEntities.Product;
import entities.dbEntities.VulProd;
import entities.dbEntities.Vulnerability;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteVulProdDAO implements VulProdDAO {


    private static final Logger logger = LogManager.getLogger(SQLiteVulnerabilityDAO.class);
    private Connection con = null;

    public SQLiteVulProdDAO() throws DAOException {
        con = SQLiteSingletonConnection.getConnection();
        //logger.debug("Successfully connected to the database");
    }

    @Override
    public VulProd createVulProd(VulProd vulProd) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO VulProd(vulnerability_id, product_id) VALUES (?,?)");
            stmt.setInt(1, vulProd.getVulnerability_id());
            stmt.setInt(2, vulProd.getProduct_id());
            stmt.executeUpdate();
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return vulProd;
    }

    @Override
    public List<VulProd> getAllVulIdsForProd(Product product) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        List<VulProd> vulProds = new ArrayList<VulProd>();
        VulProd vulProd = null;
        try{
            String sql = "SELECT * FROM VulProd v WHERE v.product_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, product.getId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int vulnerability_id = rs.getInt(1);
                int product_id = rs.getInt(2);
                vulProd = new VulProd(vulnerability_id,product_id);
                vulProds.add(vulProd);
            }
            rs.close();
            pstmt.close();
            return vulProds;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<VulProd> getAllProdIdsForVul(Vulnerability vulnerability) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        List<VulProd> vulProds = new ArrayList<VulProd>();
        VulProd vulProd = null;
        try{
            String sql = "SELECT * FROM VulProd v WHERE v.vulnerability_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vulnerability.getId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int vulnerability_id = rs.getInt(1);
                int product_id = rs.getInt(2);
                vulProd = new VulProd(vulnerability_id,product_id);
                vulProds.add(vulProd);
            }
            rs.close();
            pstmt.close();
            return vulProds;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }
}

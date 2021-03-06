package dao.SQLiteWorkingPackage;

import dao.DAOException;
import dao.db.SQLiteSingletonConnection;
import dao.interfaces.ProductDAO;
import entities.dbEntities.Product;
import entities.dbEntities.Vulnerability;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteProductDAO implements ProductDAO {


    private static final Logger logger = Logger.getLogger(SQLiteVulnerabilityDAO.class);
    private Connection con = null;

    public SQLiteProductDAO() throws DAOException {
        con = SQLiteSingletonConnection.getConnection();
        //logger.debug("Successfully connected to the database");
    }

    @Override
    public Product createProduct(Product product) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Product(name,version) VALUES (?,?)");
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getVersion());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            product.setId(rs.getInt(1));
            con.commit();
            rs.close();
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return product;
    }

    @Override
    public Product getProduct(Product product) throws DAOException {
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        Product product1 = null;
        try{
            String sql = "SELECT * FROM Product p WHERE p.name=? AND p.version=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,product.getName());
            pstmt.setString(2,product.getVersion());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id1 = rs.getInt(1);
                String name = rs.getString(2);
                String version = rs.getString(3);
                product1 = new Product (id1, name, version);
            }
            rs.close();
            pstmt.close();
            return product1;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Product product) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE Product SET name=?, version=? WHERE id=?");
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getVersion());
            stmt.setInt(3, product.getId());
            stmt.executeUpdate();
            con.commit();
            return product;
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Product getProductById(int id) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        Product product = null;
        try{
            String sql = "SELECT * FROM Product p WHERE p.id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id1 = rs.getInt(1);
                String name = rs.getString(2);
                String version = rs.getString(3);
                product = new Product (id1, name, version);
            }
            rs.close();
            pstmt.close();
            return product;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Product> getProductByName(String name) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        List<Product> products = new ArrayList<Product>();
        Product product = null;
        try{
            String sql = "SELECT * FROM Product p WHERE p.name LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id1 = rs.getInt(1);
                String name1 = rs.getString(2);
                String version = rs.getString(3);
                product = new Product (id1, name1, version);
                products.add(product);
            }
            rs.close();
            pstmt.close();
            return products;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilityByProductName(String name) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        List<Vulnerability> vulnerabilities = new ArrayList<Vulnerability>();
        Vulnerability vulnerability = null;
        try{
            String sql = "SELECT v.* " +
                    "FROM Product p JOIN VulProd vp ON p.id = vp.product_id JOIN Vulnerability v ON v.id = vp.vulnerability_id " +
                    "WHERE p.name LIKE ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id1 = rs.getInt(1);
                String name1 = rs.getString(2);
                String cve = rs.getString(3);
                String cvss = rs.getString(4);
                String date = rs.getString(5);
                String source = rs.getString(6);
                String source_type = rs.getString(7);
                vulnerability = new Vulnerability(id1, name1, cve, cvss, date, source, source_type);
                vulnerabilities.add(vulnerability);
            }
            rs.close();
            pstmt.close();
            return vulnerabilities;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilityByProductNameAndDate(String name, String date) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        List<Vulnerability> vulnerabilities = new ArrayList<Vulnerability>();
        Vulnerability vulnerability = null;
        try{
            String sql = "SELECT v.* " +
                    "FROM Product p JOIN VulProd vp ON p.id = vp.product_id JOIN Vulnerability v ON v.id = vp.vulnerability_id " +
                    "WHERE p.name LIKE ? AND v.date > ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id1 = rs.getInt(1);
                String name1 = rs.getString(2);
                String cve = rs.getString(3);
                String cvss = rs.getString(4);
                String date1 = rs.getString(5);
                String source = rs.getString(6);
                String source_type = rs.getString(7);
                vulnerability = new Vulnerability(id1, name1, cve, cvss, date1, source, source_type);
                vulnerabilities.add(vulnerability);
            }
            rs.close();
            pstmt.close();
            return vulnerabilities;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }
}

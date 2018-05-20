package dao.SQLiteWorkingPackage;

import dao.DAOException;
import dao.db.SQLiteSingletonConnection;
import dao.interfaces.DescriptionDAO;
import entities.dbEntities.Description;
import entities.dbEntities.Vulnerability;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteDescriptionDAO implements DescriptionDAO {

    private static final Logger logger = Logger.getLogger(SQLiteVulnerabilityDAO.class);
    private Connection con = null;

    public SQLiteDescriptionDAO() throws DAOException {
        con = SQLiteSingletonConnection.getConnection();
        //logger.debug("Successfully connected to the database");
    }

    @Override
    public Description createDescription(Description description) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "INSERT INTO Description(vulnerability_id,short_description" +
                    ",long_description,preprocessed_short_description,preprocessed_long_description)" +
                    " VALUES (?,?,?,?,?)");
            stmt.setInt(1, description.getVulnerability_id());
            stmt.setString(2, description.getShort_description());
            stmt.setString(3, description.getLong_description());
            stmt.setString(4, description.getPreprocessed_short_description());
            stmt.setString(5, description.getPreprocessed_long_description());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            description.setId(rs.getInt(1));
            con.commit();
            rs.close();
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return description;
    }

    @Override
    public Description updateDescription(Description description) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE Vulnerability SET vulnerability_id=?, short_description=?, " +
                    "long_description=?, preprocessed_short_description=?, " +
                    "preprocessed_long_description=? WHERE id=?");
            stmt.setInt(1, description.getVulnerability_id());
            stmt.setString(2, description.getShort_description());
            stmt.setString(3, description.getLong_description());
            stmt.setString(4, description.getPreprocessed_short_description());
            stmt.setString(5, description.getPreprocessed_long_description());
            stmt.setInt(6, description.getId());
            stmt.executeUpdate();
            con.commit();
            return description;
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Description getDescriptionByVulnerability(Vulnerability vulnerability) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        Description description = null;
        try{
            String sql = "SELECT * FROM Description d WHERE d.vulnerability_id=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, vulnerability.getId());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                int vulnerability_id1 = rs.getInt(2);
                String short_description = rs.getString(3);
                String long_description = rs.getString(4);
                String preprocessed_short_description = rs.getString(5);
                String preprocessed_long_description = rs.getString(6);
                description = new Description(id,vulnerability_id1, short_description,long_description,
                        preprocessed_short_description,preprocessed_long_description);
            }
            rs.close();
            pstmt.close();
            return description;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

}

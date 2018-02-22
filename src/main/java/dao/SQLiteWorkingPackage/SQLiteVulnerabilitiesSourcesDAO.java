package dao.SQLiteWorkingPackage;

import dao.DAOException;
import dao.db.SQLiteSingletonConnection;
import dao.interfaces.VulnerabilitiesSourcesDAO;
import entities.dbEntities.VulnerabilitiesSource;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteVulnerabilitiesSourcesDAO implements VulnerabilitiesSourcesDAO{

    private static final Logger logger = LogManager.getLogger(SQLiteVulnerabilitiesSourcesDAO.class);
    private Connection con = null;

    public SQLiteVulnerabilitiesSourcesDAO() throws DAOException{
        con = SQLiteSingletonConnection.getConnection();
    }

    @Override
    public VulnerabilitiesSource createVulnerabilitiesSource(
            VulnerabilitiesSource vulnerabilitiesSource) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "INSERT INTO VulnerabilitiesSources (source_name, source_type, link, last_access, notes) VALUES (?,?,?,?,?)");
            stmt.setString(1, vulnerabilitiesSource.getSource_name());
            stmt.setString(2, vulnerabilitiesSource.getSource_type());
            stmt.setString(3, vulnerabilitiesSource.getLink());
            stmt.setString(4, vulnerabilitiesSource.getLast_access());
            stmt.setString(5, vulnerabilitiesSource.getNotes());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            vulnerabilitiesSource.setId(rs.getInt(1));
            con.commit();
            rs.close();
            stmt.close();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return vulnerabilitiesSource;
    }

    @Override
    public VulnerabilitiesSource getVulnerabilitySourceByLink(String link) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        VulnerabilitiesSource vulnerabilitiesSource = null;
        try{
            String sql = "SELECT * FROM VulnerabilitiesSources WHERE link=?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, link);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String source_name = rs.getString(2);
                String source_type = rs.getString(3);
                String link1 = rs.getString(4);
                String last_access = rs.getString(5);
                String notes = rs.getString(6);
                vulnerabilitiesSource = new VulnerabilitiesSource(id, source_name,source_type,link1,last_access,notes);
            }
            rs.close();
            pstmt.close();
            return vulnerabilitiesSource;
        } catch (SQLException e) {
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public VulnerabilitiesSource updateVulnerabilitiesSource(
            VulnerabilitiesSource vulnerabilitiesSource) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE VulnerabilitiesSources SET source_name=?, source_type=?, link=?, last_access=?, notes=? WHERE id=?");
            stmt.setString(1, vulnerabilitiesSource.getSource_name());
            stmt.setString(2, vulnerabilitiesSource.getSource_type());
            stmt.setString(3, vulnerabilitiesSource.getLink());
            stmt.setString(4, vulnerabilitiesSource.getLast_access());
            stmt.setString(5, vulnerabilitiesSource.getNotes());
            stmt.setInt(6, vulnerabilitiesSource.getId());
            stmt.executeUpdate();
            con.commit();
            return vulnerabilitiesSource;
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }
}

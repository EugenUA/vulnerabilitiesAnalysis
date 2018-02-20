package dao.SQLiteWorkingPackage;

import dao.DAOException;
import dao.db.SQLiteSingletonConnection;
import dao.interfaces.UserDAO;
import entities.dbEntities.User;
import org.apache.log4j.Logger;
import org.apache.log4j.LogManager;

import java.sql.*;

/**
 * Created by Eugen on 22.08.2017.
 */
public class SQLiteUserDAO implements UserDAO {

    private static final Logger logger = LogManager.getLogger(SQLiteUserDAO.class);
    private Connection con = null;


    public SQLiteUserDAO() throws DAOException {
        con = SQLiteSingletonConnection.getConnection();
        //logger.debug("Successfully connected to the database");
    }

    public User create(User user) throws DAOException{
        //logger.debug("Entering the user create method and trying to insert the user: " + user);
        checkIfUserIsNotNull(user);
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        User checkUser = ifDeletedUserIsPresentInDatabase(user);
        if(checkUser == null) {
            try {
                PreparedStatement stmt = con.prepareStatement("" +
                        "INSERT INTO User(name,password,email,deleted) VALUES (?,?,?,0)");
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getEmail());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                rs.next();
                user.setId(rs.getInt(1));
                con.commit();
                rs.close();
                stmt.close();
                //logger.info("User "+ user + " inserted successfully");
            } catch (SQLException e) {
                logger.debug(e.getMessage());
                throw new DAOException(e.getMessage());
            }
            return user;
        } else {
            user = restoreAccount(user);
            return user;
        }
    }

    public User updateName(User user, String name) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE User SET name=? WHERE id=?");
            stmt.setString(1, name);
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
            user.setName(name);
            con.commit();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return user;
    }

    public User updateEmail(User user, String email) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE User SET email=? WHERE id=?");
            stmt.setString(1, email);
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
            user.setEmail(email);
            con.commit();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return user;
    }

    public User updatePassword(User user, String password) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE User SET password=? WHERE id=?");
            stmt.setString(1, password);
            stmt.setInt(2, user.getId());
            stmt.executeUpdate();
            user.setPassword(password);
            con.commit();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
        return user;
    }

    public User deleteAccount(User user) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE User SET deleted=1 WHERE id=?");
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();
            user.setIsDeleted(true);
            con.commit();
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException((e.getMessage()));
        }
        return user;
    }

    private User restoreAccount(User user) throws DAOException{
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        try{
            PreparedStatement stmt = con.prepareStatement("" +
                    "UPDATE User SET deleted=0 WHERE name=?");
            stmt.setString(1, user.getName());
            stmt.executeUpdate();
            user.setIsDeleted(false);
            con.commit();

            stmt = con.prepareStatement("SELECT id FROM User WHERE name=?;");
            stmt.setString(1, user.getName());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                user.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException((e.getMessage()));
        }
        return user;
    }

    public User selectUser(String name, String password) throws DAOException{
        // logger.debug("Entering the user selection method with name: " + name + " password: " + password);
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        User user = null;
        try{
            String sql = "SELECT * FROM User WHERE name=? AND password=? AND deleted=0";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name1 = rs.getString(2);
                String password1 = rs.getString(3);
                String email = rs.getString(4);
                user = new User(id, name1, password1, email,false);
            }
            rs.close();
            pstmt.close();
            return user;
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    public User ifUserIsUnique(User inputUser) throws DAOException{
        // logger.debug("Entering the user selection method with name: " + name + " password: " + password);
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        User user = null;
        try{
            String sql = "SELECT * FROM User WHERE (name=? OR email=?) AND deleted=0";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,inputUser.getName());
            pstmt.setString(2,inputUser.getEmail());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name1 = rs.getString(2);
                String password1 = rs.getString(3);
                String email = rs.getString(4);
                user = new User(id, name1, password1, email,false);
            }
            rs.close();
            pstmt.close();
            return user;
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    private User ifDeletedUserIsPresentInDatabase(User inputUser) throws DAOException{
        // logger.debug("Entering the user selection method with name: " + name + " password: " + password);
        con = SQLiteSingletonConnection.reconnectIfConnectionToDatabaseLost();
        User user = null;
        try{
            String sql = "SELECT * FROM User WHERE name=? AND email=? AND password=? AND deleted=1";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1,inputUser.getName());
            pstmt.setString(2,inputUser.getEmail());
            pstmt.setString(3,inputUser.getPassword());
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String name1 = rs.getString(2);
                String password1 = rs.getString(3);
                String email = rs.getString(4);
                user = new User(id, name1, password1, email, false);
            }
            rs.close();
            pstmt.close();
            return user;
        } catch(SQLException e){
            logger.debug(e.getMessage());
            throw new DAOException(e.getMessage());
        }
    }

    private void checkIfUserIsNotNull(User user) throws DAOException{
        if(user == null){
            logger.debug("User is null");
            throw new DAOException("User cannot be null");
        }
    }

}
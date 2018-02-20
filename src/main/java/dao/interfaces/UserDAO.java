package dao.interfaces;

import dao.DAOException;
import entities.dbEntities.User;

/**
 * Created by Eugen on 22.08.2017.
 */
public interface UserDAO {

    /**
     * Creates a new User
     * @param user ... new user to be created
     * @return the new user containing the id (if there were no errors during creation)
     * @throws DAOException if the user could not be created
     */
    public User create(User user) throws DAOException;

    /**
     * Selects existing user by name and password
     * @param name ... name of already registered user
     * @param password ... password of already registered user
     * @return registered user
     * @throws DAOException if the user cannot be found
     */
    public User selectUser(String name, String password) throws DAOException;

    /**
     * Checks if the user is unique
     * @param inputUser ... user to check (if it is already in the db)
     * @return  the user if it is found in db
     * @throws DAOException
     */
    public User ifUserIsUnique(User inputUser) throws DAOException;

    /**
     * Updates (changes) the name of the user
     * @param user ... user by whom the name has to be changed
     * @param name ... new name of the current user
     * @return the user with new name
     * @throws DAOException
     */
    public User updateName(User user, String name) throws DAOException;

    /**
     * Updates (changes) email of the user
     * @param user ... user by whom the email has to be changed
     * @param email ... new email of the current user
     * @return the user with new email
     * @throws DAOException
     */
    public User updateEmail(User user, String email) throws DAOException;

    /**
     * Updates (changes) the password of the user
     * @param user ... user by whom the password must be changed
     * @param password ... new password of the current user
     * @return the user with new password
     * @throws DAOException
     */
    public User updatePassword(User user, String password) throws DAOException;

    /**
     * Deletes the current user
     * @param user ... user that has to be deleted
     * @return deleted user
     * @throws DAOException
     */
    public User deleteAccount(User user) throws DAOException;
}
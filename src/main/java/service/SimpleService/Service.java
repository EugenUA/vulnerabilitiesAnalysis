package service.SimpleService;
import entities.dbEntities.User;
import entities.dbEntities.Vulnerability;
import service.ServiceException;

/**
 * Created by Eugen on 22.08.2017.
 */
public interface Service {

    /* ------------------------------- USER -------------------------------- */

    /**
     * Create the user
     * @param user ... user to be created
     * @return created user
     * @throws ServiceException
     */
    public User createUser(User user) throws ServiceException;

    /**
     * Select existing user
     * @param name ... name of the user to select
     * @param password ... password of the user to select
     * @return selected user
     * @throws ServiceException
     */
    public User selectUser(String name, String password) throws ServiceException;

    /**
     * Check if the user is unique in the db
     * @param inputUser ... user to check
     * @return users that are in db or nothing
     * @throws ServiceException
     */
    public User ifUserIsUnique(User inputUser) throws ServiceException;

    /**
     * Update user's name
     * @param user ... user by whom the name should be updated
     * @param name ... new name of the current user
     * @return current user with new name
     * @throws ServiceException
     */
    public User updateName(User user, String name) throws ServiceException;

    /**
     * Update email of the user
     * @param user ... user by whom the email should be updated
     * @param email ... new email of the user
     * @return current user with new email
     * @throws ServiceException
     */
    public User updateEmail(User user, String email) throws ServiceException;

    /**
     * Update password of the user
     * @param user ... user by whom the password should be updated
     * @param password ... new password of the user
     * @return current user with new password
     * @throws ServiceException
     */
    public User updatePassword(User user, String password) throws ServiceException;

    /**
     * Delete account of the current user
     * @param user ... user who wants to be deleted
     * @return deleted user
     * @throws ServiceException
     */
    public User deleteAccount(User user) throws ServiceException;


    /* ---------------------------------------- VULNERABILITY --------------------------------------------- */
    public Vulnerability createVulnerability(Vulnerability vulnerability) throws ServiceException;

    /* DESCRIPTION */
    //public Description createDescription(Description description) throws ServiceException;

    /* PRODUCT */
    //public Product createProduct(Product product) throws ServiceException;

    /* VulProd */
    //public VulProd createVulProdEntry(VulProd vulProd) throws ServiceException;

}
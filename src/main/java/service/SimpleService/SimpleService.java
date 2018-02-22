package service.SimpleService;


import dao.*;
import dao.SQLiteWorkingPackage.*;
import dao.interfaces.*;
import entities.dbEntities.User;
import entities.dbEntities.Vulnerability;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ServiceException;

public class SimpleService implements Service {

    private static final Logger logger = LogManager.getLogger(SimpleService.class);
    private UserDAO userDAO;
    private VulnerabilityDAO vulnerabilityDAO;
    /*private DescriptionDAO descriptionDAO;
    private ProductDAO productDAO;
    private VulProdDAO vulProdDAO;*/

    public SimpleService() throws ServiceException {
        try{
            this.userDAO = new SQLiteUserDAO();
            this.vulnerabilityDAO = new SQLiteVulnerabilityDAO();
            /*this.descriptionDAO = new SQLiteDescriptionDAO();
            this.productDAO = new SQLiteProductDAO();
            this.vulProdDAO = new SQLiteVulProdDAO();*/
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
        //logger.info("Service started");
    }


    /* ---------------------------------------------- USER ---------------------------------------------------------- */


    public User createUser(User user) throws ServiceException{
        //logger.debug("Entering create user method");
        try{
            return userDAO.create(user);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public User selectUser(String name, String password) throws ServiceException{
        //logger.debug("Entering select user method");
        try{
            return userDAO.selectUser(name, password);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public User updateName(User user, String name) throws ServiceException{
        try{
            return userDAO.updateName(user, name);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public User updateEmail(User user, String email) throws ServiceException{
        try{
            return userDAO.updateEmail(user, email);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public User updatePassword(User user, String password) throws ServiceException{
        try{
            return userDAO.updatePassword(user, password);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public User deleteAccount(User user) throws ServiceException{
        try{
            return userDAO.deleteAccount(user);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public User ifUserIsUnique(User inputUser) throws ServiceException{
        try{
            return userDAO.ifUserIsUnique(inputUser);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }


    /* --------------------------------- VULNERABILITY -------------------------------------------------------------- */

    public Vulnerability createVulnerability(Vulnerability vulnerability) throws ServiceException{
        try{
            return vulnerabilityDAO.createVulnerability(vulnerability);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }


    /* ------------------------------------ DESCRIPTION ------------------------------------------------------------- */
   /* public Description createDescription(Description description) throws ServiceException{
        try{
            return descriptionDAO.createDescription(description);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public ProductDAO createProduct(ProductDAO product) throws ServiceException{
        try{
            return productDAO.createProduct(product);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    public VulProd createVulProdEntry(VulProd vulProd) throws ServiceException{
        try{
            return vulProdDAO.createVulProdEntry(vulProd);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }*/

}

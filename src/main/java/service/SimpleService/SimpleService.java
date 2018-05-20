package service.SimpleService;


import dao.*;
import dao.SQLiteWorkingPackage.*;
import dao.interfaces.*;
import entities.dbEntities.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ServiceException;

import java.util.HashMap;
import java.util.List;

public class SimpleService implements Service {

    private static final Logger logger = Logger.getLogger(SimpleService.class);
    private UserDAO userDAO;
    private VulnerabilityDAO vulnerabilityDAO;
    private DescriptionDAO descriptionDAO;
    private ProductDAO productDAO;
    private VulnerabilitiesSourcesDAO vulnerabilitiesSourcesDAO;
    private VulProdDAO vulProdDAO;

    public SimpleService() throws ServiceException {
        try{
            this.userDAO = new SQLiteUserDAO();
            this.vulnerabilityDAO = new SQLiteVulnerabilityDAO();
            this.descriptionDAO = new SQLiteDescriptionDAO();
            this.productDAO = new SQLiteProductDAO();
            this.vulnerabilitiesSourcesDAO = new SQLiteVulnerabilitiesSourcesDAO();
            this.vulProdDAO = new SQLiteVulProdDAO();
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
        //logger.info("Service started");
    }

    /* ---------------------------------------------- USER ---------------------------------------------------------- */

    @Override
    public User createUser(User user) throws ServiceException{
        //logger.debug("Entering create user method");
        try{
            return userDAO.create(user);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User selectUser(String name, String password) throws ServiceException{
        //logger.debug("Entering select user method");
        try{
            return userDAO.selectUser(name, password);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User updateName(User user, String name) throws ServiceException{
        try{
            return userDAO.updateName(user, name);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User updateEmail(User user, String email) throws ServiceException{
        try{
            return userDAO.updateEmail(user, email);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User updatePassword(User user, String password) throws ServiceException{
        try{
            return userDAO.updatePassword(user, password);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User deleteAccount(User user) throws ServiceException{
        try{
            return userDAO.deleteAccount(user);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public User ifUserIsUnique(User inputUser) throws ServiceException{
        try{
            return userDAO.ifUserIsUnique(inputUser);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }


    /* --------------------------------- VULNERABILITY -------------------------------------------------------------- */

    @Override
    public Vulnerability createVulnerability(Vulnerability vulnerability) throws ServiceException{
        try{
            return vulnerabilityDAO.createVulnerability(vulnerability);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Vulnerability updateVulnerability(Vulnerability vulnerability) throws ServiceException{
        try {
            return vulnerabilityDAO.updateVulnerability(vulnerability);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Vulnerability getVulnerabilityById(Long id) throws ServiceException{
        try {
            return vulnerabilityDAO.getVulnerabilityById(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilityByCVE(String cve) throws ServiceException{
        try {
            return vulnerabilityDAO.getVulnerabilityByCVE(cve);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilitiesByDate(String date) throws ServiceException{
        try {
            return vulnerabilityDAO.getVulnerabilitiesByDate(date);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilitiesBySource(String source) throws ServiceException{
        try {
            return vulnerabilityDAO.getVulnerabilitiesBySource(source);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilitiesBySourceType(String source_type) throws ServiceException{
        try {
            return vulnerabilityDAO.getVulnerabilitiesBySourceType(source_type);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public HashMap<String, Integer> getNumberOfVulnerabilitiesBy() throws ServiceException{
        try{
            return vulnerabilityDAO.getNumberOfVulnerabilitiesBy();
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public HashMap<String, Integer> getNumberOfVulnerabilitiesByDateOne(String date) throws ServiceException{
        try{
            return vulnerabilityDAO.getNumberOfVulnerabilitiesByDateOne(date);
        } catch( DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public HashMap<String, Integer> getNumberOfVulnerabilitiesFromDate(String date) throws ServiceException{
        try {
            return vulnerabilityDAO.getNumberOfVulnerabilitiesFromDate(date);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    /* ----------------------------------------- DESCRIPTION ----------------------------------------------- */

    @Override
    public Description createDescription(Description description) throws ServiceException{
        try {
            return descriptionDAO.createDescription(description);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Description updateDescription(Description description) throws ServiceException{
        try {
            return descriptionDAO.updateDescription(description);
        }catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Description getDescriptionByVulnerability(Vulnerability vulnerability) throws ServiceException{
        try {
            return descriptionDAO.getDescriptionByVulnerability(vulnerability);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

     /* ------------------------------------------- PRODUCT ------------------------------------------------- */

    @Override
    public Product createProduct(Product product) throws ServiceException{
        try {
            return productDAO.createProduct(product);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Product getProduct(Product product) throws ServiceException{
        try{
            return productDAO.getProduct(product);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Product product) throws ServiceException{
        try {
            return productDAO.updateProduct(product);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Product getProductById(int id) throws ServiceException{
        try {
            return productDAO.getProductById(id);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Product> getProductByName(String name) throws ServiceException{
        try {
            return productDAO.getProductByName(name);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilityByProductName(String name) throws ServiceException{
        try{
            return productDAO.getVulnerabilityByProductName(name);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Vulnerability> getVulnerabilityByProductNameAndDate(String name, String date) throws ServiceException{
        try{
            return productDAO.getVulnerabilityByProductNameAndDate(name,date);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }

    /* ---------------------------------------- VulProd ------------------------------------------------------*/

    @Override
    public VulProd createVulProd(VulProd vulProd) throws ServiceException {
        try{
            return vulProdDAO.createVulProd(vulProd);
        } catch(DAOException e){
            throw new ServiceException(e.getMessage());
        }
    }


    /* ------------------------------------- VulnerabilitiesSources ---------------------------------------- */

    @Override
    public VulnerabilitiesSource createVulnerabilitiesSource(VulnerabilitiesSource vulnerabilitiesSource) throws ServiceException{
        try {
            return vulnerabilitiesSourcesDAO.createVulnerabilitiesSource(vulnerabilitiesSource);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public VulnerabilitiesSource getVulnerabilitySourceByLink(String link) throws ServiceException{
        try {
            return vulnerabilitiesSourcesDAO.getVulnerabilitySourceByLink(link);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public VulnerabilitiesSource updateVulnerabilitiesSource(VulnerabilitiesSource vulnerabilitiesSource) throws ServiceException{
        try {
            return vulnerabilitiesSourcesDAO.updateVulnerabilitiesSource(vulnerabilitiesSource);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

}

package service.SimpleService;
import entities.dbEntities.*;
import service.ServiceException;

import java.util.List;

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
    User createUser(User user) throws ServiceException;

    /**
     * Select existing user
     * @param name ... name of the user to select
     * @param password ... password of the user to select
     * @return selected user
     * @throws ServiceException
     */
    User selectUser(String name, String password) throws ServiceException;

    /**
     * Check if the user is unique in the db
     * @param inputUser ... user to check
     * @return users that are in db or nothing
     * @throws ServiceException
     */
    User ifUserIsUnique(User inputUser) throws ServiceException;

    /**
     * Update user's name
     * @param user ... user by whom the name should be updated
     * @param name ... new name of the current user
     * @return current user with new name
     * @throws ServiceException
     */
    User updateName(User user, String name) throws ServiceException;

    /**
     * Update email of the user
     * @param user ... user by whom the email should be updated
     * @param email ... new email of the user
     * @return current user with new email
     * @throws ServiceException
     */
    User updateEmail(User user, String email) throws ServiceException;

    /**
     * Update password of the user
     * @param user ... user by whom the password should be updated
     * @param password ... new password of the user
     * @return current user with new password
     * @throws ServiceException
     */
    User updatePassword(User user, String password) throws ServiceException;

    /**
     * Delete account of the current user
     * @param user ... user who wants to be deleted
     * @return deleted user
     * @throws ServiceException
     */
    User deleteAccount(User user) throws ServiceException;


    /* ---------------------------------------- VULNERABILITY --------------------------------------------- */

    /**
     * Create vulnerability
     * @param vulnerability ... vulnerability to be created
     * @return created vulnerability
     * @throws ServiceException
     */
    Vulnerability createVulnerability(Vulnerability vulnerability) throws ServiceException;

    /**
     * Update vulnerability
     * @param vulnerability ... vulnerability to be updated
     * @return updated vulnerability
     * @throws ServiceException
     */
    Vulnerability updateVulnerability(Vulnerability vulnerability) throws ServiceException;

    /**
     * Get vulnerability by its name
     * @param name ... name of vulnerability under search
     * @return vulnerability by its name
     * @throws ServiceException
     */
    Vulnerability getVulnerabilityByName(String name) throws ServiceException;

    /**
     * Get vulnerability by its CVE
     * @param cve ... cve of the vulnerability under search
     * @return vulnerability by its cve
     * @throws ServiceException
     */
    Vulnerability getVulnerabilityByCVE(String cve) throws ServiceException;

    /**
     * Get vulnerabilities by its date
     * @param date ... date of the vulnerabilities under search
     * @return vulnerabilities by their date
     * @throws ServiceException
     */
    List<Vulnerability> getVulnerabilitiesByDate(String date) throws ServiceException;

    /**
     * Get vulnerabilities by source
     * @param source ... source of the vulnerabilities under search
     * @return vulnerabilities by their source
     * @throws ServiceException
     */
    List<Vulnerability> getVulnerabilitiesBySource(String source) throws ServiceException;

    /**
     * Get vulnerabilites by source type
     * @param source_type ... the source type of vulnerabilities under search
     * @return vulnerabilities by their source type
     * @throws ServiceException
     */
    List<Vulnerability> getVulnerabilitiesBySourceType(String source_type) throws ServiceException;

    /* ----------------------------------------- DESCRIPTION ----------------------------------------------- */

    /**
     * Create description
     * @param description ... description to be created
     * @return created description
     * @throws ServiceException
     */
    Description createDescription(Description description) throws ServiceException;

    /**
     * Update description
     * @param description ... description to be updated
     * @return updated description
     * @throws ServiceException
     */
    Description updateDescription(Description description) throws ServiceException;

    /**
     * Get description by specified vulnerability
     * @param vulnerability ... vulnerability for which a description is needed
     * @return description of specified vulnerability
     * @throws ServiceException
     */
    Description getDescriptionByVulnerability(Vulnerability vulnerability) throws ServiceException;

    /* ------------------------------------------- PRODUCT ------------------------------------------------- */

    /**
     * Create Product
     * @param product ... product to be created
     * @return created product
     * @throws ServiceException
     */
    Product createProduct(Product product) throws ServiceException;

    /**
     * Get Product
     * @param product ... product to be retrieved
     * @return retrieved product
     * @throws ServiceException
     */
    Product getProduct(Product product) throws ServiceException;

    /**
     * Update product
     * @param product ... product to be updated
     * @return updated product
     * @throws ServiceException
     */
    Product updateProduct(Product product) throws ServiceException;

    /**
     * Get product by its id
     * @param id ... id of the product under search
     * @return product by its id
     * @throws ServiceException
     */
    Product getProductById(int id) throws ServiceException;

    /**
     * Get product by its name
     * @param name ... name of the product under search
     * @return product by its name
     * @throws ServiceException
     */
    Product getProductByName(String name) throws ServiceException;

    /* ------------------------------------- VulProd ------------------------------------------------------ */
    /**
     * Create VulProd
     * @param vulProd ... product to be created
     * @return created product
     * @throws ServiceException
     */
    VulProd createVulProd(VulProd vulProd) throws ServiceException;

    /* ------------------------------------- VulnerabilitiesSources ---------------------------------------- */

    /**
     * Create new source of the vulnerabilities
     * @param vulnerabilitiesSource ... vulnerabilities source to create
     * @return created vulnerabilities source
     * @throws ServiceException
     */
    VulnerabilitiesSource createVulnerabilitiesSource(VulnerabilitiesSource vulnerabilitiesSource) throws ServiceException;

    /**
     * Get vulnerabilities source by its link
     * @param link ... link of the vulnerabilities source under search
     * @return vulnerabilities source by its link
     * @throws ServiceException
     */
    VulnerabilitiesSource getVulnerabilitySourceByLink(String link) throws ServiceException;

    /**
     * Update already existent vulnerabilities source
     * @param vulnerabilitiesSource ... new vulnerabilities source that may replace old one
     * @return updated vulnerabilities source
     * @throws ServiceException
     */
    VulnerabilitiesSource updateVulnerabilitiesSource(VulnerabilitiesSource vulnerabilitiesSource) throws ServiceException;
}
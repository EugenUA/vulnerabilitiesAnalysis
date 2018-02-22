package dao.interfaces;

import dao.DAOException;
import entities.dbEntities.Product;
import entities.dbEntities.VulProd;
import entities.dbEntities.Vulnerability;

import java.util.List;

public interface VulProdDAO {

    /**
     * Create relationship between vulnerability and product
     * @param vulProd ... a relationship to be created
     * @return created relationship
     * @throws DAOException
     */
    VulProd createVulProd(VulProd vulProd) throws DAOException;

    /**
     * Get all vulnerabilities for given product
     * @param product ... product where we search for vulnerabilities
     * @return list of relationships between the product and its vulnerabilities
     * @throws DAOException
     */
    List<VulProd> getAllVulIdsForProd(Product product) throws DAOException;

    /**
     * Get all products for given vulnerability
     * @param vulnerability ... vulnerability for which we want to find all products affected
     * @return list of relationships between the vulnerability and its affected products
     * @throws DAOException
     */
    List<VulProd> getAllProdIdsForVul(Vulnerability vulnerability) throws DAOException;
}

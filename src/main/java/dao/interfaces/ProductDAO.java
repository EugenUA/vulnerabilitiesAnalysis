package dao.interfaces;

import dao.DAOException;
import entities.dbEntities.Product;

public interface ProductDAO {

    /**
     * Create product
     * @param product ... product to be created
     * @return created product
     * @throws DAOException
     */
    Product createProduct(Product product) throws DAOException;

    /**
     * Get product
     * @param product ... product to be retrieved
     * @return retrieved product
     * @throws DAOException
     */
    Product getProduct(Product product) throws DAOException;

    /**
     * Update current product
     * @param product ... product to be updated
     * @return updated product
     * @throws DAOException
     */
    Product updateProduct(Product product) throws DAOException;

    /**
     * Get product by id
     * @param id ... id of a product under search
     * @return searched product
     * @throws DAOException
     */
    Product getProductById(int id) throws DAOException;

    /**
     * Get product by name
     * @param name ... name of a product under search
     * @return searched product
     * @throws DAOException
     */
    Product getProductByName(String name) throws DAOException;

}

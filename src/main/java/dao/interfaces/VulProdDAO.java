package dao.interfaces;

import dao.DAOException;
import entities.dbEntities.VulProd;

public interface VulProdDAO {

     public VulProd createVulProd(VulProd vulProd) throws DAOException;

}

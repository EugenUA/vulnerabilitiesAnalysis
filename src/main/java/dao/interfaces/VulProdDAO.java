package dao.interfaces;

import dao.DAOException;
import entities.dbEntities.VulProd;

import java.util.List;

public interface VulProdDAO {

     public VulProd createVulProd(VulProd vulProd) throws DAOException;

}

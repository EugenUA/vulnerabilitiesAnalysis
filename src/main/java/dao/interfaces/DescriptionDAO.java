package dao.interfaces;

import dao.DAOException;
import entities.dbEntities.Description;
import entities.dbEntities.Vulnerability;

public interface DescriptionDAO {

    /**
     * Create the description of the current vulnerability
     * @param description ... description of the vulnerability
     * @return created description
     * @throws DAOException
     */
    Description createDescription(Description description) throws DAOException;

    /**
     * Update description by given description
     * @param description ... description of the vulnerability
     * @return updated description
     * @throws DAOException
     */
    Description updateDescription(Description description) throws DAOException;

    /**
     * Returns description of the current vulnerability
     * @param vulnerability ... vulnerability that need description
     * @return description of the current vulnerability
     * @throws DAOException
     */
    Description getDescriptionByVulnerability(Vulnerability vulnerability) throws DAOException;

}

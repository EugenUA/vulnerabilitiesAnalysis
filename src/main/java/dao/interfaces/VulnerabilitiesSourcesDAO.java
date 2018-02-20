package dao.interfaces;

import dao.DAOException;
import entities.dbEntities.VulnerabilitiesSource;

public interface VulnerabilitiesSourcesDAO {

    /**
     * Create vulnerability source in the database
     * @param vulnerabilitiesSource ... the vulnerability source that has to be in db
     * @return vulnerability source in the db
     * @throws DAOException
     */
    VulnerabilitiesSource createVulnerabilitiesSource(VulnerabilitiesSource vulnerabilitiesSource) throws DAOException;

    /**
     * Get vulnerability source by its link
     * @param link ... link of a vulnerability source under the search
     * @return vulnerabilities source with the link
     * @throws DAOException
     */
    VulnerabilitiesSource getVulnerabilitySourceByLink(String link) throws DAOException;

    /**
     * Update current vulnerabilities source
     * @param vulnerabilitiesSource ... vulnerabilities source to update
     * @return updated vulnerabilities source
     * @throws DAOException
     */
    VulnerabilitiesSource updateVulnerabilitiesSource(VulnerabilitiesSource vulnerabilitiesSource) throws DAOException;

}

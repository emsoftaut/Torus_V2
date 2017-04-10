package frameworks;

import domainprojections.DomainProjectionHandler;
import requirements.RequirementsForest;
import requirements.RequirementsOrderProvider;

/**
 * abstract class used by all requirements frameworks
 * one-place to integrate requirementsforests
 * domain projections and order provider.
 * @author rsinha
 *
 */
public abstract class RequirementsFramework implements DomainProjectionHandler, RequirementsOrderProvider {
	public abstract RequirementsForest getRequirementsForest();
}

package frameworks;

import components.ComponentView;
import domainprojections.DomainProjection;
import domainprojections.DomainProjectionHandler;

/**
 * encapsulates both a componenttree and associated domain projections
 * assumes that any subclass and related handlers will know what
 * kinds of projections are to be stored/retrieved
 * @author rsinha
 *
 */
public abstract class ComponentFramework implements DomainProjectionHandler{
	
	/**
	 * return the tree
	 * @return
	 */
	public abstract ComponentView getComponentTree();
	/**
	 * return the projection with the certain name
	 * @param projectionName
	 * @return
	 */
	public abstract DomainProjection getProjection(String projectionName);
	
}

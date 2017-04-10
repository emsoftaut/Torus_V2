package domainprojections;

import java.util.Vector;

/**
 * Class that handles the conversion of raw data into appropriate domain projection(s)
 * for all elements - reqs/components
 * @author rsinha
 *
 */
public interface DomainProjectionHandler {
	
	/**
	 * Processing the raw data and storing info about the element 
	 * @param element usually a node
	 * @param rawData
	 */
	public abstract void extractInformationFor(Object element, Object rawData);
	
	public abstract Vector<DomainProjection> getAllProjections();
	
	/**
	 * return the i-th projection
	 * @param projectionID
	 * @return
	 */
	public abstract DomainProjection getProjection(int projectionID);

}

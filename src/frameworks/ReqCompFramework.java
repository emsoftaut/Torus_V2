package frameworks;

import java.util.*;
import domainprojections.DomainProjection;
import requirements.RequirementsOrderProvider;

/**
 * Handles appropriate conversions needed between different format
 * keeps all information required for TORUS to operate in one place
 * @author rsinha
 *
 */
public interface ReqCompFramework {
	public ComponentFramework getComponentFramework();
	public RequirementsFramework getRequirementsFramework();
}

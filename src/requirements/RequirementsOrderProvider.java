package requirements;


/**
 * Interface that needs to be implemented by classes that can order requirements
 * @author rsinha
 *
 */
public interface RequirementsOrderProvider {
	
	/**
	 * decides if there can be a splice edge between sourceRequirement and targetRequirement.
	 * @param sourceRequirement
	 * @param targetRequirement
	 * @return
	 */
	public boolean canHaveDirectedEdgeBetween(Object sourceRequirement, Object targetRequirement);
}

package domainprojections;

import org.jgrapht.ext.ComponentNameProvider;

//A surjective function
public abstract class DomainProjection implements ComponentNameProvider<Object> {



	/**
	 * Returns the name of the abstraction, often used for labelling splices
	 * @return name of the abstraction
	 */
	public abstract String getProjectionName();
	
	public abstract void addEntry(Object element, Object information);

	public abstract String getName(Object component);
	
	public abstract Object getInformation(Object element);
	
}

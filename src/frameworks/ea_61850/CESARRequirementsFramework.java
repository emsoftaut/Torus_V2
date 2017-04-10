package frameworks.ea_61850;

import java.util.Vector;

import domainprojections.DomainProjection;
import domainprojections.lib.OneToOneDataProjection;
import frameworks.RequirementsFramework;
import requirements.RequirementsForest;

public class CESARRequirementsFramework extends RequirementsFramework {

	private RequirementsForest myRequirementsForest = new RequirementsForest();
	private Vector<DomainProjection> myProjections = new Vector<>();
	
	public CESARRequirementsFramework() {
		OneToOneDataProjection reqToConceptsProjection = new OneToOneDataProjection("reqToConcepts");
		myProjections.addElement(reqToConceptsProjection);
	}
	
	
	@Override
	public void extractInformationFor(Object element, Object rawData) {
		myProjections.get(0).addEntry(element, rawData);
	}

	@Override
	public Vector<DomainProjection> getAllProjections() {
		return this.myProjections;
	}

	@Override
	public boolean canHaveDirectedEdgeBetween(Object sourceRequirement, Object targetRequirement) {
		return (targetRequirement.toString().compareTo(sourceRequirement.toString())>0);
	}

	@Override
	public RequirementsForest getRequirementsForest() {
		return this.myRequirementsForest;
	}


	@Override
	public DomainProjection getProjection(int projectionID) {
		return this.myProjections.get(projectionID);
	}

}

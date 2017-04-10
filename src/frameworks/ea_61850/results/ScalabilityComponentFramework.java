package frameworks.ea_61850.results;

import java.util.Vector;

import org.w3c.dom.Node;

import components.ComponentView;
import domainprojections.DomainProjection;
import domainprojections.DomainProjectionHandler;
import domainprojections.lib.OneToOneDataProjection;
import frameworks.ComponentFramework;
import frameworks.ea_61850.IEC61850_61499ComponentFramework;
import requirements.RequirementsForest;

public class ScalabilityComponentFramework extends ComponentFramework {
	
	private ComponentView componentTree = new ComponentView();
	private Vector<DomainProjection> componentProjections = new Vector<>();

	public ScalabilityComponentFramework() {
		OneToOneDataProjection nodeToTypeProjection = new OneToOneDataProjection("nodeToType");
		this.componentProjections.addElement(nodeToTypeProjection);
		OneToOneDataProjection nodeToNameProjection = new OneToOneDataProjection("nodeToName");
		this.componentProjections.addElement(nodeToNameProjection);
	}

	@Override
	public ComponentView getComponentTree() {
		return this.componentTree;
	}

	@Override
	public DomainProjection getProjection(String projectionName) {
		return null;
	}

	
	@Override
	public void extractInformationFor(Object element, Object rawData) {
		//element is a node
		//rawData is the one to be handled
		//assumes element is a string (name) and rawData is the xml node
		this.componentProjections.elementAt(0).addEntry(element, rawData);
		this.componentProjections.elementAt(1).addEntry(element, rawData);
	}

	@Override
	public Vector<DomainProjection> getAllProjections() {
		return this.componentProjections;
	}

	@Override
	public DomainProjection getProjection(int projectionID) {
		return this.componentProjections.get(projectionID);
	}
	
	

}

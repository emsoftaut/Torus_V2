package frameworks.ea_61850;

import java.util.Vector;

import org.w3c.dom.Node;

import components.ComponentView;
import domainprojections.DomainProjection;
import domainprojections.DomainProjectionHandler;
import domainprojections.lib.OneToOneDataProjection;
import frameworks.ComponentFramework;
import requirements.RequirementsForest;

public class IEC61850_61499ComponentFramework extends ComponentFramework {
	
	
	private ComponentView componentTree = new ComponentView();
	private Vector<DomainProjection> componentProjections = new Vector<>();

	public IEC61850_61499ComponentFramework() {
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
		String componentName = (String)element;
		Node rawNode = (Node)rawData;

		//extract type
		try{
			this.componentProjections.elementAt(0).addEntry(componentName, rawNode.getNodeName());
			this.componentProjections.elementAt(1).addEntry(componentName, componentName);
		} catch (Exception ex) {
			this.componentProjections.elementAt(0).addEntry(componentName, utilities.ErrorCodes.NO_LABEL_FOUND);			
		}
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

package algorithm.splicegeneration;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import domainprojections.DomainProjection;
import requirements.RequirementsOrderProvider;
import splice.*;
import utilities.ErrorCodes;

public class MatchingsBasedSpliceGeneration {


	//INPUTS: requirements, components, domain abstractions (can be the same for reqs and components)
	private HashSet<Object> requirements;
	private HashSet<Object> components;
	private DomainProjection reqProjection, compProjection;
	//tells us if we need to only work with Requirements
	private boolean mapRequirementsOnly;
	private RequirementsOrderProvider reqsOrderer;
	
	//INTERNAL vars: a map for requirements and a map for components
	private HashMap<Object,HashSet<Object>> requirementsConceptMap = null;
	private HashMap<Object,HashSet<Object>> componentsConceptMap = null;	
	public MatchingsBasedSpliceGeneration(HashSet<Object> requirements, HashSet<Object> components, DomainProjection reqProjection, DomainProjection compProjection, boolean mapRequirementsOnly, RequirementsOrderProvider requirementsOrderer){
		this.requirements = requirements;
		if(requirements==null)
			throw new RuntimeException("MatchingsBasedSpliceGeneration, Requirements:"+ErrorCodes.OBJECT_IS_NULL);
		this.components = components;
		this.reqProjection = reqProjection;
		if(reqProjection==null)
			throw new RuntimeException("MatchingsBasedSpliceGeneration, Req Projection:"+ErrorCodes.OBJECT_IS_NULL);
		this.compProjection = compProjection;
		this.mapRequirementsOnly = (mapRequirementsOnly) || (components==null);
		this.reqsOrderer = requirementsOrderer;
	}

	//1a. the order of edges is from requirements to components, we can divide these into two components
	
	//1c. create a map of co-domain values and components (based on matching rules)
	//  THERE COULD BE MORE THAN ONE concepts within a single domain concept!!
	//use the two maps to match
	public void generateConceptMaps() {
		//do the requirements ONLY once, and hence also the components
		if(this.requirementsConceptMap!=null)
			return;

		this.requirementsConceptMap = generateConceptMap(this.requirements,this.reqProjection);

		if(!this.mapRequirementsOnly)
			this.componentsConceptMap = generateConceptMap(this.components,this.compProjection);
	
	}

	private HashMap<Object, HashSet<Object>> generateConceptMap(HashSet<Object> elements,
			DomainProjection projection) {
		//1b. create a map of co-domain values and requirements (based on matching rules)
		//  THERE COULD BE MORE THAN ONE concepts within a single domain concept!!
		HashMap<Object, HashSet<Object>> map = new HashMap<>();
		for(Object element: elements) {
			Object codomainValue = projection.getName(element);
			if(!(codomainValue instanceof Collection)) {
				addKeyValuePairToMap(codomainValue,element,map);
			} else {
				for(Object concept: (Collection<Object>)codomainValue) {
					addKeyValuePairToMap(concept,element,map);
				}
			}
		}		
		return map;
	}

	private void addKeyValuePairToMap(Object concept, Object element, HashMap<Object, HashSet<Object>> map) {
		HashSet<Object> values;
		if(map.containsKey(concept)) 
			values = map.get(concept);
		else
			values = new HashSet<>();
		values.add(element);
		map.put(concept, values);
		
	}


	public HashSet<Splice> getAllSplices() {
		//first generate the concept maps if not done already
		generateConceptMaps();

		//to be returned
		HashSet<Splice> spliceSet = new HashSet<>();

		//map requirements
		spliceSet.addAll(mapRequirements());

		//map requirements and components
		if(!this.mapRequirementsOnly) {
			spliceSet.addAll(mapRequirementsAndComponents());
		}

		
		return spliceSet;
	}

	/**
	 * maps between requirements and components
	 * if they are related to the same concept(s)
	 * @param spliceSet the object to which newly created splices are added/
	 */
	private HashSet<Splice> mapRequirementsAndComponents() {
		HashSet<Splice> spliceSet = new HashSet<>();
		for(Object concept: this.requirementsConceptMap.keySet()) {
			//System.out.println("Looking for concept: "+concept);
			if(!this.componentsConceptMap.containsKey(concept)) 
				continue;
			HashSet<Object> sourceRequirements = this.requirementsConceptMap.get(concept);
			HashSet<Object> targetComponents = this.componentsConceptMap.get(concept);

			Splice sp = new Splice();
			for(Object s: sourceRequirements) {
				sp.addVertex(s);
				for(Object t: targetComponents) {
					sp.addVertex(t);
					sp.addEdge(s, t, new SpliceEdge(s,t,concept.toString()));
				}
			}			
			spliceSet.add(sp);		
		}		
		return spliceSet;
	}



	/**
	 * Maps requirements to each other.
	 * Needs to depend on order of requirements, 
	 * as provided by the Oracle that simply says
	 * if two requirements s and t can have a link
	 * @return the set of splices created
	 */
	private HashSet<Splice> mapRequirements() {
		HashSet<Splice> spliceSet = new HashSet<>();
		for(Object concept: this.requirementsConceptMap.keySet()) {
			HashSet<Object> sourceRequirements = this.requirementsConceptMap.get(concept);
			HashSet<Object> targetRequirements = this.requirementsConceptMap.get(concept);
			
			Splice sp = new Splice();
			for(Object s: sourceRequirements) {
				sp.addVertex(s);
				for(Object t: targetRequirements) {
					if(this.reqsOrderer.canHaveDirectedEdgeBetween(s,t)){
						sp.addVertex(t);
						sp.addEdge(s, t, new SpliceEdge(s,t,concept.toString()));			
					}
				}
			}			
			spliceSet.add(sp);					
		}		
		return spliceSet;
	}

	public HashMap<Object, HashSet<Object>> getRequirementsConceptMap() {
		return requirementsConceptMap;
	}

	public HashMap<Object, HashSet<Object>> getComponentsConceptMap() {
		return componentsConceptMap;
	}


	
	
	//Getters and setters
	
	
	
}

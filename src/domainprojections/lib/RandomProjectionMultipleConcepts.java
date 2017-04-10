package domainprojections.lib;

import java.util.*;

import domainprojections.DomainProjection;

public class RandomProjectionMultipleConcepts extends DomainProjection {

	private Vector<String> possibleOutputs;
	private int maxNumberOfConceptsPerElement;
	//to save returns values
	private HashMap<Object,HashSet<Object>> returnedValues;
	
	public RandomProjectionMultipleConcepts(Vector<String> possibleOutputs, int maxNumberOfConceptsPerElement) {
		this.possibleOutputs = possibleOutputs;
		this.returnedValues = new HashMap<>();
		this.maxNumberOfConceptsPerElement = maxNumberOfConceptsPerElement;
	}

	@Override
	public String getName(Object o) {

		//System.out.println("Looking for "+o);
		if(!this.returnedValues.containsKey(o)) {		
			int numberOfConcepts = new Random().nextInt(this.maxNumberOfConceptsPerElement);
			HashSet<String> vals = new HashSet<>();
			for(int i=0; i<numberOfConcepts; i++) {
				int rnd = new Random().nextInt(this.possibleOutputs.size());
				vals.add(this.possibleOutputs.get(rnd));
			}
			this.addEntry(o, vals);			
		}
		return this.returnedValues.get(o).toString();
	}

	@Override
	public String getProjectionName() {
		return "random";
	}

	@Override
	public void addEntry(Object element, Object information) {
		this.returnedValues.put(element,(HashSet<Object>)information);
		
	}
	
	@Override
	public Object getInformation(Object element) {
		return returnedValues.get(element);
	}

}

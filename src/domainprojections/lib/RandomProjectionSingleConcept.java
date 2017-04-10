package domainprojections.lib;

import java.util.*;

import domainprojections.DomainProjection;

public class RandomProjectionSingleConcept extends DomainProjection {

	private Vector<String> possibleOutputs;
	//to save returns values
	private HashMap<Object,Object> returnedValues;
	
	public RandomProjectionSingleConcept(Vector<String> possibleOutputs) {
		this.possibleOutputs = possibleOutputs;
		this.returnedValues = new HashMap<>();
	}
	
	@Override
	public String getName(Object o) {
		if(!this.returnedValues.containsKey(o)) {			
			int rnd = new Random().nextInt(this.possibleOutputs.size());
			this.addEntry(o, this.possibleOutputs.get(rnd));
		}
		return this.returnedValues.get(o).toString();
	}

	@Override
	public String getProjectionName() {
		return "random";
	}


	@Override
	public void addEntry(Object element, Object information) {
		this.returnedValues.put(element, information);		
	}
	
	@Override
	public Object getInformation(Object element) {
		return returnedValues.get(element);
	}

}

package domainprojections.lib;

import java.util.HashMap;
import domainprojections.DomainProjection;

public class OneToOneDataProjection extends DomainProjection {

	private HashMap<Object,Object> map = new HashMap<>();
	private String name;
	
	public OneToOneDataProjection(String name) {
		this.name = name;
	}
	
	@Override
	public String getName(Object o) {
		try {
			return this.map.get(o).toString();
		} catch (Exception ex) {
			return "";
		}
	}

	@Override
	public String getProjectionName() {
		return name;
	}

	public void addEntry(Object requirement, String mappedConcept) {
		
	}

	@Override
	public void addEntry(Object element, Object information) {
		this.map.put(element, information);
		
	}
	
	@Override
	public Object getInformation(Object element) {
		return map.get(element);
	}
}

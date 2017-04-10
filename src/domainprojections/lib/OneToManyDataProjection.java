package domainprojections.lib;

import java.util.HashMap;
import java.util.HashSet;

import domainprojections.DomainProjection;

public class OneToManyDataProjection extends DomainProjection {

	private HashMap<Object,HashSet<Object>> map = new HashMap<>();
	private String name;
	
	public OneToManyDataProjection(String name) {
		this.name = name;
	}
	

	@Override
	public String getProjectionName() {
		return name;
	}

	@Override
	public void addEntry(Object element, Object information) {
		this.map.put(element, (HashSet<Object>)information);
		
	}

	@Override
	public String getName(Object component) {
		try {
			return this.map.get(component).toString();
		} catch (Exception ex) {
			return "";
		}
	}

	@Override
	public Object getInformation(Object element) {
		return map.get(element);
	}
}

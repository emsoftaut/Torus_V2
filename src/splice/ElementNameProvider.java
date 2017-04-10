package splice;

import org.jgrapht.ext.ComponentNameProvider;

public class ElementNameProvider implements ComponentNameProvider<Object> {
	public String getName(Object component) {
		//TODO - reject is not R or C or null
		if(component!=null)
			return component.toString();
		return "";
	}
	
}

package splice;

import org.jgrapht.ext.ComponentNameProvider;

import utilities.ErrorCodes;

public class EdgeLabelProvider implements ComponentNameProvider<SpliceEdge> {
	public String getName(SpliceEdge component) {
		//TODO - reject is not R or C or null
		if(component==null)
			return ErrorCodes.OBJECT_IS_NULL;
		if(!(component instanceof SpliceEdge))
			return ErrorCodes.NO_LABEL_FOUND;
				
		SpliceEdge edge = (SpliceEdge) component;
		return edge.toString();
		
	}
	
}

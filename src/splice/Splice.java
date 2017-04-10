package splice;

import org.jgrapht.graph.*;

public class Splice extends DirectedMultigraph<Object,SpliceEdge>{	
	
	public Splice(){
		super (new ClassBasedEdgeFactory<>(SpliceEdge.class));
	}

	
	
}

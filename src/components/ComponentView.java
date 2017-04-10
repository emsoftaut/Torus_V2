package components;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;


/**
 * Models a contains-a relationship
 * @author rsinha
 *
 */
@SuppressWarnings("serial")
public class ComponentView extends DirectedAcyclicGraph<Object,DefaultEdge> {

	private Object root;
	public ComponentView() {
		super (DefaultEdge.class);
		
	}
	
	public Object getRoot() {
		return this.root;
	}
	
	public void setRoot(Object root) {
		this.root = root;
	}
	
	

}

package requirements;

import java.util.HashSet;

import org.jgrapht.experimental.dag.DirectedAcyclicGraph;
import org.jgrapht.graph.DefaultEdge;


/**
 * Models a DAG showing all the trees in the requirements forest.
 * @author rsinha
 *
 */
@SuppressWarnings("serial")
public class RequirementsForest extends DirectedAcyclicGraph<Object,DefaultEdge> {

	private HashSet<Object> treeRoots = new HashSet<>();
	public RequirementsForest() {
		super (DefaultEdge.class);		
	}
	public HashSet<Object> getTreeRoots() {
		return treeRoots;
	}
	public void setTreeRoots(HashSet<Object> treeRoots) {
		this.treeRoots = treeRoots;
	}
	

}

package algorithms;

import splice.*;
import java.util.*;

import org.jgrapht.Graphs;

public class SpliceRefactoring {
	public static HashSet<Splice> refactor(Collection<Splice> spliceSet, HashSet<HashSet<Object>> elementPartitioning) {
		HashSet<Splice> retSpliceSet = new HashSet<>();
		HashMap<HashSet<Object>,HashSet<Splice>> map = new HashMap<>();
		for(Splice sp: spliceSet) {
			for(HashSet<Object> partition: elementPartitioning) {
				if(!Collections.disjoint(sp.vertexSet(), partition)) {
					HashSet<Splice> valueSet = map.get(partition);
					if(valueSet==null) 
						valueSet = new HashSet<>();
					valueSet.add(sp);
					map.put(partition, valueSet);
				}
			}
		}
		for(HashSet<Object> partition: elementPartitioning) {
			Splice newSplice = new Splice();
			for(Splice sp: map.get(partition)) {
				Graphs.addGraph(newSplice, sp);
			}
			retSpliceSet.add(newSplice);
		}		
		return retSpliceSet;
	}
}

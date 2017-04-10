package frameworks.ea_61850;

import java.util.Comparator;
import java.util.HashSet;


public class HashSetSubsetMatcher implements Comparator<Object> {

	/**
	 * o1 is a HashSet<Object> and o2 another set
	 * return 0 if o1 is a superset of o2
	 */
	@Override
	public int compare(Object o1, Object o2) {
		// returns 0 only if two objects are equal
		HashSet<Object> set = (HashSet<Object>) o1;
		HashSet<Object> set2 = (HashSet<Object>) o2;
		return set.containsAll(set2)?0:-1;
	}
}

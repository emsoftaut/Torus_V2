package frameworks.ea_61850;

import java.util.Comparator;
import java.util.HashSet;


public class HashSetMembershipMatcherMatcher implements Comparator<Object> {

	/**
	 * o1 is a HashSet<Object> and o2 is an object
	 */ 
	@Override
	public int compare(Object o1, Object o2) {
		//System.out.println("Trying to match:"+o1+" and "+o2);
		HashSet<Object> set = (HashSet<Object>) o1;
		return set.contains(o2)?0:-1;
	}
}

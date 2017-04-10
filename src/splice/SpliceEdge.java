package splice;

import org.jgrapht.graph.*;

public class SpliceEdge extends DefaultEdge{
	private Object v1;
	private Object v2;
	private String edgeLabel;
	
	public SpliceEdge(Object v1, Object v2, String label) {
		this.v1 = v1;
		this.v2 = v2;
		this.edgeLabel = label;
	}
	
	public Object getV1() {
        return v1;
    }

    public Object getV2() {
        return v2;
    }

    public String toString() {
        return edgeLabel;
    }


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpliceEdge other = (SpliceEdge) obj;
		if (edgeLabel == null) {
			if (other.edgeLabel != null)
				return false;
		} else if (!edgeLabel.equals(other.edgeLabel))
			return false;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		return true;
	}
    
    

}

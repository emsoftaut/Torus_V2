package frameworks.ea_61850;

import java.util.HashSet;

import org.jgrapht.graph.DefaultEdge;

import frameworks.RequirementsFramework;
import requirements.RequirementsForest;

public class CaseStudyRequirementsForest {
	
	private static CESARRequirementsFramework sampleFramework = new CESARRequirementsFramework();
	//private static RequirementsForest forest = new RequirementsForest();
//	private static CESARDomainProjectionHandler projectionHandler = new CESARDomainProjectionHandler();
	
	static {
		try{
			sampleFramework.getRequirementsForest().addVertex("R1");
			sampleFramework.getRequirementsForest().addVertex("R1_1");
			sampleFramework.getRequirementsForest().addVertex("R1_2");
			sampleFramework.getRequirementsForest().addEdge("R1", "R1_1");
			sampleFramework.getRequirementsForest().addDagEdge("R1", "R1_2");	
			
			sampleFramework.getRequirementsForest().addVertex("R2");
			sampleFramework.getRequirementsForest().addVertex("R2_1");
			sampleFramework.getRequirementsForest().addVertex("R2_2");
			sampleFramework.getRequirementsForest().addEdge("R2", "R2_1");
			sampleFramework.getRequirementsForest().addDagEdge("R2", "R2_2");	
			
			sampleFramework.getRequirementsForest().addVertex("R3");
			sampleFramework.getRequirementsForest().addVertex("R3_1");
			sampleFramework.getRequirementsForest().addVertex("R3_2");
			sampleFramework.getRequirementsForest().addEdge("R3", "R3_1");
			sampleFramework.getRequirementsForest().addDagEdge("R3", "R3_2");	
			
			sampleFramework.getRequirementsForest().addVertex("R4");
			sampleFramework.getRequirementsForest().addVertex("R4_1");
			sampleFramework.getRequirementsForest().addVertex("R4_2");
			sampleFramework.getRequirementsForest().addEdge("R4", "R4_1");
			sampleFramework.getRequirementsForest().addDagEdge("R4", "R4_2");	
			
			//domain projections
			HashSet<String> r1set = new HashSet<>();
			r1set.add("IED64");
			r1set.add("Vearth");
			r1set.add("Q1");
			r1set.add("Q2");
			sampleFramework.extractInformationFor("R1",r1set);
			HashSet<String> r11set = new HashSet<>();
			r11set.add("IED64");
			r11set.add("Q1");
			r11set.add("SW01");
			sampleFramework.extractInformationFor("R1_1",r11set);
			HashSet<String> r12set = new HashSet<>();
			r12set.add("IED64");
			r12set.add("Q2");
			r12set.add("SW02");
			sampleFramework.extractInformationFor("R1_2",r12set);
			HashSet<String> r2set = new HashSet<>();
			r2set.add("IED87");
			r2set.add("I1");
			r2set.add("I2");
			r2set.add("Ipickup");
			r2set.add("Q1");
			r2set.add("Q2");
			sampleFramework.extractInformationFor("R2",r2set);
			HashSet<String> r21set = new HashSet<>();
			r21set.add("IED87");
			r21set.add("Q1");
			r21set.add("SW01");			
			sampleFramework.extractInformationFor("R2_1",r21set);
			HashSet<String> r22set = new HashSet<>();
			r22set.add("IED87");
			r22set.add("Q2");
			r22set.add("SW02");
			sampleFramework.extractInformationFor("R2_2",r22set);
			HashSet<String> r3set = new HashSet<>();
			r3set.add("IED50");
			r3set.add("I1");
			r3set.add("I2");
			r3set.add("Q1");
			r3set.add("Q2");
			sampleFramework.extractInformationFor("R3",r3set);
			HashSet<String> r31set = new HashSet<>();
			r31set.add("IED50");
			r31set.add("Q1");
			r31set.add("SW01");		
			sampleFramework.extractInformationFor("R3_1",r31set);
			HashSet<String> r32set = new HashSet<>();
			r32set.add("IED50");
			r32set.add("Q2");
			r32set.add("SW02");	
			sampleFramework.extractInformationFor("R3_2",r32set);
			HashSet<String> r4set = new HashSet<>();
			r4set.add("IED50");
			r4set.add("I");
			r4set.add("Icurve");
			r4set.add("Q1");
			r4set.add("Q2");
			sampleFramework.extractInformationFor("R4",r4set);
			HashSet<String> r41set = new HashSet<>();
			r41set.add("IED50");
			r41set.add("Q1");
			r41set.add("SW01");
			sampleFramework.extractInformationFor("R4_1",r41set);
			HashSet<String> r42set = new HashSet<>();
			r42set.add("IED50");
			r42set.add("Q2");
			r42set.add("SW02");
			sampleFramework.extractInformationFor("R4_2",r42set);			
		} catch (Exception ex) {
			System.out.println("Something wrong with creating mock requirements");
		}
	}

	public static CESARRequirementsFramework getRequirementsForest() {
		return sampleFramework;
	}
	
	

}

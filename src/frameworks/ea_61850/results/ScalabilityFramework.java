package frameworks.ea_61850.results;

import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import org.jgraph.graph.DefaultEdge;

import frameworks.ComponentFramework;
import frameworks.ReqCompFramework;
import frameworks.RequirementsFramework;
import frameworks.ea_61850.CESARRequirementsFramework;
import frameworks.ea_61850.CaseStudyRequirementsForest;
import frameworks.ea_61850.IEC61850_61499ComponentFramework;
import frameworks.ea_61850.REA_C61850Framework;
import parsers.inputs.IEC61850ToComponentViewParser;

public class ScalabilityFramework implements ReqCompFramework {
	
	
	ScalabilityComponentFramework myComponentFramework = new ScalabilityComponentFramework();
	CESARRequirementsFramework myRequirementsFramework = new CESARRequirementsFramework();
	private int nodeCount = 0;
	private int reqCount = 0;
	
	public ScalabilityFramework(
			int numberOfComponentNames,
			int depthOfComponentTree, 
			int maxOutDegreeOfComponents, 
			int numberOfRequirementTrees, 
			int maxDepthOfRequirementTree, 
			int maxOutDegreeOfrequirements) {
		try{
			//CESARRequirementsFramework requirementsFramework = CaseStudyRequirementsForest.getRequirementsForest();
			createComponentFramework(numberOfComponentNames,depthOfComponentTree,maxOutDegreeOfComponents);
			createRequirementsFramework(numberOfComponentNames,numberOfRequirementTrees,maxDepthOfRequirementTree,maxOutDegreeOfrequirements);
	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void createRequirementsFramework(int numberOfConcepts, int numberOfRequirementTrees,
			int maxTreeDepth, int maxOutDegree) throws Exception {
		for(int i=0; i<numberOfRequirementTrees; i++) {
			createRequirementsTree(numberOfConcepts,maxTreeDepth,maxOutDegree);
		}
	}

	private void createRequirementsTree(int numberOfConcepts, int maxTreeDepth, int maxOutDegree) 
		throws Exception {
		int depth = ThreadLocalRandom.current().nextInt(0, maxTreeDepth+1);
		String treeRoot = "R"+ reqCount;
		reqCount++;
		recurseAndCreateRequirementNode(treeRoot,numberOfConcepts,maxOutDegree,depth);
		
	}

	private void recurseAndCreateRequirementNode(Object req, int numberOfConcepts, int maxOutDegree, int depth) 
		throws Exception{
		
		
		this.myRequirementsFramework.getRequirementsForest().addVertex(req);
		HashSet<Object> concepts = new HashSet<>();
		for(int j=0; j<numberOfConcepts; j++) {
			int conceptIndex = ThreadLocalRandom.current().nextInt(0, numberOfConcepts);
			concepts.add("N"+conceptIndex);
		}
		this.myRequirementsFramework.extractInformationFor(req, concepts);
		
		if(depth==0)
			return;
		
		int outDegree = ThreadLocalRandom.current().nextInt(0, maxOutDegree);
		for(int i=0; i<outDegree; i++) {
			Object childReq = "R"+reqCount;
			reqCount++;
			recurseAndCreateRequirementNode(childReq, numberOfConcepts, maxOutDegree, depth-1);
			this.myRequirementsFramework.getRequirementsForest().addDagEdge(req, childReq);
		}
		
	}

	private void createComponentFramework(
			int numberOfComponentNames,
			int depthOfComponentTree,
			int maxOutDegreeOfComponents) {
		//IEC61850_61499ComponentFramework retFramework = new IEC61850_61499ComponentFramework();
		Object root = "root";
		this.myComponentFramework.getComponentTree().addVertex(root);
		this.myComponentFramework.getComponentTree().setRoot(root);
		this.myComponentFramework.extractInformationFor(root, "N"+ThreadLocalRandom.current().nextInt(0, numberOfComponentNames));
		try{
			recurseAndCreateComponentTree(root,numberOfComponentNames,depthOfComponentTree-1,maxOutDegreeOfComponents);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private void recurseAndCreateComponentTree(
			Object sourceNode,
			int numberOfComponentNames,
			int depth, 
			int maxOutDegree) 
	throws Exception{
		if(depth==0)
			return;
		int randomNum = ThreadLocalRandom.current().nextInt(0, depth+1);
		for(int i=0; i<randomNum; i++) {
			Object childNode = "n"+(nodeCount);
			nodeCount++;
			this.myComponentFramework.getComponentTree().addVertex(childNode);
			this.myComponentFramework.extractInformationFor(childNode, "N"+ThreadLocalRandom.current().nextInt(0, numberOfComponentNames));
			this.myComponentFramework.getComponentTree().addDagEdge(sourceNode, childNode);
			recurseAndCreateComponentTree(childNode, numberOfComponentNames,depth-1, maxOutDegree);
		}
	}

	@Override
	public ComponentFramework getComponentFramework() {
		return this.myComponentFramework;
	}

	@Override
	public RequirementsFramework getRequirementsFramework() {
		return this.myRequirementsFramework;
	}
	



}

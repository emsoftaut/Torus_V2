package frameworks.ea_61850.results;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import algorithm.splicegeneration.*;
import frameworks.ea_61850.HashSetMembershipMatcherMatcher;
import frameworks.ea_61850.HashSetSubsetMatcher;
import frameworks.ea_61850.REA_C61850Framework;
import splice.Splice;
import splice.SpliceEdge;
import parsers.outputs.*;

public class SpliceCreation {
	
	private REA_C61850Framework reqCompFramework;
	private int requirementProjectionsIndex;
	private int componentProjectionsIndex;
	
	/**
	 * constructs the splice creator object
	 * r- and c-index identify which domain projections to use while splice creation
	 * @param reqCompFramework
	 * @param rIndex
	 * @param cIndex
	 */
	public SpliceCreation(REA_C61850Framework reqCompFramework, int rIndex, int cIndex){
		this.reqCompFramework = reqCompFramework;
		this.requirementProjectionsIndex = rIndex;
		this.componentProjectionsIndex = cIndex;
	}
	
	/**
	 * creates req-req splices based on 
	 * @return
	 */
	public HashSet<Splice> createReqToReqSplices() {
		HashSet<Splice> retSpliceSet = new HashSet<>();		
		//MatchingBasedSpliceCreation.createSpliceFrom(reqSet, componentSet, reqProjection, compProjection, dataMatcher, orderProvider)
		Splice s = MatchingBasedSpliceCreation.createSpliceFrom(
					this.reqCompFramework.getRequirementsFramework().getRequirementsForest().vertexSet(), 
					this.reqCompFramework.getRequirementsFramework().getRequirementsForest().vertexSet(), 
					this.reqCompFramework.getRequirementsFramework().getProjection(this.requirementProjectionsIndex), 
					this.reqCompFramework.getRequirementsFramework().getProjection(this.requirementProjectionsIndex),
					new HashSetSubsetMatcher(),
					this.reqCompFramework.getRequirementsFramework());
		retSpliceSet.add(s);		
		return retSpliceSet;		
	}
	
	public HashSet<Splice> createSplicesOnePerRequirement() {
		
		HashSet<Splice> retSpliceSet = new HashSet<>();		
		Collection<Object> requirements = this.reqCompFramework.getRequirementsFramework().getRequirementsForest().vertexSet();
		HashSet<Object> reqSet = new HashSet<>();
		for(Object req:requirements) {
			reqSet.add(req);
			//MatchingBasedSpliceCreation.createSpliceFrom(reqSet, componentSet, reqProjection, compProjection, dataMatcher, orderProvider)
			Splice s = MatchingBasedSpliceCreation.createSpliceFrom(
					reqSet, 
					this.reqCompFramework.getComponentFramework().getComponentTree().vertexSet(), 
					this.reqCompFramework.getRequirementsFramework().getProjection(this.requirementProjectionsIndex), 
					this.reqCompFramework.getComponentFramework().getProjection(this.componentProjectionsIndex),
					new HashSetMembershipMatcherMatcher(), 
					null);
			retSpliceSet.add(s);		
			reqSet.remove(req);
		}
		return retSpliceSet;		
	}
	
	public HashSet<Splice> createSplicesOnePerComponent() {
		
		HashSet<Splice> retSpliceSet = new HashSet<>();		
		Collection<Object> requirements = this.reqCompFramework.getRequirementsFramework().getRequirementsForest().vertexSet();
		Collection<Object> components = this.reqCompFramework.getComponentFramework().getComponentTree().vertexSet();
		HashSet<Object> compSet = new HashSet<>();
		for(Object component:components) {
			compSet.add(component);
			//MatchingBasedSpliceCreation.createSpliceFrom(reqSet, componentSet, reqProjection, compProjection, dataMatcher, orderProvider)
			Splice s = MatchingBasedSpliceCreation.createSpliceFrom(
					requirements, 
					compSet, 
					this.reqCompFramework.getRequirementsFramework().getProjection(this.requirementProjectionsIndex), 
					this.reqCompFramework.getComponentFramework().getProjection(this.componentProjectionsIndex),
					new HashSetMembershipMatcherMatcher(), 
					null);
			if(s.vertexSet().contains(component))
				retSpliceSet.add(s);		
			compSet.remove(component);
		}
		return retSpliceSet;		
	}
	
	public HashSet<Splice> createOneSplicePerRequirementsTree() {
		HashSet<Splice> spliceSet = new HashSet<>();
		Set<Object> reqSet = this.reqCompFramework.getRequirementsFramework().getRequirementsForest().vertexSet();
		Iterator<Object> iterator = reqSet.iterator();
		while(iterator.hasNext()) {
			Object req = iterator.next();
			if(this.reqCompFramework.getRequirementsFramework().getRequirementsForest().inDegreeOf(req)!=0) {
				continue;
			}
			HashSet<Object> reqTreeNodes = new HashSet<>();
			reqTreeNodes.add(req);
			DepthFirstIterator<Object,DefaultEdge> dfI = new DepthFirstIterator<>(this.reqCompFramework.getRequirementsFramework().getRequirementsForest(),req);
			while(dfI.hasNext()) {
				Object childReq = dfI.next();
				//reqSet.remove(childReq);
				reqTreeNodes.add(childReq);
			}
			spliceSet.add(MatchingBasedSpliceCreation.createSpliceFrom(
					reqTreeNodes, 
					this.reqCompFramework.getComponentFramework().getComponentTree().vertexSet(),
					this.reqCompFramework.getRequirementsFramework().getProjection(this.requirementProjectionsIndex), 
					this.reqCompFramework.getComponentFramework().getProjection(this.componentProjectionsIndex),
					new HashSetMembershipMatcherMatcher(), 
					null));
			
		}
		return spliceSet;
	}
	
	
	public Splice createSingleSplice() {
		return MatchingBasedSpliceCreation.createSpliceFrom(
			this.reqCompFramework.getRequirementsFramework().getRequirementsForest().vertexSet(), 
			this.reqCompFramework.getComponentFramework().getComponentTree().vertexSet(), 
			this.reqCompFramework.getRequirementsFramework().getProjection(this.requirementProjectionsIndex), 
			this.reqCompFramework.getComponentFramework().getProjection(this.componentProjectionsIndex),
			new HashSetMembershipMatcherMatcher(), 
			null);		
	}
	
	public static void main(String[] args) {
		System.out.println("r1_1".compareTo("r1"));
		System.out.println("r1".compareTo("r1_2"));
		
		REA_C61850Framework caseStudyInformation = REA_C61850Framework.getCaseStudyInformation();
		SpliceCreation tc = new SpliceCreation(caseStudyInformation,
				REA_C61850Framework.REQ_TO_COMPS_PROJECTION_INDEX,
				REA_C61850Framework.COMP_TO_NAME_PROJECTION_INDEX);
		
		//0: single splice for whole system
		//1: single splice per requirement
		//2: one splice per component
		//3: one splice per reqTree
		//4: req-req splices
		//5: all and then fuse
		int choice = 5;
		
		switch(choice){
		case 0: //One splice for the whole system
			Splice singleSplice = tc.createSingleSplice();
			SpliceToGraphViz.exportSpliceToFile(singleSplice, utilities.Paths.outputPath+"caseStudySingleSplice.gv");				
			System.out.println("Found vertices:edges"+singleSplice.vertexSet().size()+":"+singleSplice.edgeSet().size());
			break;
		
		case 1: //one splice per requirement
			HashSet<Splice> splices = tc.createSplicesOnePerRequirement();
			int i = 0;
			int numberOfVertices = 0;
			int numberOfEdges = 0;
			for(Splice splice: splices) {
				SpliceToGraphViz.exportSpliceToFile(splice, utilities.Paths.outputPath+"splCreateOnePerReq"+i+".gv");				
				numberOfVertices += splice.vertexSet().size();
				numberOfEdges += splice.edgeSet().size();
				i++;
			}
			System.out.println("Found vertices:edges"+numberOfVertices+":"+numberOfEdges);
			break;
		case 2: //one splice per component
			splices = tc.createSplicesOnePerComponent();
			i = 0;
			numberOfVertices = 0;
			numberOfEdges = 0;
			for(Splice splice: splices) {
				SpliceToGraphViz.exportSpliceToFile(splice, utilities.Paths.outputPath+"splCreateOnePerComp"+i+".gv");				
				numberOfVertices += splice.vertexSet().size();
				numberOfEdges += splice.edgeSet().size();
				i++;
			}
			System.out.println("Found vertices:edges"+numberOfVertices+":"+numberOfEdges);
			break;
		case 3: //one splice per reqTree
			splices = tc.createOneSplicePerRequirementsTree();
			i = 0;
			numberOfVertices = 0;
			numberOfEdges = 0;
			for(Splice splice: splices) {
				SpliceToGraphViz.exportSpliceToFile(splice, utilities.Paths.outputPath+"oneSplicePerRTree"+i+".gv");				
				numberOfVertices += splice.vertexSet().size();
				numberOfEdges += splice.edgeSet().size();
				i++;
			}
			System.out.println("Found vertices:edges"+numberOfVertices+":"+numberOfEdges);
			break;
		
		case 4: //create req-req splices
			splices = tc.createReqToReqSplices();
			i = 0;
			numberOfVertices = 0;
			numberOfEdges = 0;
			for(Splice splice: splices) {
				SpliceToGraphViz.exportSpliceToFile(splice, utilities.Paths.outputPath+"reqtoreq"+i+".gv");				
				numberOfVertices += splice.vertexSet().size();
				numberOfEdges += splice.edgeSet().size();
				i++;
			}
			System.out.println("Found vertices:edges"+numberOfVertices+":"+numberOfEdges);
			break;
		case 5: //all and then fuse
			HashSet<Splice> spliceSet = new HashSet<>();
			spliceSet.add(tc.createSingleSplice());
			spliceSet.addAll(tc.createSplicesOnePerRequirement());
			spliceSet.addAll(tc.createSplicesOnePerComponent());
			spliceSet.addAll(tc.createOneSplicePerRequirementsTree());
			spliceSet.addAll(tc.createReqToReqSplices());
			i = 0;
			numberOfVertices = 0;
			numberOfEdges = 0;
			Splice finalSplice = new Splice();
			for(Splice splice: spliceSet) {
				Graphs.addGraph(finalSplice, splice);
			}

			
			//remove duplicate edges
			HashSet<SpliceEdge> toRemove = new HashSet<>();
			for(Object node: finalSplice.vertexSet()) {
				HashSet<SpliceEdge> edgeSet = new HashSet<>();
				for(SpliceEdge e: finalSplice.outgoingEdgesOf(node)) {
					System.out.println("checking edge:"+e);
					for(SpliceEdge e2: edgeSet) {
						if(e2.equals(e)) {
							toRemove.add(e);
							continue;
						}
					}
					edgeSet.add(e);
				}
			}
			finalSplice.removeAllEdges(toRemove);
			
			SpliceToGraphViz.exportSpliceToFile(finalSplice, utilities.Paths.outputPath+"fused.gv");				
			System.out.println("Found vertices:edges"+finalSplice.vertexSet().size()+":"+finalSplice.edgeSet().size());
			break;
		}
	}


}

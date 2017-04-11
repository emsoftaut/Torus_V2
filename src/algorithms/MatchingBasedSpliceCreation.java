package algorithms;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import domainprojections.*;
import requirements.RequirementsOrderProvider;
import splice.*;
import utilities.ErrorCodes;

public final class MatchingBasedSpliceCreation {
	
	/*
	public static Splice createSpliceFrom(Collection<Object> reqSet, Collection<Object> componentSet, DomainProjection reqProjection, DomainProjection compProjection, Comparator<Object> reqDataMatcher, Comparator<Object> reqCompDataMatcher, RequirementsOrderProvider orderProvider) {
		//first generate the concept maps if not done already
		Splice retSplice = new Splice();		
		for(Object req: reqSet) {
			Object reqConcept = reqProjection.getInformation(req);			
			for(Object req2: reqSet) {
				Object req2Concept = reqProjection.getInformation(req2);				
				if((reqDataMatcher.compare(reqConcept,req2Concept)==0)&&orderProvider.canHaveDirectedEdgeBetween(req, req2)) {
					System.out.println("req=req");
					retSplice.addVertex(req);
					retSplice.addVertex(req2);
					retSplice.addEdge(req, req2, new SpliceEdge(req,req2,reqConcept.toString()));
				}
			}
			for(Object comp: componentSet) {
				Object compConcept = compProjection.getInformation(comp);
				if(reqCompDataMatcher.compare(reqConcept,compConcept)==0) {
					System.out.println("Found match req=comp");
					retSplice.addVertex(req);
					retSplice.addVertex(comp);
					retSplice.addEdge(req, comp, new SpliceEdge(req,comp,reqConcept.toString()));
				}
			}
			
		}
		return retSplice;
	}	
	
	*/
	
	public static Splice createSpliceFrom(Collection<Object> sourceSet, 
			Collection<Object> targetSet, 
			DomainProjection sourceProjection, 
			DomainProjection targetProjection,
			Comparator<Object> projectionDataMatcher, 
			RequirementsOrderProvider orderProvider) {
		//first generate the concept maps if not done already
		Splice retSplice = new Splice();		
		for(Object sourceElement: sourceSet) {
			Object sourceProjectionData = sourceProjection.getInformation(sourceElement);			
			for(Object targetElement: targetSet) {
				Object targetProjectionData = targetProjection.getInformation(targetElement);				
				if((projectionDataMatcher.compare(sourceProjectionData,targetProjectionData)==0)){
					if((orderProvider==null)||(orderProvider.canHaveDirectedEdgeBetween(sourceElement, targetElement))) {
						//System.out.println("found match:"+sourceElement+" and "+targetElement);
						retSplice.addVertex(sourceElement);
						retSplice.addVertex(targetElement);
						retSplice.addEdge(sourceElement, targetElement, new SpliceEdge(sourceElement,targetElement,sourceProjectionData.toString()));
					}
				}
			}
		}
		return retSplice;
	}	
	
	
}

package frameworks.ea_61850;

import java.util.*;
import components.ComponentView;
import requirements.RequirementsForest;
import requirements.RequirementsOrderProvider;
import domainprojections.DomainProjection;
import frameworks.ComponentFramework;
import frameworks.ReqCompFramework;
import frameworks.RequirementsFramework;
import parsers.inputs.IEC61850ToComponentViewParser;


public class REA_C61850Framework implements ReqCompFramework {

	public static final int REQ_TO_COMPS_PROJECTION_INDEX = 0;
	public static final int COMP_TO_TYPE_PROJECTION_INDEX = 0;
	public static final int COMP_TO_NAME_PROJECTION_INDEX = 1;
	
	
	private IEC61850_61499ComponentFramework myComponentFramework;
	private CESARRequirementsFramework myRequirementsFramework;
		
	
	public REA_C61850Framework(IEC61850_61499ComponentFramework componentFramework,
			CESARRequirementsFramework requirementsFramework
			) {
		this.myComponentFramework = componentFramework;
		this.myRequirementsFramework = requirementsFramework;	
	}



	
	
	public static REA_C61850Framework getCaseStudyInformation() {
		CESARRequirementsFramework requirementsFramework = CaseStudyRequirementsForest.getRequirementsForest();
		IEC61850ToComponentViewParser ssdParser = new IEC61850ToComponentViewParser();
		ssdParser.parseSSDWithIgnore("./in/Substation_130kv_ver 01.xml");
		REA_C61850Framework myFramework = new REA_C61850Framework(
				ssdParser.getComponentFramework(),
				requirementsFramework);
		return myFramework;
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

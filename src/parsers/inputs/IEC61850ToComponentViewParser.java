//
// SUBSTATION CONFIGURATION LANGUAGE ANALYZER
// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// This class implements an interface to the an IEC 61850-compliant XML-format file that contains a configuration of
// a Smart Grid substation. 
//
// AUT University - 2017 - Roopak Sinha and Barry Dowdeswell
//
// Documentation
// ~~~~~~~~~~~~~
// Electrical sub-stations can be described using the IEC 61850 Substation Control Language. Each bay within a substation
// can be represented with elements that detail the attributes of individual components such as switches, transformers and 
// other electro-mechanical switchgear. A Single Line Diagram (SLD) is typically created in compliant IEC 61850 substation
// development tools. When the design is complete, these tools are able to export the an SSD file which can be read by TORUS.
//
// The current version of this class only saves instances of the Intelligent Electronic Devices (IED's) found in the SSD file.
// IED's are used in the Substation Control Language to represent electronic Smart Grid protection devices. Note that in the SSD
// file, these are mostly placeholders that identify only the name and type of the IED. The full IEC 61499 function block 
// application implementation is stored externally. 
//
//
// Revision History
// ~~~~~~~~~~~~~~~~
// 06.03.2017 BRD Original version.
//
package parsers.inputs;
import org.jgrapht.Graphs;
//
// XML support packages
// ~~~~~~~~~~~~~~~~~~~~
import org.w3c.dom.*;

import components.ComponentView;
import frameworks.ea_61850.IEC61850_61499ComponentFramework;
import parsers.outputs.ComponentViewToGraphViz;
import utilities.Paths;

import javax.xml.parsers.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import java.io.File;

//
// List support packages
// ~~~~~~~~~~~~~~~~~~~~~
import java.util.HashMap;
import java.util.HashSet;


public class IEC61850ToComponentViewParser{

	//what I will return
	//private ComponentView myTree;
	private IEC61850_61499ComponentFramework myComponentFramework = new IEC61850_61499ComponentFramework();
	
	//statics
	private static HashMap<Integer,String> levelToXPathExpressionMap = new HashMap<>();
	private static HashSet<String> attributeNames = new HashSet<>();
	private static String defaultName = "system";

	static {
		levelToXPathExpressionMap.put(1, ".//Substation");
		levelToXPathExpressionMap.put(2, ".//ConductingEquipment|./*/*/*/*/*");
		attributeNames.add("name");
		attributeNames.add("iedName");
	}
	
	
	public void parseSSDWithIgnore(String fileName) {
		try {
			File ssd = new File(fileName);  
	                                       
			//load XML
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(ssd);
			//this.myComponentFramework.myTree = new ComponentView();

			this.myComponentFramework.getComponentTree().setRoot(processNode(doc.getDocumentElement(),1));
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * creates a node
	 * Uses level+1 recursion to get all nodes in the next level
	 * then creates edges between all nodes returned.
	 * @param root
	 * @param node
	 * @param level
	 */
	private Object processNode(Node node, int level) throws Exception {
		
		String vertex = createVertex(node);
		XPath xPath = XPathFactory.newInstance().newXPath();
		NodeList nodeList = (NodeList) xPath.compile(
				IEC61850ToComponentViewParser.levelToXPathExpressionMap.get(level)).
				evaluate(node, XPathConstants.NODESET);
		
		
		for(int i=0; i<nodeList.getLength(); i++) {
			final Node childNode = nodeList.item(i);
			Object childVertex = null;
			
			if(isIED(childNode)) {
				childVertex = processIEDNode(childNode);
			} else if(IEC61850ToComponentViewParser.levelToXPathExpressionMap.containsKey(level+1)){
				childVertex = processNode(childNode, level+1);
			} else {
				childVertex = createVertex(childNode);					
			}			
			this.myComponentFramework.getComponentTree().addDagEdge(vertex, childVertex);
		}		
		return vertex;		
	}
	
	private String createVertex(Node n) {
		String nodeName = getName(n);
		this.myComponentFramework.extractInformationFor(nodeName, n);
		this.myComponentFramework.getComponentTree().addVertex(nodeName);
		return nodeName;
	}
	
	private String getName(Node node) {
		for(String attribute: IEC61850ToComponentViewParser.attributeNames) {
			if(node.getAttributes().getNamedItem(attribute)!=null)
				return ((Element)node).getAttribute(attribute);
		}
		return IEC61850ToComponentViewParser.defaultName;
	}


	
	private Object processIEDNode(Node node) {
		try { 
			IEC61499ToComponentViewParser fbParser = new IEC61499ToComponentViewParser(((Element)node).getAttribute("iedName"),this.myComponentFramework);
			//Graphs.addGraph(this.myComponentFramework.getComponentTree(), iedParser.getTree());
			return fbParser.buildTree();
		} catch (Exception ex) {
			return "Error reading 61499 file";
		}
	}
	
	

	/**
	 * check if this node is an IED and hence may require 
	 * @param node
	 * @return
	 */
	private boolean isIED(Node node) {
		return (node.getNodeName().equals("LNode")
				&&
				(((Element)node).getAttribute("iedName")!=null)&&
				((Element)node).getAttribute("iedName").startsWith("IED"));
	}

	public IEC61850_61499ComponentFramework getComponentFramework() {
		return this.myComponentFramework;
	}


	
	
	public static void main(String[] args) {
		IEC61850ToComponentViewParser ssdParser = new IEC61850ToComponentViewParser();
		ssdParser.parseSSDWithIgnore("./in/Substation_130kv_ver 01.xml");
		//ComponentViewToGraphViz.exportComponentViewToFile(ssdParser.getTree(),ssdParser.getDataHandler().getNameToTypeProjection(),Paths.outputPath+"SCLToTreeWithTypes.gv");
		ComponentViewToGraphViz.exportComponentViewToFile(ssdParser.getComponentFramework().getComponentTree(),null,Paths.outputPath+"SCLToTreeWithNames.gv");
	}

	
}

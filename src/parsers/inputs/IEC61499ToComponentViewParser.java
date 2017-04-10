package parsers.inputs;

import java.io.File;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jgrapht.Graphs;
import org.w3c.dom.*;

import components.ComponentView;
import frameworks.ea_61850.IEC61850_61499ComponentFramework;
import parsers.outputs.ComponentViewToGraphViz;
import utilities.Paths;

public class IEC61499ToComponentViewParser {
	
	//ComponentView myTree;
	private File file;
	private static String fileExtension = ".fbt";
	private static HashMap<String,Integer> uniqueNameMap = new HashMap<>();
	IEC61850_61499ComponentFramework myFramework = null;
		
	public IEC61499ToComponentViewParser(String fileName, 
			IEC61850_61499ComponentFramework framework) throws Exception {
		//super();
		this.myFramework = framework;
		this.file = new File(utilities.Paths.inputFBPath+fileName+fileExtension);	
		if(!file.exists())
			throw new RuntimeException("File does not exist");
		//buildTree();
	}
	
			
	public Object buildTree() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        Element rootNode = doc.getDocumentElement();     
        //this.myTree = new ComponentView();
        Object rootVertex = parseByElementName(rootNode);
        this.myFramework.getComponentTree().addVertex(rootVertex);
        return rootVertex;
        //this.myTree.setRoot(rootVertex);
        
	}
	
	
	public Object parseByElementName(final Element element) throws Exception {
		
		System.out.println("Processing element :" + element.getNodeName());
		
		switch(element.getNodeName()) {
		case "FBType":
			Object vertex = IEC61499ToComponentViewParser.getNewName(element.getAttribute("Name"));
			this.myFramework.extractInformationFor(vertex, element);
			this.myFramework.getComponentTree().addVertex(vertex);
			NodeList childList = element.getChildNodes();
			for(int i=0; i<childList.getLength(); i++) {
				if(childList.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				Object childVertex = parseByElementName((Element)childList.item(i));
				if(childVertex!=null) {
					this.myFramework.getComponentTree().addVertex(childVertex);
					System.out.println("Adding dag edge:"+vertex+" to "+childVertex);
					this.myFramework.getComponentTree().addDagEdge(vertex, childVertex);
				}
			}
			return vertex;
		case "FBNetwork":
			vertex = IEC61499ToComponentViewParser.getNewName("FBNetwork");
			this.myFramework.extractInformationFor(vertex, element);
			this.myFramework.getComponentTree().addVertex(vertex);
			childList = element.getChildNodes();
			for(int i=0; i<childList.getLength(); i++) {
				if(childList.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				Object childVertex = parseByElementName((Element)childList.item(i));
				if(childVertex!=null) {
					this.myFramework.getComponentTree().addVertex(childVertex);
					System.out.println("Adding dag edge:"+vertex+" to "+childVertex);
					this.myFramework.getComponentTree().addDagEdge(vertex, childVertex);
				}
			}
			return vertex;
		case "FB":
			//
			String fileName = element.getAttribute("Type");
			System.out.println("Looking for file:"+fileName);
			try {
				IEC61499ToComponentViewParser convertFB = new IEC61499ToComponentViewParser(fileName,this.myFramework);		
				System.out.println("Converging graphs after processing:"+fileName);
				//Graphs.add
				//Graphs.addGraph(this.myFramework.getComponentTree(),convertFB.getComponentFramework().getComponentTree());
				return convertFB.buildTree();
			} catch (Exception ex) {
				System.out.println("Did not find:"+fileName);
				vertex = getNewName(fileName);
				//System.out.println("So returning:"+);
				this.myFramework.extractInformationFor(vertex, element);
				this.myFramework.getComponentTree().addVertex(vertex);
				return vertex;
			}	
		//only process BFBs for right now.
		case "BasicFB":
			childList = element.getChildNodes();
			Node childNode = null;
			for(int i=0; i<childList.getLength(); i++) {
				if(childList.item(i).getNodeName().equals("ECC")) {
					childNode = childList.item(i);		
					break;
				}
			}
			vertex = parseByElementName((Element)childNode);
			return vertex;
		case "ECC":
			vertex = IEC61499ToComponentViewParser.getNewName("ECC");
			this.myFramework.extractInformationFor(vertex, element);
			this.myFramework.getComponentTree().addVertex(vertex);
			childList = element.getChildNodes();
			for(int i=0; i<childList.getLength(); i++) {
				if(childList.item(i).getNodeType() != Node.ELEMENT_NODE)
					continue;
				Object childVertex = parseByElementName((Element)childList.item(i));
				if(childVertex!=null) {
					this.myFramework.getComponentTree().addVertex(childVertex);
					System.out.println("Adding dag edge:"+vertex+" to "+childVertex);
					this.myFramework.getComponentTree().addDagEdge(vertex, childVertex);
				}
			}
			return vertex;
		case "ECState":
			vertex = IEC61499ToComponentViewParser.getNewName(element.getAttribute("Name"));
			this.myFramework.extractInformationFor(vertex, element);
			this.myFramework.getComponentTree().addVertex(vertex);
			return vertex;
		default:
			return null;		
		
		}
			
	
	}
	
	private static String[] uniqueNameStarts = {"IED"};
	
	/**
	 * assigns a unique identified for a given name
	 * the unique identified ensures that we have different
	 * names for the components in the the component tree
	 * that may have the same names in the files.
	 * Also ensures that items that are supposed to have
	 * unique names (names starting with strings in uniqueNameStarts)
	 * do not have addition unique IDs.
	 * @param key
	 * @return
	 */
	private static String getNewName(String key) {
		for(int i=0; i<uniqueNameStarts.length; i++)
			if(key.startsWith(uniqueNameStarts[i]))
				return key;
					
		
		int count;
		if(uniqueNameMap.containsKey(key)) 
			count = uniqueNameMap.get(key);
		else
			count = -1;
		count++;
		uniqueNameMap.put(key, count);
		String ret = key+"_"+count;
		//System.out.println("New node:"+ret);
		return ret;
	}
	
	public IEC61850_61499ComponentFramework getComponentFramework() {
		return this.myFramework;
	}
	
	public static void main(String[] args) {
		try{
			IEC61499ToComponentViewParser converter = new IEC61499ToComponentViewParser("IED64",new IEC61850_61499ComponentFramework());
			ComponentViewToGraphViz.exportComponentViewToFile(converter.getComponentFramework().getComponentTree(),null,Paths.outputPath+"61499only.gv");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
		
}

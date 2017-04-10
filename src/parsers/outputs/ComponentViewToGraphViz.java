package parsers.outputs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.ext.ComponentNameProvider;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.graph.DefaultEdge;

import components.ComponentView;
import splice.EdgeLabelProvider;
import splice.ElementNameProvider;

public class ComponentViewToGraphViz {
	
	public static void exportComponentViewToFile(ComponentView tree, 
			ComponentNameProvider<Object> nodeLabelProvider, 
			String fileName) {
		try{
			File outputFile = new File(fileName);
			if(!outputFile.exists())
				outputFile.createNewFile();
			ElementNameProvider vProvider = new ElementNameProvider();
			//EdgeLabelProvider eProvider = new EdgeLabelProvider();
			FileWriter fileWriter = new FileWriter(outputFile);
			DOTExporter<Object,DefaultEdge> dotExporter = new DOTExporter<>(vProvider,nodeLabelProvider,null);
			dotExporter.exportGraph(tree, fileWriter);
		}catch(IOException ex) {
			System.out.println(ex);
		}
	}


}

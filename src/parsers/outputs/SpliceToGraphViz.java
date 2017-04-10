package parsers.outputs;

import org.jgrapht.ext.DOTExporter;
import splice.EdgeLabelProvider;
import splice.ElementNameProvider;
import splice.Splice;
import splice.SpliceEdge;

import java.io.*;

public class SpliceToGraphViz {
	
	/**
	 * exports splice to a filename - filename must include path
	 * @param sp
	 * @param fileName
	 */
	public static void exportSpliceToFile(Splice sp, String fileName) {
		try{
			File outputFile = new File(fileName);
			if(!outputFile.exists())
				outputFile.createNewFile();
			ElementNameProvider vProvider = new ElementNameProvider();
			EdgeLabelProvider eProvider = new EdgeLabelProvider();
			FileWriter fileWriter = new FileWriter(outputFile);
			DOTExporter<Object,SpliceEdge> dotExporter = new DOTExporter<>(vProvider,null,eProvider);
			dotExporter.exportGraph(sp, fileWriter);
		}catch(IOException ex) {
			System.out.println(ex);
		}
	}

}

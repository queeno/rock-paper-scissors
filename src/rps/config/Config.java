/*
 * This class reads the configuration and stores it in convenient data
 * structure.
 */

package rps.config;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import rps.ui.Cli;

public final class Config {
	
	private List<HashMap<String,String>> players;
	private String[] shapes;
	private int[][] shape_matrix;
	private int rounds;
	private boolean logging;
	
	private Cli ui;
	
	public String[] GetShapes(){
		return shapes;
	}
	
	public int[][] GetShapeMatrix(){
		return shape_matrix;
	}
	
	public List<HashMap<String,String>> GetPlayers(){
		return players;
	}
	
	public int GetRounds(){
		return rounds;
	}
	
	public boolean GetLogging(){
		return logging;
	}
	
	
	public Config(Cli ui, String[] args) {
		
		String config_file = "config.xml";
		String shapes_file = "shapes-base.csv";
		
		this.ui = ui;
	
		if (args.length == 1) {
			config_file = args[0];
		} else if (args.length > 1){
			config_file = args[0];
			shapes_file = args[1];
		}
		
		readConfig(config_file);
		readShapeMatrix(shapes_file);
		
		setLoggingLevel();
		
		Validate validate = new Validate(ui, shape_matrix, shapes);
		validate.validateShapeMatrix();

	}
	
	private void setLoggingLevel(){

		ui.SetPrintOutLog(logging);
		
	}

	private List<String[]> readShapeMatrixFile(String FileName){
		
		ui.PrintLog("Reading " + FileName);
		
		int no_shapes = -1;
		List<String[]> raw_shape_matrix = new ArrayList<String[]>();
		
		try {
			String line;
			File inputFile = new File(FileName);
		
			FileReader fr = new FileReader(inputFile);
			BufferedReader br = new BufferedReader(fr);

			while ((line = br.readLine()) != null) {
				if (no_shapes == -1){
					String[] line_split = line.replace("\"", "").split(",");
					no_shapes = line_split.length;
				}
				
				String[] line_split = line.replace("\"", "").split(",", no_shapes);
				raw_shape_matrix.add(line_split);
			}

			br.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return raw_shape_matrix;
	}
	
	
	private void createShapeMatrix(List<String[]> raw_shape_matrix) {
		
		shapes = raw_shape_matrix.get(0);
		int no_shapes = shapes.length;
		
		shape_matrix = new int[no_shapes][no_shapes];
		
		for (int i=0; i < no_shapes; i++){
			for (int j=0; j < no_shapes; j++){

				String elem = raw_shape_matrix.get(i+1)[j];
				
				// One extra row is taken by names
				if (elem.equals("0")) {
					shape_matrix[i][j] = 0;
				} else if (elem.equals("1")) {
					shape_matrix[i][j] = 1;
				} else {
					shape_matrix[i][j] = -1;
				}	
			}
		}
		
	}
	
	private void readShapeMatrix(String shapeMatrixFile){
		List<String[]> raw_matrix = readShapeMatrixFile(shapeMatrixFile);
		createShapeMatrix(raw_matrix);
	}
	

	
	private void readConfig (String FileName){
		players = new ArrayList<HashMap<String,String>>();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			
			File inputFile = new File(FileName);
			
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			Document doc = db.parse(inputFile);
			
			Element rootElem = doc.getDocumentElement();
			
			// Get only the elements within the 'player' tag
			NodeList nodes = rootElem.getElementsByTagName("player");

			for (int i=0; i < nodes.getLength(); i++) {
				Element e = (Element) nodes.item(i);
				getPlayer(e);	
			}
			
			// Get only the elements within the 'rounds' tag
			NodeList rounds_tag = rootElem.getElementsByTagName("rounds");
			Element e_rounds = (Element) rounds_tag.item(0);
			
			rounds = Integer.parseInt(e_rounds.getFirstChild().getNodeValue());
			
			// Get only the elements within the 'logging' tag
			NodeList logging_tag = rootElem.getElementsByTagName("logging");
			Element e_logging = (Element) logging_tag.item(0);
			
			logging = Boolean.parseBoolean(e_logging.getFirstChild().getNodeValue());
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	private void getPlayer(Element player) {
		HashMap<String,String> dict = new HashMap<String,String>();
		
		// Get all the elements in 'player'
		NodeList nodes = player.getElementsByTagName("*");
		
		for (int i=0; i < nodes.getLength(); i++){
			Element e = (Element) nodes.item(i);
			dict.put(e.getTagName(), e.getFirstChild().getNodeValue());			
		}
		
		players.add(dict);
	}
	
}

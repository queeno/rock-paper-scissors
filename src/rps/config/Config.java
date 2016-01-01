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
	private HashMap<String,Integer> shapes;
	private int[][] shape_matrix;
	private int rounds;
	private boolean logging;
	
	private Cli ui;
	
	public HashMap<String,Integer> GetShapes(){
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
	
	
	public Config(Cli ui) {
		
		this.ui = ui;
	
		readConfig("config.xml");
		readShapeMatrix("shapes-base.csv");
		
		setLoggingLevel();
		
		Validate validate = new Validate(ui, shape_matrix, shapes.keySet().toArray(new String[shapes.size()]));
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
		
		String[] shape_names = raw_shape_matrix.get(0);
		
		int no_shapes = shape_names.length;

		shapes = new HashMap<String,Integer>();
		
		for (int i=0; i < no_shapes; i++) {
			shapes.put(shape_names[i], i);
		}
		
		
		shape_matrix = new int[no_shapes][no_shapes];
		
		for (int i=0; i < no_shapes; i++){
			for (int j=0; j < no_shapes; j++){
				
				// One extra row is taken by names
				switch (raw_shape_matrix.get(i+1)[j]) {
				case "0":
					shape_matrix[i][j] = 0;
					break;
				case "1":
					shape_matrix[i][j] = 1;
					break;
				default:
					shape_matrix[i][j] = -1;
					break;
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

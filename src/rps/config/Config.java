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


public final class Config {
	
	private List<HashMap<String,String>> players = new ArrayList<HashMap<String,String>>();
	private String[] shape_names;
	private int[][] shape_matrix;
	
	public String[] GetShapeNames(){
		return shape_names;
	}
	
	public int[][] GetShapeMatrix(){
		return shape_matrix;
	}
	
	public List<HashMap<String,String>> GetPlayers(){
		return players;
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
	
	
	private void readShapeMatrixFile(String FileName){
		
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
		
		shape_names = raw_shape_matrix.get(0);
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

	
	private boolean checkElements(int i, int j){	

		if (i == j) {
			if (shape_matrix[i][j] != -1){
				System.err.println(shape_matrix[i][j]);
				
				System.err.println("Shape Matrix, element [" + i + "][" + j
						+ "]" + " expected empty, got " +
						shape_matrix[j][i]);
				System.err.println("The " + shape_names[i] + " cannot beat itself.");
				return true;
			}
			else {
				return false;
			}
		} else {
			if (shape_matrix[i][j] == shape_matrix[j][i]){
				if (shape_matrix[i][j] == 0) {

					System.err.println("Shape Matrix, element [" + i + "][" + j
							+ "]" + " expected 1 got 0. "
							+ "The " + shape_names[i] +
							" cannot be defeated by the " + shape_names[j] + " and the "
							+ shape_names[j] + " cannot be defeated by the "
							+ shape_names[i]);
					return true;
				} else if (shape_matrix[i][j] == 1) {
					System.err.println("Shape Matrix, element [" + i + "][" + j
							+ "]" + " expected 0 got 1. "
							+ "The " + shape_names[i] +
							" cannot be beat the " + shape_names[j] +
							" and the " + shape_names[j] +
							" cannot beat the " + shape_names[i]);
					return true;
				}
			}
			else {
				if (shape_matrix[i][j] == 0){
					System.out.println("The " + shape_names[i] + " is defeated by the "
							+ shape_names[j]) ;
					return false;
				}
				else if (shape_matrix[i][j] == 1) {
					System.out.println("The " + shape_names[i] + " beats the "
							+ shape_names[j]);
					return false;
				}
			}
		}

		// This point should be never reached.
		return true;
	}
	
	private void validateShapeMatrix(){
		Boolean error = false;
		System.out.println("Starting shape matrix validation...");
		System.out.flush();

		for (int i=0; i<shape_matrix.length; i++){
			for (int j=0; j<shape_matrix.length; j++){
				if (checkElements(i,j)) error = true ;
				System.err.flush();
			}
		}

		if (error) {
			System.err.flush();
			System.err.println("The shape matrix cannot be validated. Terminating...");
			System.exit(1);
		} else {
			System.out.flush();
			System.out.println("The shape matrix has been validated!");
		}
		
	}
	
	private void readShapeMatrix(String shapeMatrixFile){
		readShapeMatrixFile(shapeMatrixFile);
		validateShapeMatrix();
	}
	
	public void Init() {
		readPlayerConfig("players.xml");
		readShapeMatrix("game-base.csv");	
	}
	
	private void readPlayerConfig (String FileName){
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
		
		} catch(Exception e){
			e.printStackTrace();
		}

		
		
	}
	
}

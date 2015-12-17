/*
 * This class reads the configuration and stores it in convenient data
 * structure.
 */

package rps.config;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public final class Config {
	
	public static List<HashMap<String,String>> players = new ArrayList<HashMap<String,String>>();
	
	
	private static void getPlayer(Element player) {
		HashMap<String,String> dict = new HashMap<String,String>();
		
		// Get all the elements in 'player'
		NodeList nodes = player.getElementsByTagName("*");
		
		for (int i=0; i < nodes.getLength(); i++){
			Element e = (Element) nodes.item(i);
			dict.put(e.getTagName(), e.getFirstChild().getNodeValue());			
		}
		
		players.add(dict);
		
		System.out.println(players);
	}
	
	
	public static void Init() {
		readPlayerConfig("players.xml");
	}
	
	private static void readPlayerConfig (String FileName){
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

/*
 * This class implements a Player.
 */

package rps.game;

import java.util.Random;
import java.util.HashMap;

public class Player implements Players {

	private String name;
	private boolean automated;
	
	private int score;
	
	public Player(String name, boolean automated) {
		this.name = name;
		this.automated = automated;
		this.score = 0;
	}
	
	public int makeChoice(HashMap<String,Integer> choices) {
		
		if (automated) {
			Random rn = new Random();
			int choice = rn.nextInt(choices.size());
			return choice;
		}
		
		return -1;
		
	}
	
	public int getScore(){
		return score;
	}
	
	public void wins(){
		score++;
	}
	
	public boolean isAutomated(){
		return automated;
	}
	
	public String getName(){
		return name;
	}
	
}

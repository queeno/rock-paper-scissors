/*
 * This class implements a Player.
 */

package rps.game;

public class Player implements Players {

	private String name;
	private boolean automated;
	
	private int score;
	
	public Player(String name, boolean automated) {
		this.name = name;
		this.automated = automated;
		this.score = 0;
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

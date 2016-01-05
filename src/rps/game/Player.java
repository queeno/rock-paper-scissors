/*
 * This class implements a Player.
 */

package rps.game;

import java.util.Random;
import java.util.Arrays;

import rps.ui.Cli;

import utils.Utils;

public class Player implements Players {

	private String name;
	private boolean automated;
	
	private int score;
	private Cli ui;
	
	public Player(Cli ui, String name, boolean automated) {
		this.name = name;
		this.automated = automated;
		this.score = 0;
		this.ui = ui;
	}
	
	public int makeChoice(String[] choices) {
		
		if (automated) {
			Random rn = new Random();
			int choice = rn.nextInt(choices.length);
			return choice;
		}
		
		String choice = "";
		String[] lower_choices = new String[choices.length];
		
		for (int i=0; i<choices.length;i++){
			lower_choices[i] = choices[i].toLowerCase();
		}
		
		while (true) {
			ui.PrintOutputNoReturn(name + ", please make a choice ["
					+ Utils.join(", ",choices) + ", or empty to abort]: ");
	
			choice = ui.TakeInput();
			
			choice = choice.toLowerCase();
			
			if (choice.equals("")) {
				ui.PrintOutput("Terminating game... Hej hej!");
				System.exit(0);
			}
			
			if (Arrays.asList(lower_choices).contains(choice)) {
				break;
			}
			else {
				ui.PrintError("Invalid choice. Please try again.");
			}
		}

		return Arrays.asList(lower_choices).indexOf(choice);
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

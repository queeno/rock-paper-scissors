/*
 * This class implements the Rock-Paper-Scissors game.
 * 
*/

package rps.game;

import rps.config.Config;
import rps.ui.Cli;

import java.util.List;
import java.util.HashMap;

import utils.Utils;

public class Game implements Games {

	private Player[] players;
	private String[] shapes;
	private int[][] shape_matrix;
	
	private Config config;
	private Cli ui;
	
	private void createPlayers(List <HashMap<String,String>> conf_players){

		int no_players = conf_players.size();
		players = new Player[no_players];
		
		for (int i=0; i< no_players; i++) {
	
			String player_name = conf_players.get(i).get("name");
			boolean automated = Boolean.parseBoolean(conf_players.get(i).get("automated"));
			
			players[i] = new Player(ui, player_name, automated);

		}
	}
	
	private int play(int shape1_i, int shape2_j) {

		return shape_matrix[shape1_i][shape2_j];
		
	}
	
	
	private void nextRound(int round_number){
		
		int[] choices = new int[players.length];
		
		ui.PrintOutput("--- Round " + round_number + " of " + config.GetRounds()
						+ " ---");
		ui.PrintOutput("");
		
		ui.PrintOutput("Waiting for all players to make their choices...");
		// Collect inputs from players
		for (int i=0; i < players.length; i++){
			choices[i] = players[i].makeChoice(shapes);
		}
		
		
		// Play between players
		for (int i=0; i < players.length; i++){
			for (int j=i+1; j < players.length; j++){
				ui.PrintOutput("");
				ui.PrintOutput(players[i].getName() + " vs " + players[j].getName());
				ui.PrintOutput(players[i].getName() + " selects " + shapes[choices[i]]);
				ui.PrintOutput(players[j].getName() + " selects " + shapes[choices[j]]);

				switch(play(choices[i], choices[j])) {
				case 0:
					players[j].wins();
					ui.PrintOutput(players[j].getName() + " wins!");
					break;
				case 1:
					players[i].wins();
					ui.PrintOutput(players[i].getName() + " wins!");
					break;
				default:
					System.out.println(players[i].getName() + " and " + players[j].getName() + " draw.");
					break;
				}
			}
		}
	}
	
	
	private void printStatus(){
		
		ui.PrintOutput("");
		ui.PrintOutput("--- SCORES ---");
		
		for (Player player : players) {
			ui.PrintOutput(player.getName() + ": " + player.getScore());
		}
		
		ui.PrintOutput("");
		
	}
	
	
	private void printGameConfig(){

		String[] myPlayers = new String[players.length];
		
		for (int i=0; i < players.length; i++ ) {
			myPlayers[i] = players[i].getName();
			if (players[i].isAutomated()) {
				myPlayers[i] = myPlayers[i] + " (C)";
			} else {
				myPlayers[i] = myPlayers[i] + " (H)";
			}
		}
		
		ui.PrintOutput("");
		ui.PrintOutput("--- GAME CONFIGURATION ---");
		
		ui.PrintOutput("Shapes: " + Utils.join(", ", shapes));
		ui.PrintOutput("Players: " + Utils.join(", ", myPlayers));
		
	}
	
	public void Run(){
		
		ui.PrintOutput("WELCOME TO ROCK-PAPER-SCISSORS");
		printGameConfig();
		
		for (int i=1; i <= config.GetRounds(); i++){
			printStatus();
			nextRound(i);
		}
		
		ui.PrintOutput("");
		ui.PrintOutput("--- FINAL RESULTS ---");
		printStatus();
		
	}
	
	
	public Game(String[] args) {

		// Initialise UI
		ui = new Cli();

		// Initialise config
		config = new Config(ui, args);

		// Create players
		createPlayers(config.GetPlayers());
		
		// Create shapes
		shapes = config.GetShapes();
		shape_matrix = config.GetShapeMatrix();

	}
	
}

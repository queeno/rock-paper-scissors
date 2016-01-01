/*
 * This class implements the Rock-Paper-Scissors game.
 * 
*/

package rps.game;

import rps.config.Config;
import rps.ui.Cli;

import java.util.List;
import java.util.HashMap;

public class Game {

	private Player[] players;
	private HashMap<String,Integer> shapes;
	private int[][] shape_matrix;
	
	private Config config;
	private Cli ui;
	
	private void createPlayers(List <HashMap<String,String>> conf_players){

		int no_players = conf_players.size();
		players = new Player[no_players];
		
		for (int i=0; i< no_players; i++) {
	
			String player_name = conf_players.get(i).get("name");
			boolean automated = Boolean.parseBoolean(conf_players.get(i).get("automated"));
			
			players[i] = new Player(player_name, automated);

		}
	}
	
	private int play(int shape1_i, int shape2_j) {

		return shape_matrix[shape1_i][shape2_j];
		
	}
	
	
	private void nextRound(){
		
		int[] choices = new int[players.length];
		
		// Collect inputs from players
		for (int i=0; i < players.length; i++){
			choices[i] = players[i].makeChoice(shapes);
		}
		
		
		// Play between players
		for (int i=0; i < players.length; i++){
			for (int j=i+1; j < players.length; j++){
				System.out.println("playing " + players[i].getName() + " vs " + players[j].getName());
				System.out.println(i + " " + j + "choices: " + choices[i] + " " + choices[j]);
				switch(play(choices[i], choices[j])) {
				case 0:
					players[j].wins();
					System.out.println("player " + players[i].getName() + " wins");
					break;
				case 1:
					players[i].wins();
					System.out.println("player " + players[j].getName() + " wins");
					break;
				default:
					System.out.println("player " + players[i].getName() + " and " + players[j].getName() + " draw.");
					break;
				}
				System.out.flush();
			}
		}
	}
	
	
	private void printStatus(int current_round){
		
		ui.PrintOutput("--- GAME STATUS ---");
		ui.PrintOutput("Round " + current_round + " of " + config.GetRounds());
		ui.PrintOutput("CURRENT SCORES:");
		
		for (Player player : players) {
			ui.PrintOutput(player.getName() + ": " + player.getScore());
		}
		
		ui.PrintOutput("");
		
	}
	
	
	public void Run(){
		
		ui.PrintOutput("WELCOME TO ROCK-PAPER-SCISSORS");
		
		for (int i=1; i <= config.GetRounds(); i++){
			printStatus(i);
			nextRound();
		}
		
	}
	
	
	public Game() {

		// Initialise UI
		ui = new Cli();

		// Initialise config
		config = new Config(ui);

		// Create players
		createPlayers(config.GetPlayers());
		
		// Create shapes
		shapes = config.GetShapes();
		shape_matrix = config.GetShapeMatrix();

	}
	
}

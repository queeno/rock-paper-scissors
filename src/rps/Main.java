/*
 * Rock-Paper-Scissors
 * ===================
 * 
 * This class is the point of entry of the app.
 * It should:
 *   - Read the config.
 *   - Set the environment
 *   - Initialise the objects
 *   - Launch the game
 */

package rps;

import rps.game.Game;

public class Main {
	
	public static void main(String args[]){

		Game game = new Game();
		game.Run();
		
	}

}

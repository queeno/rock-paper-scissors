package test.rps;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.PrintStream;

import rps.game.Game;

public class Acceptance_Test {

	private Game game;
	private String gameOutput;
	private String sp;

	
	@Before
	public void setUp() throws Exception {
		ByteArrayOutputStream myOut = new ByteArrayOutputStream();
		System.setOut(new PrintStream(myOut));
		
		sp = System.getProperty("line.separator");
		
		String input = 	"rock" + sp + "paper" + sp +
					  	"scissors" + sp + "scissors" + sp +
					  	"scissors" + sp + "paper" + sp +
					  	"rock" + sp + "scissors" + sp +
					  	"scissors" + sp + "rock" + sp;
		
		ByteArrayInputStream myIn = new ByteArrayInputStream(input.getBytes());
		System.setIn(myIn);
		
		game = new Game(new String[]{"src/test/config-acceptance.xml",
				"src/test/shapes-acceptance.csv"});

		game.Run();
		gameOutput = myOut.toString();
		
	}

	// Check output contains welcome message
	@Test
	public void containsWelcomeMessage() {
		assertTrue(gameOutput.contains("WELCOME TO ROCK-PAPER-SCISSORS"));
	}

	// Check output contains game configuration
	@Test
	public void containsConfiguration() {
		assertTrue(gameOutput.contains("--- GAME CONFIGURATION ---"));
	}
	
	// Check output contains players
	@Test
	public void containsPlayers() {
		assertTrue(gameOutput.contains("Players: Simon (H), Computer (H)"));
	}
	
	// Check output contains right shapes
	@Test
	public void containsShapes() {
		assertTrue(gameOutput.contains("Shapes: rock, paper, scissors"));
	}

	// Check round 1
	@Test
	public void checkRoundOne(){
		
		String roundOneExpectedTest = "--- Round 1 of 5 ---" + sp + sp + 
				"Waiting for all players to make their choices..." + sp + 
				"Simon, please make a choice [rock, paper, scissors, or empty to abort]: Computer, please make a choice [rock, paper, scissors, or empty to abort]: " + sp +
				"Simon vs Computer" + sp +
				"Simon selects rock" + sp +
				"Computer selects paper" + sp +
				"Computer wins!";
		
		assertTrue(gameOutput.contains(roundOneExpectedTest));
	}
	
	// Check round 2
	@Test
	public void checkRoundTwo(){
		
		String roundTwoExpectedTest = "--- Round 2 of 5 ---" + sp + sp + 
				"Waiting for all players to make their choices..." + sp + 
				"Simon, please make a choice [rock, paper, scissors, or empty to abort]: Computer, please make a choice [rock, paper, scissors, or empty to abort]: " + sp +
				"Simon vs Computer" + sp +
				"Simon selects scissors" + sp +
				"Computer selects scissors" + sp +
				"Simon and Computer draw";
		
		assertTrue(gameOutput.contains(roundTwoExpectedTest));
		
	}
		
	// Check round 3
	@Test
	public void checkRoundThree(){
		
		String roundThreeExpectedTest = "--- Round 3 of 5 ---" + sp + sp + 
				"Waiting for all players to make their choices..." + sp + 
				"Simon, please make a choice [rock, paper, scissors, or empty to abort]: Computer, please make a choice [rock, paper, scissors, or empty to abort]: " + sp +
				"Simon vs Computer" + sp +
				"Simon selects scissors" + sp +
				"Computer selects paper" + sp +
				"Simon wins!";
		
		assertTrue(gameOutput.contains(roundThreeExpectedTest));	
		
	}
	
	
	// Check round 4
	@Test
	public void checkRoundFour(){
		
		String roundFourExpectedTest = "--- Round 4 of 5 ---" + sp + sp + 
				"Waiting for all players to make their choices..." + sp + 
				"Simon, please make a choice [rock, paper, scissors, or empty to abort]: Computer, please make a choice [rock, paper, scissors, or empty to abort]: " + sp +
				"Simon vs Computer" + sp +
				"Simon selects rock" + sp +
				"Computer selects scissors" + sp +
				"Simon wins!";
		
		assertTrue(gameOutput.contains(roundFourExpectedTest));	
		
	}
	
	// Check round 5
	@Test
	public void checkRoundFive(){
		
		String roundFiveExpectedTest = "--- Round 5 of 5 ---" + sp + sp + 
				"Waiting for all players to make their choices..." + sp + 
				"Simon, please make a choice [rock, paper, scissors, or empty to abort]: Computer, please make a choice [rock, paper, scissors, or empty to abort]: " + sp +
				"Simon vs Computer" + sp +
				"Simon selects scissors" + sp +
				"Computer selects rock" + sp +
				"Computer wins!";
		
		assertTrue(gameOutput.contains(roundFiveExpectedTest));	
		
	}
	
	// Check final score
	@Test
	public void checkFinalScore() {
		
		String finalScore = "--- SCORES ---" + sp +
				"Simon: 2" + sp +
				"Computer: 2";
		
		assertTrue(gameOutput.contains(finalScore));
	}
	
}

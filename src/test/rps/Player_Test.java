package test.rps;

import org.junit.Before;
import org.junit.Test;

import rps.game.Player;
import rps.ui.Cli;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class Player_Test {

	private Player player1;
	private Player player2;
	
	@Before
	public void setUp() throws Exception {
		player1 = new Player(new Cli(), "name1", true);
		player2 = new Player(new Cli(), "name2", true);
	}

	@Test
	public void getName() {
		assertEquals(player1.getName(), "name1");
		assertEquals(player2.getName(), "name2");
	}
	
	@Test
	public void isAutomated() {
		assertEquals(player1.isAutomated(), true);
		assertEquals(player2.isAutomated(), true);
	}
	
	@Test
	public void wins() {
		player1.wins();
		
		assertEquals(player1.getScore(), 1);
		assertEquals(player2.getScore(), 0);
	}
	
	@Test
	public void makeChoice(){
		String[] choices = new String[]{"rock", "paper"};
		
		assertThat(player1.makeChoice(choices), anyOf(is(0), is(1)));
		assertThat(player2.makeChoice(choices), anyOf(is(0), is(1)));
		
	}

}

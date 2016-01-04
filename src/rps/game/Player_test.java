package rps.game;

import org.junit.Before;
import org.junit.Test;

import rps.ui.Cli;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class Player_test {

	private Player player1;
	private Player player2;
	
	@Before
	public void setUp() throws Exception {
		player1 = new Player(new Cli(), "name1", false);
		player2 = new Player(new Cli(), "name2", true);
	}

	@Test
	public void getName() {
		assertEquals(player1.getName(), "name1");
		assertEquals(player2.getName(), "name2");
	}
	
	@Test
	public void isAutomated() {
		assertEquals(player1.isAutomated(), false);
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
		
		assertEquals(player1.makeChoice(choices), -1);
		assertThat(player2.makeChoice(choices), anyOf(is(0), is(1)));
		
	}

}

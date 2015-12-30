package rps.game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Player_test {

	private Player player1;
	private Player player2;
	
	@Before
	public void setUp() throws Exception {
		player1 = new Player("name1", false);
		player2 = new Player("name2", true);
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

}

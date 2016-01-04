/* 
 * This interface defines the methods and attributes that Player should
 * implement.
 */

package rps.game;

public interface Players {

	public int getScore();

	public void wins();
	
	public boolean isAutomated();
	
	public String getName();

	public int makeChoice(String[] choices);
}

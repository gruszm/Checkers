package pack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class represents a key manager that detects user input from a keyboard and updates an array of boolean
 * values to indicate whether a particular key is pressed or released.
 */
public class KeyManager implements KeyListener
{
	public boolean[] keys; /* Array of boolean values representing the state of each key */

	/**
	 * Creates a new KeyManager object with an array of boolean values representing the keys.
	 */
	public KeyManager()
	{
		keys = new boolean[255];
	}

	/**
	 * Called when a key is pressed. Sets the boolean value of the pressed key to true.
	 * 
	 * @param e The KeyEvent object representing the key event.
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		keys[e.getKeyCode()] = true;
	}

	/**
	 * Called when a key is released. Sets the boolean value of the released key to false.
	 * 
	 * @param e The KeyEvent object representing the key event.
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e)
	{

	}
}

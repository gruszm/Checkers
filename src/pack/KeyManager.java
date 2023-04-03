package pack;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener
{
	boolean[] keys;

	public KeyManager()
	{
		keys = new boolean[255];
	}

	@Override
	public void keyPressed(KeyEvent arg0)
	{
		keys[arg0.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent arg0)
	{
		keys[arg0.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{

	}
}

package pack;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * This class implements the MouseListener interface and listens for mouse events.
 */
public class MouseManager implements MouseListener
{
	private Game game; /* The game object responsible for handling the click events. */

	/**
	 * Creates a new instance of the MouseManager class.
	 * @param game The Game object that this MouseManager is associated with.
	 */
	public MouseManager(Game game)
	{
		this.game = game;
	}

	/**
	 * Called when the mouse is clicked. Delegates the click event to the Game class.
	 * @param mouseEvent The MouseEvent object representing the click event.
	 */
	@Override
	public void mouseClicked(MouseEvent mouseEvent)
	{
		game.mouseClickedAndReleased(mouseEvent.getButton(), mouseEvent.getX(), mouseEvent.getY());
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{

	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{

	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{

	}

}

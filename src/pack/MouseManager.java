package pack;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseManager implements MouseListener
{
	Game game;
	int x, y;

	public MouseManager(Game game)
	{
		this.game = game;
		x = -1;
		y = -1;
	}

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

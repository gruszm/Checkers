package pack;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tile
{
	public static final int SIZE = 100;
	private int x, y;

	public Tile(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void render(Graphics2D g)
	{
		g.setColor(Color.black);
		g.fillRect(SIZE * x, SIZE * y, SIZE, SIZE);
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}

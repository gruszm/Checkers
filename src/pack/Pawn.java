package pack;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pawn
{
	public static final int RADIUS = 70;

	private PawnType pawnType;
	private int x;
	private int y;
	private Color almostBlack;
	private int direction; // 0 = down, 1 = up
	private boolean damka;

	public Pawn(PawnType pawnType, int x, int y, int direction)
	{
		this.pawnType = pawnType;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.damka = false;

		almostBlack = new Color(80, 80, 80);
	}

	public void tick()
	{

	}

	public void render(Graphics2D g)
	{
		if (pawnType == PawnType.BLACK)
			g.setColor(almostBlack);
		else if (pawnType == PawnType.WHITE)
			g.setColor(Color.WHITE);

		g.fillOval(x * Tile.SIZE + (Tile.SIZE - RADIUS) / 2, y * Tile.SIZE + (Tile.SIZE - RADIUS) / 2, RADIUS, RADIUS);

		if (isDamka())
		{
			g.setColor(Color.yellow);

			for(int i = 0; i < 10; i++)
			{
				g.drawOval(x * Tile.SIZE + (Tile.SIZE - RADIUS + i) / 2, y * Tile.SIZE + (Tile.SIZE - RADIUS + i) / 2, RADIUS - i, RADIUS - i);
			}
		}
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public PawnType getPawnType()
	{
		return pawnType;
	}

	public void setX(int x)
	{
		this.x = x;
	}

	public void setY(int y)
	{
		this.y = y;
	}

	public int getDirection()
	{
		return direction;
	}

	public void setDirection(int direction)
	{
		this.direction = direction;
	}

	public boolean isDamka()
	{
		return damka;
	}

	public void setDamka(boolean damka)
	{
		this.damka = damka;
	}

}

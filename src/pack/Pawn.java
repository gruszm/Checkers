package pack;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pawn
{
	/* PUBLIC CONSTANTS */
	
	/**
	 * Radius of the circle, which represents a pawn on the map.
	 */
	public static final int RADIUS = 70;
	
	/* END OF PUBLIC CONSTANTS */

	/* PRIVATE VARIABLES */
	
	private PawnType pawnType; /* Type of the pawn (black, white) */
	private Color almostBlack; /* Color of a black pawn other than black */
	private int x;             /* X coordinate on the map */
	private int y;             /* Y coordinate on the map */
	private int direction;     /* Direction of the pawn, 0 means down, 1 means up */
	private boolean king;      /* Flag, which tells, if the pawn is a king */
	
	/* END OF PRIVATE VARIABLES */

	/**
	 * Creates a new Pawn object with the given type, position, and direction.
	 * 
	 * @param pawnType The type of pawn (either BLACK or WHITE).
	 * @param x The x-coordinate of the pawn's position on the board.
	 * @param y The y-coordinate of the pawn's position on the board.
	 * @param direction The direction in which the pawn is facing (either up or down).
	 */
	public Pawn(PawnType pawnType, int x, int y, int direction)
	{
		this.pawnType = pawnType;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.king = false;

		almostBlack = new Color(80, 80, 80);
	}

	public void render(Graphics2D g)
	{
		if (pawnType == PawnType.BLACK)
			g.setColor(almostBlack);
		else if (pawnType == PawnType.WHITE)
			g.setColor(Color.WHITE);

		g.fillOval(x * Tile.SIZE + (Tile.SIZE - RADIUS) / 2, y * Tile.SIZE + (Tile.SIZE - RADIUS) / 2, RADIUS, RADIUS);

		if (isKing())
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

	public boolean isKing()
	{
		return king;
	}

	public void setKing(boolean king)
	{
		this.king = king;
	}

}

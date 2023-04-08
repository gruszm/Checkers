package pack;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * This class represents a pawn on a checkers map.
 */
public class Pawn
{
	/* PUBLIC CONSTANTS */
	
	/**
	 * Diameter of the circle, which represents a pawn on the map.
	 */
	public static final int DIAMETER = 70;
	
	/* END OF PUBLIC CONSTANTS */

	/* PRIVATE VARIABLES */
	
	private PawnType pawnType; /* Type of the pawn (black, white) */
	private Color almostBlack; /* Color of a black pawn other than black */
	private int x;             /* x-coordinate on the map */
	private int y;             /* y-coordinate on the map */
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

	/**
	 * Function used to render the pawn on the map.
	 * 
	 * @param g2d Graphics2D object, which is used to render the pawn.
	 */
	public void render(Graphics2D g2d)
	{
		/* Set the fill color of the graphics context to the appropriate color for the pawn */
		if (pawnType == PawnType.BLACK)
		{
			g2d.setColor(almostBlack);
		}
		else if (pawnType == PawnType.WHITE)
		{
			g2d.setColor(Color.WHITE);
		}

		/* Draw the pawn's circle on the board */
		g2d.fillOval(x * Tile.SIZE + (Tile.SIZE - DIAMETER) / 2, y * Tile.SIZE + (Tile.SIZE - DIAMETER) / 2, DIAMETER, DIAMETER);

		if (isKing())
		{
			/* Draw a yellow border around the pawn's circle to indicate it's a king */
			g2d.setColor(Color.yellow);

			for(int i = 0; i < 10; i++)
			{
				g2d.drawOval(x * Tile.SIZE + (Tile.SIZE - DIAMETER + i) / 2, y * Tile.SIZE + (Tile.SIZE - DIAMETER + i) / 2, DIAMETER - i, DIAMETER - i);
			}
		}
	}

	/**
	 * Returns the x-coordinate of the pawn on the map.
	 * 
	 * @return The x-coordinate of the pawn on the map.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the y-coordinate of the pawn on the map.
	 * 
	 * @return The y-coordinate of the pawn on the map.
	 */
	public int getY()
	{
		return y;
	}

	/**
	 * Returns the type of the pawn (BLACK or WHITE).
	 * 
	 * @return The type of the pawn.
	 */
	public PawnType getPawnType()
	{
		return pawnType;
	}

	/**
	 * Sets the x-coordinate of the pawn's position on the board.
	 *
	 * @param x The x-coordinate of the pawn's position on the board.
	 */
	public void setX(int x)
	{
	    this.x = x;
	}

	/**
	 * Sets the y-coordinate of the pawn's position on the board.
	 *
	 * @param y The y-coordinate of the pawn's position on the board.
	 */
	public void setY(int y)
	{
	    this.y = y;
	}

	/**
	 * Returns the direction in which the pawn is facing (either up or down).
	 *
	 * @return The direction of the pawn.
	 */
	public int getDirection()
	{
	    return direction;
	}

	/**
	 * Sets the direction in which the pawn is facing (either up or down).
	 *
	 * @param direction The direction in which the pawn is facing.
	 */
	public void setDirection(int direction)
	{
	    this.direction = direction;
	}

	/**
	 * Returns a boolean value indicating whether the pawn is a king.
	 *
	 * @return true if the pawn is a king, false otherwise.
	 */
	public boolean isKing()
	{
	    return king;
	}

	/**
	 * Sets the king flag of the pawn.
	 *
	 * @param king true if the pawn is a king, false otherwise.
	 */
	public void setKing(boolean king)
	{
	    this.king = king;
	}
}

package pack;

import java.awt.Color;
import java.awt.Graphics2D;

public class Tile
{
	/* PUBLIC CONSTANTS */
	
	/**
	 * Size of a tile in pixels.
	 */
	public static final int SIZE = 100;
	
	/* END OF PUBLIC CONSTANTS */
	
	
	/* PRIVATE VARIABLES */
	
	private int x; /* X coordinate of a tile */
	private int y; /* Y coordinate of a tile */

	/* END OF PRIVATE VARIABLES */
	
	/**
 	 * Creates a tile with given x, y (coordinates independent of the size of a tile).
	 * @param x the x-coordinate of the tile on the map
	 * @param y the y-coordinate of the tile on the map
	 * 
	 */
	public Tile(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Function used for rendering the tile on its position on the map.
	 * @param graphics2d - Graphics2D object, which is used to render the tile.
	 */
	public void render(Graphics2D graphics2d)
	{
		graphics2d.setColor(Color.black);                    /* Set the tile color to black */
		graphics2d.fillRect(SIZE * x, SIZE * y, SIZE, SIZE); /* Fill the rectangle representing the tile with the black color */
	}

	/**
	 * Returns the X coordinate of a tile.
	 * @return X coordinate of a tile.
	 */
	public int getX()
	{
		return x;
	}

	/**
	 * Returns the Y coordinate of a tile.
	 * @return Y coordinate of a tile.
	 */
	public int getY()
	{
		return y;
	}
}

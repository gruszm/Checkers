package pack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Map
{
	public static final int WIDTH = 8;
	ArrayList<Tile> tiles;
	ArrayList<Pawn> pawns;
	Dimension pawnSelectedPosition;
	byte playerTurn; /* 0 = bottom player, white pawns, 1 = top player, black pawns */
	private static boolean czyByloBicie = false;
	private int punktyBialego;
	private int punktyCzarnego;

	public Map()
	{
		tiles = new ArrayList<Tile>();
		pawns = new ArrayList<Pawn>();
		pawnSelectedPosition = new Dimension(-1, -1);
		playerTurn = 0;
		punktyBialego = 0;
		punktyCzarnego = 0;
		init();
	}

	private void init()
	{
		for(int y = 0; y < WIDTH; y++)
		{
			for(int x = 0; x < WIDTH; x++)
			{
				if (((x + 1) + (y + 1)) % 2 == 0)
				{
					tiles.add(new Tile(x, y));

					if (y <= 2)
						pawns.add(new Pawn(PawnType.BLACK, x, y, 0));
					else if (y >= 5)
						pawns.add(new Pawn(PawnType.WHITE, x, y, 1));
				}
			}
		}
	}

	public void render(Graphics2D graphics)
	{
		for(Tile t : tiles)
		{
			t.render(graphics);
		}

		for(Pawn p : pawns)
		{
			p.render(graphics);
		}

		if (pawnSelectedPosition.width >= 0 && pawnSelectedPosition.height >= 0)
		{
			Color tempColor = graphics.getColor();
			graphics.setColor(Color.red);

			graphics.drawRect((int) pawnSelectedPosition.getWidth() * Tile.SIZE + 1, (int) pawnSelectedPosition.getHeight() * Tile.SIZE + 1, Tile.SIZE - 2, Tile.SIZE - 2);

			graphics.setColor(tempColor);
		}
	}

	private Pawn getPawn(int x, int y)
	{
		Pawn p = null;

		for(Pawn tempPawn : pawns)
		{
			if (tempPawn.getX() == x && tempPawn.getY() == y)
			{
				p = tempPawn;
				break;
			}
		}

		return p;
	}

	private Tile getTile(int x, int y)
	{
		Tile t = null;

		for(Tile tempTile : tiles)
		{
			if (tempTile.getX() == x && tempTile.getY() == y)
			{
				t = tempTile;
				break;
			}
		}

		return t;
	}

	private int getXRelativeToMap(int absoluteX)
	{
		return (int) ((float) absoluteX / (float) Tile.SIZE);
	}

	private int getYRelativeToMap(int absoluteY)
	{
		return (int) ((float) absoluteY / (float) Tile.SIZE);
	}

	private boolean canPawnMoveToTile(Pawn pawn, Tile targetTile)
	{
		if (null == targetTile || null == pawn)
		{
			return false;
		}

		byte absRangeX = (byte) Math.abs(pawn.getX() - targetTile.getX());
		byte rangeY = (byte) (targetTile.getY() - pawn.getY());

		if ((1 == absRangeX && (-1 == rangeY && (1 == pawn.getDirection() || pawn.isKing())) || (1 == rangeY && (0 == pawn.getDirection() || pawn.isKing())) && isTileEmpty(targetTile)))
		{
			return true;
		}
		else if (2 == absRangeX && (-2 == rangeY && (1 == pawn.getDirection() || true == czyByloBicie || pawn.isKing())
				|| 2 == rangeY && (0 == pawn.getDirection() || true == czyByloBicie || pawn.isKing()) && isTileEmpty(targetTile)))
		{
			byte xBetween = (byte) ((pawn.getX() + targetTile.getX()) / 2);
			byte yBetween = (byte) ((pawn.getY() + targetTile.getY()) / 2);

			Pawn pawnBetween = getPawn(xBetween, yBetween);

			if (null == pawnBetween)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	private boolean isTileEmpty(Tile tile)
	{
		return (null == getPawn(tile.getX() * Tile.SIZE, tile.getY() * Tile.SIZE)) ? true : false;
	}

	private boolean czyJestMozliweKolejneBicie(Pawn pawn)
	{
		boolean retVal = false;
		int pawnX = pawn.getX();
		int pawnY = pawn.getY();
		PawnType enemyPawnType = PawnType.BLACK;
		Pawn neighbourPawns[] = new Pawn[4];
		Pawn targetPawns[] = new Pawn[4];
		Tile targetTiles[] = new Tile[4];

		if (pawn.getPawnType() == PawnType.BLACK)
		{
			enemyPawnType = PawnType.WHITE;
		}

		neighbourPawns[0] = getPawn(pawnX - 1, pawnY - 1); // left up
		neighbourPawns[1] = getPawn(pawnX + 1, pawnY - 1); // right up
		neighbourPawns[2] = getPawn(pawnX - 1, pawnY + 1); // left down
		neighbourPawns[3] = getPawn(pawnX + 1, pawnY + 1); // right down

		targetPawns[0] = getPawn(pawnX - 2, pawnY - 2); // left up
		targetPawns[1] = getPawn(pawnX + 2, pawnY - 2); // right up
		targetPawns[2] = getPawn(pawnX - 2, pawnY + 2); // left down
		targetPawns[3] = getPawn(pawnX + 2, pawnY + 2); // right down

		targetTiles[0] = getTile(pawnX - 2, pawnY - 2); // left up
		targetTiles[1] = getTile(pawnX + 2, pawnY - 2); // right up
		targetTiles[2] = getTile(pawnX - 2, pawnY + 2); // left down
		targetTiles[3] = getTile(pawnX + 2, pawnY + 2); // right down

		for(int i = 0; i < 4; i++)
		{
			if (neighbourPawns[i] != null && enemyPawnType == neighbourPawns[i].getPawnType() && targetPawns[i] == null && targetTiles[i] != null)
			{
				retVal = true;
				break;
			}
		}

		return retVal;
	}

	protected void handleMouseClicked(int button, int mouseX, int mouseY)
	{
		/* on right-click, clear the selection */
		if (button == MouseEvent.BUTTON3)
		{
			pawnSelectedPosition.width = -1;
			pawnSelectedPosition.height = -1;
		}
		else if (button == MouseEvent.BUTTON1)
		{
			Pawn pawnAtMouseLocalisation = getPawn(getXRelativeToMap(mouseX), getYRelativeToMap(mouseY));
			Pawn pawnSelected = getPawn(pawnSelectedPosition.width, pawnSelectedPosition.height);
			Tile tileAtMousePosition = getTile(getXRelativeToMap(mouseX), getYRelativeToMap(mouseY));

			if (pawnAtMouseLocalisation != null) /* if the clicked tile isn't null */
			{
				if (false == czyByloBicie)
				{
					if ((pawnAtMouseLocalisation.getPawnType() == PawnType.WHITE && playerTurn == 0) || (pawnAtMouseLocalisation.getPawnType() == PawnType.BLACK && playerTurn == 1))
					{
						pawnSelectedPosition.width = getXRelativeToMap(mouseX);
						pawnSelectedPosition.height = getYRelativeToMap(mouseY);
					}
				}
			}
			else if (canPawnMoveToTile(pawnSelected, tileAtMousePosition))
			{
				byte absRangeX = (byte) Math.abs(pawnSelected.getX() - tileAtMousePosition.getX());
				byte absRangeY = (byte) Math.abs(pawnSelected.getY() - tileAtMousePosition.getY());

				if (1 == absRangeX && 1 == absRangeY)
				{
					pawnSelectedPosition.width = -1;
					pawnSelectedPosition.height = -1;

					if (playerTurn == 0)
					{
						playerTurn = 1;
					}
					else
					{
						playerTurn = 0;
					}

					pawnSelected.setX(tileAtMousePosition.getX());
					pawnSelected.setY(tileAtMousePosition.getY());

					if ((pawnSelected.getPawnType() == PawnType.BLACK && pawnSelected.getY() == 7) || (pawnSelected.getPawnType() == PawnType.WHITE && pawnSelected.getY() == 0))
					{
						pawnSelected.setKing(true);
					}

					czyByloBicie = false;
				}
				else if (2 == absRangeX && 2 == absRangeY)
				{
					int xBetween = (pawnSelected.getX() + tileAtMousePosition.getX()) / 2;
					int yBetween = (pawnSelected.getY() + tileAtMousePosition.getY()) / 2;

					Pawn pawnBetween = getPawn(xBetween, yBetween);

					if (pawnBetween.getPawnType() == PawnType.BLACK)
					{
						punktyBialego++;
					}
					else
					{
						punktyCzarnego++;
					}

					pawns.remove(pawnBetween);
					czyByloBicie = true;

					pawnSelected.setX(tileAtMousePosition.getX());
					pawnSelected.setY(tileAtMousePosition.getY());

					if ((pawnSelected.getPawnType() == PawnType.BLACK && pawnSelected.getY() == 7) || (pawnSelected.getPawnType() == PawnType.WHITE && pawnSelected.getY() == 0))
					{
						pawnSelected.setKing(true);
					}

					if (false == czyJestMozliweKolejneBicie(pawnSelected))
					{
						if (playerTurn == 0)
						{
							playerTurn = 1;
						}
						else
						{
							playerTurn = 0;
						}

						pawnSelectedPosition.width = -1;
						pawnSelectedPosition.height = -1;
						czyByloBicie = false;
					}
					else
					{
						pawnSelectedPosition.width = tileAtMousePosition.getX();
						pawnSelectedPosition.height = tileAtMousePosition.getY();
					}
				}
			}
		}
	}

	public int getPunktyBialego()
	{
		return punktyBialego;
	}

	public int getPunktyCzarnego()
	{
		return punktyCzarnego;
	}

}

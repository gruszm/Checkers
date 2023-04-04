package pack;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Game implements Runnable
{
	/* PRIVATE CONSTANTS */
	
	private static final short  WIDTH                  = 800;        /* Width of the windows */
	private static final short  HEIGHT                 = 800;        /* Height of the window */
	private static final short  POINTS_AREA_HEIGHT     = 80;         /* Height of the area below the Map, intended for displaying the points of both players */
	private static final short  WINNING_MESSAGE_WIDTH  = 350;        /* Width of the message, which is shown when one of the players wins */
	private static final short  WINNING_MESSAGE_HEIGHT = 60;         /* Height of the message, which is shown when one of the players wins */
	private static final String WINDOW_NAME            = "Checkers"; /* Name of the game window */
	private static final String FONT_NAME              = "Arial";    /* Name or type of the font used in the game */
	private static final byte   FONT_SIZE              = 30;         /* Size of the font used in the game */
	
	/* END OF PRIVATE CONSTANTS */
	
	
	/* PRIVATE VARIABLES */
	
	private boolean      isRunning;    /* Flag, which is raised, as long as the application is running */
	private JFrame       frame;        /* Object of the frame */
	private Canvas       canvas;       /* Object of the canvas */
	private Thread       thread;       /* Main thread */
	private Map          map;          /* Object of the game map */
	private KeyManager   keyManager;   /* Object of the key manager */
	private MouseManager mouseManager; /* Object of the mouse manager */
	private Font         font;         /* The rendered font */
	
	/* END OF PRIVATE VARIABLES */
	
	
	/**
	 * The main class's constructor
	 */
	public Game()
	{
		font         = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
		frame        = new JFrame(WINDOW_NAME);
		canvas       = new Canvas();
		keyManager   = new KeyManager();
		mouseManager = new MouseManager(this);
		map          = new Map();

		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT + POINTS_AREA_HEIGHT));
		canvas.setEnabled(true);
		canvas.setFocusable(false);
		canvas.addMouseListener(mouseManager);
		
		frame.add(canvas);
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.addKeyListener(keyManager);
	}

	public void start()
	{
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}

	private void stop()
	{
		isRunning = false;
		frame.dispose();

		try
		{
			thread.join(1);
		}
		catch(InterruptedException e)
		{

		}
	}

	@Override
	public void run()
	{
		try
		{
			while(isRunning)
			{
				tick();
				render();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private void tick()
	{
		if (keyManager.keys[KeyEvent.VK_ESCAPE])
			stop();
	}

	/* Throws something, don't touch */
	private void render() throws IllegalStateException
	{
		BufferStrategy bs = canvas.getBufferStrategy();
		
		if (bs == null)
		{
			canvas.createBufferStrategy(2);
			bs = canvas.getBufferStrategy();
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		// draw
		g.setBackground(new Color(150, 150, 150));
		g.clearRect(0, 0, WIDTH, HEIGHT + POINTS_AREA_HEIGHT);
		g.setFont(font);
		g.drawString(String.format("Punkty gracza bialego: %d", map.getPunktyBialego()), 0, HEIGHT + 30);
		g.drawString(String.format("Punkty gracza czarnego: %d", map.getPunktyCzarnego()), 0, HEIGHT + 70);
		map.render(g);

		/* The game has ended, white player has won */
		if (map.getPunktyBialego() == 12)
		{
			g.setColor(Color.yellow);
			g.fillRect((WIDTH - WINNING_MESSAGE_WIDTH) / 2, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2, WINNING_MESSAGE_WIDTH, WINNING_MESSAGE_HEIGHT);
			g.setColor(Color.red);
			g.drawString("Bialy gracz wygrywa!", (WIDTH - WINNING_MESSAGE_WIDTH) / 2 + 25, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2 + 40);
		}
		/* The game has ended, black player has won */
		else if (map.getPunktyCzarnego() == 12)
		{
			g.setColor(Color.yellow);
			g.fillRect((WIDTH - WINNING_MESSAGE_WIDTH) / 2, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2, WINNING_MESSAGE_WIDTH, WINNING_MESSAGE_HEIGHT);
			g.setColor(Color.red);
			g.drawString("Czarny gracz wygrywa!", (WIDTH - WINNING_MESSAGE_WIDTH) / 2 + 10, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2 + 40);
		}

		bs.show();
		g.dispose();
	}

	protected void mouseClickedAndReleased(int button, int x, int y)
	{
		map.handleMouseClicked(button, x, y);
	}
}

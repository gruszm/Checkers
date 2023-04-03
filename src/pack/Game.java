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
	public static final int WIDTH = 800;
	public static final int HEIGHT = 800;
	public static final int POINTS_BOARD_HEIGHT = 80;
	public static final String NAME = "Warcaby";
	public static final int WINNING_MESSAGE_WIDTH = 350;
	public static final int WINNING_MESSAGE_HEIGHT = 60;

	private JFrame frame;
	private Canvas canvas;
	private boolean isRunning;
	private Thread thread;
	private Map map;
	private KeyManager keyManager;
	private MouseManager mouseManager;
	private Font font;

	public Game()
	{
		font = new Font("Arial", Font.BOLD, 30);

		frame = new JFrame("Warcaby");
		canvas = new Canvas();
		keyManager = new KeyManager();
		mouseManager = new MouseManager(this);

		canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT + POINTS_BOARD_HEIGHT));
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

		map = new Map();

		start();
	}

	private void start()
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
//				System.out.println("running...");
				tick();
				render();
			}
		}
		catch(Exception e)
		{

		}
	}

	private void tick()
	{
		if (keyManager.keys[KeyEvent.VK_ESCAPE])
			stop();
	}

	/* throws something, don't touch */
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
		g.clearRect(0, 0, WIDTH, HEIGHT + POINTS_BOARD_HEIGHT);
		g.setFont(font);
		g.drawString(String.format("Punkty gracza bialego: %d", map.getPunktyBialego()), 0, HEIGHT + 30);
		g.drawString(String.format("Punkty gracza czarnego: %d", map.getPunktyCzarnego()), 0, HEIGHT + 70);
		map.render(g);

		// game has ended, the white player has won
		if (map.getPunktyBialego() == 12)
		{
			g.setColor(Color.yellow);
			g.fillRect((WIDTH - WINNING_MESSAGE_WIDTH) / 2, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2, WINNING_MESSAGE_WIDTH, WINNING_MESSAGE_HEIGHT);
			g.setColor(Color.red);
			g.drawString("Bialy gracz wygrywa!", (WIDTH - WINNING_MESSAGE_WIDTH) / 2 + 25, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2 + 40);
		}
//		// game has ended, the black player has won
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

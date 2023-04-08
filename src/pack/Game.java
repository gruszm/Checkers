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
    
    private static final short  WIDTH = 800;                 /* Width of the game window */
    private static final short  HEIGHT = 800;                /* Height of the game window */
    private static final short  POINTS_AREA_HEIGHT = 80;     /* Height of the area below the map, intended for displaying the points of both players */
    private static final short  WINNING_MESSAGE_WIDTH = 350; /* Width of the message, which is shown when one of the players wins */
    private static final short  WINNING_MESSAGE_HEIGHT = 60; /* Height of the message, which is shown when one of the players wins */
    private static final byte   FONT_SIZE = 30;              /* Size of the font used in the game */
    private static final String FONT_NAME = "Arial";         /* Name of the font used in the game */
    private static final String WINDOW_NAME = "Checkers";    /* Name of the game window */
    
    /* END OF PRIVATE CONSTANTS */
    
    
    /* PRIVATE VARIABLES */
    
    private boolean isRunning;         /* Flag that indicates whether the application is running */
    private JFrame frame;              /* The frame that holds the game window */
    private Canvas canvas;             /* The canvas where the game is rendered */
    private Thread thread;             /* The main game loop thread */
    private Map map;                   /* The game map object */
    private KeyManager keyManager;     /* The object that manages keyboard input */
    private MouseManager mouseManager; /* The object that manages mouse input */
    private Font font;                 /* The font used to render text on the game screen */
    
    /* END OF PRIVATE VARIABLES */
    
    
    /**
     * The main class's constructor, where all objects are created and the canvas and the frame are set up.
     */
    public Game()
    {
    	/* Initialize the font, frame, canvas, key manager, mouse manager, and map */
        font = new Font(FONT_NAME, Font.BOLD, FONT_SIZE);
        frame = new JFrame(WINDOW_NAME);
        canvas = new Canvas();
        keyManager = new KeyManager();
        mouseManager = new MouseManager(this);
        map = new Map();

        /* Set the canvas dimensions, focusability, and add the mouse listener */
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT + POINTS_AREA_HEIGHT));
        canvas.setEnabled(true);
        canvas.setFocusable(false);
        canvas.addMouseListener(mouseManager);
        
        /* Add the canvas to the frame and pack the frame */
        frame.add(canvas);
        frame.pack();
        
        /* Center the frame on the screen, set the close operation, make the frame non-resizable, and add the key listener */
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.addKeyListener(keyManager);
        
        /* Make the frame visible */
        frame.setVisible(true);
    }

    /**
     * Function initializes the main thread and starts the main loop of the game.
     */
    public void start()
    {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Stops the main game loop and attempts to terminate the thread.
     */
    private void stop()
    {
        isRunning = false;
        frame.dispose();

        /* Wait for the thread to finish before moving on */
        try
        {
            thread.join(1);
        }
        /* Thread interrupted, do nothing */
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

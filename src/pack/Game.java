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

    /**
     * This method represents the game loop that continuously runs tick and render methods
     * while the isRunning flag is set to true.
     */
    @Override
    public void run() {
        try {
            while(isRunning) {
                tick();
                render();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called on each game loop iteration to update game logic.
     * If the ESC key is pressed, the game loop is stopped.
     */
    private void tick() {
        if (keyManager.keys[KeyEvent.VK_ESCAPE])
            stop();
    }

    /**
     * This method renders the current state of the game by drawing the game map and the score of each player on the screen.
     * It also displays a message if the game has ended and a player has won.
     *
     * @throws IllegalStateException if the rendering fails for any reason.
     */
    private void render() throws IllegalStateException
    {
    	/* Get the buffer strategy for the canvas */
        BufferStrategy bs = canvas.getBufferStrategy();
        
        /* If there is no buffer strategy, create one with 2 buffers */
        if (bs == null)
        {
            canvas.createBufferStrategy(2);
            bs = canvas.getBufferStrategy();
        }
        
        /* Get the graphics object from the buffer strategy */
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        /* Draw the game map and the score of each player on the screen */
        g.setBackground(new Color(150, 150, 150));
        g.clearRect(0, 0, WIDTH, HEIGHT + POINTS_AREA_HEIGHT);
        g.setFont(font);
        g.drawString(String.format("Punkty gracza bialego: %d", map.getPunktyBialego()), 0, HEIGHT + 30);
        g.drawString(String.format("Punkty gracza czarnego: %d", map.getPunktyCzarnego()), 0, HEIGHT + 70);
        map.render(g);

        /* Check if the game has ended and a player has won, and display a message on the screen */
        if (map.getPunktyBialego() == 12)
        {
            g.setColor(Color.yellow);
            g.fillRect((WIDTH - WINNING_MESSAGE_WIDTH) / 2, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2, WINNING_MESSAGE_WIDTH, WINNING_MESSAGE_HEIGHT);
            g.setColor(Color.red);
            g.drawString("Bialy gracz wygrywa!", (WIDTH - WINNING_MESSAGE_WIDTH) / 2 + 25, (HEIGHT - WINNING_MESSAGE_HEIGHT) / 2 + 40);
        }
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

    /**
     * This method handles mouse clicks and releases by passing them to the game map.
     *
     * @param button the mouse button that was clicked or released.
     * @param x      the x-coordinate of the mouse cursor.
     * @param y      the y-coordinate of the mouse cursor.
     */
    protected void mouseClickedAndReleased(int button, int x, int y)
    {
        map.handleMouseClicked(button, x, y);
    }
}

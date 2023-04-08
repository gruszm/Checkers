package pack;

/**
 * The main class of the program, contains the main method which creates a Game object and calls its start method.
 */
public class Main {
    /**
     * The main method creates a new Game object and calls its start method.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TypingTestView extends JFrame
{

    // Instance variables
    public final static int SCREEN_WIDTH = 1000;
    public final static int SCREEN_HEIGHT = 600;
    public final static int SCREEN_X_OFFSET = 100;
    public final static int LINE_Y_OFFSET = 40;
    public final static int PIXELS_BETWEEN_WORDS = 25;
    public final static int NUM_WORDS_TO_SHOW = 9;

    // Stats spacings
    public final static int STATS_STARTING_HEIGHT = 75;
    public final static int STATS_X_OFFSET = SCREEN_WIDTH / 8 * 6;
    public final static int STATS_Y_OFFSET = 30;

    private final static Color lightBlue = new Color(37, 125, 141);
    private final static Color darkBlue = new Color(61, 88, 147);
    private final static Color transparentBlue = new Color(93, 93, 117, 128);
    private final static Font statsFont = new Font("Calibri", Font.BOLD, 30);
    private final static Font textFont = new Font("Calibri", Font.BOLD, 40);
    private final static Image backgroundImage = new ImageIcon("Resources/Background.png").getImage();

    private final TypingTest backend;

    // Constructor
    public TypingTestView(TypingTest backend)
    {
        this.backend = backend;
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setTitle("TypingTest");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new DrawingPanel());
        this.setVisible(true);
    }

    private ArrayList<String> getNewArrayList()
    {
        // Declare and initialize a new arrayList and set it equal to the backend's arrayList. I can't just set
        // it equal to the backend's arrayList though because if I modify that new arrayList, it'll modify the
        // one in the backend as well.
        ArrayList<String> textToPrint = new ArrayList<>(backend.getText());

        // Add the cursor to the first word.
        String currentWord = textToPrint.get(0);
        // Set first word to have the cursor in the right position
        textToPrint.set(0, currentWord.substring(0, backend.getCursorIndex()) + "|" +
                currentWord.substring(backend.getCursorIndex()) + " ");

        return textToPrint;
    }

    // Draws the text onto the screen with the correct offsets, so it wraps around to a new line
    // instead of going off the screen.
    private void drawText(ArrayList<String> ar, Graphics g, int startingHeight)
    {
        // Set color and font to the correct ones for the main text.
        g.setColor(transparentBlue);
        g.setFont(textFont);

        // Code I found online that lets me access the on-screen width of a string for a given font
        FontMetrics metrics = g.getFontMetrics();
        int yPos = startingHeight;
        int xPos = SCREEN_X_OFFSET;

        // For loop that runs for the number of words I want to print
        for (int i = 0; i < NUM_WORDS_TO_SHOW; i++)
        {
            // Draws the current word at i in the arrayList of Strings
            if (i == 0)
            {
                for (int j = 0; j < ar.get(0).length(); j++)
                {
                    if (j < backend.getCursorIndex())
                    {
                        g.setColor(darkBlue);

                    }
                    else
                    {
                        g.setColor(transparentBlue);
                    }
                    g.drawString(ar.get(0).substring(j, j + 1), xPos, yPos);
                    xPos += metrics.stringWidth(ar.get(0).substring(j, j + 1));
                }
                xPos += PIXELS_BETWEEN_WORDS;
            }
            else
            {
                g.drawString(ar.get(i), xPos, yPos);

                // If the next word will go off the screen, reset it to the starting x position and start a new line.
                // Else, just print it normally
                if (xPos + metrics.stringWidth(ar.get(i) + " ") + metrics.stringWidth(ar.get(i + 1) + " ")
                        > SCREEN_WIDTH - SCREEN_X_OFFSET)
                {
                    xPos = SCREEN_X_OFFSET;
                    yPos += LINE_Y_OFFSET;
                }
                else
                {
                    xPos += metrics.stringWidth(ar.get(i) + " ") + PIXELS_BETWEEN_WORDS;
                }
            }
        }
    }

    // Print Stats
    private void printStats(Graphics g)
    {
        // Set color and font to the correct ones for the stats.
        g.setColor(lightBlue);
        g.setFont(statsFont);

        int height = STATS_STARTING_HEIGHT;
        // Draw WPM
        g.drawString("WPM: " + backend.getWPM(), STATS_X_OFFSET, height);

        height += STATS_Y_OFFSET;
        // Draw Error Count
        g.drawString("Error Count: " + backend.getErrorCount(), STATS_X_OFFSET, height);

//        height += STATS_Y_OFFSET;
//        // Draw Error Count
//        g.drawString("Error Count: " + backend.getWPM(), STATS_X_OFFSET, height);

    }

    // Code I found online that allows me to use double buffering for a smoother screen refresh
    // and to eliminate JFrame flickering
    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            // Checks to make sure the backend is initialized
            if (backend != null)
            {
                // Draw the background image over the whole screen to clear the last frame
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

                printStats(g);

                // Make a new arraylist instead of just setting it to backend.getText because this means I will be
                // editing the original arrayList which I don't want to modify from this.
                ArrayList<String> textToPrint = getNewArrayList();
                // Draw the text to the screen
                drawText(textToPrint, g, SCREEN_HEIGHT / 2);
            }
        }
    }
}
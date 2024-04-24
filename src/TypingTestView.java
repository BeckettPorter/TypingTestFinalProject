import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TypingTestView extends JFrame {

    // Instance variables
    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 600;

    Font bigFont = new Font("Calibri", Font.BOLD, 30);
    private TypingTest backend;

    private final Image backgroundImage = new ImageIcon("Resources/Background.png").getImage();

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

    private void printCenteredText(ArrayList<String> text, Graphics g, int startingHeight) {
        // Code I found online that allows me to access the size of the text
        FontMetrics metrics = g.getFontMetrics();
        ArrayList<Integer> textWidth = new ArrayList<>();

        int totalWidth = 0;
        int height = startingHeight;

        for (int i = 0; i < text.size(); i++)
        {
            totalWidth += metrics.stringWidth(text.get(i));
            textWidth.add(metrics.stringWidth(text.get(i)));

            if (totalWidth > SCREEN_WIDTH - 200)
            {
                totalWidth = 0;
                height += 100;
            }
            g.drawString(text.get(i), calcCenteredTextOffset(text.get(i), g), height);
        }
    }

    private int calcCenteredTextOffset(String text, Graphics g)
    {
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(text);

        return (SCREEN_WIDTH - textWidth) / 2;
    }


    // Code I found online that allows me to use double buffering for a smoother screen refresh
    // and to eliminate JFrame flickering
    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            if (backend != null)
            {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                Color lightBlue = new Color(37, 125, 141);
                g.setColor(lightBlue);
                g.setFont(bigFont);

                // Draw WPM
                g.drawString("WPM: " + backend.getWPM(),
                        calcCenteredTextOffset("WPM: " + backend.getWPM(), g),
                        SCREEN_HEIGHT / 4);




                // Draw text
                ArrayList<String> textToPrint = ArrayList<String>();
                // TODO: Fix this and make it so that the textToPrint is a copy of the getText array,
                //  but don't just set it equal because I think this is breaking it
                        backend.getText();

                String currentWord = textToPrint.get(0);
                // Set first word to have the cursor in the right position
                textToPrint.set(0, currentWord.substring(0, backend.getCursorIndex()) + "|" +
                        currentWord.substring(backend.getCursorIndex()) + " ");

                printCenteredText(textToPrint, g, SCREEN_HEIGHT / 2);

            }
        }
    }
}

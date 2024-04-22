import javax.swing.*;
import java.awt.*;


public class TypingTestView extends JFrame {

    // Instance variables
    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 600;

    Font bigFont = new Font("Calibri", Font.BOLD, 30);


    TypingTest backend;

    private final Image backgroundImage;

    public TypingTestView(TypingTest backend)
    {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setTitle("TypingTest");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.backend = backend;
        this.backgroundImage = new ImageIcon("Resources/Background.png").getImage();
    }




    // Paint method to draw onto the screen
    public void paint(Graphics g)
    {
        if (backend != null)
        {
            g.drawImage(backgroundImage,
                    0, 0,
                    this);

            // Color I made for the text
            Color lightBlue = new Color(37, 125, 141);

            g.setColor(lightBlue);


            g.setFont(bigFont);
            g.drawString(String.valueOf(backend.getWPM()), SCREEN_WIDTH / 2, SCREEN_HEIGHT / 4);

            // Updates thing such as WPM, typed text, etc.
        }
    }
}
import javax.swing.*;
import java.awt.*;

public class TypingTestView extends JFrame {

    // Instance variables
    public static int SCREEN_WIDTH = 1000;
    public static int SCREEN_HEIGHT = 800;

    TypingTest backend;

    public TypingTestView()
    {
        this.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        this.setTitle("TypingTest");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }




    // Paint method to draw onto the screen
    public void paint(Graphics g)
    {
        // Updates thing such as WPM, typed text, etc.
    }
}

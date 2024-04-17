import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class TypingTest implements KeyListener {

    // Instance variables
    private TypingTestView window;
    private ArrayList<String> text;
    private int WPM;
    private int cursorIndex;
    private ArrayList<String> typedWords;
    private double elapsedTime;
    private double startingTime;
    private Timer updateTimer;

    // No argument constructor
    public TypingTest()
    {
        window = new TypingTestView();
    }

    // Getters and Setters
    public ArrayList<String> getText() { return text; }

    public void setText(ArrayList<String> text) { this.text = text; }

    public int getWPM() {
        return WPM;
    }

    public void setWPM(int WPM) {
        this.WPM = WPM;
    }

    public int getCursorIndex() {
        return cursorIndex;
    }

    public void setCursorIndex(int cursorIndex) {
        this.cursorIndex = cursorIndex;
    }

    public ArrayList<String> getTypedWords() {
        return typedWords;
    }

    public void setTypedWords(ArrayList<String> typedWords) {
        this.typedWords = typedWords;
    }


    // Other Methods
    @Override
    public void keyTyped(KeyEvent e)
    {
        // Method called every time a key is typed
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        // Not needed, just required by KeyListener
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Not needed, just required by KeyListener
    }

    // Method that returns the words per minute.
    public int calculateWPM()
    {
        double minutes = getElapsedTimeMinutes();
        if (minutes == 0)
        {
            // If the time hasn't been updated yet, return 0, so it doesn't divide by 0 and break.
            return 0;
        }
        return (int) (typedWords.size() / minutes);
    }



    private void updateView()
    {
        // This method will update the window with updated information every frame.
    }

    // Main logic to run the test
    private void startTest()
    {
        // Variables that should be reset each time a new run starts.
        text = initializeText();
        // I found this online it allows me to access the current time of the computer,
        // so I can calculate elapsed time.
        startingTime = System.currentTimeMillis();
        WPM = 0;
        cursorIndex = 0;
        typedWords = new ArrayList<>();
        elapsedTime = 0;
        setupUpdateTimer();
        updateTimer.start();
    }

    public double getElapsedTimeMinutes()
    {
        double currentTime = System.currentTimeMillis();
        double elapsedTimeMillis = currentTime - startingTime;

        // This converts milliseconds to minutes
        return elapsedTimeMillis / 60000.0;
    }

    private void setupUpdateTimer()
    {
        // Code I found online that is calls this event every frame (so these variables will be continuously updated).
        updateTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WPM = calculateWPM();
                System.out.println(WPM);
            }
        }
        );
    }

    // Initializes the arrayList of strings that represents the text the user will type with random words.
    private ArrayList<String> initializeText()
    {
        // Add random words from a list to this input array
        return null;
    }

    // Main method that makes a new TypingTest.java object and calls startTest() on it.
    public static void main(String[] args)
    {
        TypingTest t = new TypingTest();
        t.startTest();
    }
}

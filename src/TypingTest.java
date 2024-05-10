import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * TypingTest and TypingTestView
 * Written by Beckett Porter for CS2
 *
 * This project implements the KeyListener and uses JFrame to make a typing test that allows users to find
 * their typing speed, along with accuracy.
 */

public class TypingTest implements KeyListener
{
    // Instance variables
    private final TypingTestView window;
    private ArrayList<String> text;
    private int WPM;
    private int errorCount;
    private int cursorIndex;
    private int typedWords;
    private double elapsedTime;
    private double startingTime;
    private Timer updateTimer;
    private Timer testTimer;
    private boolean testRunning;
    public static final int TEST_LENGTH = 30;

    // No argument constructor
    public TypingTest()
    {
        window = new TypingTestView(this);
        window.addKeyListener(this);
        testRunning = false;
    }

    // Getters and Setters
    public ArrayList<String> getText() { return text; }

    public int getWPM() {
        return WPM;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public int getCursorIndex() {
        return cursorIndex;
    }

    public int getTypedWords() {
        return typedWords;
    }


    public boolean isTestRunning() {
        return testRunning;
    }

    public int getTestLength() {
        return TEST_LENGTH;
    }

    // Other Methods
    // Method called every time a key is typed
    @Override
    public void keyTyped(KeyEvent e)
    {
        String currentWord = text.get(typedWords);

        if (isTestRunning())
        {
            if (cursorIndex < currentWord.length() && e.getKeyChar() == currentWord.charAt(cursorIndex))
            {
                cursorIndex++;
                if (cursorIndex == currentWord.length())
                {
                    cursorIndex = 0;
                    typedWords++;
                }
            }
            else
            {
                errorCount++;
            }
        }
        else
        {
            if (e.getKeyChar() == KeyEvent.VK_ENTER)
            {
                System.out.println("new game");
                startTest();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        // Not needed, just required by KeyListener.
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Not needed, just required by KeyListener.
    }

    // Method that returns the words per minute.
    private int calculateWPM()
    {
        double minutes = getElapsedTimeMinutes();
        if (minutes == 0)
        {
            // If the time hasn't been updated yet, return 0, so it doesn't divide by 0 and break.
            return 0;
        }
        return (int) (typedWords / minutes);
    }

    // Initializes an arrayList of strings that represents the text the user will type with random words.
    private ArrayList<String> generateRandomWords()
    {
        // This is code I found online that allows me to add all these words to an arrayList
        // without having to do it on many separate lines.
        ArrayList<String> newAr = new ArrayList<>(Arrays.asList(
                "skill", "odd", "school", "entry", "shift", "trip", "end", "total", "alert", "mix", "study", "root",
                "bend", "old", "sorry", "gear", "like", "random", "store", "field", "catch", "clear", "hit", "plus",
                "give", "help", "think", "sure", "air", "join", "sell", "check", "real", "allow", "fall", "use",
                "icon", "road", "fact", "great", "clumsy", "grad", "dance", "sign", "wall", "base", "fight",
                "act", "trust", "bike", "life", "cover", "storm", "plan", "error", "path", "loud", "edge",
                "gift", "courage", "short", "agent", "box", "math", "photo", "drive", "part", "city",
                "chief", "ride", "list", "spark", "type", "bug", "careful", "warn", "party", "count", "event",
                "test", "room", "hero", "mess", "trait", "charm", "map", "race", "point", "joy", "chem", "find",
                "star", "music", "balloon", "car", "motion", "maze", "magnet", "guide", "riddle",
                "tiny", "tough", "tech", "cycle", "swirl", "funny", "gas", "boat", "fervent", "sound",
                "bird", "laugh", "color", "wood", "hop", "berry", "leaf", "art", "book", "water",
                "bot", "plant", "big", "rain", "nut", "food", "horse", "gem", "motor", "ape",
                "place", "fly", "flower", "peak", "fruit", "arrow", "stream", "shade", "myth", "mount",
                "day", "cup", "night", "tree", "jar", "nothing"
        ));



        for (int i = 0; i < newAr.size(); i++)
        {
            newAr.set(i, newAr.get(i) + " ");
        }

        // Line of code I found online that randomizes the array order
        Collections.shuffle(newAr);

        // Remove space after every NUM_WORDS_TO_SHOW word so the user doesn't have
        // to press space after typing the final word.

        for (int i = TypingTestView.NUM_WORDS_TO_SHOW - 1; i < newAr.size(); i += TypingTestView.NUM_WORDS_TO_SHOW)
        {
            newAr.set(i, newAr.get(i).substring(0, newAr.get(i).length() - 1));
        }
        return newAr;
    }

    // Main logic to run the test
    private void startTest()
    {
        // Variables that should be reset each time a new run starts.
        text = generateRandomWords();
        // I found this online it allows me to access the current time of the computer,
        // so I can calculate elapsed time.
        startingTime = System.currentTimeMillis();
        WPM = 0;
        errorCount = 0;
        cursorIndex = 0;
        typedWords = 0;
        elapsedTime = 0;
        setupUpdateTimer();
        updateTimer.start();
        testTimer.start();
        testRunning = true;
    }

    // Method that ends the test and resets variables so the next test will start correctly. Also stops the timer.
    private void endTest()
    {
        testRunning = false;
        testTimer.stop();
        updateTimer.stop();
        window.repaint();
    }

    // Gets the time since the test started in minutes.
    public double getElapsedTimeMinutes()
    {
        double currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - startingTime;

        // This converts milliseconds to minutes
        return elapsedTime / 60000.0;
    }

    // Gets the time since the test started in seconds.
    public double getElapsedTimeSeconds()
    {
        double currentTime = System.currentTimeMillis();
        elapsedTime = currentTime - startingTime;

        return elapsedTime / 1000;
    }

    //
    private void setupUpdateTimer()
    {
        // Code I found online that calls this event every frame (so these variables will be continuously updated).
        updateTimer = new Timer(16, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WPM = calculateWPM();
                window.repaint();
            }
        }
        );
        testTimer = new Timer(TEST_LENGTH * 1000, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                endTest();
            }
        }
        );

    }

    // Main method that makes a new TypingTest.java object and calls startTest() on it.
    public static void main(String[] args)
    {
        TypingTest t = new TypingTest();
        t.startTest();
    }
}
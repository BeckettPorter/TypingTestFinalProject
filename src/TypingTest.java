import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        window = new TypingTestView(this);
        window.addKeyListener(this);
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
    // Method called every time a key is typed
    @Override
    public void keyTyped(KeyEvent e)
    {
        String currentWord = text.get(0);

        if (cursorIndex < currentWord.length() && e.getKeyChar() == currentWord.charAt(cursorIndex))
        {
            cursorIndex++;
            if (cursorIndex == currentWord.length())
            {
                cursorIndex = 0;
                typedWords.add(text.remove(0));
            }
        }
        else
        {
            System.out.println("Wrong letter");
        }
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
    private int calculateWPM()
    {
        double minutes = getElapsedTimeMinutes();
        if (minutes == 0)
        {
            // If the time hasn't been updated yet, return 0, so it doesn't divide by 0 and break.
            return 0;
        }
        return (int) (typedWords.size() / minutes);
    }

    // Initializes an arrayList of strings that represents the text the user will type with random words.
    private ArrayList<String> generateRandomWords()
    {
        // This is code I found online that allows me to add all these words to an arrayList
        // without having to do it on many separate lines.
        ArrayList<String> newAr = new ArrayList<>(Arrays.asList(
                "ability", "absurd", "academy", "access", "adapt", "adventure", "aftermath", "aggregate",
                "alarm", "alloy", "analysis", "ancestor", "angle", "antique", "apology", "apparatus",
                "appreciate", "arbitrary", "archive", "arena", "arrest", "articulate", "assault", "asset",
                "assign", "assist", "assume", "assure", "atmosphere", "attach", "auction", "audit",
                "authentic", "authorize", "autumn", "availability", "avatar", "avenue", "awareness", "awesome",
                "awkward", "bachelor", "ballet", "banner", "barrier", "baseline", "battle", "beacon",
                "behavior", "believe", "bicycle", "biography", "biology", "blanket", "blizzard", "blossom",
                "blueprint", "blunder", "boardwalk", "boisterous", "boundary", "bouquet", "bravery", "brevity",
                "broker", "butterfly", "cabinet", "calculus", "camera", "campaign", "canal", "candle",
                "capability", "capacitor", "capital", "captain", "carriage", "catalog", "catalyst", "category",
                "caterpillar", "cautious", "caveat", "celebrate", "census", "ceremony", "certainty", "challenge",
                "chamber", "champion", "channel", "chaos", "characteristic", "charisma", "charity", "chart",
                "chase", "checkpoint", "cheer", "chemistry", "discovery", "elephant", "frosting", "galaxy",
                "harmonica", "inflatable", "jaguar", "kinetic", "labyrinth", "magnet", "navigator", "optical",
                "paradox", "quantum", "resilient", "saxophone", "technology", "unicycle", "vortex", "whimsical",
                "xenon", "yacht", "zealous", "dolphin", "echo", "flamingo", "giraffe", "hyena",
                "indigo", "jungle", "kangaroo", "lemon", "mosaic", "novel", "oasis", "penguin",
                "quilt", "robot", "sunflower", "tiger", "umbrella", "violin", "walnut", "xylophone",
                "yogurt", "zebra", "diamond", "engine", "fern", "gorilla", "harp", "island",
                "jewel", "kite", "lotus", "mountain", "neon", "orange", "peacock", "quiver",
                "river", "shadow", "turtle", "unicorn", "volcano", "whale", "x-ray", "yellow",
                "zoo", "apple", "bridge", "cloud", "dove", "eagle", "forest", "grape",
                "hill", "ice", "juice", "key", "leaf", "moon", "night", "ocean",
                "palm", "queen", "rose", "star", "tree", "vase", "wind", "fox", "yard", "zero"
        ));



        // Line of code I found online that randomizes the array order
        Collections.shuffle(newAr);

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
        // Code I found online that calls this event every frame (so these variables will be continuously updated).
        updateTimer = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                WPM = calculateWPM();
                window.repaint();
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

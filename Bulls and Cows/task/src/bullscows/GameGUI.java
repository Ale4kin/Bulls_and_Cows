package bullscows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameGUI extends JFrame {

    private JTextField lengthField;
    private JTextField symbolsField;
    private JButton startGameButton;
    private JTextArea gameLogArea;
    private JTextField guessField;
    private JButton submitGuessButton;

    // Game logic variables will be needed here
    // For now, let's use placeholders from the existing Main.java
    private String secretCode;
    private int codeLength;
    private int numSymbols;
    private int turn = 1;

    public GameGUI() {
        try {
            // Attempt to set the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // Fallback to Nimbus if system L&F fails or isn't great
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception ex) {
                // If Nimbus is not available, proceed with the default L&F
                System.err.println("Nimbus L&F not found, using default. Error: " + ex.getMessage());
            }
        }

        setTitle("Bulls and Cows - Modern Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 550); // Slightly larger for better spacing
        setMinimumSize(new Dimension(550, 450)); // Prevent making it too small
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15)); // Increased main panel gaps

        // Add overall padding to the main content pane
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        // Setup Panel (North)
        JPanel setupPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout for more control
        setupPanel.setBorder(BorderFactory.createTitledBorder("Game Setup"));
        GridBagConstraints gbcSetup = new GridBagConstraints();
        gbcSetup.insets = new Insets(5, 5, 5, 5); // Padding for components
        gbcSetup.anchor = GridBagConstraints.WEST;

        gbcSetup.gridx = 0; gbcSetup.gridy = 0;
        setupPanel.add(new JLabel("Secret Code Length:"), gbcSetup);
        lengthField = new JTextField(5);
        gbcSetup.gridx = 1; gbcSetup.gridy = 0;
        setupPanel.add(lengthField, gbcSetup);

        gbcSetup.gridx = 0; gbcSetup.gridy = 1;
        setupPanel.add(new JLabel("Possible Symbols (max 36):"), gbcSetup);
        symbolsField = new JTextField(5);
        gbcSetup.gridx = 1; gbcSetup.gridy = 1;
        setupPanel.add(symbolsField, gbcSetup);

        startGameButton = new JButton("Start Game");
        startGameButton.setFont(new Font(startGameButton.getFont().getName(), Font.BOLD, 13));
        gbcSetup.gridx = 0; gbcSetup.gridy = 2; gbcSetup.gridwidth = 2; gbcSetup.anchor = GridBagConstraints.CENTER;
        setupPanel.add(startGameButton, gbcSetup);
        add(setupPanel, BorderLayout.NORTH);

        // Game Log Area (Center)
        gameLogArea = new JTextArea();
        gameLogArea.setEditable(false);
        gameLogArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Good for logs
        gameLogArea.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding inside text area
        JScrollPane scrollPane = new JScrollPane(gameLogArea);
        scrollPane.setBorder(BorderFactory.createEtchedBorder());
        add(scrollPane, BorderLayout.CENTER);

        // Controls Panel (South)
        JPanel controlsPanel = new JPanel(new GridBagLayout()); // Using GridBagLayout
        controlsPanel.setBorder(BorderFactory.createTitledBorder("Your Move"));
        GridBagConstraints gbcControls = new GridBagConstraints();
        gbcControls.insets = new Insets(5, 5, 5, 5);
        gbcControls.anchor = GridBagConstraints.WEST;
        gbcControls.fill = GridBagConstraints.HORIZONTAL;

        gbcControls.gridx = 0; gbcControls.gridy = 0;
        controlsPanel.add(new JLabel("Your Guess:"), gbcControls);
        guessField = new JTextField(15);
        guessField.setFont(new Font(guessField.getFont().getName(), Font.PLAIN, 13));
        gbcControls.gridx = 1; gbcControls.gridy = 0; gbcControls.weightx = 1.0; // Allow guessField to expand
        controlsPanel.add(guessField, gbcControls);

        submitGuessButton = new JButton("Submit Guess");
        submitGuessButton.setFont(new Font(submitGuessButton.getFont().getName(), Font.BOLD, 13));
        gbcControls.gridx = 2; gbcControls.gridy = 0; gbcControls.weightx = 0; // Don't expand button
        controlsPanel.add(submitGuessButton, gbcControls);
        add(controlsPanel, BorderLayout.SOUTH);

        // Initially, disable guess input until game starts
        guessField.setEnabled(false);
        submitGuessButton.setEnabled(false);

        // Action Listeners
        startGameButton.addActionListener(e -> initializeGame());
        submitGuessButton.addActionListener(e -> processGuess());
        guessField.addActionListener(e -> {
            if (submitGuessButton.isEnabled()) {
                processGuess();
            }
        });

        setVisible(true);
        appendToLog("Welcome to Bulls and Cows!\nEnter code length and number of symbols to start.");
    }

    private void appendToLog(String message) {
        gameLogArea.append(message + "\n");
        gameLogArea.setCaretPosition(gameLogArea.getDocument().getLength()); // Auto-scroll
    }

    private void initializeGame() {
        // Clear log for new game at the very beginning of initialization
        gameLogArea.setText(""); 
        appendToLog("Welcome to Bulls and Cows!\nEnter code length and number of symbols to start.\n");
        try {
            String lengthStr = lengthField.getText();
            if (lengthStr.isEmpty()) {
                appendToLog("Error: Please enter the secret code's length.");
                return;
            }
            codeLength = Integer.parseInt(lengthStr);

            if (codeLength <= 0) {
                appendToLog("Error: Secret code length must be greater than 0.");
                return;
            }
            if (codeLength > 36) {
                appendToLog("Error: Maximum secret code length is 36.");
                return;
            }

            String symbolsStr = symbolsField.getText();
            if (symbolsStr.isEmpty()) {
                appendToLog("Error: Please enter the number of possible symbols.");
                return;
            }
            numSymbols = Integer.parseInt(symbolsStr);

            if (numSymbols < codeLength) {
                appendToLog("Error: it's not possible to generate a code with a length of " + codeLength + " with " + numSymbols + " unique symbols.");
                return;
            }
            if (numSymbols > 36) {
                appendToLog("Error: Maximum number of possible symbols is 36 (0-9, a-z).");
                return;
            }
             if (numSymbols <= 0) {
                appendToLog("Error: Number of possible symbols must be greater than 0.");
                return;
            }

            // Clear log before generating new code and starting game messages
            gameLogArea.setText(""); 

            secretCode = generateRandomSecretCode(codeLength, numSymbols);
            if (secretCode == null) { // Error occurred in generation
                lengthField.setEnabled(true); // Re-enable setup fields on error
                symbolsField.setEnabled(true);
                startGameButton.setEnabled(true);
                return;
            }

            appendToLog("Okay, let's start a game!");
            turn = 1;

            lengthField.setEnabled(false);
            symbolsField.setEnabled(false);
            startGameButton.setEnabled(false);

            guessField.setEnabled(true);
            submitGuessButton.setEnabled(true);
            guessField.requestFocus();

        } catch (NumberFormatException e) {
            appendToLog("Error: Code length and number of symbols must be valid numbers.");
            lengthField.setEnabled(true); // Re-enable setup fields on error
            symbolsField.setEnabled(true);
            startGameButton.setEnabled(true);
        } catch (IllegalArgumentException e) {
            appendToLog("Error: " + e.getMessage());
            lengthField.setEnabled(true); // Re-enable setup fields on error
            symbolsField.setEnabled(true);
            startGameButton.setEnabled(true);
        }
    }

    private void processGuess() {
        String guess = guessField.getText().trim().toLowerCase(); // Standardize guess to lowercase

        if (guess.length() != codeLength) {
            appendToLog("Error: Guess must be " + codeLength + " characters long.");
            guessField.requestFocus();
            return;
        }

        // Ensure the source characters in generateRandomSecretCode are distinct and cover the validation here.
        // Current validation (isLetterOrDigit) is broad. More specific validation against the `numSymbols` character set could be added.
        ArrayList<Character> allowedChars = new ArrayList<>();
        char[] allPossibleChars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        for (int i = 0; i < numSymbols; i++) {
            allowedChars.add(allPossibleChars[i]);
        }

        for (char c : guess.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) { // Initial check
                appendToLog("Error: Guess can only contain letters and digits.");
                guessField.requestFocus();
                return;
            }
            if (!allowedChars.contains(Character.toLowerCase(c))) {
                String validRangeStr;
                if (numSymbols == 1) {
                     validRangeStr = "(" + allowedChars.get(0) + ")";
                } else if (numSymbols <= 10) {
                    validRangeStr = "(0-" + allowedChars.get(numSymbols - 1) + ")";
                } else { 
                    validRangeStr = "(0-9, a-" + allowedChars.get(numSymbols - 1) + ")";
                }
                appendToLog("Error: Guess contains characters outside the allowed symbol range: " + validRangeStr);
                guessField.requestFocus();
                return;
            }
        }

        appendToLog("\nTurn " + turn + ": Your guess is " + guess);

        int countBulls = 0;
        int countCows = 0;

        char[] secretChars = secretCode.toCharArray(); // secretCode is already lowercase from generation
        char[] guessChars = guess.toCharArray(); // guess is now lowercased

        // Calculate Bulls
        for (int i = 0; i < codeLength; i++) {
            if (secretChars[i] == guessChars[i]) {
                countBulls++;
            }
        }

        boolean[] secretUsed = new boolean[codeLength];
        boolean[] guessUsed = new boolean[codeLength];

        for (int i = 0; i < codeLength; i++) {
            if (secretChars[i] == guessChars[i]) {
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        for (int i = 0; i < codeLength; i++) {
            if (!secretUsed[i]) { 
                for (int j = 0; j < codeLength; j++) {
                    if (!guessUsed[j] && i != j) { 
                        if (secretChars[i] == guessChars[j]) {
                            countCows++;
                            secretUsed[i] = true; 
                            guessUsed[j] = true;  
                            break; 
                        }
                    }
                }
            }
        }

        String grade;
        if (countBulls == codeLength) {
            grade = "Grade: " + countBulls + " bull(s). Congratulations! You guessed the secret code: " + secretCode;
            appendToLog(grade);
            appendToLog("\nGame Over! Click 'Start Game' to play again with new settings.");
            guessField.setEnabled(false);
            submitGuessButton.setEnabled(false);
            lengthField.setEnabled(true);
            symbolsField.setEnabled(true);
            startGameButton.setEnabled(true);
            lengthField.requestFocus();
        } else {
            if (countCows == 0 && countBulls > 0) {
                grade = "Grade: " + countBulls + " bull(s).";
            } else if (countBulls == 0 && countCows > 0) {
                grade = "Grade: " + countCows + " cow(s).";
            } else if (countBulls == 0 && countCows == 0) {
                grade = "Grade: None.";
            } else { 
                grade = "Grade: " + countBulls + " bull(s) and " + countCows + " cow(s).";
            }
            appendToLog(grade);
            turn++;
            guessField.setText("");
            guessField.requestFocus();
        }
    }

    // We will need to move/adapt the getRandomSecretCode method from Main.java here
    // and the bull/cow calculation logic.

    private String generateRandomSecretCode(int size, int possibleNumber) {
        Random random = new Random();
        //char[] code = new char[size]; // Not directly used to build the final string

        ArrayList<Character> source = new ArrayList<>(Arrays.asList(
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                'u', 'v', 'w', 'x', 'y', 'z'
        ));

        if (possibleNumber > source.size()) {
            appendToLog("Error: Number of possible symbols cannot exceed " + source.size() + " (0-9, a-z).");
            return null;
        }
         if (possibleNumber <= 0) { // Added check for non-positive/zero numSymbols here as well
            appendToLog("Error: Number of possible symbols must be greater than 0.");
            return null;
        }
        if (size > possibleNumber) {
             appendToLog("Error: it's not possible to generate a code with a length of " + size + " with " + possibleNumber + " unique symbols.");
             return null;
        }
        if (size <= 0) { // Added check for non-positive/zero size here as well
            appendToLog("Error: Secret code length must be greater than 0.");
            return null;
        }


        List<Character> availableChars = new ArrayList<>(source.subList(0, possibleNumber));
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            if (availableChars.isEmpty()) { // Should not happen if size <= possibleNumber validation is correct
                appendToLog("Error: Ran out of unique characters to generate the code.");
                return null;
            }
            int randomIndex = random.nextInt(availableChars.size());
            sb.append(availableChars.remove(randomIndex));
        }
        String result = sb.toString();

        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < size; i++) {
            stars.append('*');
        }

        // Determine the range string correctly
        String rangeStr;
        if (possibleNumber == 1 && size ==1) {
            rangeStr = "(" + source.get(0) + ")";
        } else if (possibleNumber <= 10) {
            rangeStr = "(0-" + source.get(possibleNumber - 1) + ")";
        } else { // possibleNumber > 10
            rangeStr = "(0-9, a-" + source.get(possibleNumber - 1) + ")";
        }

        appendToLog("The secret is prepared: " + stars.toString() + " " + rangeStr);
        return result;
    }

    public static void main(String[] args) {
        // Ensure GUI updates are handled on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameGUI();
            }
        });
    }
} 
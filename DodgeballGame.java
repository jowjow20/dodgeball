import java.util.Random;
import java.util.Scanner;

public class DodgeballGame {

    // Method 1: Print the current state of the game
    public static void printGameState(char[][] matcherBoard, int matcherNumPlays, char[] dodgerRow, int dodgerNumPlays) {
        System.out.println("Here is the current Matcher Board:");
        for (int i = 0; i < matcherBoard.length; i++) {
            for (int j = 0; j < matcherBoard[i].length; j++) {
                System.out.print((matcherBoard[i][j] == '\u0000' ? "." : matcherBoard[i][j]) + " ");
            }
            System.out.println();
        }

        System.out.println("\nHere is the current Dodger Row:");
        for (int i = 0; i < dodgerRow.length; i++) {
            System.out.print((dodgerRow[i] == '\u0000' ? "." : dodgerRow[i]) + " ");
        }
        System.out.println("\n");
    }

    // Method 2: Generate a random choice of 'X' or 'O'
    public static char getRandomChoice() {
        Random random = new Random();
        return random.nextBoolean() ? 'X' : 'O';
    }

    // Method 3: Matcher Play - Fill the End of the Row
    public static void matcherPlay(char[][] matcherBoard, int matcherNumPlays) {
        int currentRow = matcherNumPlays;
        if (currentRow < matcherBoard.length) {
            for (int col = 0; col < matcherBoard[currentRow].length; col++) {
                if (matcherBoard[currentRow][col] == '\u0000') {
                    matcherBoard[currentRow][col] = getRandomChoice();
                }
            }
        }
    }

    // Method 4: Matcher Optimal Strategy
    public static void matcherOptimalStrategy(char[][] matcherBoard, int matcherNumPlays, char[] dodgerRow) {
        int currentRow = matcherNumPlays;
        if (currentRow < matcherBoard.length) {
            for (int col = 0; col < matcherBoard[currentRow].length; col++) {
                if (col < dodgerRow.length && dodgerRow[col] != '\u0000' && Math.random() > 0.5) {
                    // Use the Dodger's move strategically (50% chance)
                    matcherBoard[currentRow][col] = dodgerRow[col];
                } else {
                    // Fill randomly otherwise
                    matcherBoard[currentRow][col] = getRandomChoice();
                }
            }
        }
    }    

    // Method 5: Getting Dodger Play
    public static int getDodgerPlay(char[] dodgerRow, int dodgerNumPlays) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Dodger, what would you like to play next? (X or O): ");
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.length() == 1 && (input.charAt(0) == 'X' || input.charAt(0) == 'O')) {
            if (dodgerNumPlays < dodgerRow.length) {
                dodgerRow[dodgerNumPlays] = input.charAt(0);
                return dodgerNumPlays + 1;
            } else {
                System.out.println("Dodger row is already full!");
                return dodgerNumPlays;
            }
        } else {
            System.out.println("Invalid input! You must enter 'X' or 'O'. Please try again.");
            return dodgerNumPlays;
        }
    }

    // Method 6: Play the Game
    public static void playGame(char[][] matcherBoard, char[] dodgerRow) {
        int matcherNumPlays = 1; // Start from the second row, as the first row is pre-populated
        int dodgerNumPlays = 1; // Start from the second slot, as the first slot is pre-filled

        while (matcherNumPlays < matcherBoard.length && dodgerNumPlays < dodgerRow.length) {
            printGameState(matcherBoard, matcherNumPlays, dodgerRow, dodgerNumPlays);

            int newDodgerNumPlays = getDodgerPlay(dodgerRow, dodgerNumPlays);
            if (newDodgerNumPlays == dodgerNumPlays) {
                continue;
            }
            dodgerNumPlays = newDodgerNumPlays;

            matcherOptimalStrategy(matcherBoard, matcherNumPlays, dodgerRow);
            matcherNumPlays++;
        }

        System.out.println("The game has ended. Here are the final boards:");
        printGameState(matcherBoard, matcherNumPlays, dodgerRow, dodgerNumPlays);
        String winner = determineWinner(matcherBoard, dodgerRow);
        System.out.println(winner);
    }

    // Method 7: Determine the Winner
    public static String determineWinner(char[][] matcherBoard, char[] dodgerRow) {
        for (int i = 0; i < matcherBoard.length; i++) {
            boolean match = true;

            for (int j = 0; j < dodgerRow.length; j++) {
                if (matcherBoard[i][j] != dodgerRow[j]) {
                    match = false;
                    break;
                }
            }

            if (match) {
                return "Matcher Wins! Any row in matcherBoard matches dodgerRow.";
            }
        }
        return "Dodger Wins! No rows in matcherBoard match dodgerRow.";
    }

    public static void main(String[] args) {
        // Initialize the Matcher board
        char[][] matcherBoard = new char[6][6];

        // Fill the first row with random X or O
        for (int col = 0; col < matcherBoard[0].length; col++) {
            matcherBoard[0][col] = getRandomChoice();
        }

        // Initialize the Dodger's row with the first random value
        char[] dodgerRow = new char[6];
        dodgerRow[0] = getRandomChoice();
        for (int i = 1; i < dodgerRow.length; i++) {
            dodgerRow[i] = '\u0000';
        }

        playGame(matcherBoard, dodgerRow);
    }
}

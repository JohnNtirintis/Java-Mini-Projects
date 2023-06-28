package TicTacToe;

import java.util.Arrays;
import java.util.Scanner;

/**
 * This class represents a TiacTacToe game.
 *
 * @author Ntirintis John
 */
public class TicTacToeApp {
    // Init scanner to read user input
    static Scanner sc = new Scanner(System.in);
    // Game board presented as a 2D array
    static char[][] tictactoe = {
            {' ', ' ',' '},
            {' ', ' ',' '},
            {' ', ' ',' '}
    };
    // Stores the shape chosen by the players
    static char[] playerChars = new char[2];
    // Variable to keep track of current player
    static int index = 0;
    // Variables to hold the position entered by the player
    static int height;
    static int width;
    // Counter variable to keep track of the total moves in the game
    static int count = 0;

    /**
     * Main method that drives the game
     */
    public static void main(String[] args) {
        int choice = 0;

        printMenu();
        System.out.print("Pick: ");
        System.out.println();
        choice = getChoice();
        // Keep playing until user chooses to exit
        while(choice != 2) {
            System.out.println("The game begins.");
            ShapeChoice(); // players choose their shape
            playGame(); // plays the game
            resetBoard(); // resets for the new game

            System.out.println("Do you want to play again?");
            printMenu();
            choice = getChoice();
        }

        // Close scanner to avoid memory/resources leak
        sc.close();
    }

    /**
     * Prints the options menu to the console
     */
    static void printMenu(){
        System.out.println("1. Play.");
        System.out.println("2. Exit.");
    }

    /**
     * Prints the game instructions to the console
     */
    static void printInstructions(){
        System.out.println("In order to play the game and enter the location you want to place your sign,");
        System.out.println("You have to enter the coordinates for the specific location.");
        System.out.println("The coordinates range from 0 to 2,");
        System.out.println("The first number represents the height and the other the width.");
        System.out.println("I.e. a valid coordinate is 0 1 (1st row, 2nd square) or 2 0 or even 0 0");
        System.out.println("invalid coordinates are: 3 0 or 3 3 and -1 1.");
    }

    /**
     * Handles shape choice of the players
     */
    static void ShapeChoice(){
        System.out.println("Player 1 pick your shape. Either X or O");
        try {
            String choice = getShape();
            switch (choice.toUpperCase().charAt(0)) {
                case 'X':
                    playerChars[0] = 'X';
                    playerChars[1] = 'O';
                    System.out.println("Player 1 plays with X.");
                    System.out.println("Player 2 plays with O");
                    printInstructions();
                    break;
                case 'O':
                    playerChars[0] = 'O';
                    playerChars[1] = 'X';
                    System.out.println("Player 1 plays with O.");
                    System.out.println("Player 2 plays with X");
                    printInstructions();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            System.err.println("Error!Enter either X or O");
        }
    }

    /**
     * Places the player's choice on the board
     */
    static void putChoice(int height, int width){

        tictactoe[height][width] = playerChars[index];
    }

    /**
     * Changes the current player
     */
    static void changePlayer(){
        switch (index){
            case 0:
                index++;
                break;
            case 1:
                index--;
                break;
        }
    }

    /**
     * Prints the current state of the game board to the console
     */
    static void printBoard(){
        for(char[] chars : tictactoe){
            for(int i = 0; i < chars.length; i++){
                if(chars[i] == 'X'){
                    System.out.print('\u274C');
                }
                if(chars[i] == 'O'){
                    System.out.print('\u25EF');
                }
                // length - 1 so it skips the last char | at the very right of the board
                if(i < chars.length - 1) {
                System.out.print(" | ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Handles the game play
     */
    static void playGame(){
        while(true){
            System.out.printf("Enter the coordinates for where you want to enter your sign (%c) \n", playerChars[index]);
            System.out.print("Height: ");
            height = getPosition();
            System.out.println();
            System.out.print("Width: ");
            width = getPosition();

            try {
                if(canPutSign(height,width)){
                    count++;
                    putChoice(height,width);
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.err.println("Please enter a num in the range 0-2.");
            }

            printBoard();
            if(win(playerChars[index])){
                System.out.printf("Player with the sign %c win!", playerChars[index]);
                System.out.println();
                break;
            }
            if(draw()){
                System.out.println("Its a draw!");
                break;
            }
            changePlayer();
        }
    }

    /**
     * Checks to see if a draw.
     */
    static boolean draw(){
        int emptySpaces = 0;

        for(char[] row : tictactoe){
            for (char c : row) {
                if (c == ' ') {
                    emptySpaces++;
                }
            }
        }
        return emptySpaces == 0 || count == 10;
    }

    /**
     * Checks if its a win, by calling each dedicated win function
     */
    static boolean win(char playerChar){
        return checkHorizontalWin(playerChar) || checkVerticalWin(playerChar) || checkDiagonalWin(playerChar);
    }

    static boolean checkHorizontalWin(char playerChar){
        for (char[] chars : tictactoe) {
            if (chars[0] == playerChar && chars[1] == playerChar && chars[2] == playerChar) return true;
        }
        return false;
    }

    static boolean checkVerticalWin(char playerChar){
        for(int i = 0; i < tictactoe.length; i++){
        if(tictactoe[0][i] == playerChar && tictactoe[1][i] == playerChar && tictactoe[2][i] == playerChar) return true;
        }
        return false;
    }

    static boolean checkLeftDiagonalWin(char playerChar){
        return tictactoe[0][0] == playerChar && tictactoe[1][1] == playerChar && tictactoe[2][2] == playerChar;
    }

    static boolean checkRightDiagonalWin(char playerChar){
        return tictactoe[0][2] == playerChar && tictactoe[1][1] == playerChar && tictactoe[2][0] == playerChar;
    }

    static boolean checkDiagonalWin(char playerChar){
        return checkLeftDiagonalWin(playerChar) || checkRightDiagonalWin(playerChar);
    }

    /**
     * Resets the board for the new game
     *
     */
    static void resetBoard(){
        index = 0;
        count = 0;
        for(char[] row : tictactoe){
            Arrays.fill(row, ' ');
        }
    }

    /**
     * Checks to see if the position is already played (taken)
     */
    static boolean canPutSign(int height, int width){
        return tictactoe[height][width] == ' ';
    }

    static int getChoice(){
        return sc.nextInt();
    }

    /**
     * Gets the position of where the player wants to play/put their sign
     */
    static int getPosition() throws IllegalArgumentException{
        int num = -1;
        try {
            num = sc.nextInt();
            if(num < 0 || num > 2){
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e){
            e.printStackTrace();
            System.err.println("Enter a position from 0 to 2.");
        }
        return num;
    }

    static String getShape() { return sc.next(); }
}

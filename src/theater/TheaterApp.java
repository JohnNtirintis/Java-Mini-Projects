package theater;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is a simple theater ticket booking application.
 * It provides functionalities to book a seat, cancel a booking,
 * and display the current status of all seats.
 * Each seat is represented by a coordinate (row and column).
 * Each seat is either free (false) or booked (true).
 *
 * @author Ntirintis John
 */
public class TheaterApp {

    static Scanner sc = new Scanner(System.in);
    static  String[][] seats = new String[30][12];
    static boolean[][] booked = new boolean[30][12];

    public static void main(String[] args) {
        int choice = 0;

        fill();
        do{
            try {
                printMenu();
                System.out.println("Enter your choice:");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice){
                    case 1:
                        book();
                        break;
                    case 2:
                        cancel();
                        break;
                    case 3:
                        print();
                        break;
                    case 4:
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Please enter a valid number 1-4.");
            }
        }while(choice != 4);
    }

    /**
     * Displays the main menu to the user.
     */
    public static void printMenu(){
        System.out.println("1. Book a ticket.");
        System.out.println("2. Cancel a booking.");
        System.out.println("3. Show all of the seats.");
        System.out.println("4. Exit");
    }

    /**
     * Fills the "seats" array with seat coordinates,
     * Rows range from 1-30 and columns from A-L
     * and the inits all the seats in the "booked" array to false (unbooked).
     */
    public static void fill(){
        for(boolean[] row : booked){
            Arrays.fill(row, false);
        }
        for(int i = 0; i < seats.length; i++) {
            for(int j = 0; j < seats[i].length; j++) {
                seats[i][j] = (i+1) + Character.toString((char)(j + 'A'));
            }
        }
    }

    /**
     * Displays the status of all seats.
     * A booked seat is represented with a red X, and a free seat with a green checkmark.
     */
    public static void print(){
        for(int i = 0; i < seats.length; i++) {
            for(int j = 0; j < seats[i].length; j++) {
                System.out.print(seats[i][j] + " ");
                if(booked[i][j]) {
                    // If the seat is booked, print a red circle.
                    System.out.print("\u274C ");
                } else {
                    // If the seat is not booked, print a green circle.
                    System.out.print("\u2705 ");
                }
            }
            System.out.println();
        }
    }


    /**
     * Provides the functionality for the user to book a seat.
     * It prompts the user for a seat coordinate and marks the seat as booked.
     * Books the seat by calling the confirmBooking().
     */
    public static void book(){
        String user_input;
        int row;

        System.out.println("Here You can book your tickets fast and easy!");
        do{
            try {
                System.out.println("The rows are numbered 1-30 and the columns A-L");
                System.out.println("Please enter the seat you would like to book. I.e. C 4");
                System.out.println("Enter the column. (A-L)");
                user_input = sc.nextLine();
                if((int) Character.toUpperCase(user_input.charAt(0)) < 65 || (int) Character.toUpperCase(user_input.charAt(0)) > 76){
                    throw new IllegalArgumentException();
                }
                char column = Character.toUpperCase(user_input.charAt(0));
                System.out.println("Please enter the row. (1-30)");
                row = sc.nextInt();
                if(row < 1 || row > 30){
                    throw new InputMismatchException();
                }
                sc.nextLine();  // consume the newline

                if (booked[row - 1][column - 'A']) {
                    System.out.println("This seat is already booked!");
                    continue;
                } else {
                    confirmBooking(column, row - 1);
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Please enter a letter ranging from A-L.");
            } catch (InputMismatchException ex){
                ex.printStackTrace();
                System.out.println("Please enter number ranging from 1-30");
            }

            System.out.println("Would you like to book another seat? Y/N");
            user_input = sc.nextLine();
        }while(!user_input.matches("[Nn]"));
    }

    /**
     * Confirms a booking by marking the corresponding seat as booked in the "booked" array
     * by changing its value to true.
     *
     * @param column the column of the seat
     * @param row the row of the seat
     */
    public static void confirmBooking(char column, int row){

        int asciiValue = (int) column - 65;

        booked[row][asciiValue] = true;
    }

    /**
     * Provides the functionality for the user to cancel a booking.
     * It prompts the user for a seat coordinate and marks the seat as free.
     * It cancels the booking by calling confirmCancel()
     */
    public static void cancel(){
        String user_input = "";
        int row;
        char column;

        do {
            try {
                System.out.println("What is the row and column details of the booking that you want to cancel?");
                System.out.println("Please first enter the column. (A-L)");
                user_input = sc.nextLine();
                if((int) Character.toUpperCase(user_input.charAt(0)) < 65 || (int) Character.toUpperCase(user_input.charAt(0)) > 76){
                    throw new IllegalArgumentException();
                }
                column = Character.toUpperCase(user_input.charAt(0));
                System.out.println("Please enter the row. (1-30)");
                row = sc.nextInt();
                if(row < 1 || row > 30){
                    throw new InputMismatchException();
                }
                sc.nextLine();  // consume the newline

                if (!booked[row - 1][column - 'A']) {
                    System.out.println("This seat hasn't been booked.");
                    continue;
                } else{
                    confirmCancel(column, row - 1);
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Please enter a letter ranging from A-L");
            } catch (InputMismatchException ex){
                ex.printStackTrace();
                System.out.println("Please enter a num from 1 to 30.");
            }

            System.out.println("Would you like to cancel another booking? Y/N");
            try {
                user_input = sc.nextLine();
                if(!(user_input.matches("[Yy]") || user_input.matches("[Nn]"))){
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e){
                e.printStackTrace();
                System.out.println("Please enter either Y/y or N/n");
            }

        } while (!user_input.matches("[Nn]"));
    }

    /**
     * Confirms a cancellation by marking the corresponding seat as free in the "booked" array
     * by changing its value to false.
     *
     * @param column the column of the seat
     * @param row the row of the seat
     */
    public static void confirmCancel(char column, int row){

        int asciiValue = (int) column - 65;

        booked[row][asciiValue] = false;
    }
}

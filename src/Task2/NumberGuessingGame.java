package Task2;

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {
        bye();
    }

    static void bye() {
        Random r = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.println("""           
                                             ----------------------
                ++++++++++++++++++++++++++++| Number Guessing Game |+++++++++++++++++++++++++++
                                             ----------------------
                | You have to guess the computer generated number between the inputted range. |
                | Score will be generated according to the User's attempts out of 100.        |
                | Condition:- You have to guess the random number in 10 attempts.             |
                """);

        System.out.println("\nEnter range starting from ");
        int start = sc.nextInt();
        System.out.println("Enter range up to ");
        int end = sc.nextInt();
        if (start > end) {
            System.out.println("Input the values accordingly!!! ");
        }
        int value = end - start;
        int ch = r.nextInt(value) + start; //formula to take random number between start and end
        values(ch,start,end);
    }

    static void values(int ch,int start, int end) {
        Scanner sc = new Scanner(System.in);
        int attempt = 9;
        boolean tr = false;

        while(attempt >= 0 && !tr) {
            System.out.println("\nPick a number between "+start+" to "+end);
            int input = sc.nextInt();
            if (input == ch) {  // if the condition is true it will react

                if (attempt == 9){
                    System.out.println("Epic !!! you've won the game on your first attempt, you scored 100 ");

                } else if (attempt == 0) {
                    System.out.println("Woah Congratulations!!! you've won the game at the edge, you scored 10 out of 100");

                } else {
                System.out.println("Victory!!! You won the game in " + (10 - attempt) +
                        " attempts\nyou've Scored " + attempt * 10 + " out of 100");
                }
                tr = true;
            } else if (input > ch) {  // if input value is greater than random number
                System.out.println("  _______________________________________________");
                System.out.println("|| Entered value is higher than generated number ||");
                System.out.println("|| Number of attempt left: " + attempt);
                --attempt;
            } else {                  //if the input value is smaller than random number
                System.out.println("  ______________________________________________");
                System.out.println("|| Entered value is lower than generated number ||");
                System.out.println("|| Number of attempt left: " + attempt);
                --attempt;
            }

            if (tr) {                 // when user won the game and wants to play again
                System.out.println("\nDo you wish to play again? Type: [yes/no]"); // user can play game by typing yes and no by typing no
                String s = sc.next().toUpperCase();  // text is gonna converted into uppercase in case if user typed one of the character in capital/small
                if (s.equals("YES")) {
                    bye();
                } else {
                    System.exit(0);
                }
            }
        }
        System.out.println("Better Luck Next Time! The Generated Number was: " + ch);
        // if user lose the game this statement will let him know what was the number he was looking for
    }
}

package com.mycompany.assignmentlargenumarithmetic;

/**
 * Main class
 * A tester class to test all the arithmetic methods
 */

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LargeNumberManager list1;
        LargeNumberManager list2;

        while (true) { //an infinite loop that recursively prompt user to enter numbers if invalid characters are entered

            list1 = new LargeNumberManager();
            list2 = new LargeNumberManager();

            System.out.println("m = ");
            String num1 = sc.next();

            System.out.println("n = ");
            String num2 = sc.next();

            boolean isValid = true; // boolean variable to flag invalid input

            // validate first number
            for (int i = 0; i < num1.length(); i++) { //a for loop that checks if every character entered as input is a digit
                if (Character.isDigit(num1.charAt(i))) {
                    list1.addLast(num1.charAt(i) - '0'); // digits are stored in a doubly linked list using ASCII value subtraction
                } else {
                    isValid = false;
                    break;
                }
            }

            // validate second number
            if (isValid) { //if the first input is invalid, skip the verification for second input
                for (int i = 0; i < num2.length(); i++) { //a for loop that checks if every character entered as input is a digit
                    if (Character.isDigit(num2.charAt(i))) {
                        list2.addLast(num2.charAt(i) - '0'); // digits are stored in a doubly linked list using ASCII value subtraction
                    } else {
                        isValid = false;
                        break;
                    }
                }
            }

            if (!isValid) { //Recursively prompt user for invalid input entered
                System.out.println("Invalid input! Please enter digits only.\n");
                continue;
            }

            break; //the inputs are valid
        }

        //clear leading zeros for both numbers first to get accurate list size, essential when comparing equality or larger
        list1.clearLeadingZeros();
        list2.clearLeadingZeros();

        System.out.println();

        // perform arithmetic process (addition, subtraction, multiplication, division)
        LargeNumberManager addResult =
                LargeNumberManager.addition(list1, list2);
        System.out.println("addition = " + addResult);

        LargeNumberManager subResult =
                LargeNumberManager.subtraction(list1, list2);
        System.out.println("subtraction = " + subResult);

        LargeNumberManager mulResult =
                LargeNumberManager.multiplication(list1, list2);
        System.out.println("multiplication = " + mulResult);

        try { //use a try-catch block for division to handle division by 0
            String divResult =
                    LargeNumberManager.division(list1, list2);
            System.out.println("division = " + divResult);
        } catch (ArithmeticException e) {
            System.out.println("division = Error: Division by zero is not allowed!");
        }

        sc.close();
    }
}

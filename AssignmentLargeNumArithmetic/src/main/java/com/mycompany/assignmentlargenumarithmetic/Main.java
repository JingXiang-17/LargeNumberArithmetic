package com.mycompany.assignmentlargenumarithmetic;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LargeNumberManager list1;
        LargeNumberManager list2;

        while (true) {

            list1 = new LargeNumberManager();
            list2 = new LargeNumberManager();

            System.out.println("m = ");
            String num1 = sc.next();

            System.out.println("n = ");
            String num2 = sc.next();

            boolean valid = true;

            // validate first number
            for (int i = 0; i < num1.length(); i++) {
                if (Character.isDigit(num1.charAt(i))) {
                    list1.addLast(num1.charAt(i) - '0');
                } else {
                    valid = false;
                    break;
                }
            }

            // validate second number
            if (valid) {
                for (int i = 0; i < num2.length(); i++) {
                    if (Character.isDigit(num2.charAt(i))) {
                        list2.addLast(num2.charAt(i) - '0');
                    } else {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                System.out.println("Invalid input! Please enter digits only.\n");
                continue;
            }

            break;
        }

        list1.clearLeadingZeros();
        list2.clearLeadingZeros();

        System.out.println();

        LargeNumberManager addResult =
                LargeNumberManager.addition(list1, list2);
        System.out.println("addition = " + addResult);

        LargeNumberManager subResult =
                LargeNumberManager.subtraction(list1, list2);
        System.out.println("subtraction = " + subResult);

        LargeNumberManager mulResult =
                LargeNumberManager.multiplication(list1, list2);
        System.out.println("multiplication = " + mulResult);

        try {
            String divResult =
                    LargeNumberManager.division(list1, list2);
            System.out.println("division = " + divResult);
        } catch (ArithmeticException e) {
            System.out.println("division = Error: Division by zero is not allowed!");
        }

        sc.close();
    }
}
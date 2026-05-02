/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignmentlargenumarithmetic;

/**
 *
 * @author KOR JING XIANG
 */
import java.util.Scanner;

public class Main {
    public static void main (String [] args) {
        //TODO : use a do-while loop to repeatedly prompt user to enter valid numbers if invalid characters are entered.
        Scanner sc=new Scanner (System.in) ;
        System.out.println("=====Large Number Arithmetic=====");
        System.out.println("Caution: The format is always [num1] [operator] [num2]");
        System.out.println("Please enter num1: ");
        String num1 = sc.next(); sc.nextLine();
        System.out.println("Please enter num2: ");
        String num2 = sc.next(); sc.nextLine();
        
        LargeNumberManager <Integer> list1 = new LargeNumberManager <>();
        LargeNumberManager <Integer> list2 = new LargeNumberManager <>();
        
        //check if the String consists of digits only
        for (int i=0; i<num1.length(); i++) {
            if (Character.isDigit(num1.charAt(i))) {
                list1.addLast((int)num1.charAt(i)-'0');
            } else {
                System.out.println("Provided number is not an integer. Please try again.");
                return;
            }
        }
        
        for (int i=0; i<num2.length(); i++) {
           if (Character.isDigit(num2.charAt(i))) {
                list1.addLast((int)num2.charAt(i)-'0');
            } else {
                System.out.println("Provided number is not an integer. Please try again. ");
                return;
            }
        }
        //check ends
        
        list1.clearLeadingZeros();
        list2.clearLeadingZeros();
        System.out.println("===Operator selection===");
        System.out.println("+:Addition");
        System.out.println("-:Subtraction");
        System.out.println("*:Multiplication");
        System.out.println("/=Division");
        System.out.println("Please enter your operator: ");
        char choice=sc.next().charAt(0);
        
        LargeNumberManager <Integer> answer;
        
        switch (choice) {
            
            case '+' -> {
                System.out.println("Addition is selected. Performing addition...");
                answer = addition (list1,list2);
                System.out.println("Answer: "+answer.toString());
            }
            
            case '-' -> {
                System.out.println("Subtraction is selected. Performing subtraction...");
                answer = subtraction (list1,list2);
                System.out.println("Answer: "+answer.toString());
            }
            
            case '*' -> {
                System.out.println("Multiplication is selected. Performing multiplication...");
                answer = multiplication (list1, list2);
                System.out.println("Answer: "+answer.toString());
                break;
            }
            
            case '/' -> {
                System.out.println("Division is selected. Performing division...");
                try {
                    answer = division (list1,list2);
                    System.out.println("Answer: "+answer.toString());
                } catch (ArithmeticException e) {
                    System.out.println("Can't perform division by 0!");
                }
            }
        }
    }
    
    public static LargeNumberManager <Integer> addition (LargeNumberManager <Integer> list1, LargeNumberManager <Integer> list2) {
        LargeNumberManager <Integer> answer = new LargeNumberManager <>();
        if (list1.isEmpty()) {
            list1.addFirst(0);
        }

        if (list2.isEmpty()) {
            list2.addFirst(0);
        }

        Node <Integer> i=list1.tail;
        Node <Integer> j=list2.tail;
        int carry=0;
        
        while (i!=null || j!=null || carry!=0) {
            int temp1 = (i==null) ? 0 : i.getElement();
            int temp2 = (j==null) ? 0 : j.getElement();
            answer.addFirst((temp1 + temp2 + carry)%10);
            carry=(temp1 + temp2 + carry)/10;

            if (i!=null) {
                i=i.getPrev();
            }

            if (j!=null) {
                j=j.getPrev();
            }
        }
        return answer;
    }
    
    public static LargeNumberManager <Integer> subtraction (LargeNumberManager <Integer> list1, LargeNumberManager <Integer> list2) {
        LargeNumberManager <Integer> bigList = list1;
        LargeNumberManager <Integer> smallList = list2;
        LargeNumberManager <Integer> answer = new LargeNumberManager <>();
        
        if (LargeNumberManager.isEqual(list1, list2)) { //a-a=0
            answer.addFirst(0);
            return answer;
        }
        
        if (LargeNumberManager.isLarger(list2, list1)) {
            bigList=list2;
            smallList=list1;
            answer.isNegative=true;
        }
        
        Node <Integer> i = bigList.tail;
        Node <Integer> j = smallList.tail;
        
        int borrow=0;
        while (i!=null) {
            
            int temp1=i.getElement()-borrow;
            int temp2=(j==null)?0:j.getElement();
            
            if (temp1<temp2) {
                answer.addFirst(temp1+10-temp2);
                borrow=1;
            }
            else {
                answer.addFirst(temp1-temp2);
                borrow=0;
            }
            
            i=i.getPrev();
            if (j!=null){
                j=j.getPrev();
            }
        }
        return answer;
    }
    
    public static LargeNumberManager <Integer> multiplication (LargeNumberManager <Integer> list1, LargeNumberManager <Integer> list2) {
        LargeNumberManager <Integer> answer = new LargeNumberManager <>();
        boolean isZero = ((list1.size==1 && (int)list1.head.getElement()==0) || (list2.size==1 && (int)list2.head.getElement()==0)); //check if a list is 0
        
        boolean listOneIsOne = list1.size==1 && (int)list1.head.getElement()==1;
        
        boolean listTwoIsOne = list2.size==1 && (int)list2.head.getElement()==1;
        
        if (isZero) {
            answer.addFirst(0);
            return answer;
        }
        if (listOneIsOne) {
            return list2;
        }
        if (listTwoIsOne) {
            return list1;
        }
        
        
        Node <Integer> j = list2.tail;
        int zeroShift = 0;
        LargeNumberManager <Integer> runningTotal = new LargeNumberManager <>();
        
        while (j!=null) {
            LargeNumberManager <Integer> tempRow = new LargeNumberManager <>();
            
            for (int k=0; k<zeroShift; k++) { //append 0 logic
                tempRow.addFirst(0);
            }
            
            Node <Integer> i = list1.tail;
            int carry = 0;
            while (i!=null) {
                int productOnes = (i.getElement()*j.getElement()+carry)%10;
                carry = (i.getElement()*j.getElement()+carry)/10;
                tempRow.addFirst(productOnes);
                i=i.getPrev();
            }
            if (carry>0) {
                tempRow.addFirst(carry);
            }
            
            runningTotal = addition (tempRow, runningTotal);
            zeroShift++;
            j=j.getPrev();
        }
        
        return runningTotal;
    }
    
    public static LargeNumberManager <Integer> division (LargeNumberManager <Integer> list1, LargeNumberManager <Integer> list2) {
        if (list2.isEmpty() || list2.size==1 && list2.head.getElement()==0) {
            throw new ArithmeticException (); //division by 0 is not allowed
        }
        
        LargeNumberManager <Integer> answer = new LargeNumberManager <>();
        
        if ((list1.isEmpty() || list1.size==1 && list1.head.getElement()==0) ||
            LargeNumberManager.isLarger(list2, list1)) { //0/a=0, a/b=0 if a<b
            answer.addFirst(0);
            return answer;
        }
        
        if (LargeNumberManager.isEqual(list1, list2)) { //a/a=1
            answer.addFirst(1);
            return answer;
        }
        
        //TODO: implementation of division
        LargeNumberManager <Integer> tempList = new LargeNumberManager <>();
        int count=0;
        Node <Integer> i=list1.head;
        
        while (i!=null) {
            tempList.addLast (i.getElement());
            tempList.clearLeadingZeros();
            
            while (LargeNumberManager.isLarger(tempList, list2) || LargeNumberManager.isEqual(tempList, list2)) {
                tempList=subtraction(tempList,list2);
                tempList.clearLeadingZeros();
                count++;
            }
            
            if ((!answer.isEmpty()) || count>0) {
                answer.addLast(count); //add 0 or quotient
            }
            
            count=0;
            i=i.getNext();
        }
        return answer;
    }
}
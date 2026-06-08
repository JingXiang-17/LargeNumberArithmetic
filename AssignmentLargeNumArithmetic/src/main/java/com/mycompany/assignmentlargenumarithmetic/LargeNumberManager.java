package com.mycompany.assignmentlargenumarithmetic;

/**
 * LargeNumberManager using Doubly Linked List
 * Stores large integers digit-by-digit
 * Contains basic methods and arithmetic methods
 */
public class LargeNumberManager {

    private Node head;
    private Node tail;
    private int size;

    public LargeNumberManager() {
        head = null;
        tail = null;
        size = 0;
    }

    // =========================
    // BASIC OPERATIONS
    // =========================

    public void addFirst(int element) {
        
        // Method to add a node at the beginning of the doubly linked list

        Node newNode = new Node(element);

        if (head == null) { //if the doubly linked list is empty
            head = tail = newNode;
        } else { //the doubly linked list is not empty
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }

        size++;
    }

    public void addLast(int element) {

        // Method to add an element to the end of the doubly linked list
        
        if (head == null) { //the linked list is empty
            addFirst(element); //reuse addFirst method logic since adding at the beginning or the end makes no difference
            return;
        }

        //addLast method logic if the doubly linked list is not empty
        Node newNode = new Node(element);

        tail.setNext(newNode);
        newNode.setPrev(tail);
        tail = newNode;

        size++;
    }

    public void removeFirst() {

        // Method to remove the first element from the doubly linked list
        
        if (head == null) return; //there is nothing to remove in an empty doubly linked list

        if (head == tail) { //there is exactly one element in the doubly linked list
            head = tail = null; //nullify head and tail clears the doubly linked list
            size = 0;
            return;
        }

        //removeFirst method logic if there is more than 1 element in the linked list
        
        head = head.getNext();
        head.setPrev(null);
        size--;
    }

    public void clearLeadingZeros() {

        // Method to clear leading zeros in the doubly linked list
        
        while (size > 1 && head.getElement() == 0) { 
            removeFirst(); //recursively clears the leading zeros while the total number of nodes in the doubly linked list is more than 1
        } 
    }

    public boolean isEmpty() { 

        // Method to check if the doubly linked list is empty
        
        return size == 0;
    }

    public int getSize() {

        // Method that returns the number of elements of a doubly linked list  (how many digits are there in the large number)
        
        return size;
    }

    // =========================
    // COMPARISON
    // =========================

    public static boolean isLarger(LargeNumberManager list1,
                                   LargeNumberManager list2) {

        if (list1.size != list2.size) { //check the number of digits
            return list1.size > list2.size;
        }

        Node i = list1.head;
        Node j = list2.head;

        while (i != null) {
            
            /*
            If both doubly linked list have the same size
            A while loop that traverse both doubly linked list from head is used
            Return true if list1 > list2, false otherwise
            */

            if (i.getElement() != j.getElement()) {
                return i.getElement() > j.getElement();
            }

            i = i.getNext();
            j = j.getNext();
        }

        return false; // equal case
    }

    public static boolean isEqual(LargeNumberManager list1,
                                  LargeNumberManager list2) {

        /* 
        Method that checks equality of two large numbers
        Useful for subtraction and division
        */

        if (list1.size != list2.size) return false; //return false if number of digits are different

        Node i = list1.head;
        Node j = list2.head;

        while (i != null) {

            /* 
            A while loop that traverse both doubly linked list from head
            return false immediately if the elements from two doubly linked list are different
            */

            if (i.getElement() != j.getElement()) {
                return false;
            }

            i = i.getNext();
            j = j.getNext();
        }

        return true; //both large number are equal
    }

    // =========================
    // ADDITION
    // =========================

    public static LargeNumberManager addition(LargeNumberManager list1,
                                              LargeNumberManager list2) {

        /*
        Using traversal from the tail and add both doubly linked list digit by digit
        Carry is propagated to the front to continue performing addition
        */

        LargeNumberManager answer = new LargeNumberManager();

        Node i = list1.tail;
        Node j = list2.tail;

        int carry = 0;

        while (i != null || j != null || carry != 0) {

            int num1 = (i == null) ? 0 : i.getElement();
            int num2 = (j == null) ? 0 : j.getElement();

            int sum = num1 + num2 + carry;

            answer.addFirst(sum % 10);
            carry = sum / 10;

            if (i != null) i = i.getPrev();
            if (j != null) j = j.getPrev();
        }

        answer.clearLeadingZeros();
        return answer;
    }

    // =========================
    // SUBTRACTION
    // =========================

    public static LargeNumberManager subtraction(LargeNumberManager list1,
                                                 LargeNumberManager list2) {

        /*
        Using traversal from the tail to perform subtraction of large numbers (doubly linked list) digit by digit
        Borrow is propagated to the front (-1) to continue performing subtraction
        */

        LargeNumberManager big;
        LargeNumberManager small;

        LargeNumberManager answer = new LargeNumberManager();

        if (isLarger(list1, list2) || isEqual(list1, list2)) { //determine the larger number between two doubly linked list
            big = list1;
            small = list2;
        } else {
            big = list2;
            small = list1;
        }

        if (isEqual(list1, list2)) { //for equal numbers, immediately return 0
            answer.addFirst(0);
            return answer;
        }

        Node i = big.tail;
        Node j = small.tail;

        int borrow = 0;

        while (i != null) { //performs subtraction digit by digit, traversing from tail

            int num1 = i.getElement() - borrow;
            int num2 = (j == null) ? 0 : j.getElement();

            if (num1 < num2) { //cases where involving borrow
                num1 += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            answer.addFirst(num1 - num2);

            i = i.getPrev();
            if (j != null) j = j.getPrev();
        }

        answer.clearLeadingZeros();
        return answer;
    }

    // =========================
    // MULTIPLICATION
    // =========================

    public static LargeNumberManager multiplication(LargeNumberManager list1,
                                                     LargeNumberManager list2) {

        /*
        Using long multiplication method by traversing from tail
        Each digit of the second number multiplies each digit of the first number
        Immediate results are shifted by appending zeros
        Partial results are added together using addition method
        */

        LargeNumberManager finalAnswer = new LargeNumberManager();
        finalAnswer.addFirst(0);

        Node j = list2.tail;
        int shift = 0;

        while (j != null) {

            LargeNumberManager temp = new LargeNumberManager();

            for (int s = 0; s < shift; s++) { //to determine the number of zeros to be appended
                temp.addLast(0);
            }

            Node i = list1.tail;
            int carry = 0;

            while (i != null) { //multiplication digit by digit logic

                int product = i.getElement() * j.getElement() + carry;

                temp.addFirst(product % 10);
                carry = product / 10;

                i = i.getPrev();
            }

            if (carry > 0) {
                temp.addFirst(carry);
            }

            finalAnswer = addition(finalAnswer, temp); //update the finalAnswer by summing up the partial results

            shift++;
            j = j.getPrev();
        }

        finalAnswer.clearLeadingZeros();
        return finalAnswer;
    }

    // =========================
    // DIVISION
    // =========================

    public static String division(LargeNumberManager dividend,
                                  LargeNumberManager divisor) {

        /*
        This method returns a String instead of a doubly linked list due to the decimal appending process
        Using long division method by performing traversing from head
        Repeatedly calls subtraction method to calculate the quotient digits
        */

        if (divisor.isEmpty()
                || (divisor.size == 1 && divisor.head.getElement() == 0)) { //check if the divisor is 0
            throw new ArithmeticException("Division by zero"); 
        }

        StringBuilder integerPart = new StringBuilder();
        LargeNumberManager remainder = new LargeNumberManager();

        Node current = dividend.head;

        while (current != null) { //digit by digit long division

            remainder.addLast(current.getElement());
            remainder.clearLeadingZeros();

            int count = 0;

            while (isLarger(remainder, divisor)
                    || isEqual(remainder, divisor)) { //only perform division if remainder >= divisor, otherwise, append more digits to remainder

                remainder = subtraction(remainder, divisor); 
                remainder.clearLeadingZeros();
                count++;
            }

            integerPart.append(count); //count is the quotient digit
            current = current.getNext();
        }

        while (integerPart.length() > 1 && integerPart.charAt(0) == '0') { //clear leading zeros
            integerPart.deleteCharAt(0);
        }

        if (remainder.size == 1 && remainder.head.getElement() == 0) { //if there is no remainder, the divisor divides the dividend completely
            return integerPart.toString(); 
        }

        StringBuilder decimal = new StringBuilder(); //there is decimals in the quotient

        for (int d = 0; d < 20; d++) { //maximum number of decimals = 20

            remainder.addLast(0);
            remainder.clearLeadingZeros();

            int count = 0;

            while (isLarger(remainder, divisor)
                    || isEqual(remainder, divisor)) {

                remainder = subtraction(remainder, divisor);
                remainder.clearLeadingZeros();
                count++;
            }

            decimal.append(count);

            if (remainder.size == 1 && remainder.head.getElement() == 0) { //check if the division has no remainder
                break;
            }
        }

        return integerPart + "." + decimal; // the returned String format will be `INTEGER`.`DECIMAL`
    }

    // =========================
    // TO STRING
    // =========================

    public String toString() {

        /*
        Convert the final answer to String to avoid garbage values printed out
        Using StringBuilder because StringBuilder object is mutable, which is suitable to append the object digit by digit
        Traversing from head and append the StringBuilder object digit by digit to copy all digits
        StringBuilder object is finally converted to String
        */

        if (head == null) return "0"; //empty list is assumed as 0

        StringBuilder sb = new StringBuilder();

        Node current = head;

        while (current != null) {
            sb.append(current.getElement());
            current = current.getNext();
        }

        return sb.toString();
    }
}

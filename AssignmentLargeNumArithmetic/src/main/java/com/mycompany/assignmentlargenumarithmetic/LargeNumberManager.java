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
    private boolean isNegative = false; //a boolean variable to indicate if the large number is negative

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

    public void setNegative(boolean isNegative) {

        // Method to set the sign of the large number
        
        this.isNegative = isNegative;
    }

    // =========================
    // COMPARISON
    // =========================

    public static boolean isLargerAbsolute(LargeNumberManager list1,
                                        LargeNumberManager list2) {

        // Method to compare the absolute values of two large numbers (doubly linked list)
        // Useful for comparing two negative numbers

        if (list1.size != list2.size) { //check the number of digits
            return list1.size > list2.size;
        }

        Node i = list1.head;
        Node j = list2.head;

        while (i != null) {
            
            /*
            If both doubly linked list have the same size
            A while loop that traverse both doubly linked list from head is used
            Return true if |list1| > |list2|, false otherwise
            */

            if (i.getElement() != j.getElement()) {
                return i.getElement() > j.getElement();
            }

            i = i.getNext();
            j = j.getNext();
        }

        return false; // equal case
    }

    public static boolean isLarger(LargeNumberManager list1,
                                   LargeNumberManager list2) {

        if (list1.size != list2.size) { //check the number of digits
            return list1.size > list2.size;
        }

        if (list1.isNegative && !list2.isNegative) return false; //if list1 is negative and list2 is positive, list1 is smaller
        if (!list1.isNegative && list2.isNegative) return true; //if list1 is positive and list2 is negative, list1 is larger
        if (list1.isNegative && list2.isNegative) { //if both are negative, compare their absolute values
            return isLargerAbsolute(list2, list1); //the larger absolute value is the smaller number when both are negative
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

        if (list1.isNegative != list2.isNegative) return false; //if one number is negative and the other is positive, they are not equal
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
        For cases involving negative numbers, the addition method will call the subtraction method to perform the calculation
        */

        //handle cases involving negative numbers first
        if (list1.isNegative && !list2.isNegative) { //if list1 is negative and list2 is positive, perform subtraction list2 - |list1|
            LargeNumberManager absList1 = new LargeNumberManager();
            absList1.head = list1.head;
            absList1.tail = list1.tail;
            absList1.size = list1.size;
            return subtraction(list2, absList1);
        }

        if (!list1.isNegative && list2.isNegative) { //if list1 is positive and list2 is negative, perform subtraction list1 - |list2|
            LargeNumberManager absList2 = new LargeNumberManager();
            absList2.head = list2.head;
            absList2.tail = list2.tail;
            absList2.size = list2.size;
            return subtraction(list1, absList2);
        }

        if (list1.isNegative && list2.isNegative) { //if both list1 and list2 are negative, perform addition of their absolute values and mark the answer as negative
            LargeNumberManager absList1 = new LargeNumberManager();
            absList1.head = list1.head;
            absList1.tail = list1.tail;
            absList1.size = list1.size;

            LargeNumberManager absList2 = new LargeNumberManager();
            absList2.head = list2.head;
            absList2.tail = list2.tail;
            absList2.size = list2.size;

            LargeNumberManager answer = addition(absList1, absList2);
            answer.isNegative = true; //the sum of two negative numbers is negative
            return answer;
        }
        //negative number cases handling ends

        LargeNumberManager answer = new LargeNumberManager();

        Node i = list1.tail;
        Node j = list2.tail;

        int carry = 0;

        while (i != null || j != null || carry != 0) {

            //check if the current pointer points to null, if true, treat as 0 to avoid NullPointerException
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
        For cases involving negative numbers, the subtraction method will call the addition method to perform the calculation
        */

        LargeNumberManager big;
        LargeNumberManager small;

        LargeNumberManager answer = new LargeNumberManager();

        //handle cases involving negative numbers first
        if (list1.isNegative && list2.isNegative) { //if both list1 and list2 are negative, perform subtraction of their absolute values and determine the sign of the answer based on subtraction rules
            LargeNumberManager absList1 = new LargeNumberManager();
            absList1.head = list1.head;
            absList1.tail = list1.tail;
            absList1.size = list1.size;

            LargeNumberManager absList2 = new LargeNumberManager();
            absList2.head = list2.head;
            absList2.tail = list2.tail;
            absList2.size = list2.size;

            return subtraction(absList2, absList1); //the difference of two negative numbers is the difference of their absolute values but in reverse order
        }

        if (!list1.isNegative && list2.isNegative) { //if list1 is positive and list2 is negative, perform addition list1 + |list2|
            LargeNumberManager absList2 = new LargeNumberManager();
            absList2.head = list2.head;
            absList2.tail = list2.tail;
            absList2.size = list2.size;
            return addition(list1, absList2);
        }

        if (list1.isNegative && !list2.isNegative) { //if list1 is negative and list2 is positive, perform addition |list1| + list2|
            LargeNumberManager absList1 = new LargeNumberManager();
            absList1.head = list1.head;
            absList1.tail = list1.tail;
            absList1.size = list1.size;
            return addition(absList1, list2);
        }
        //handling of negative number cases ends

        if (isLarger(list1, list2) || isEqual(list1, list2)) { //determine the larger number between two doubly linked list
            big = list1;
            small = list2;
            answer.isNegative = false; //the difference of a larger number and a smaller number is positive
        } else {
            big = list2;
            small = list1;
            answer.isNegative = true; //the difference of a smaller number and a larger number is negative
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
        For negative numbers, the multiplication method will flag negative based on multiplication rules
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
        if (list1.isNegative && list2.isNegative) {
            finalAnswer.isNegative = false; //the product of two negative numbers is positive
        }
        else if (list1.isNegative || list2.isNegative) {
            finalAnswer.isNegative = true; //the product of a negative number and a positive number is negative
        }
        else {
            finalAnswer.isNegative = false; //the product of two positive numbers is positive
        }
        return finalAnswer;
    }

    // =========================
    // DIVISION
    // =========================

    public static String division(LargeNumberManager dividend, LargeNumberManager divisor) {
        // Handle Division by Zero
        if (divisor.isEmpty() || (divisor.size == 1 && divisor.head.getElement() == 0)) {
            throw new ArithmeticException("Division by zero");
        }

        // Determine final sign
        boolean isNegativeResult = (dividend.isNegative != divisor.isNegative);

        // Create absolute copies to avoid modifying originals
        LargeNumberManager absDividend = new LargeNumberManager();
        copyNodes(dividend, absDividend);
        
        LargeNumberManager absDivisor = new LargeNumberManager();
        copyNodes(divisor, absDivisor);

        // Pre-check: If |dividend| < |divisor|, result is 0.something
        StringBuilder integerPart = new StringBuilder();
        LargeNumberManager remainder = new LargeNumberManager();
        
        Node current = absDividend.head;
        while (current != null) {
            remainder.addLast(current.getElement());
            remainder.clearLeadingZeros();

            int count = 0;
            while (isLarger(remainder, absDivisor) || isEqual(remainder, absDivisor)) {
                remainder = subtraction(remainder, absDivisor);
                remainder.clearLeadingZeros();
                count++;
            }
            integerPart.append(count);
            current = current.getNext();
        }

        // Clean up leading zeros in integer part
        while (integerPart.length() > 1 && integerPart.charAt(0) == '0') {
            integerPart.deleteCharAt(0);
        }

        // Handle Decimal Part
        StringBuilder decimal = new StringBuilder();
        if (!(remainder.size == 1 && remainder.head.getElement() == 0)) {
            for (int d = 0; d < 20; d++) {
                remainder.addLast(0);
                remainder.clearLeadingZeros();

                int count = 0;
                while (isLarger(remainder, absDivisor) || isEqual(remainder, absDivisor)) {
                    remainder = subtraction(remainder, absDivisor);
                    remainder.clearLeadingZeros();
                    count++;
                }
                decimal.append(count);
                if (remainder.size == 1 && remainder.head.getElement() == 0) break;
            }
        }

        // Assemble Result
        String finalResult = integerPart.toString();
        if (decimal.length() > 0) {
            finalResult += "." + decimal.toString();
        }
        
        return isNegativeResult ? "-" + finalResult : finalResult;
    }

    /**
     * Helper to deep-copy nodes from one manager to another
     */
    private static void copyNodes(LargeNumberManager source, LargeNumberManager target) {
        Node current = source.head;
        while (current != null) {
            target.addLast(current.getElement());
            current = current.getNext();
        }
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
        For negative numbers, a negative sign is appended at the front of the StringBuilder object
        */

        if (head == null) return "0"; //empty list is assumed as 0

        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append("-");
        }

        Node current = head;

        while (current != null) {
            sb.append(current.getElement());
            current = current.getNext();
        }

        return sb.toString();
    }
}

package com.mycompany.assignmentlargenumarithmetic;

/**
 * LargeNumberManager using Doubly Linked List
 * Stores large integers digit-by-digit
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

        Node newNode = new Node(element);

        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
        }

        size++;
    }

    public void addLast(int element) {

        if (head == null) {
            addFirst(element);
            return;
        }

        Node newNode = new Node(element);

        tail.setNext(newNode);
        newNode.setPrev(tail);
        tail = newNode;

        size++;
    }

    public void removeFirst() {

        if (head == null) return;

        if (head == tail) {
            head = tail = null;
            size = 0;
            return;
        }

        head = head.getNext();
        head.setPrev(null);
        size--;
    }

    public void clearLeadingZeros() {

        while (size > 1 && head.getElement() == 0) {
            removeFirst();
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    // =========================
    // COMPARISON (FIXED)
    // =========================

    public static boolean isLarger(LargeNumberManager list1,
                                   LargeNumberManager list2) {

        if (list1.size != list2.size) {
            return list1.size > list2.size;
        }

        Node i = list1.head;
        Node j = list2.head;

        while (i != null) {

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

        if (list1.size != list2.size) return false;

        Node i = list1.head;
        Node j = list2.head;

        while (i != null) {

            if (i.getElement() != j.getElement()) {
                return false;
            }

            i = i.getNext();
            j = j.getNext();
        }

        return true;
    }

    // =========================
    // ADDITION
    // =========================

    public static LargeNumberManager addition(LargeNumberManager list1,
                                              LargeNumberManager list2) {

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
    // SUBTRACTION (FIXED LOGIC)
    // =========================

    public static LargeNumberManager subtraction(LargeNumberManager list1,
                                                 LargeNumberManager list2) {

        LargeNumberManager big;
        LargeNumberManager small;

        LargeNumberManager answer = new LargeNumberManager();

        // FIX: clean + consistent decision
        if (isLarger(list1, list2) || isEqual(list1, list2)) {
            big = list1;
            small = list2;
        } else {
            big = list2;
            small = list1;
        }

        if (isEqual(list1, list2)) {
            answer.addFirst(0);
            return answer;
        }

        Node i = big.tail;
        Node j = small.tail;

        int borrow = 0;

        while (i != null) {

            int num1 = i.getElement() - borrow;
            int num2 = (j == null) ? 0 : j.getElement();

            if (num1 < num2) {
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

        LargeNumberManager finalAnswer = new LargeNumberManager();
        finalAnswer.addFirst(0);

        Node j = list2.tail;
        int shift = 0;

        while (j != null) {

            LargeNumberManager temp = new LargeNumberManager();

            for (int s = 0; s < shift; s++) {
                temp.addLast(0);
            }

            Node i = list1.tail;
            int carry = 0;

            while (i != null) {

                int product = i.getElement() * j.getElement() + carry;

                temp.addFirst(product % 10);
                carry = product / 10;

                i = i.getPrev();
            }

            if (carry > 0) {
                temp.addFirst(carry);
            }

            finalAnswer = addition(finalAnswer, temp);

            shift++;
            j = j.getPrev();
        }

        finalAnswer.clearLeadingZeros();
        return finalAnswer;
    }

    // =========================
    // DIVISION (UNCHANGED LOGIC - SAFE)
    // =========================

    public static String division(LargeNumberManager dividend,
                                  LargeNumberManager divisor) {

        if (divisor.isEmpty()
                || (divisor.size == 1 && divisor.head.getElement() == 0)) {
            throw new ArithmeticException("Division by zero");
        }

        StringBuilder integerPart = new StringBuilder();
        LargeNumberManager remainder = new LargeNumberManager();

        Node current = dividend.head;

        while (current != null) {

            remainder.addLast(current.getElement());
            remainder.clearLeadingZeros();

            int count = 0;

            while (isLarger(remainder, divisor)
                    || isEqual(remainder, divisor)) {

                remainder = subtraction(remainder, divisor);
                remainder.clearLeadingZeros();
                count++;
            }

            integerPart.append(count);
            current = current.getNext();
        }

        while (integerPart.length() > 1 && integerPart.charAt(0) == '0') {
            integerPart.deleteCharAt(0);
        }

        if (remainder.size == 1 && remainder.head.getElement() == 0) {
            return integerPart.toString();
        }

        StringBuilder decimal = new StringBuilder();

        for (int d = 0; d < 20; d++) {

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

            if (remainder.size == 1 && remainder.head.getElement() == 0) {
                break;
            }
        }

        return integerPart + "." + decimal;
    }

    // =========================
    // TO STRING
    // =========================

    public String toString() {

        if (head == null) return "0";

        StringBuilder sb = new StringBuilder();

        Node current = head;

        while (current != null) {
            sb.append(current.getElement());
            current = current.getNext();
        }

        return sb.toString();
    }
}
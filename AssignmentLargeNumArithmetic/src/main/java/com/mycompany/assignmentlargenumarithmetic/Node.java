package com.mycompany.assignmentlargenumarithmetic;

/* Node class
Every digit of the large number input will be stored as node, and the properties are defined in this class.
The nodes are designed to create a doubly linked list so that bidirectional traversal is possible.
*/

public class Node {

    //instance variables
    private int element;
    private Node prev;
    private Node next;

    //Parameterized Node constructor that takes a single digit (int type) as argument
    public Node(int element) {
        this.element = element;
        this.prev = null;
        this.next = null;
    }

    //getters and setters
    public int getElement() {
        return element;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

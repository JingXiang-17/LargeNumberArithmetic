package com.mycompany.assignmentlargenumarithmetic;

public class Node {

    private int element;
    private Node prev;
    private Node next;

    public Node(int element) {
        this.element = element;
        this.prev = null;
        this.next = null;
    }

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
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignmentlargenumarithmetic;

/**
 *
 * @author KOR JING XIANG
 */
//Doubly Linked List
public class Node <T> {
    private T element;
    private Node prev;
    private Node next;
    
    public Node () {
        this.element=null;
        this.prev=null;
        this.next=null;
    }
    
    public Node (T element) {
        this.element=element;
        this.prev=null;
        this.next=null;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
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

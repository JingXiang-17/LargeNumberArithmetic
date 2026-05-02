/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignmentlargenumarithmetic;

/**
 *
 * @author KOR JING XIANG
 */
public class LargeNumberManager <T> {
    Node <T> head;
    Node <T> tail;
    int size;
    boolean isNegative = false; //mainly for subtraction, don't complicate this.
    
    public LargeNumberManager () {
        this.head=null;
        this.tail=null;
        this.size=0;
    }
    
    public void addFirst (T element) {
        Node <T> newNode = new Node <>(element);
        if (head==null && tail==null) { //linkedlist is empty
            head=tail=newNode;
            size++;
            return; 
        }
        newNode.setNext(head);
        head.setPrev(newNode);
        head=newNode;
        size++;
    }
    
    public void addLast (T element) {
        if (head==null && tail==null) { //linkedlist is empty
            addFirst(element);
            return;
        }
        Node <T> newNode = new Node <>(element);
        tail.setNext(newNode);
        newNode.setPrev(tail);
        tail=newNode;
        size++;
    }
    
    public boolean isEmpty () {
        return size==0;
    }
    
    public static boolean isLarger (LargeNumberManager list1, LargeNumberManager list2) { //use this for subtraction and division
        if (list1.size!=list2.size) {
            return (list1.size>list2.size);
        }
        for (Node i=list1.head, j=list2.head; i!=null; i=i.getNext(), j=j.getNext()) {
            int num1=(int)i.getElement();
            int num2=(int)j.getElement();
            if (num1==num2) continue;
            return (num1>num2);
        }
        return true; //number are equal, no need to flag negative
    }
    
    public static boolean isEqual (LargeNumberManager list1, LargeNumberManager list2) { //use this for subtraction and division
        if (list1.size!=list2.size) {
            return false;
        }
        for (Node <Integer> i=list1.head, j=list2.head; i!=null; i=i.getNext(), j=j.getNext()) {
            int num1=i.getElement();
            int num2=j.getElement();
            if (num1!=num2) return false;
        }
        return true; //number are equal
    }
    
    public void removeFirst () {
        if (head == null) return;
        if (head == tail) {
            head=tail=null;
            size=0;
            return;
        }
        
        head=head.getNext();
        head.setPrev(null);
        size--;
    }
    
    public void clearLeadingZeros () {
        while (this.size>1 && (int)this.head.getElement()==0) {
            this.removeFirst();
        }
    }
    
    @Override
    public String toString () {
        if (head==null) {
            return "0";
        }
        StringBuilder sb=new StringBuilder ();
        if (isNegative) {
            sb.append("-");
        }
        for (Node i=head; i!=null; i=i.getNext()) {
            sb.append(i.getElement());
        } 
        return sb.toString();
    }
}

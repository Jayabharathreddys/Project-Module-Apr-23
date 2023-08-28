package com.jaya.rest;

/**
 * Definition for singly-linked list.
 * class ListNode {
 *     public int val;
 *     public ListNode next;
 *     ListNode(int x) { val = x; next = null; }
 * }
 */
public class Solution {

    public static void main(String[] args) {
        //int[][] A = {{2, 18, 0}, {2, 5, 1}, {2, 8, 0}, {1, 7, -1}, {1, 5, -1}};
        int[][] A = {{2,6,0},{1,17,-1},{1,19,-1},{2,16,1},{1,13,-1},{3,3,-1},{1,19,-1}};
                //{{2,18,0},{2,5,1},{2,8,0},{1,7,-1},{1,5,-1}};
                //{{1,13,-1},{3,0,-1},{3,1,-1},{2,15,0},{3,0,-1},{1,12,-1},{3,0,-1},{1,19,-1},{1,13,-1},{3,0,-1},{0,12,-1},{1,13,-1},{3,2,-1}};
        solve(A);
    }
    public static int length =0;
    public static ListNode head = null;
    public static ListNode solve(int[][] A) {

        for(int i=0;i<A.length;i++){
            int operation = A[i][0];
            int value = A[i][1];
            int index = A[i][2];

            switch(operation){
                case 0:
                    insertNd(value,0);
                    print();
                    break;
                case 1:
                    insertNd(value,length == 0? length: length+1);
                    print();
                    break;
                case 2:
                    insertNd(value, index);
                    print();
                    break;
                case 3:
                    deleteNd(value);
                    print();
                    break;
            }

        }
        print();
return head;
    }

    public static void insertNode(int value, int index){
        ListNode temp = head;
        if(index < 0 || index > length){
            return;
        }
        if(index == 0){
            ListNode n = new ListNode(value);
            n.next = head;
            head = n;
            length++;
            return;
        }
        for(int i=1;i<index-1; i++){
            temp = temp.next;
        }
        ListNode n = new ListNode(value);
        n.next = temp.next;
        temp.next = n;
        length++;
    }


    public static  void insertAtTail(int value){
        print();
        ListNode temp = head;

        if(length == 0){
            ListNode n = new ListNode(value);
            head = n;
            length++;
            return;
        }

        for(int i=1;i<length; i++){
            temp = temp.next;
        }
        ListNode n = new ListNode(value);
        //n.next = temp.next;
        temp.next = n;
        length++;
    }

    public static void deleteNode(int index){
        ListNode temp = head;
        if(index < 0 || index > length){
            return;
        }
        if(index == 0){
            head = null;
            length--;
            return;
        }
        for(int i=1;i<index; i++){
            temp = temp.next;
        }

        temp.next = temp.next.next;
        length--;
    }
    public static class ListNode {
        public int val;
        public ListNode next;
        ListNode(int x) { val = x; next = null; }
    }

    public static void print(){
        if(head == null) {
            System.out.println("No elements");
            return;
        }
        ListNode temp = head;
        System.out.println("Elements are -->");
        while(temp.next!=null){
            System.out.print(temp.val+" ");
            temp = temp.next;
        }
        System.out.print(temp.val+" ");
        System.out.println("");
    }

    public static void insertNd(int val , int pos){
        if(pos>=0 && pos <=length+1 ){
            ListNode temp = new ListNode(val);
            if(pos == 0){
                temp.next = head;
                head = temp;
            }else{
                int count =1;
                ListNode prev = head;
                while(count < pos -1){
                    prev = prev.next;
                    count++;
                }
                temp.next = prev.next;
                prev.next = temp;
            }
            length++;
        }
    }

    public static void deleteNd(int pos){
        if(pos>=0 && pos <=length ){

            if(pos == 0){
                head = head.next;
            }else{
                int count =0;
                ListNode prev = head;
                while(count < pos -1){
                    prev = prev.next;
                    count++;
                }
                prev.next = prev.next.next;
            }
            length--;
        }
    }

}


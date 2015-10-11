/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree_lab4;

/**
 *
 * @author Ashley Bertrand
 */
public class Node {

    private int item;
    private Node left;
    private Node right;

    public static final int BALANCED = 0;
    public static final int RIGHT_HEAVY = 1;
    public static final int LEFT_HEAVY = -1;
    public int balance = 0;

    public Node(int value) {
        item = value;
    }

    public int getItem() {
        return item;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node leftChild) {
        left = leftChild;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node rightChild) {
        right = rightChild;
    }
}

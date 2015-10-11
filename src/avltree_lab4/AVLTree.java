/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package avltree_lab4;

import java.util.*;

/**
 *
 * @author Ashley Bertrand
 */
public class AVLTree {

    private Node root;
    private boolean increase;
    private boolean decrease;
    private boolean addReturn;
    private Scanner stop;

    public AVLTree() {
        root = null;
        addReturn = false;
        increase = false;
        stop = new Scanner(System.in);
    }

    public boolean addHelper(int item) {
        System.out.println("Starting to search for a place to put " + item);
        increase = false;
        root = add(root, item);
        return addReturn;
    }

    private Node add(Node localRoot, int item) {
        if (localRoot == null) {
            addReturn = true;
            increase = true;
            System.out.println("Added " + item);
            return new Node(item);
        }
        System.out.println("Add called with " + localRoot.getItem() + " as the root.");
        //item is alreday in tree
        if (item == localRoot.getItem()) {                              
            increase = false;
            addReturn = false;
            return localRoot;
        //item < data
        } else if (item < localRoot.getItem()) {
            System.out.println("Branching left");			
            localRoot.setLeft(add(localRoot.getLeft(), item));
            if (increase) {
                decrementBalance(localRoot);
                if (localRoot.balance < Node.LEFT_HEAVY) {
                    increase = false;
                    return rebalanceLeft(localRoot);
                }
            }
            //rebalance not needed
            return localRoot; 
        //item > data
        } else {
            System.out.println("Branching right");                      
            localRoot.setRight(add(localRoot.getRight(), item));
            if (increase) {
                incrementBalance(localRoot);
                if (localRoot.balance > Node.RIGHT_HEAVY) {
                    return rebalanceRight(localRoot);
                } else {
                    return localRoot;
                }
            } else {
                return localRoot;
            }
        }
    }

    private void decrementBalance(Node node) {
        node.balance--;
        if (node.balance == Node.BALANCED) {
            increase = false;
        }
    }

    private Node rebalanceRight(Node localRoot) {
        Node rightChild = localRoot.getRight();
        //R-L tree
        if (rightChild.balance < Node.BALANCED) {
            Node rightLeftChild = rightChild.getLeft();
            if (rightLeftChild.balance > Node.BALANCED) {
                //with tree 10, 15, 20, 25, 23, 22 (internal R-L: +2, -1, +1)
                rightChild.balance = Node.RIGHT_HEAVY;
                rightLeftChild.balance = Node.BALANCED;
                localRoot.balance = Node.BALANCED;
            } else if (rightLeftChild.balance < Node.BALANCED) {
                //with tree 20, 10, 30, 32, 25, 23 (internal R-L: +2, -1, -1)
                rightChild.balance = Node.RIGHT_HEAVY;
                rightLeftChild.balance = Node.BALANCED;
                localRoot.balance = Node.BALANCED;
            } else {
                //with tree 10, 15, 20, 25, 23 (external R-L: +2, -1)
                rightChild.balance = Node.BALANCED;
                rightLeftChild.balance = Node.BALANCED;
                localRoot.balance = Node.BALANCED;
            }
            increase = false;
            decrease = true;
            localRoot.setRight(rotateRight(rightChild));
            return rotateLeft(localRoot);
        }
        //R-R tree
        if (rightChild.balance > Node.BALANCED) {
            localRoot.balance = Node.BALANCED;
            rightChild.balance = Node.BALANCED;
            increase = false;
            decrease = true;
        } else {
            //After rotateLeft(localRoot), overall height of tree doesn't change
            //localRoot (new left child) is RIGHT_HEAVY
            //rightChild (new root) is LEFT_HEAVY
            localRoot.balance = Node.RIGHT_HEAVY;
            rightChild.balance = Node.LEFT_HEAVY;
        }
        return rotateLeft(localRoot);
    }

    private Node rebalanceLeft(Node localRoot) {
        Node leftChild = localRoot.getLeft();
        //L-R tree
        if (leftChild.balance > Node.BALANCED) {
            Node leftRightChild = leftChild.getRight();
            if (leftRightChild.balance > Node.BALANCED) {
                //with tree 15, 10, 12, 1, 5, 11 (internal L-R: -2, +1, +1)
                leftChild.balance = Node.LEFT_HEAVY;
                leftRightChild.balance = Node.BALANCED;
                localRoot.balance = Node.BALANCED;
            } else if (leftRightChild.balance < Node.BALANCED) {
                //with tree 15, 10, 12, 1, 5, 7 (internal L-R: -2, +1, -1)
                leftChild.balance = Node.BALANCED;
                leftRightChild.balance = Node.BALANCED;
                localRoot.balance = Node.RIGHT_HEAVY;
            } else {
                //with tree 10, 15, 20, 5, 7 (external L-R: -2, +1)
                leftChild.balance = Node.BALANCED;
                leftRightChild.balance = Node.BALANCED;
                localRoot.balance = Node.BALANCED;
            }
            increase = false;
            decrease = true;
            localRoot.setLeft(rotateLeft(leftChild));
            return rotateRight(localRoot);
        }
        //L-L tree
        if (leftChild.balance < Node.BALANCED) {
            localRoot.balance = Node.BALANCED;
            leftChild.balance = Node.BALANCED;
            increase = false;
            decrease = true;
        } else {
            localRoot.balance = Node.LEFT_HEAVY;
            leftChild.balance = Node.RIGHT_HEAVY;
        }
        return rotateRight(localRoot);
    }

    private void incrementBalance(Node node) {
        node.balance++;
        if (node.balance > Node.BALANCED) {
            //if now right heavy, the overall height has increased, but it has not decreased
            increase = true;
            decrease = false;
        } else {
            //if now balanced, the overall height has not increased, but it has decreased
            increase = false;
            decrease = true;
        }
    }

    //called if L-L or R-L
    private Node rotateRight(Node localRoot) {
        System.out.println("Rotating Right");
        Node temp = localRoot.getLeft();
        localRoot.setLeft(temp.getRight());
        temp.setRight(localRoot);
        return temp;
    }

    //called if R-R or L-R
    private Node rotateLeft(Node localRoot) {
        System.out.println("Rotating Left");
        Node temp = localRoot.getRight();
        localRoot.setRight(temp.getLeft());
        temp.setLeft(localRoot);
        return temp;
    }

    public void inOrderHelper() {
        System.out.println("Tree contents:");
        inOrder(root);
    }

    private void inOrder(Node node) {
        //traverses left, prints, traverses right
        if (node != null) {
            inOrder(node.getLeft());
            System.out.println(node.getItem() + " (" + node.balance + ") ");
            inOrder(node.getRight());
        }
    }
}
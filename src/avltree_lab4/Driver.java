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
public class Driver {

    public static void main(String[] args) {
        System.out.println("AVL Tree Simulator");
        AVLTree avl = new AVLTree();
        Scanner scan = new Scanner(System.in);
        int start = 0;
        do {
            avl.inOrderHelper();
            System.out.println("\nAdd what? (-1 to quit)");
            start = scan.nextInt();
            if (start != -1) {
                scan.nextLine();
                boolean ans = avl.addHelper(start);
                if (!ans) {
                    System.out.println(start + " not added");
                }
            } else {
                System.out.println("Goodbye");
            }
        } while (start != -1);
    }
}

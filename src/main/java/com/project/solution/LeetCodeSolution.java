package com.project.solution;

public class LeetCodeSolution {

    public static void main(String[] args) {

        TreeNode root1 = recoverFromPreorder("1-2--3--4-5--6--7");
        System.out.println(root1.val);
        System.out.println(root1.left.val);
        System.out.println(root1.left.left.val);
        System.out.println(root1.left.right.val);
        System.out.println(root1.right.val);
        System.out.println(root1.right.left.val);
        System.out.println(root1.right.right.val);

        TreeNode root2 = recoverFromPreorder("1-2--3---4-5--6---7");
        System.out.println(root2.val);
        System.out.println(root2.left.val);
        System.out.println(root2.left.left.val);
        System.out.println(root2.left.left.left.val);
        System.out.println(root2.right.val);
        System.out.println(root2.right.left.val);
        System.out.println(root2.right.left.left.val);

        TreeNode root3 = recoverFromPreorder("1-401--349---90--88");
        System.out.println(root3.val);
        System.out.println(root3.left.val);
        System.out.println(root3.left.left.val);
        System.out.println(root3.left.right.val);
        System.out.println(root3.left.left.left.val);

    }

    static int index = 0;

    public static TreeNode recoverFromPreorder(String traversal) {

        index = 0;

        return helper(traversal, 0);

    }

    private static TreeNode helper(String traversal, int depth) {

        if (index >= traversal.length()) return null;

        // Count the number of dashes
        int dashCount = 0;

        while ((index + dashCount) < traversal.length() && traversal.charAt(index + dashCount) == '-') {
            dashCount++;
        }

        // If the number of dashes doesn't match the current depth, return null
        if (dashCount != depth) return null;

        // Move index past the dashes
        index += dashCount;

        // Extract the node value
        int value = 0;

        while (index < traversal.length() && Character.isDigit(traversal.charAt(index))) {
            value = value * 10 + (traversal.charAt(index++) - '0');
        }

        // Create the current node
        TreeNode node = new TreeNode(value);

        // Recursively build the left and right subtrees
        node.left = helper(traversal, depth + 1);
        node.right = helper(traversal, depth + 1);

        return node;

    }

}

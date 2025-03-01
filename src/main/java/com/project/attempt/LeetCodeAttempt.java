package com.project.attempt;

import java.util.LinkedList;

public class LeetCodeAttempt {

    public static void main(String[] args) {

        TreeNode root1 = recoverATreeFromPreorderTraversal("1-2--3--4-5--6--7");
        System.out.println(root1.val);
        System.out.println(root1.left.val);
        System.out.println(root1.left.left.val);
        System.out.println(root1.left.right.val);
        System.out.println(root1.right.val);
        System.out.println(root1.right.left.val);
        System.out.println(root1.right.right.val);

        TreeNode root2 = recoverATreeFromPreorderTraversal("1-2--3---4-5--6---7");
        System.out.println(root2.val);
        System.out.println(root2.left.val);
        System.out.println(root2.left.left.val);
        System.out.println(root2.left.left.left.val);
        System.out.println(root2.right.val);
        System.out.println(root2.right.left.val);
        System.out.println(root2.right.left.left.val);

        TreeNode root3 = recoverATreeFromPreorderTraversal("1-401--349---90--88");
        System.out.println(root3.val);
        System.out.println(root3.left.val);
        System.out.println(root3.left.left.val);
        System.out.println(root3.left.right.val);
        System.out.println(root3.left.left.left.val);

    }

    // This method generates a tree according to the string traversal provided.
    public static TreeNode recoverATreeFromPreorderTraversal(String traversal) {

        // We have a LinkedList containing every value listed in string traversal,
        // as well as another LinkedList containing the depth level of each of those values.
        // The two LinkedList will have equal sizes and their indexes will correspond to each other.
        LinkedList<Integer> nodeValues = new LinkedList();
        LinkedList<Integer> nodeDepths = new LinkedList();

        // int nodeValue and int nodeDepth will be set to a default of 0. We will use these
        // values to respectively store each value and its depth found within string traversal.
        // The main purpose of int nodeValue will be to store multi-digit numbers that are found in
        // string traversal (as we have to perform certain calculations to convert a multi-digit
        // string of digits to its proper integer representation). The main purpose of int nodeDepth
        // will be to count the number of dashes found before each number, indicating the depth level
        // of that number.
        int nodeValue = 0;
        int nodeDepth = 0;

        // We will use these boolean values to help string digits together and dashes together to
        // perform the appropriate logic necessary. The boolean previousDash will be used to tell
        // whether the previous character looked at was a dash or not. If both the previous and current
        // character is a dash, we want to increment int nodeDepth instead of adding it to LinkedList
        // nodeDepths, and if the previous character is a dash but the current is a digit, then we
        // want to add whatever value has been accumulated in int nodeDepth to LinkedList nodeDepths
        // as that value represents the depth of the number that we're currently looking at. Likewise,
        // boolean previousDigit will be used to tell whether the previous character was a digit or
        // not. If both the previous and current characters are digits, we want to chain them together
        // in int nodeValue instead of immediately adding them to LinkedList nodeValues. If the
        // previous character is a digit but the current is a dash, then we want to add whatever value
        // has been accumulated in int nodeValue to LinkedList nodeValues.
        boolean previousDash = false;
        boolean previousDigit = false;

        // This loop will fill the two LinkedList with their appropriate values.
        for (int i = 0; i < traversal.length(); i++) {

            // We want to perform different operations depending on
            // whether the current character is some digit or a dash.
            if (Character.isDigit(traversal.charAt(i))) {

                // If the current character is a digit, we set boolean previousDash to false,
                // as the latest character that's been read through is not a dash.
                previousDash = false;

                // If the previous character was not a digit (i.e. a dash), then we want to set
                // boolean previousDigit to true and add the current int nodeDepth that we have
                // to LinkedList nodeDepths. The current int nodeDepth represents the depth of
                // the number that the current digit we're looking at belongs to. If the current
                // digit is the very first character of string traversal, then it will automatically
                // be assigned a depth of 0 as that was the default we set before the loop.
                if (!previousDigit) {

                    // We set this to true to indicate that the latest character is now a digit.
                    previousDigit = true;

                    // We reset int nodeDepth once we finish adding it to the LinkedList.
                    nodeDepths.add(nodeDepth);
                    nodeDepth = 0;

                }

                // If the previous character is a digit, we want to properly express the combined
                // digits as a proper number by multiplying the existing int nodeValue by 10 and
                // adding the current digit onto it. If the previous character is not a digit, we
                // perform this operation anyway, where int nodeValue will be treated as 0, and we
                // simply add the existing digit onto it.
                nodeValue = nodeValue * 10 + Character.getNumericValue(traversal.charAt(i));

                // If we're at the final index, then we want to add int nodeValues to its
                // LinkedList as we won't get a chance to do so otherwise (as normally this
                // loop adds int nodeValues to its LinkedList when we switch from looking at
                // a digit to looking at a dash).
                if (i == traversal.length() - 1) { nodeValues.add(nodeValue); }

            // We want to perform different operations depending on
            // whether the current character is some digit or a dash.
            } else if (traversal.charAt(i) == '-') {

                // If the current character is a dash, we set boolean previousDigit to false,
                // as the latest character that's been read through is not a digit.
                previousDigit = false;

                // If the previous character was not a dash (i.e. a digit), then we want to set
                // boolean previousDash to true and add the current int nodeValue that we have
                // to LinkedList nodeValues. The current int nodeValue represents the value of
                // the latest number that we looked at from string traversal. Its corresponding
                // depth will be the latest value in LinkedList nodeDepths that we added in a prior
                // iteration of this loop.
                if (!previousDash) {

                    // We set this to true to indicate that the latest character is now a dash.
                    previousDash = true;

                    // We reset int nodeValue once we finish adding it to the LinkedList.
                    nodeValues.add(nodeValue);
                    nodeValue = 0;

                }

                // For each dash, we want to increment the value of nodeDepth by 1. This value will
                // keep accumulating in each iteration of this for-loop until we finally encounter
                // a digit again. And when we do encounter a digit, the value of int nodeDepth will
                // represent the depth level of the number that digit we encountered belongs to.
                nodeDepth = nodeDepth + 1;

            }

        }

//        // For testing purposes.
//        System.out.println(nodeValues);
//        System.out.println(nodeDepths);

        // Now that we have the LinkedLists with the information we need, we have to
        // build the actual TreeNode objects that will reflect this information.
        // We will have a pointer to the root of the original TreeNode that will remain unchanged,
        // as well as a regular TreeNode object that we will move around during upcoming logic.
        TreeNode originalTreeNode = new TreeNode(nodeValues.get(0));
        TreeNode treeNode = originalTreeNode;

        // We will use int currentDepth to keep track of the current depth level as we
        // traverse down the TreeNode as they don't inherently keep track of this information.
        int currentDepth = 0;

        // This for-loop will loop through the information in both LinkedList(s) from earlier
        // and will build the TreeNode tree using the information from there.
        for (int i = 1; i < nodeValues.size(); i++) {

            int currentValue = nodeValues.get(i);

            // If the depth of the current value is greater than the depth of the previous value
            // we looked at, that means we went down a level in the tree and the new value
            // should be placed to the left of the current TreeNode we're at, as that is what
            // the challenge specifications clarified.
            if (nodeDepths.get(i) > currentDepth) {

                // We update the latest depth with the new one.
                currentDepth = nodeDepths.get(i);

                // We place a new TreeNode object to the left of the current TreeNode we're at.
                // We then traverse down and set TreeNode treeNode to this new TreeNode that
                // we just created, if and only if the *next* value has an even higher depth
                // than the latest depth that we set just now. This is because we only want to
                // travel down if we are sure we don't want to add anything to the right node
                // of the current TreeNode that we're at. If the next value has an equal depth,
                // then we want to stay at the current TreeNode so that we can add the next value
                // to the right of the current TreeNode.
                treeNode.left = new TreeNode(currentValue);
                if (i + 1 != nodeDepths.size() && nodeDepths.get(i + 1) > currentDepth) {
                    treeNode = treeNode.left;
                }

            // If the depth of the current value is equal to the depth of the previous value,
            // then that probably means a node has already been added to the left side, and we
            // want to add one to the right side now. So we add a TreeNode to the right side and
            // then travel down the left node, as that is the node that we want to add new nodes
            // to when we encounter values with a higher depth rating.
            } else if (nodeDepths.get(i) == currentDepth) {

                treeNode.right = new TreeNode(currentValue);
                treeNode = treeNode.left;

            // If the depth of the current value is lower than the depth of the previous value,
            // then that means we have to travel back up the tree to add a node of the current value
            // to its appropriate position in the tree.
            } else if (nodeDepths.get(i) < currentDepth) {

                // If the depth of the current value is 1, we then go back to the very original
                // root node and add a node containing the current value to the right side.
                // This should only happen once after the initial traversal as this depth can only
                // ever contain two values.
                if (nodeDepths.get(i) == 1) {

                    treeNode = originalTreeNode;

                    // On the off-chance that the right node is not null, we exit the program
                    // as this should basically never happen if the inputs aren't incorrect.
                    if (treeNode.right != null) {
                        System.out.println("TEST");
                        System.exit(0);
                    }

                    // We add a node to the right side and then go down that node, as we
                    // do not want to go back down the left side of the tree anymore according
                    // to the challenge specifications.
                    treeNode.right = new TreeNode(currentValue);
                    treeNode = treeNode.right;

                    currentDepth = nodeDepths.get(i);

                }

                // If the depth of the current value is not 1, then we need to call a special
                // helper method that will add the node at its appropriate depth by starting from
                // the root node and adding the node to the first valid opening. We then set
                // TreeNode treeNode to that newly created node.
                if (nodeDepths.get(i) != 1) {

                    // If the right side of the root is not null, we are probably currently
                    // traversing the right side of the root node and want to prioritize it
                    // for adding any new nodes. So in this case, we start our traversal on
                    // the right side and adjust the depth argument to reflect this fact.
                    // Otherwise, we start at the original root node and prioritize the left
                    // sides first for every node, only looking at the right sides after checking
                    // the left side and failing to find a valid space to place the new node.
                    if (originalTreeNode.right != null) {
                        treeNode = backTravel(originalTreeNode.right, nodeDepths.get(i) - 1, currentValue);
                    } else {
                        treeNode = backTravel(originalTreeNode, nodeDepths.get(i), currentValue);
                    }

                    currentDepth = nodeDepths.get(i);

                }

            }

        }

        // At the very end, after all the logic, we return the root of the original TreeNode.
        return originalTreeNode;

    }

    // This is a helper method that uses recursion to place the node of a certain value (int value) at
    // the desired depth (int depth). It will recursively call itself until int depth is 1 (basically
    // right above the actual depth that we want to place the node) and travel down the tree as it
    // does so, trying to find a valid empty slot at the desired depth and returning null if it can't.
    private static TreeNode backTravel(TreeNode root, int depth, int value) {

        // If int depth is 1, that means we are basically right above the desired depth, and we
        // want to place a node to the left or right of the current TreeNode object we're at.
        if (depth == 1) {

            // If the left node is null, place the node there.
            if (root.left == null) {
                root.left = new TreeNode(value);
                return root.left;
            // Else, if the right node is null, place the node there.
            } else if (root.right == null) {
                root.right = new TreeNode(value);
                return root.right;
            }

            // If we were able to place a node on either side, return the node that we created.
            // Otherwise, return null to indicate that we could not place it anywhere as both sides
            // are taken.
            return null;

        }

        // We will make a recursive call that has us travel down the left node first.
        TreeNode node = backTravel(root.left, depth - 1, value);

        // If the result is not null, that means we successfully found an empty valid space
        // to put down the node at the desired depth, so we return that node to indicate this.
        if (node != null) {
            return node;
        }

        // We then make a recursive call that travels down the right node if the left side didn't work.
        node = backTravel(root.right, depth - 1, value);

        // If the result is not null, that means we successfully found an empty valid space
        // to put down the node at the desired depth, so we return that node to indicate this.
        if (node != null) {
            return node;
        }

        // If at no point we found a valid space to put down the node, we return null to indicate
        // that we've hit a dead end where no nodes can be placed on either side anymore.
        return null;

    }

}

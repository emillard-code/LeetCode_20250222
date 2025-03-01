package com.project;

import com.project.attempt.TreeNode;
import com.project.attempt.LeetCodeAttempt;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeAttemptTest {

    @Test
    public void recoverATreeFromPreorderTraversalTest() {

        TreeNode root1 = LeetCodeAttempt.recoverATreeFromPreorderTraversal("1-2--3--4-5--6--7");
        assertEquals(1, root1.val);
        assertEquals(2, root1.left.val);
        assertEquals(3, root1.left.left.val);
        assertEquals(4, root1.left.right.val);
        assertEquals(5, root1.right.val);
        assertEquals(6, root1.right.left.val);
        assertEquals(7, root1.right.right.val);

    }

}

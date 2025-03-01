package com.project;

import com.project.solution.TreeNode;
import com.project.solution.LeetCodeSolution;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class LeetCodeSolutionTest {

    @Test
    public void recoverFromPreorderTest() {

        TreeNode root1 = LeetCodeSolution.recoverFromPreorder("1-2--3--4-5--6--7");
        assertEquals(1, root1.val);
        assertEquals(2, root1.left.val);
        assertEquals(3, root1.left.left.val);
        assertEquals(4, root1.left.right.val);
        assertEquals(5, root1.right.val);
        assertEquals(6, root1.right.left.val);
        assertEquals(7, root1.right.right.val);

    }

}

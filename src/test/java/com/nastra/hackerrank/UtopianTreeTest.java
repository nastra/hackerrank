package com.nastra.hackerrank;

import junit.framework.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author nastra - Eduard Tudenhoefner
 */
public class UtopianTreeTest {

    @Test
    public void testSimple() {
        Assert.assertEquals(6, UtopianTree.heightAfterCycles(3));
        Assert.assertEquals(7, UtopianTree.heightAfterCycles(4));
        Assert.assertEquals(14, UtopianTree.heightAfterCycles(5));
        Assert.assertEquals(2, UtopianTree.heightAfterCycles(1));
        Assert.assertEquals(3, UtopianTree.heightAfterCycles(2));

        Assert.assertEquals(1, UtopianTree.heightAfterCycles(-1));
        Assert.assertEquals(2147483647, UtopianTree.heightAfterCycles(60));
        Assert.assertEquals(1, UtopianTree.heightAfterCycles(0));
    }
}
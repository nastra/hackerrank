package com.nastra.hackerrank;

import java.math.BigInteger;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author nastra - Eduard Tudenhoefner
 */
public class BDayGiftTest {

    @Test
    public void testSimple() {
        BigInteger[] a = {new BigInteger("1")};
        Assert.assertEquals(BDayGift.priceOfPhone(a), 0.5d);

        BigInteger[] b = {new BigInteger("2")};
        Assert.assertEquals(BDayGift.priceOfPhone(b), 1.0d);

        BigInteger dec = new BigInteger("1000000000");
        BigInteger[] c = {dec};
        Assert.assertEquals(BDayGift.priceOfPhone(c), 500000002.0d);
    }
}

package com.nastra.hackerrank;

import java.math.BigDecimal;
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
        Assert.assertTrue(BDayGift.priceOfPhone(a).equals(new BigDecimal(0.5)));

        BigInteger[] b = {new BigInteger("2")};
        Assert.assertTrue(BDayGift.priceOfPhone(b).equals(new BigDecimal("1.0")));

        BigInteger dec = new BigInteger("1000000000");
        BigInteger[] c = {dec};
        BigDecimal price = BDayGift.priceOfPhone(c);
        Assert.assertTrue(price.equals(new BigDecimal("500000000.0")));
    }
}

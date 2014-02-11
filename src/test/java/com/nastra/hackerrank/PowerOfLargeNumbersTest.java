package com.nastra.hackerrank;

import java.math.BigInteger;
import java.sql.Time;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * 
 * @author nastra - Eduard Tudenhoefner
 */
public class PowerOfLargeNumbersTest {

    @Test
    public void testSimple() {
        Assert.assertEquals(PowerOfLargeNumbers.powerIterative(new BigInteger("4"), new BigInteger("5")), new BigInteger("1024"));
        Assert.assertEquals(PowerOfLargeNumbers.power(new BigInteger("3"), new BigInteger("2")), new BigInteger("9"));
        // Assert.assertEquals(PowerOfLargeNumbers.montgomery(new BigInteger("3"), new BigInteger("2")), new BigInteger("9"));
        Assert.assertEquals(PowerOfLargeNumbers.powerIterative(new BigInteger("3"), new BigInteger("2")), new BigInteger("9"));
        Assert.assertEquals(PowerOfLargeNumbers.power(new BigInteger("3"), new BigInteger("1")), new BigInteger("3"));
        // Assert.assertEquals(PowerOfLargeNumbers.montgomery(new BigInteger("3"), new BigInteger("1")), new BigInteger("3"));
        Assert.assertEquals(PowerOfLargeNumbers.powerIterative(new BigInteger("3"), new BigInteger("1")), new BigInteger("3"));
        Assert.assertEquals(PowerOfLargeNumbers.power(new BigInteger("4"), new BigInteger("5")), new BigInteger("1024"));
        // Assert.assertEquals(PowerOfLargeNumbers.montgomery(new BigInteger("4"), new BigInteger("5")), new BigInteger("1024"));
        Assert.assertEquals(PowerOfLargeNumbers.power(new BigInteger("7"), new BigInteger("4")), new BigInteger("2401"));
        // Assert.assertEquals(PowerOfLargeNumbers.montgomery(new BigInteger("7"), new BigInteger("4")), new BigInteger("2401"));
        Assert.assertEquals(PowerOfLargeNumbers.powerIterative(new BigInteger("7"), new BigInteger("4")), new BigInteger("2401"));
        Assert.assertEquals(PowerOfLargeNumbers.power(new BigInteger("12"), new BigInteger("8")), new BigInteger("429981696"));
        // Assert.assertEquals(PowerOfLargeNumbers.montgomery(new BigInteger("12"), new BigInteger("8")), new BigInteger("429981696"));
        Assert.assertEquals(PowerOfLargeNumbers.powerIterative(new BigInteger("12"), new BigInteger("8")), new BigInteger("429981696"));
    }

    @Test
    public void testBiggerNumbers() {
        // Assert.assertEquals(PowerOfLargeNumbers.power(new BigInteger("34534985349875439875439875349875"), new
        // BigInteger("93475349759384754395743975349573495")), new BigInteger("735851262"));
        // Assert.assertEquals(PowerOfLargeNumbers.powerIterative(new BigInteger("34534985349875439875439875349875"), new
        // BigInteger("93475349759384754395743975349573495")), new BigInteger("735851262"));
        // Assert.assertEquals(PowerOfLargeNumbers.power(new BigInteger("34543987529435983745230948023948"), new
        // BigInteger("3498573497543987543985743989120393097595572309482304")), new BigInteger("985546465"));
        // Assert.assertEquals(PowerOfLargeNumbers.powerIterative(new BigInteger("34543987529435983745230948023948"), new
        // BigInteger("3498573497543987543985743989120393097595572309482304")), new BigInteger("985546465"));
        Time before = new Time(System.currentTimeMillis());
        // Assert.assertEquals(PowerOfLargeNumbers.montgomery(longNumber(50000), longNumber(50000)), new BigInteger("945477534"));
        // Assert.assertEquals(longNumber(50000).modPow(longNumber(50000), new BigInteger("1000000007")), new BigInteger("945477534"));
        // Assert.assertEquals(longNumber(1000000).modPow(longNumber(1000000), new BigInteger("1000000007")), new BigInteger("412397233"));
        Assert.assertEquals(PowerOfLargeNumbers.powerIterative(longNumber(50000), longNumber(50000)), new BigInteger("945477534"));
        Time after = new Time(System.currentTimeMillis());
        long time = after.getTime() - before.getTime();
        System.out.println("time taken: " + time / 1000);
        // Assert.assertEquals(PowerOfLargeNumbers.powerIterative(longNumber(20000), longNumber(20000)), new BigInteger("985546465"));
    }

    private BigInteger longNumber(int zeroes) {
        StringBuilder out = new StringBuilder("1");
        for (int i = 0; i < zeroes; i++) {
            out.append("0");
        }
        return new BigInteger(out.toString());
    }
}
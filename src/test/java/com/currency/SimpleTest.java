package com.currency;

import com.currency.Simple;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SimpleTest {

    @Test
    public void TestFunctionMultiplyPass() {
        assertEquals(Simple.functionMultiply(2,3), 6);
    }

    @Test
    public void TestFunctionMultiplyPassToo() {
        assertEquals(Simple.functionMultiply(3,3), 9);
    }
}


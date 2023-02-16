package com.example;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class OtherWayToTest {
    @Test
    public void testApp() {
        assertEquals("Hello World!", new App().getMessage());
    }
}
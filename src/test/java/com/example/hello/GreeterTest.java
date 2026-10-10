package com.example.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GreeterTest {
    private final Greeter greeter = new Greeter();

    @Test
    void greetsByName() {
        assertEquals("Hello, Sana!", greeter.greet("Sana"));
    }

    @Test
    void defaultsToWorldWhenNameMissing() {
        assertEquals("Hello, World!", greeter.greet(null));
        assertEquals("Hello, World!", greeter.greet("   "));
    }
}

package interpreter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class AppTest {
    @Test
    void examplesCanBeCreated() {
        assertNotNull(Examples.example1());
        assertNotNull(Examples.example2());
        assertNotNull(Examples.example3());
    }
}

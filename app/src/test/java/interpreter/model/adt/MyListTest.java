package interpreter.model.adt;

import interpreter.exceptions.ADTException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {

    @Test
    void testAddAndGet() throws ADTException {
        MyList<String> list = new MyList<>();

        list.add("first");
        list.add("second");

        assertEquals("first", list.get(0));
        assertEquals("second", list.get(1));
    }

    @Test
    void testSize() {
        MyList<Integer> list = new MyList<>();

        assertEquals(0, list.size());

        list.add(10);
        list.add(20);

        assertEquals(2, list.size());
    }

    @Test
    void testGetNegativeIndex() {
        MyList<Integer> list = new MyList<>();
        list.add(10);

        assertThrows(ADTException.class, () -> list.get(-1));
    }

    @Test
    void testGetIndexOutOfBounds() {
        MyList<Integer> list = new MyList<>();
        list.add(10);

        assertThrows(ADTException.class, () -> list.get(1));
    }

    @Test
    void testToString() {
        MyList<Integer> list = new MyList<>();
        list.add(10);
        list.add(20);

        assertEquals("[10, 20]", list.toString());
    }
}

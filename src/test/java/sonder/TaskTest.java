package sonder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Read a book", false);
    }

    @Test
    void constructor_initializesCorrectly() {
        assertEquals("Read a book", task.getDescription());
        assertFalse(task.getStatusIcon().equals("1"));
    }

    @Test
    void setDone_marksTaskAsDone() {
        task.setDone();
        assertEquals("1", task.getStatusIcon());
    }

    @Test
    void setUndone_marksTaskAsNotDone() {
        task.setDone();
        task.setUndone();
        assertEquals("0", task.getStatusIcon());
    }

    @Test
    void getDescription_returnsCorrectDescription() {
        assertEquals("Read a book", task.getDescription());
    }

    @Test
    void getStatusIcon_returnsCorrectIcon() {
        assertEquals("0", task.getStatusIcon());
        task.setDone();
        assertEquals("1", task.getStatusIcon());
    }

    @Test
    void toString_returnsCorrectFormat() {
        assertEquals("0 | Read a book", task.toString());
        task.setDone();
        assertEquals("1 | Read a book", task.toString());
    }
}

package sonder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class EventTest {

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event("Conference", false, LocalDate.of(2025, 2, 10),
                LocalDate.of(2025, 2, 12));
    }

    @Test
    void constructor_initializesCorrectly() {
        assertEquals("Conference", event.getDescription());
        assertEquals("0", event.getStatusIcon());
        assertEquals(LocalDate.of(2025, 2, 10), event.getStartDate());
        assertEquals(LocalDate.of(2025, 2, 12), event.getEndDate());
    }

    @Test
    void setDone_marksEventAsDone() {
        event.setDone();
        assertEquals("1", event.getStatusIcon());
    }

    @Test
    void setUndone_marksEventAsNotDone() {
        event.setDone();
        event.setUndone();
        assertEquals("0", event.getStatusIcon());
    }

    @Test
    void toString_returnsCorrectFormat() {
        assertEquals("E | 0 | Conference | from: Feb 10 2025 | to: Feb 12 2025", event.toString());
        event.setDone();
        assertEquals("E | 1 | Conference | from: Feb 10 2025 | to: Feb 12 2025", event.toString());
    }
}

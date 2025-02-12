package sonder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task that has a description, completion status, and a due date.
 * Inherits from the {@code Task} class.
 */
public class Deadline extends Task {

    private LocalDate by;

    /**
     * Constructs a {@code Deadline} task with the given description, completion status, and due date.
     *
     * @param description The description of the deadline task.
     * @param isDone Boolean indicating whether the task is completed.
     * @param by The due date of the task as a {@code LocalDate} object.
     */
    public Deadline(String description, boolean isDone, LocalDate by) {
        super(description, isDone);
        assert by != null : "Deadline date cannot be null";
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline task in the format:
     * <pre>
     * D | [status] | description | by: MMM dd yyyy
     * </pre>
     * where `[status]` is "1" if completed and "0" if not.
     *
     * @return A formatted string representing the deadline task.
     */
    @Override
    public String toString() {
        return "D | " + super.toString() + " | by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
}

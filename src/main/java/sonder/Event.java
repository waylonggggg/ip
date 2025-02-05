package sonder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task that has a description, completion status,
 * a start date, and an end date. Inherits from the {@code Task} class.
 */
public class Event extends Task{
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Constructs an {@code Event} task with the given description, completion status,
     * start date, and end date.
     *
     * @param description The description of the event task.
     * @param isDone Boolean indicating whether the event is completed.
     * @param startDate The start date of the event as a {@code LocalDate} object.
     * @param endDate The end date of the event as a {@code LocalDate} object.
     */
    public Event(String description, boolean isDone, LocalDate startDate, LocalDate endDate) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets the start date of the event.
     *
     * @return The start date as a {@code LocalDate} object.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the end date of the event.
     *
     * @return The end date as a {@code LocalDate} object.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Returns a string representation of the event task in the format:
     * <pre>
     * E | [status] | description | from: MMM dd yyyy | to: MMM dd yyyy
     * </pre>
     * where `[status]` is "1" if completed and "0" if not.
     *
     * @return A formatted string representing the event task.
     */
    @Override
    public String toString() {
        return "E | " + super.toString() + " | from: "
                + startDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " | to: "
                + endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
}

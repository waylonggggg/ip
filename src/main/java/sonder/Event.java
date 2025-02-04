package sonder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task{
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(String description, boolean isDone, LocalDate startDate, LocalDate endDate) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "E | " + super.toString() + " | from: "
                + startDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " | to: "
                + endDate.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
}

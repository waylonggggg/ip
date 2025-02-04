package sonder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    private LocalDate by;

    public Deadline(String description, boolean isDone, LocalDate by) {
        super(description, isDone);
        this.by = by;
    }

    @Override
    public String toString() {
        return "D | " + super.toString() + " | by: " + by.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }
}

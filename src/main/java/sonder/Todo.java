package sonder;

/**
 * Represents a Todo task, which is a basic task without a deadline or event duration.
 * This class extends the {@code Task} class.
 */
public class Todo extends Task {

    /**
     * Constructs a {@code Todo} task with the given description and completion status.
     *
     * @param description The description of the task.
     * @param isDone {@code true} if the task is completed, {@code false} otherwise.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
    }

    /**
     * Returns a formatted string representation of the {@code Todo} task.
     * The format follows:
     * <pre>
     * T | isDone | description
     * </pre>
     *
     * @return The formatted string representation of the {@code Todo} task.
     */
    @Override
    public String toString() {
        return "T | " + super.toString();
    }
}

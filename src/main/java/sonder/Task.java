package sonder;

/**
 * Represents a task with a description and a completion status.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a {@code Task} with the given description and completion status.
     *
     * @param description The description of the task.
     * @param isDone Boolean indicating whether the task is completed.
     */
    public Task(String description, boolean isDone) {
        assert description != null && !description.trim().isEmpty() : "Task description cannot be null or empty";
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Marks the task as completed.
     */
    public void setDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void setUndone() {
        this.isDone = false;
    }

    /**
     * Retrieves the task description.
     *
     * @return The task description as a {@code String}.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieves the completion status of the task as an icon.
     * "1" represents a completed task, while "0" represents an incomplete task.
     *
     * @return A {@code String} representing the task status icon.
     */
    public String getStatusIcon() {
        return this.isDone ? "1" : "0";
    }

    /**
     * Returns a string representation of the task in the format:
     * <pre>
     * [status] | description
     * </pre>
     * where `[status]` is "1" if completed and "0" if not.
     *
     * @return A formatted {@code String} representing the task.
     */
    @Override
    public String toString() {
        return String.format("%s | %s", this.getStatusIcon(),
                this.getDescription());
    }
}

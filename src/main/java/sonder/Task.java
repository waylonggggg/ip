package sonder;

public class Task {
    private String description;
    private boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setUndone() {
        this.isDone = false;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return this.isDone ? "1" : "0";
    }

    @Override
    public String toString() {
        return String.format("%s | %s", this.getStatusIcon(),
                this.getDescription());
    }
}

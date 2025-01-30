public class Event extends Task{
    private String startDate;
    private String endDate;

    public Event(String description, boolean isDone, String startDate, String endDate) {
        super(description, isDone);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "E | " + super.toString() + " | from: " + startDate + " | to: " + endDate;
    }
}

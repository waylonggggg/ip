import java.util.ArrayList;

public class Task {
    private String description;
    private boolean isDone;
    private static ArrayList<Task> taskList = new ArrayList<>();

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public static void addTask(Task t) {
        taskList.add(t);
        System.out.println("____________________________________________________________\n"
                + "Got it. I've added this task:\n"
                + t.toString() + "\n"
                + String.format("Now you have %d tasks in the list", getTaskListSize())
        );
    }

    public static void setDone(int index) {
        taskList.get(index).isDone = true;
        System.out.println("____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + taskList.get(index)
        );
    }

    public static void setUndone(int index) {
        taskList.get(index).isDone = false;
        System.out.println("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + taskList.get(index)
        );
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    public static int getTaskListSize() {
        return taskList.size();
    }

//    public String getTypeIcon() {
//        if (this instanceof )
//    }

    public static void getList() {
        for (int i = 1; i <= getTaskListSize(); i++) {
            System.out.println(i + ". " + taskList.get(i - 1));
        }
    }

    public void addTaskHelper(Task task) {

    }

    @Override
    public String toString() {
        return String.format("[%s] %s", this.getStatusIcon(),
                this.getDescription());
    }
}

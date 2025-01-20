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
    }

    public static void setDone(int index) {
        taskList.get(index).isDone = true;
        System.out.println("____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + String.format("%d. [%s] %s", index + 1, taskList.get(index).getStatusIcon(),
                        taskList.get(index).getDescription())
        );
    }

    public static void setUndone(int index) {
        taskList.get(index).isDone = false;
        System.out.println("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + String.format("%d. [%s] %s", index + 1, taskList.get(index).getStatusIcon(),
                taskList.get(index).getDescription())
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
        for (int i = 0; i < taskList.size(); i++) {
            String line = String.format("%d. [%s] %s", i + 1, taskList.get(i).getStatusIcon(),
                    taskList.get(i).getDescription());
            System.out.println(line);
        }
    }
}

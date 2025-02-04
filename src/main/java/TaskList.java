import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    private static ArrayList<Task> taskList;

    public TaskList(ArrayList<Task> taskList) {
        TaskList.taskList = taskList;
    }

    public void addTask(Task t) throws IOException {
        taskList.add(t);
    }

    public void delete(int index) {
        taskList.remove(index);
    }

    public static Task getTask(int index) {
        return taskList.get(index);
    }

    public static int getTaskListSize() {
        return taskList.size();
    }
}

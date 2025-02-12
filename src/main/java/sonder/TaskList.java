package sonder;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages a list of tasks, allowing tasks to be added, retrieved, and removed.
 */
public class TaskList {

    private static ArrayList<Task> taskList;

    /**
     * Constructs a {@code TaskList} with an existing list of tasks.
     *
     * @param taskList The list of tasks to initialize the task manager with.
     */
    public TaskList(ArrayList<Task> taskList) {
        assert taskList != null : "TaskList cannot be initialized with a null list";
        TaskList.taskList = taskList;
    }

    /**
     * Adds a task to the task list.
     *
     * @param t The task to be added.
     * @throws IOException If an I/O error occurs while adding the task.
     */
    public void addTask(Task t) throws IOException {
        assert taskList != null : "TaskList must be initialized before adding a task";
        taskList.add(t);
    }

    /**
     * Removes a task from the task list by its index.
     *
     * @param index The index of the task to be removed.
     */
    public void delete(int index) {
        assert index >= 0 && index < taskList.size() : "Index out of bounds in delete()";
        taskList.remove(index);
    }

    /**
     * Retrieves a task from the task list by its index.
     *
     * @param index The index of the task to retrieve.
     * @return The {@code Task} at the specified index.
     */
    public static Task getTask(int index) {
        assert index >= 0 && index < taskList.size() : "Index out of bounds in getTask()";
        return taskList.get(index);
    }

    /**
     * Gets the total number of tasks in the task list.
     *
     * @return The size of the task list as an integer.
     */
    public static int getTaskListSize() {
        assert taskList != null : "TaskList must be initialized before calling getTaskListSize()";
        return taskList.size();
    }
}

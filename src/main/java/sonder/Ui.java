package sonder;

import java.util.Scanner;

/**
 * The {@code Ui} class handles user interactions, including displaying messages
 * and reading user input. It provides methods for showing greetings, task updates,
 * error messages, and user input retrieval.
 */
public class Ui {
    private Scanner sc;

    /**
     * Constructs a {@code Ui} instance and initializes the scanner for user input.
     */
    public Ui() {
        sc = new Scanner(System.in);
        assert sc != null : "Scanner instance should not be null in Ui";
    }

    /**
     * Displays a welcome message to the user.
     */
    public String welcomeMessage() {
        String message = "____________________________________________________________\n"
                + "Hello! I'm Sonder, your personal chatbot!\n"
                + "What can I do for you!\n"
                + "____________________________________________________________";
        return message;
    }

    /**
     * Displays a goodbye message when the user exits the program.
     */
    public String goodbyeMessage() {
        String message = "____________________________________________________________\n"
                + "Bye. Hope to see you again soon!\n"
                + "____________________________________________________________";
        return message;
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param index The index of the task that has been marked as done.
     */
    public String setDoneMessage(int index) {
        assert index >= 0 && index < TaskList.getTaskListSize() : "Index out of bounds in setDoneMessage()";

        String message = "____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + TaskList.getTask(index) + "\n"
                + "____________________________________________________________";
        return message;
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param index The index of the task that has been marked as not done.
     */
    public String setUndoneMessage(int index) {
        assert index >= 0 && index < TaskList.getTaskListSize() : "Index out of bounds in setDoneMessage()";

        String message = "____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + TaskList.getTask(index) + "\n"
                + "____________________________________________________________";
        return message;
    }

    /**
     * Displays a message confirming the addition of a new task.
     *
     * @param t The task that was added.
     */
    public String addTaskMessage(Task t) {
        assert t != null : "Task cannot be null in addTaskMessage()";

        String message = "____________________________________________________________\n"
                + "Got it. I've added this task:\n"
                + t.toString() + "\n"
                + String.format("Now you have %d tasks in the list", TaskList.getTaskListSize()) + "\n"
                + "____________________________________________________________";
        return message;
    }

    /**
     * Displays a message confirming the deletion of a task.
     *
     * @param oldTask The task that was removed.
     */
    public String deleteMessage(String oldTask) {
        assert oldTask != null && !oldTask.trim().isEmpty() : "Deleted task description cannot be null or empty in deleteMessage()";

        String message = "____________________________________________________________\n"
                + "Noted. I've removed this task:\n"
                + oldTask + "\n"
                + String.format("Now you have %d tasks in the list\n", TaskList.getTaskListSize())
                + "____________________________________________________________";
        return message;
    }

    /**
     * Displays a message showing the tasks that match the user's search query.
     *
     * @param input The formatted list of matching tasks, or an empty string if no match is found.
     */
    public String findTaskMessage(String input) {
        if (input.isEmpty()) {
            return "Sorry, I couldn't find your task! :(";
        } else {
            String message = "____________________________________________________________\n"
                    + "Here are the matching tasks in your list:\n"
                    + input
                    + "____________________________________________________________";
            return message;
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public String showErrorMessage(String message) {
        return "Error: " + message;
    }

    /**
     * Reads and returns user input from the console.
     *
     * @return The user's input as a {@code String}.
     */
    public String getUserInput() {
        return sc.nextLine();
    }
}

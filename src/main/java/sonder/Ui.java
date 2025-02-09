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
    }

    /**
     * Displays a welcome message to the user.
     */
    public void welcomeMessage() {
        String message = "____________________________________________________________\n"
                + "Hello! I'm Sonder, your personal chatbot!\n"
                + "What can I do for you!\n"
                + "____________________________________________________________";
        System.out.println(message);
    }

    /**
     * Displays a goodbye message when the user exits the program.
     */
    public void goodbyeMessage() {
        System.out.println("____________________________________________________________\n"
                + "Bye. Hope to see you again soon!\n"
                + "____________________________________________________________"
        );
    }

    /**
     * Displays a message indicating that a task has been marked as done.
     *
     * @param index The index of the task that has been marked as done.
     */
    public void setDoneMessage(int index) {
        System.out.println("____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + TaskList.getTask(index) + "\n"
                + "____________________________________________________________"
        );
    }

    /**
     * Displays a message indicating that a task has been marked as not done.
     *
     * @param index The index of the task that has been marked as not done.
     */
    public void setUndoneMessage(int index) {
        System.out.println("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + TaskList.getTask(index) + "\n"
                + "____________________________________________________________"
        );
    }

    /**
     * Displays a message confirming the addition of a new task.
     *
     * @param t The task that was added.
     */
    public void addTaskMessage(Task t) {
        System.out.println("____________________________________________________________\n"
                + "Got it. I've added this task:\n"
                + t.toString() + "\n"
                + String.format("Now you have %d tasks in the list", TaskList.getTaskListSize()) + "\n"
                + "____________________________________________________________"
        );
    }

    /**
     * Displays a message confirming the deletion of a task.
     *
     * @param oldTask The task that was removed.
     */
    public void deleteMessage(String oldTask) {
        System.out.println("____________________________________________________________\n"
                + "Noted. I've removed this task:\n"
                + oldTask + "\n"
                + String.format("Now you have %d tasks in the list\n", TaskList.getTaskListSize())
                + "____________________________________________________________"
        );
    }

    /**
     * Displays a message showing the tasks that match the user's search query.
     *
     * @param input The formatted list of matching tasks, or an empty string if no match is found.
     */
    public void findTaskMessage(String input) {
        if (input.isEmpty()) {
            System.out.println("Sorry, I couldn't find your task! :(");
        } else {
            System.out.println("____________________________________________________________\n"
                    + "Here are the matching tasks in your list:\n"
                    + input
                    + "____________________________________________________________"
            );
        }
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to be displayed.
     */
    public void showErrorMessage(String message) {
        System.out.println("Error: " + message);
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

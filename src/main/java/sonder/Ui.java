package sonder;

import java.util.Scanner;

public class Ui {
    private Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
    }

    public void welcomeMessage() {
        String message = "____________________________________________________________\n"
                + "Hello! I'm Sonder, your personal chatbot!\n"
                + "What can I do for you!\n"
                + "____________________________________________________________";
        System.out.println(message);
    }

    public void goodbyeMessage() {
        System.out.println("____________________________________________________________\n"
                + "Bye. Hope to see you again soon!\n"
                + "____________________________________________________________"
        );
    }

    public void setDoneMessage(int index) {
        System.out.println("____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + TaskList.getTask(index) + "\n"
                + "____________________________________________________________"
        );
    }

    public void setUndoneMessage(int index) {
        System.out.println("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + TaskList.getTask(index) + "\n"
                + "____________________________________________________________"
        );
    }

    public void addTaskMessage(Task t) {
        System.out.println("____________________________________________________________\n"
                + "Got it. I've added this task:\n"
                + t.toString() + "\n"
                + String.format("Now you have %d tasks in the list", TaskList.getTaskListSize()) + "\n"
                + "____________________________________________________________"
        );
    }

    public void deleteMessage(String oldTask) {
        System.out.println("____________________________________________________________\n"
                + "Noted. I've removed this task:\n"
                + oldTask + "\n"
                + String.format("Now you have %d tasks in the list\n", TaskList.getTaskListSize())
                + "____________________________________________________________"
        );
    }

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

    public void showErrorMessage(String message) {
        System.out.println("Error: " + message);
    }

    public String getUserInput() {
        return sc.nextLine();
    }
}

import java.util.Scanner;

public class Sonder {

    public static void main(String[] args) {
        String message = "____________________________________________________________\n"
                + "Hello! I'm Sonder!\n"
                + "What can I do for you!\n"
                + "____________________________________________________________\n";
        System.out.println(message);

        Scanner sc = new Scanner(System.in);

        // Dealing with user inputs
        while (true) {
            String input = sc.nextLine();
            String[] inputArr = input.split("\\s+");
            String command = inputArr[0].toLowerCase();
            int length = inputArr.length;

            switch (command) {
            case "bye":
                System.out.println("____________________________________________________________\n"
                            + "Bye. Hope to see you again soon!\n"
                            + "____________________________________________________________"
                );
                return;

            case "list":
                if (Task.getTaskListSize() == 0) {
                    System.out.println("Your list is empty!");
                }
                Task.getList();
                break;

            case "mark":
                 markHelper("mark", inputArr, length);
                 break;

            case "unmark":
                markHelper("unmark", inputArr, length);
                break;

            case "todo":
                taskHelper("todo", input, inputArr, length);
                break;
            case "deadline":
                taskHelper("deadline", input, inputArr, length);
                break;
            case "event":
                taskHelper("event", input, inputArr, length);
                break;

            default:
                System.out.println("I don't know what that means. Sorry! :(");

            }
        }
    }

    // Helper function for marking and unmarking
    public static void markHelper(String action, String[] arr, int len) {
        if (len == 1) {
            System.out.println("Please input a number!");
        } else if (len > 2) {
            System.out.println("Invalid Input");
        } else {
            try {
                int index = Integer.parseInt(arr[1]);
                if (index > 0 && index <= Task.getTaskListSize()) {
                    if (action.equals("mark")) {
                        Task.setDone(index - 1);
                    } else if (action.equals("unmark")) {
                        Task.setUndone(index - 1);
                    }
                } else {
                    System.out.println("Choose a valid number please!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input numbers only please!");
            }
        }
    }

    // Helper function for adding tasks
    public static void taskHelper(String action, String input, String[] arr, int len) {
        if (len == 1) {
            System.out.println("Please input a task!");
        } else {
            if (action.equals("todo")) {
                String task = input.substring(5).trim();
                Task.addTask(new Todo(task));
            } else if (action.equals("deadline")) {
                if (input.contains("/by ")) {
                    String[] split = input.split("/by");
                    String task = split[0].substring(9).trim();
                    String deadline = split[1].trim();
                    if (!deadline.equals("")) {
                        Task.addTask(new Deadline(task, deadline));
                    } else {
                        System.out.println("Please include a due date!");
                    }
                } else {
                    System.out.println("Please include a due date!");
                }
            } else if (action.equals("event")) {
                if (input.contains("/from ") && input.contains("/to ")) {
                    String[] split = input.split("\\/from|\\/to");
                    String task = split[0].substring(6);
                    String start = split[1].trim();
                    String end = split[2].trim();
                    if (!start.equals("") && !end.equals("")) {
                        Task.addTask(new Event(task, start, end));
                    } else {
                        System.out.println("You did not input a start and/or end date!");
                    }
                } else if (input.contains("/from ")) {
                    System.out.println("Please include an end date/time");
                } else if (input.contains("/to ")) {
                    System.out.println("Please include a start date/time");
                }
            }
        }
    }
}

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
                break;

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

            default:
                System.out.println("added: " + input);
                Task t = new Task(input);
                Task.addTask(t);
            }
        }
    }

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
}

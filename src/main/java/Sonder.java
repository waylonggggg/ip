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

            if (input.equalsIgnoreCase("bye")) {
                System.out.println(
                        "____________________________________________________________\n"
                                + "Bye. Hope to see you again soon!\n"
                                + "____________________________________________________________"
                );
                break;
            } else if (input.equalsIgnoreCase("list")) {
                Task.getList();
            } else if (input.startsWith("mark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]);

                    if (index > 0 && index <= Task.getTaskListSize()) {
                        Task.setDone(index - 1);
                    } else {
                        System.out.println("Choose a valid number please!");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("You forgot to pick a number!");
                } catch (NumberFormatException e) {
                    System.out.println("Input numbers only please!");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int index = Integer.parseInt(input.split(" ")[1]);

                    if (index > 0 && index <= Task.getTaskListSize()) {
                        Task.setUndone(index - 1);
                    } else {
                        System.out.println("Choose a valid number please!");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("You forgot to pick a number!");
                } catch (NumberFormatException e) {
                    System.out.println("Input numbers only please!");
                }
            } else {
                System.out.println("added: " + input);
                Task t = new Task(input);
                Task.addTask(t);
            }
        }
    }
}

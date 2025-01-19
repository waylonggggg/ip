import java.util.Scanner;

public class Sonder {
    private static String[] list = new String[100];
    private static int index = 0;

    public static void main(String[] args) {
        String message = "____________________________________________________________\n"
                + "Hello! I'm Sonder!\n"
                + "What can I do for you!\n"
                + "____________________________________________________________\n";
        System.out.println(message);

        Scanner sc = new Scanner(System.in);

        // List inputs
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
                for (int i = 0; i < index; i++) {
                    System.out.println(String.format("%d." + list[i], i + 1));
                }
            } else {
                System.out.println("added: " + input);
                list[index] = input;
                index++;
            }
        }
    }
}

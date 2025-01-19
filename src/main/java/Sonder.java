import java.util.Scanner;

public class Sonder {
    public static void main(String[] args) {
        String message = "____________________________________________________________\n"
                + "Hello! I'm Sonder!\n"
                + "What can I do for you!\n"
                + "____________________________________________________________\n";
        System.out.println(message);

        Scanner sc = new Scanner(System.in);

        // Echo Message
        while (true) {
            String echo = sc.nextLine();

            if (echo.equalsIgnoreCase("bye")) {
                System.out.println(
                        "____________________________________________________________\n"
                                + "Bye. Hope to see you again soon!\n"
                                + "____________________________________________________________"
                );
                break;
            } else {
                System.out.println(echo);
            }
        }
    }
}

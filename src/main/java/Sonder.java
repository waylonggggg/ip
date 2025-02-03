import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.time.LocalDateTime;

public class Sonder {

    public static void main(String[] args) {
        String message = "____________________________________________________________\n"
                + "Hello! I'm Sonder!\n"
                + "What can I do for you!\n"
                + "____________________________________________________________";
        System.out.println(message);

        Scanner sc = new Scanner(System.in);

        // Initialise list
        Task.listInitialiser();

        // Intialise file
        Task.fileDirChecker();

        // Dealing with user inputs
        while (true) {
            try {
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
                        throw new SonderException("Your list is empty!");
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
                case "delete":
                    deleteHelper(inputArr, length);
                    break;
                default:
                    throw new SonderException("I don't know what that means. Sorry! :(");
                }
            } catch (SonderException e) {
                System.out.println(e.getMessage());
            } catch (FileNotFoundException e) { // Subclass of IOException
                System.out.println("File not found: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Error occurred while creating file: " + e.getMessage());
            } catch (DateTimeParseException e) {
                System.out.println("Please enter a valid date!" + e.getMessage());
            }
        }
    }

    // Helper function for marking and unmarking
    public static void markHelper(String action, String[] arr, int len) throws SonderException {
        if (len == 1) {
            throw new SonderException("Please input a number!");
        } else if (len > 2) {
            throw new SonderException("Invalid Input");
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
                    throw new SonderException("Choose a valid number please!");
                }
            } catch (NumberFormatException e) {
                throw new SonderException("Input numbers only please!");
            }
        }
    }

    // Helper function for adding tasks
    public static void taskHelper(String action, String input, String[] arr, int len) throws SonderException, IOException {
        if (len == 1) {
            throw new SonderException("Please input a task!");
        } else {
            if (action.equals("todo")) {
                String task = input.substring(5).trim();
                Task.addTask(new Todo(task, false));
            } else if (action.equals("deadline")) {
                if (input.contains("/by ")) {
                    String[] split = input.split("/by");
                    String task = split[0].substring(9).trim();
                    String deadline = split[1].trim();
                    if (!deadline.equals("")) {
                        if (isValidDate(deadline)) {
                            LocalDate date = LocalDate.parse(deadline);
                            Task.addTask(new Deadline(task, false, date));
                        } else {
                            throw new SonderException("Please include a valid due date!");
                        }
                    } else {
                        throw new SonderException("Please include a due date!");
                    }
                } else {
                    throw new SonderException("Please include a due date!");
                }
            } else if (action.equals("event")) {
                if (input.contains("/from ") && input.contains("/to ")) {
                    String[] split = input.split("\\/from|\\/to");
                    String task = split[0].substring(6).trim();
                    String start = split[1].trim();
                    String end = split[2].trim();
                    if (!start.equals("") && !end.equals("")) {
                        if (isValidStartAndEnd(start, end)) {
                            LocalDate startDate = LocalDate.parse(start);
                            LocalDate endDate = LocalDate.parse(end);
                            Task.addTask(new Event(task, false, startDate, endDate));
                        } else {
                            throw new SonderException("Please include a valid start/end date!");
                        }
                    } else {
                        throw new SonderException("You did not input a start and/or end date!");
                    }
                } else if (input.contains("/from ")) {
                    throw new SonderException("Please include an end date/time");
                } else if (input.contains("/to ")) {
                    throw new SonderException("Please include a start date/time");
                } else {
                    throw new SonderException("Please include a start and end date/time");
                }
            }
        }
    }

    public static void deleteHelper(String[] arr, int len) throws SonderException {
        if (len == 1) {
            throw new SonderException("Please input a number!");
        } else if (len > 2) {
            throw new SonderException("Invalid input!");
        } else {
            try {
                int index = Integer.parseInt(arr[1]);
                if (index > 0 && index <= Task.getTaskListSize()) {
                    Task.delete(index - 1);
                } else {
                    throw new SonderException("Choose a valid number please!");
                }
            } catch (NumberFormatException e) {
                throw new SonderException("Input numbers only please!");
            }
        }
    }

    public static boolean isValidDate(String input) {
        if (input.contains("-")) {
            String[] inputArr = input.split("-");
            int yearDigits = inputArr[0].length();
            int monthDigits = inputArr[1].length();
            int dayDigits = inputArr[2].length();
            if ((yearDigits == 4) && (monthDigits == 2) && (dayDigits == 2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidStartAndEnd(String input1, String input2) {
        if (input1.contains("-") && input2.contains("-")) {
            String[] inputArr1 = input1.split("-");
            int year1 = Integer.parseInt(inputArr1[0]);
            int month1 = Integer.parseInt(inputArr1[1]);
            int day1 = Integer.parseInt(inputArr1[2]);
            int yearDigits1 = inputArr1[0].length();
            int monthDigits1 = inputArr1[1].length();
            int dayDigits1 = inputArr1[2].length();

            String[] inputArr2 = input2.split("-");
            int year2 = Integer.parseInt(inputArr2[0]);
            int month2 = Integer.parseInt(inputArr2[1]);
            int day2 = Integer.parseInt(inputArr2[2]);
            int yearDigits2 = inputArr2[0].length();
            int monthDigits2 = inputArr2[1].length();
            int dayDigits2 = inputArr2[2].length();
            if ((yearDigits1 == 4) && (monthDigits1 == 2) && (dayDigits1 == 2)
                    && (yearDigits2 == 4) && (monthDigits2 == 2) && (dayDigits2 == 2)) {
                if (year1 > year2) {
                    return false;
                } else if (month1 > month2) {
                    return false;
                } else if (day1 > day2) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }
}

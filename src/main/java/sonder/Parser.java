package sonder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class Parser {

    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    public Parser(TaskList tasks, Ui ui, Storage storage) {
        this.tasks = tasks;
        this.ui = ui;
        this.storage = storage;
    }

    public void run(String input, String[] inputArr, String command, int length) throws SonderException,
            FileNotFoundException, IOException {
        switch (command) {
        case "bye":
            ui.goodbyeMessage();
            return;

        case "list":
            if (TaskList.getTaskListSize() == 0) {
                throw new SonderException("Your list is empty!");
            }
            storage.getList();
            break;

        case "find":
            ui.findTaskMessage(storage.findTask(input));
            break;

        case "mark":
            this.markHelper("mark", inputArr, length);
            break;

        case "unmark":
            this.markHelper("unmark", inputArr, length);
            break;

        case "todo":
            this.taskHelper("todo", input, inputArr, length);
            break;

        case "deadline":
            this.taskHelper("deadline", input, inputArr, length);
            break;

        case "event":
            this.taskHelper("event", input, inputArr, length);
            break;

        case "delete":
            this.deleteHelper(inputArr, length);
            break;

        default:
            throw new SonderException("I don't know what that means. Sorry! :(");
        }
    }

    // Helper function for marking and unmarking
    public void markHelper(String action, String[] arr, int len) throws SonderException {
        if (len == 1) {
            throw new SonderException("Please input a number!");
        } else if (len > 2) {
            throw new SonderException("Invalid Input");
        } else {
            try {
                int index = Integer.parseInt(arr[1]);
                if (index > 0 && index <= tasks.getTaskListSize()) {
                    Task task = TaskList.getTask(index - 1);
                    if (action.equals("mark")) {
                        task.setDone();
                        storage.fileListAmendHelper("mark", index - 1);
                        ui.setDoneMessage(index);
                    } else if (action.equals("unmark")) {
                        task.setUndone();
                        storage.fileListAmendHelper("unmark", index - 1);
                        ui.setUndoneMessage(index);
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
    public void taskHelper(String action, String input, String[] arr, int len) throws SonderException, IOException {
        if (len == 1) {
            throw new SonderException("Please input a task!");
        } else {
            if (action.equals("todo")) {
                String task = input.substring(5).trim();
                Task t = new Todo(task, false);
                tasks.addTask(t);
                storage.appendTask(t);
                ui.addTaskMessage(t);

            } else if (action.equals("deadline")) {
                if (input.contains("/by ")) {
                    String[] split = input.split("/by");
                    String task = split[0].substring(9).trim();
                    String deadline = split[1].trim();
                    if (!deadline.isEmpty()) {
                        if (isValidDate(deadline)) {
                            LocalDate date = LocalDate.parse(deadline);
                            Task t = new Deadline(task, false, date);
                            tasks.addTask(t);
                            storage.appendTask(t);
                            ui.addTaskMessage(t);
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
                            Task t = new Event(task, false, startDate, endDate);
                            tasks.addTask(t);
                            storage.appendTask(t);
                            ui.addTaskMessage(t);
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

    public void deleteHelper(String[] arr, int len) throws SonderException {
        if (len == 1) {
            throw new SonderException("Please input a number!");
        } else if (len > 2) {
            throw new SonderException("Invalid input!");
        } else {
            try {
                int index = Integer.parseInt(arr[1]);
                if (index > 0 && index <= tasks.getTaskListSize()) {
                    String oldTask = TaskList.getTask(index - 1).toString();
                    tasks.delete(index - 1);
                    storage.fileListAmendHelper("delete", index - 1);
                    ui.deleteMessage(oldTask);
                } else {
                    throw new SonderException("Choose a valid number please!");
                }
            } catch (NumberFormatException e) {
                throw new SonderException("Input numbers only please!");
            }
        }
    }

    public static boolean isValidDate(String input) {
        try {
            LocalDate.parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidStartAndEnd(String input1, String input2) {
        if (input1.contains("-") && input2.contains("-")) {
            String[] inputArr1 = input1.split("-");
            int year1 = Integer.parseInt(inputArr1[0]), month1 = Integer.parseInt(inputArr1[1]),
                    day1 = Integer.parseInt(inputArr1[2]);

            String[] inputArr2 = input2.split("-");
            int year2 = Integer.parseInt(inputArr2[0]), month2 = Integer.parseInt(inputArr2[1]),
                    day2 = Integer.parseInt(inputArr2[2]);

            if (isValidDate(input1) && isValidDate(input2)) {
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

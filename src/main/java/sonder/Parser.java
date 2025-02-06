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
            handleListCommand();
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


    private void handleListCommand() throws SonderException, FileNotFoundException, IOException{
        if (TaskList.getTaskListSize() == 0) {
            throw new SonderException("Your list is empty!");
        }
        storage.getList();
    }

    // Helper function for marking and unmarking
    private void markHelper(String action, String[] arr, int len) throws SonderException {
        validateSingleIndex(arr, len);
        int index = Integer.parseInt(arr[1]);
        validateIndexRange(index);

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
    }

    private void validateIndexRange(int index) {
        if (index <= 0 || index > TaskList.getTaskListSize());
    }

    private void validateSingleIndex(String[] arr, int len) throws SonderException{
        if (len == 1) {
            throw new SonderException("Please input a number!");
        } else if (len > 2) {
            throw new SonderException("Invalid input!");
        } else if (!isNumeric(arr[1])) {
            throw new SonderException("Input numbers only please");
        }
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    // Helper function for adding tasks
    private void taskHelper(String action, String input, String[] arr, int len) throws SonderException, IOException {
        if (len == 1) {
            throw new SonderException("Please input a task!");
        }
        switch (action) {
        case "todo":
            addTodoTask(input);
            break;
        case "deadline":
            addDeadlineTask(input);
            break;
        case "event":
            addEventTask(input);
            break;
        }
    }

    private void addTodoTask(String input) throws IOException {
        String taskDescription = input.substring(5).trim();
        Task task = new Todo(taskDescription, false);
        tasks.addTask(task);
        storage.appendTask(task);
        ui.addTaskMessage(task);
    }

    private void addDeadlineTask(String input) throws SonderException, IOException {
        if (!input.contains("/by ")) {
            throw new SonderException("Please include a due date!");
        }

        String[] split = input.split("/by");
        String taskDescription = split[0].substring(9).trim();
        String deadline = split[1].trim();

        if (!isValidDate(deadline)) {
            throw new SonderException("Please include a valid due date!");
        }

        LocalDate date = LocalDate.parse(deadline);
        Task task = new Deadline(taskDescription, false, date);
        tasks.addTask(task);
        storage.appendTask(task);
        ui.addTaskMessage(task);
    }

    private void addEventTask(String input) throws SonderException, IOException {
        if (!input.contains("/from ") || !input.contains("/to ")) {
            throw new SonderException("Please include a start and/or end date");
        }

        String[] split = input.split("\\/from|\\/to");
        String taskDescription = split[0].substring(6).trim();
        String start = split[1].trim();
        String end = split[2].trim();

        if (!isValidStartAndEnd(start, end)) {
            throw new SonderException("Please include a valid start/end date!");
        }

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        Task task = new Event(taskDescription, false, startDate, endDate);
        tasks.addTask(task);
        storage.appendTask(task);
        ui.addTaskMessage(task);
    }

    private void deleteHelper(String[] arr, int len) throws SonderException {
        validateSingleIndex(arr, len);
        int index = Integer.parseInt(arr[1]);
        validateIndexRange(len);

        String oldTask = TaskList.getTask(index - 1).toString();
        tasks.delete(index - 1);
        storage.fileListAmendHelper("delete", index - 1);
        ui.deleteMessage(oldTask);

    }

    private static boolean isValidDate(String input) {
        try {
            LocalDate.parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isValidStartAndEnd(String input1, String input2) {
        return isValidDate(input1) && isValidDate(input2) &&
                LocalDate.parse(input1).isBefore(LocalDate.parse(input2));
    }
}

package sonder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

/**
 * The {@code Parser} class is responsible for processing user input,
 * executing corresponding commands, and interacting with the {@code TaskList},
 * {@code Ui}, and {@code Storage} components.
 */
public class Parser {

    private TaskList tasks;
    private Ui ui;
    private Storage storage;

    /**
     * Constructs a {@code Parser} instance with the required dependencies.
     *
     * @param tasks   The task list that stores all tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage system for reading and writing tasks.
     */
    public Parser(TaskList tasks, Ui ui, Storage storage) {
        this.tasks = tasks;
        this.ui = ui;
        this.storage = storage;
    }

    /**
     * Processes user input and executes the corresponding command.
     *
     * @param input   The full user input string.
     * @param inputArr The tokenized user input array.
     * @param command The extracted command keyword.
     * @param length  The length of the input array.
     * @throws SonderException       If an invalid command or argument is given.
     * @throws FileNotFoundException If the file containing tasks cannot be found.
     * @throws IOException           If an error occurs while reading or writing to the file.
     */
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

    /**
     * Handles the "list" command by displaying all tasks in storage.
     *
     * @throws SonderException       If the task list is empty.
     * @throws FileNotFoundException If the task file is missing.
     * @throws IOException           If an error occurs while reading the file.
     */
    private void handleListCommand() throws SonderException, FileNotFoundException, IOException {
        if (TaskList.getTaskListSize() == 0) {
            throw new SonderException("Your list is empty!");
        }
        storage.getList();
    }

    /**
     * Marks or unmarks a task as done or not done.
     *
     * @param action The action to perform ("mark" or "unmark").
     * @param arr    The user input split into an array.
     * @param len    The length of the input array.
     * @throws SonderException If an invalid index is given.
     */
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

    /**
     * Validates if the given index is within a valid range.
     *
     * @param index The task index to validate.
     * @throws SonderException If the index is out of range.
     */
    private void validateIndexRange(int index) throws SonderException {
        if (index <= 0 || index > TaskList.getTaskListSize()) {
            throw new SonderException("Please input a valid index");
        }
    }

    /**
     * Validates if a single numerical index is provided.
     *
     * @param arr The input array.
     * @param len The length of the input array.
     * @throws SonderException If the input is missing, too long, or not numeric.
     */
    private void validateSingleIndex(String[] arr, int len) throws SonderException {
        if (len == 1) {
            throw new SonderException("Please input a number!");
        } else if (len > 2) {
            throw new SonderException("Invalid input!");
        } else if (!isNumeric(arr[1])) {
            throw new SonderException("Input numbers only please");
        }
    }

    /**
     * Checks if a given string consists of only numeric characters.
     *
     * @param str The input string.
     * @return {@code true} if the string is numeric, {@code false} otherwise.
     */
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    /**
     * Handles adding a task to the task list.
     *
     * @param action The task type ("todo", "deadline", or "event").
     * @param input  The full user input string.
     * @param arr    The tokenized user input array.
     * @param len    The length of the input array.
     * @throws SonderException If input is invalid.
     * @throws IOException     If an error occurs while saving the task.
     */
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
        default:
            throw new IllegalArgumentException("Invalid task action: " + action);
        }
    }

    /**
     * Adds a Todo task to the task list.
     *
     * @param input The full user input string containing the task description.
     * @throws IOException If an error occurs while saving the task to storage.
     */
    private void addTodoTask(String input) throws IOException {
        String taskDescription = input.substring(5).trim();
        Task task = new Todo(taskDescription, false);
        tasks.addTask(task);
        storage.appendTask(task);
        ui.addTaskMessage(task);
    }

    /**
     * Adds a Deadline task to the task list.
     *
     * @param input The full user input string containing the task description and deadline.
     * @throws SonderException If the deadline is missing or invalid.
     * @throws IOException If an error occurs while saving the task to storage.
     */
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

    /**
     * Adds an Event task to the task list.
     *
     * @param input The full user input string containing the task description, start date, and end date.
     * @throws SonderException If the start and/or end date is missing or invalid.
     * @throws IOException If an error occurs while saving the task to storage.
     */
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

    /**
     * Deletes a task from the task list.
     *
     * @param arr The input array containing the task index.
     * @param len The length of the input array.
     * @throws SonderException If an invalid index is given.
     */
    private void deleteHelper(String[] arr, int len) throws SonderException {
        validateSingleIndex(arr, len);
        int index = Integer.parseInt(arr[1]);
        validateIndexRange(len);

        String oldTask = TaskList.getTask(index - 1).toString();
        tasks.delete(index - 1);
        storage.fileListAmendHelper("delete", index - 1);
        ui.deleteMessage(oldTask);

    }

    /**
     * Validates if a given date string is a valid date.
     *
     * @param input The date string to validate.
     * @return {@code true} if valid, {@code false} otherwise.
     */
    private static boolean isValidDate(String input) {
        try {
            LocalDate.parse(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validates if the start and end dates are correctly formatted and ordered.
     *
     * @param firstDate  The start date string.
     * @param secondDate The end date string.
     * @return {@code true} if both dates are valid and in order, {@code false} otherwise.
     */
    private static boolean isValidStartAndEnd(String firstDate, String secondDate) {
        return isValidDate(firstDate) && isValidDate(secondDate)
                && LocalDate.parse(firstDate).isBefore(LocalDate.parse(secondDate));
    }
}

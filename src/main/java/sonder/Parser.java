package sonder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

import javafx.application.Platform;

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
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
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
    public String run(String input, String[] inputArr, String command, int length) throws SonderException,
            FileNotFoundException, IOException {
        switch (command) {
        case "bye":
            Platform.exit();
            return ui.goodbyeMessage();
        case "list":
            return handleListCommand();
        case "find":
            return ui.findTaskMessage(storage.findTask(input));
        case "mark":
            return markHelper("mark", inputArr, length);
        case "unmark":
            return markHelper("unmark", inputArr, length);
        case "todo":
            return taskHelper("todo", input, inputArr, length);
        case "deadline":
            return taskHelper("deadline", input, inputArr, length);
        case "event":
            return taskHelper("event", input, inputArr, length);
        case "delete":
            return deleteHelper(inputArr, length);
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
    private String handleListCommand() throws SonderException, FileNotFoundException, IOException {
        if (TaskList.getTaskListSize() == 0) {
            throw new SonderException("Your list is empty!");
        }
        return storage.getList();
    }

    /**
     * Marks or unmarks a task as done or not done.
     *
     * @param action The action to perform ("mark" or "unmark").
     * @param arr    The user input split into an array.
     * @param len    The length of the input array.
     * @throws SonderException If an invalid index is given.
     */
    private String markHelper(String action, String[] arr, int len) throws SonderException {
        validateIndex(arr, len);
        int index = Integer.parseInt(arr[1]);

        assert index > 0 && index <= TaskList.getTaskListSize() : "Invalid index used in markHelper";

        Task task = TaskList.getTask(index - 1);
        boolean isMarking = action.equals("mark");
        if (isMarking) {
            task.setDone();
        } else {
            task.setUndone();
        }
        storage.fileListAmendHelper(action, index - 1);
        return isMarking ? ui.setDoneMessage(index) : ui.setUndoneMessage(index);
    }

    /**
     * Validates the index provided in the input array.
     * Ensures that the input has exactly two elements and that the second element is a valid numeric index.
     * Throws an exception if the index is not a positive integer within the valid range of task numbers.
     *
     * @param arr The input array containing the command and the task number.
     * @param len The expected length of the input array.
     * @throws SonderException If the input length is incorrect, the task number is not numeric,
     *                         or the index is out of the valid range.
     */
    private void validateIndex(String[] arr, int len) throws SonderException {
        if (len != 2 || !isNumeric(arr[1])) {
            throw new SonderException("Please provide a valid task number.");
        }
        int index = Integer.parseInt(arr[1]);
        if (index <= 0 || index > TaskList.getTaskListSize()) {
            throw new SonderException("Index out of range. Please enter a valid task number.");
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
    private String taskHelper(String action, String input, String[] arr, int len) throws SonderException, IOException {
        if (len == 1) {
            throw new SonderException("Please input a task!");
        }
        switch (action) {
        case "todo":
            return addTodoTask(input);
        case "deadline":
            return addDeadlineTask(input);
        case "event":
            return addEventTask(input);
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
    private String addTodoTask(String input) throws IOException {
        String taskDescription = input.substring(5).trim();

        assert !taskDescription.isEmpty() : "Task description cannot be empty in addDeadlineTask";

        Task task = new Todo(taskDescription, false);
        tasks.addTask(task);
        storage.appendTask(task);
        return ui.addTaskMessage(task);
    }

    /**
     * Adds a Deadline task to the task list.
     *
     * @param input The full user input string containing the task description and deadline.
     * @throws SonderException If the deadline is missing or invalid.
     * @throws IOException If an error occurs while saving the task to storage.
     */
    private String addDeadlineTask(String input) throws SonderException, IOException {
        if (!input.contains("/by ")) {
            throw new SonderException("Please include a due date!");
        }

        String[] split = input.split("/by");
        String taskDescription = split[0].substring(9).trim();
        String deadline = split[1].trim();

        assert !taskDescription.isEmpty() : "Task description cannot be empty in addDeadlineTask";
        assert !deadline.isEmpty() : "Deadline cannot be empty in addDeadlineTask";

        if (!isValidDate(deadline)) {
            throw new SonderException("Please include a valid due date!");
        }

        LocalDate date = LocalDate.parse(deadline);
        Task task = new Deadline(taskDescription, false, date);
        tasks.addTask(task);
        storage.appendTask(task);
        return ui.addTaskMessage(task);
    }

    /**
     * Adds an Event task to the task list.
     *
     * @param input The full user input string containing the task description, start date, and end date.
     * @throws SonderException If the start and/or end date is missing or invalid.
     * @throws IOException If an error occurs while saving the task to storage.
     */
    private String addEventTask(String input) throws SonderException, IOException {
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

        assert startDate != null : "Start date cannot be null in addEventTask";
        assert endDate != null : "End date cannot be null in addEventTask";
        assert startDate.isBefore(endDate) : "Start date must be before end date in addEventTask";

        Task task = new Event(taskDescription, false, startDate, endDate);
        tasks.addTask(task);
        storage.appendTask(task);
        return ui.addTaskMessage(task);
    }

    /**
     * Deletes a task from the task list.
     *
     * @param arr The input array containing the task index.
     * @param len The length of the input array.
     * @throws SonderException If an invalid index is given.
     */
    private String deleteHelper(String[] arr, int len) throws SonderException {
        validateIndex(arr, len);
        int index = Integer.parseInt(arr[1]);

        assert index > 0 && index <= TaskList.getTaskListSize() : "Invalid index used in markHelper";

        String oldTask = TaskList.getTask(index - 1).toString();
        storage.fileListAmendHelper("delete", index - 1);
        tasks.delete(index - 1);
        return ui.deleteMessage(oldTask);

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

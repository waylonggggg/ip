package sonder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * The {@code Sonder} class is the main entry point of the application.
 * It initializes the user interface, task list, storage system, and command parser.
 * This class is responsible for running the main program loop.
 */
public class Sonder {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private Parser parser;

    /**
     * Constructs a {@code Sonder} instance and initializes the necessary components.
     *
     * @param filePath The file path where tasks are stored.
     */
    public Sonder(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = initialiseTaskList();
        this.parser = new Parser(tasks, ui, storage);
    }

    /**
     * Initializes the task list by loading tasks from storage.
     * If an error occurs during loading, an empty task list is created.
     *
     * @return The initialized {@code TaskList} containing loaded tasks or an empty list if loading fails.
     */
    private TaskList initialiseTaskList() {
        try {
            storage.fileDirChecker();
            return new TaskList(storage.load());
        } catch (FileNotFoundException e) {
            ui.showErrorMessage("Task file not found, creating a new task list.");
        } catch (IOException e) {
            ui.showErrorMessage("Error loading tasks from storage: " + e.getMessage());
        }
        return new TaskList(new ArrayList<>()); // Return an empty task list if loading fails
    }

    /**
     * Runs the main program loop, continuously reading user input
     * and passing commands to the {@code Parser}.
     */
    public void run() {
        ui.welcomeMessage();

        while (true) {
            try {
                String input = ui.getUserInput().trim();
                String[] inputArr = input.split("\\s+");
                String command = inputArr[0].toLowerCase();
                int length = inputArr.length;

                parser.run(input, inputArr, command, length);
            } catch (Exception e) {
                handleException(e);
            }
        }
    }

    /**
     * Handles various exceptions that may occur during program execution and
     * displays appropriate error messages to the user.
     *
     * @param e The exception that was thrown.
     */
    private void handleException(Exception e) {
        if (e instanceof SonderException) {
            ui.showErrorMessage(e.getMessage());
        } else if (e instanceof FileNotFoundException) {
            ui.showErrorMessage("File not found: " + e.getMessage());
        } else if (e instanceof IOException) {
            ui.showErrorMessage("Error occurred while accessing file: " + e.getMessage());
        } else if (e instanceof DateTimeParseException) {
            ui.showErrorMessage("Please enter a valid date.");
        } else if (e instanceof IllegalArgumentException) {
            ui.showErrorMessage(e.getMessage());
        } else {
            ui.showErrorMessage("Unexpected error: " + e.getMessage());
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            String trimmedInput = input.trim();
            String[] inputArr = trimmedInput.split("\\s+");
            String command = inputArr[0].toLowerCase();
            int length = inputArr.length;

            return parser.run(input, inputArr, command, length);
        } catch (SonderException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "An unexpected error occurred. Please try again.";
        }
    }

    /**
     * The main method that starts the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Sonder sonder = new Sonder("./data/list.txt");
        sonder.run();
    }
}

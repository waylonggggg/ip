package sonder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Sonder {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private Parser parser;

    public Sonder(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.tasks = initialiseTaskList();
        this.parser = new Parser(tasks, ui, storage);
    }

    private TaskList initialiseTaskList() {
        try {
            storage.fileDirChecker();
            return new TaskList(storage.load());
        } catch (Exception e) {
            ui.showErrorMessage("Initialization error: " + e.getMessage());
            return new TaskList(new ArrayList<Task>()); // Return an empty task list
        }
    }

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
                ui.showErrorMessage(e.getMessage());
            }
        }
    }

    private void handleException(Exception e) {
        if (e instanceof SonderException) {
            ui.showErrorMessage(e.getMessage());
        } else if (e instanceof FileNotFoundException) {
            ui.showErrorMessage("File not found: " + e.getMessage());
        } else if (e instanceof IOException) {
            ui.showErrorMessage("Error occurred while accessing file: " + e.getMessage());
        } else if (e instanceof DateTimeParseException) {
            ui.showErrorMessage("Please enter a valid date.");
        } else {
            ui.showErrorMessage("Unexpected error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Sonder("./data/list.txt").run();
    }
}

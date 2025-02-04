import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Sonder {

    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private Parser parser;

    public Sonder(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            //Initialise storage files
            storage.fileDirChecker();

            //Initialise new TaskList and load tasks from txt
            tasks = new TaskList(storage.load());
        } catch (Exception e) {
            ui.showErrorMessage(e.getMessage());
        }
        parser = new Parser(tasks, ui, storage);
    }

    public void run() {
        ui.welcomeMessage();

        while (true) {
            try {
                String input = ui.getUserInput();
                String[] inputArr = input.split("\\s+");
                String command = inputArr[0].toLowerCase();
                int length = inputArr.length;

                //Deal with user inputs
                parser.run(input, inputArr, command, length);
            } catch (SonderException e) {
                ui.showErrorMessage(e.getMessage());
            } catch (FileNotFoundException e) { // Subclass of IOException
                ui.showErrorMessage("File not found: " + e.getMessage());
            } catch (IOException e) {
                ui.showErrorMessage("Error occurred while creating file: " + e.getMessage());
            } catch (DateTimeParseException e) {
                ui.showErrorMessage("Please enter a valid date: " + e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Sonder("./data/list.txt").run();
    }
}

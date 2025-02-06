package sonder;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles file storage operations for saving and loading tasks from a file.
 */
public class Storage {

    private final String filePath;

    /**
     * Constructs a {@code Storage} instance with the specified file path.
     *
     * @param filePath The path to the file where tasks will be saved and loaded.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Ensures that the directory and file for storing tasks exist.
     * If they do not exist, this method creates them.
     */
    public void fileDirChecker() {
        try {
            File f = new File(this.getFilePath());
            File directory = f.getParentFile();

            if (!directory.exists()) {
                directory.mkdirs();
            }

            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Loads tasks from the storage file and returns them as a list.
     * If the file does not exist or an error occurs, an empty list is returned.
     *
     * @return An {@code ArrayList} of tasks loaded from the file.
     */
    public ArrayList<Task> load() throws IOException {
        String filePath = this.getFilePath();
        ArrayList<Task> taskList = new ArrayList<>();
        File f = new File(filePath);

        if (!f.exists()) {
            return taskList;
        }

        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            taskList.add(parseTask(sc.nextLine()));
        }
        return taskList;
    }

    private Task parseTask(String line) {
        String[] taskArr = line.split("\\|");
        String type = taskArr[0].trim();
        boolean isDone = taskArr[1].trim().equals("1");
        String desc = taskArr[2].trim();

        switch (type) {
        case "T":
            return new Todo(desc, isDone);
        case "D":
            LocalDate by = LocalDate.parse(taskArr[3].substring(5).trim(),
                    DateTimeFormatter.ofPattern("MMM dd yyyy"));
            return new Deadline(desc, isDone, by);
        case "E":
            LocalDate start = LocalDate.parse(taskArr[3].substring(7).trim(),
                    DateTimeFormatter.ofPattern("MMM dd yyyy"));
            LocalDate end = LocalDate.parse(taskArr[4].substring(5).trim(),
                    DateTimeFormatter.ofPattern("MMM dd yyyy"));
            return new Event(desc, isDone, start, end);
        default:
        throw new IllegalArgumentException("Invalid task type: " + type);
        }
    }

    /**
     * Reads and displays the task list stored in the file.
     * If the file does not exist, it is created.
     *
     * @throws FileNotFoundException If the file is not found.
     * @throws IOException If an I/O error occurs.
     */
    public void getList() throws FileNotFoundException, IOException {
        File f = new File("./data/list.txt");
        if (f.exists()) {
            Scanner sc = new Scanner(f);
            int counter = 1;
            System.out.println("____________________________________________________________");
            while (sc.hasNext()) {
                System.out.println(counter + ". " + sc.nextLine());
                counter++;
            }
            System.out.println("____________________________________________________________");
        } else {
            f.createNewFile();
        }
    }

    /**
     * Updates or deletes a task entry in the storage file.
     *
     * @param action The action to perform ("delete" or update).
     * @param index The index of the task in the file to update or delete.
     */
    public void fileListAmendHelper(String action, int index) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(this.getFilePath()));
            StringBuffer buffer = new StringBuffer();
            Task task = TaskList.getTask(index);
            int counter = 0;

            String taskNewString = task.toString();

            String line;
            while ((line = file.readLine()) != null) {
                if ((counter == index) && action.equals("delete")) {
                    counter++;
                    continue;
                } else if (counter == index) {
                    line = taskNewString;
                }
                buffer.append(line).append("\n");
                counter++;
            }
            file.close();

            String inputStr = buffer.toString();   // for debugging

            FileOutputStream fileOut = new FileOutputStream(this.getFilePath());
            fileOut.write(buffer.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file: " + e.getMessage());
        }
    }

    /**
     * Appends a new task to the storage file.
     *
     * @param t The task to be appended.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void appendTask(Task t) throws IOException{
        FileWriter fw = new FileWriter("./data/list.txt", true);
        fw.write(t.toString() + "\n");
        fw.close();
    }

    public String findTask(String input) throws FileNotFoundException, SonderException {
        String taskToFind = input.substring(4).trim();

        if (taskToFind.isEmpty()) {
            throw new SonderException("Please input a task to find!");
        }

        Scanner sc = new Scanner(new File(getFilePath()));
        ArrayList<String> taskArr = new ArrayList<>();

        while (sc.hasNext()) {
            String taskLine = sc.nextLine();
            if (taskLine.toLowerCase().contains(taskToFind.toLowerCase())) {
                taskArr.add(taskLine);
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskArr.size(); i++) {
            sb.append(i + 1).append(". ").append(taskArr.get(i)).append("\n");
        }

        return sb.toString();
    }

    /**
     * Returns the file path where tasks are stored.
     *
     * @return The file path as a {@code String}.
     */
    public String getFilePath() {
        return this.filePath;
    }
}

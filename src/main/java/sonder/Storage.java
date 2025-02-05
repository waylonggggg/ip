package sonder;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

    public void appendTask(Task t) throws IOException{
        FileWriter fw = new FileWriter("./data/list.txt", true);
        fw.write(t.toString() + "\n");
        fw.close();
    }

    public String getFilePath() {
        return this.filePath;
    }
}

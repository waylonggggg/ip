import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Task {
    private String description;
    private boolean isDone;
    private static ArrayList<Task> taskList = new ArrayList<>();
    private static String FILEPATH = "./data/list.txt";

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public static void fileDirChecker() {
        try {
            File f = new File(FILEPATH);
            File directory = f.getParentFile();

            if (!directory.exists()) {
                directory.mkdirs();
            }

            if (!f.exists()) {
                f.createNewFile();
            }
        } catch (IOException e) {
            System.out.println("Errpr: " + e.getMessage());
        }
    }

    public static void addTask(Task t) throws IOException{
        taskList.add(t);
        FileWriter fw = new FileWriter(FILEPATH, true);
        fw.write(t.toString() + "\n");
        fw.close();
        System.out.println("____________________________________________________________\n"
                + "Got it. I've added this task:\n"
                + t.toString() + "\n"
                + String.format("Now you have %d tasks in the list", getTaskListSize()) + "\n"
                + "____________________________________________________________"
        );
    }

    public static void setDone(int index) {
        fileListAmendHelper("mark", index);
        System.out.println("____________________________________________________________\n"
                + "Nice! I've marked this task as done:\n"
                + taskList.get(index) + "\n"
                + "____________________________________________________________"
        );
    }

    public static void setUndone(int index) {
        fileListAmendHelper("unmark", index);
        System.out.println("____________________________________________________________\n"
                + "OK, I've marked this task as not done yet:\n"
                + taskList.get(index) + "\n"
                + "____________________________________________________________"
        );
    }

    public static void delete(int index) {
        String oldTask = taskList.get(index).toString();
        fileListAmendHelper("delete", index);
        taskList.remove(index);
        System.out.println("____________________________________________________________\n"
                + "Noted. I've removed this task:\n"
                + oldTask + "\n"
                + String.format("Now you have %d tasks in the list\n", getTaskListSize())
                + "____________________________________________________________"
        );
    }

    public String getDescription() {
        return this.description;
    }

    public String getStatusIcon() {
        return this.isDone ? "1" : "0";
    }

    public static int getTaskListSize() {
        return taskList.size();
    }

    public static void getList() throws FileNotFoundException, IOException {
        File f = new File(FILEPATH);
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

    public static void fileListAmendHelper(String action, int index) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(FILEPATH));
            StringBuffer buffer = new StringBuffer();
            Task task = taskList.get(index);
            int counter = 0;

            if (action.equals("mark")) {
                task.isDone = true;
            } else if (action.equals("unmark")) {
                task.isDone = false;
            }
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

            FileOutputStream fileOut = new FileOutputStream(FILEPATH);
            fileOut.write(buffer.toString().getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file: " + e.getMessage());
        }
    }

    public static void listInitialiser() {
        try {
            File f = new File(FILEPATH);
            if (f.exists()) {
                Scanner sc = new Scanner(f);
                while (sc.hasNext()) {
                    String[] taskArr = sc.nextLine().split("\\|");
                    String type = taskArr[0].trim();
                    String isDone = taskArr[1].trim();
                    String desc = taskArr[2].trim();
                    if (type.equals("T")) {
                        taskList.add(new Todo(desc, false));
                    } else if (type.equals("D")) {
                        String by = taskArr[3].substring(5);
                        taskList.add(new Deadline(desc, false, by));
                    } else if (type.equals("E")) {
                        String start = taskArr[3].substring(7);
                        String end = taskArr[4].substring(5);
                        taskList.add(new Event(desc, false, start, end));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("IO error occurred: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return String.format("%s | %s", this.getStatusIcon(),
                this.getDescription());
    }
}

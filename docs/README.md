# Sonder User Guide

![Screenshot of the gui](/docs/Ui.png)

Sonder is a desktop app for managing tasks, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Sonder can manage your tasks faster than traditional GUI apps.

- [Quick Start](#quick-start)

- [Features](#features)
  - [Viewing task list](#viewing-task-list)
  - [Adding todos](#adding-todos)
  - [Adding deadlines](#adding-deadlines)
  - [Adding events](#adding-events)
  - [Marking tasks](#marking-tasks)
  - [Unmarking tasks](#unmarking-tasks)
  - [Finding tasks by name](#finding-tasks-by-name)
  - [Deleting tasks](#deleting-tasks)
  - [Exiting the program](#exiting-the-program)

## Quick start
1. Ensure you have Java 17 or above installed in your Computer.
   Mac users: Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).
2. Download the latest .jar file from [here](https://github.com/waylonggggg/ip/releases/tag/v0.2).
3. Copy the file to the folder you want to use as the home folder for your AddressBook.
4. Open a command terminal, cd into the folder you put the jar file in, and use the java -jar sonder.jar command to run the application.
5. Some example commands you can try:
   - list: Lists all tasks
   - todo Wash Dishes: Adds a task called "Wash Dishes" to the list.
   - mark 1: Marks the first task in the list as done by denoting it with "1".
   - unmark 1: Unmarks the first task in the list as undone by denoting it with "0".
   - delete 1: Deletes the first task from the list.
   - bye: Exits the app.
6. Refer to the [Features](#features) below for details of each command.

## Features
### Viewing task list
> command: `list`

Shows the full task list the user has.
![Screenshot of an example of the list command](/docs/list_example.png)

### Adding todos
> command: `todo [name of task]`

Adds a task without a start or due date to the list

Format: `todo [name of task]`

![Screenshot of an example of the list command](/docs/todo_example.png)

Examples:
- `todo Watch big bang theory` Adds the task "Watch big bang theory" to the list.

### Adding deadlines
> command: `deadline [name of task] /by [yyyy-mm-dd]`

Adds a task with a due date to the list

Format: `deadline [name of task] /by [yyyy-mm-dd]`
- yyyy-mm-dd format to be adhered to (e.g. 2025-10-23)

![Screenshot of an example of the list command](/docs/deadline_example.png)

Examples:
- `deadline Clean my room /by 2025-02-23` Adds the task "Clean my room" with a due date "2025-02-23" to the list.

### Adding events
> command: `event [name of task] /from [yyyy-mm-dd] /to [yyyy-mm-dd]`

Adds a task with a start and end date to the list

Format: `event [name of task] /from [yyyy-mm-dd] /to [yyyy-mm-dd]`
- yyyy-mm-dd format to be adhered to (e.g. 2025-10-23)

![Screenshot of an example of the list command](/docs/event_example.png)

Examples:
- `event F1 Weekend /from 2025-02-23 /to 2025-02-25` Adds the task "F1 weekend" with a start date "2025-02-23" and end date "2025-02-25" to the list.

### Marking tasks
> command: `mark [index]`

Marks a task as done by denoting it with a "1" beside.

Format: `mark [index]`
- Index refers to the task number

![Screenshot of an example of the list command](/docs/mark_example.png)

Examples:
- `mark 1` Marks the first task as done

### Unmarking tasks
> command: `unmark [index]`

Marks a task as undone by denoting it with a "0" beside.

Format: `unmark [index]`
- Index refers to the task number

![Screenshot of an example of the list command](/docs/unmark_example.png)

Examples:
- `unmark 1` Marks the first task as undone

### Finding tasks by name
> command: `find [keyword]`

Finds tasks whose names contain the given keyword

Format: `find [keyword]`
- The search is case-insensitive e.g. `shopping` will match `Shopping`.
- Still finds tasks even if the keyword matches the task name only partially.

Examples:
- `find get` and `find ge` will return the same result as shown below

![Screenshot of an example of the list command](/docs/find_example.png)

### Deleting tasks
> command: `delete [index]`

Deletes a task from the list

Format: `delete [index]`
- Index refers to the task number

![Screenshot of an example of the list command](/docs/delete_example.png)

Examples:
- `delete 1`
- `delete 2`

### Exiting the program
> command: `exit`

Exits the program

Format: `exit`

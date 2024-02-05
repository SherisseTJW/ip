package Oak.task;

import Oak.utility.FileUtility;
import Oak.task.model.Deadline;
import Oak.task.model.Event;
import Oak.task.model.Task;
import Oak.task.model.Todo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class TaskService {
    /** An Array of all the tasks in the system */
    private ArrayList<Task> tasks = new ArrayList<>();
    /** The relative file path to the tasklist.txt where all the tasks are stored  */
    private final String tasklistFilePath = "/src/main/java/Oak/resources/tasklist.txt";
    /** The separator for the tasklist.txt */
    private final String taskListSep = "\\|";
    /** An instance of the file utility class to load, save and delete tasks */
    private FileUtility fileUtility = new FileUtility();

    public TaskService() {
        this.loadTasks();
    }

    /**
     * Load tasks from the tasklist.txt (located at this.taskListFilePath), and calls a helper method, `parseTaskList` to parse each line to save it into this.tasks
     */
    public void loadTasks() {
        ArrayList<String> fileData = new ArrayList<>();

        try {
            fileData = this.fileUtility.loadFile(this.tasklistFilePath);
        }
        catch (FileNotFoundException e) {
            System.out.println("Error reading file.. Unable to load tasks from " + this.tasklistFilePath);
        }

        for (String line : fileData) {
            this.parseTaskList(line);
        }
    }

    /**
     * Saves the Task item in tasklist.txt (located at this.taskListFilePath)
     *
     * @param task to be saved
     * @throws IOException
     */
    private void saveTask(Task task) throws IOException {
        this.fileUtility.writeToFile(this.tasklistFilePath, task.toTaskListStringFormat());
    }

    /**
     * Deletes the Task item from tasklist.txt (located at this.taskListFilePath)
     *
     * @param task to be deleted
     * @throws IOException
     */
    private void removeTask(Task task) throws IOException {
        this.fileUtility.removeLineFromFile(this.tasklistFilePath, task.toTaskListStringFormat());
    }

    /**
     * Parses a line of the tasklist and adds the task to this.tasks
     *
     * @param line of the tasklist
     */
    private void parseTaskList(String line) {
        String[] task = line.split(this.taskListSep);
        Task newTask = null;

        if (task.length <= 1) {
            return;
        }

        Boolean isCompleted = task[1].equals("1");

        if (task[0].equals(Todo.typeIcon)) {
            newTask = new Todo(task[2], isCompleted);
        }
        else if (task[0].equals(Deadline.typeIcon)) {
            newTask = new Deadline(task[2], isCompleted, task[3]);
        }
        else if (task[0].equals(Event.typeIcon)) {
            newTask = new Event(task[2], isCompleted, task[3], task[4]);
        }
        else {
            // TODO: Throw invalid task Oak.type exception
            System.out.println("Invalid task detected");
        }

        this.tasks.add(newTask);
    }


    /**
     * Add todo task to this.tasks, saves it and returns a string updating the status of the operation
     *
     * @param taskName the task name
     * @return the string updating the status of the operation
     * @throws IOException the io exception
     */
    public String addTodo(String taskName) throws IOException {
        Todo newTodo = new Todo(taskName);

        this.tasks.add(newTodo);
        this.saveTask(newTodo);

        return String.format("Added new Todo: %s", taskName);
    }

    /**
     * Add deadline task to this.tasks, saves it and returns a string updating the status of the operation
     *
     * @param taskName   the task name
     * @param byDateTime the by date time
     * @return the string updating the status of the operation
     * @throws IOException the io exception
     */
    public String addDeadline(String taskName, String byDateTime) throws IOException {
        Deadline newDeadline = new Deadline(taskName, byDateTime);

        this.tasks.add(newDeadline);
        this.saveTask(newDeadline);

        return String.format("Added new Deadline: %s with Due Date: %s", taskName, byDateTime);
    }

    /**
     * Add event task to this.tasks, saves it and returns a string updating the status of the operation
     *
     * @param taskName     the task name
     * @param fromDateTime the from date time
     * @param toDateTime   the to date time
     * @return the string updating the status of the operation
     * @throws IOException the io exception
     */
    public String addEvent(String taskName, String fromDateTime, String toDateTime) throws IOException {
        Event newEvent = new Event(taskName, fromDateTime, toDateTime);

        this.tasks.add(newEvent);
        this.saveTask(newEvent);

        return String.format("Added new Event: %s occurring from %s to %s", taskName, fromDateTime, toDateTime);
    }

    /**
     * Delete task from this.tasks and tasklist.txt (located at this.taskListFilePath)
     *
     * @param taskId the task id
     * @return the string updating the status of the operation
     * @throws IOException the io exception
     */
    public String deleteTask(int taskId) throws IOException {
        Task removedTask = this.tasks.remove(taskId);
        this.removeTask(removedTask);

        return String.format("Are you giving up? Or is this task no longer needed?\nHmmm.. I've deleted Task %s for you for now.\nBut, I'll be watching you.", taskId);
    }

    /**
     * Mark task completed
     *
     * @param taskId the task id
     * @return the string
     */
    public String markTaskCompleted(int taskId) throws IOException {
        // TODO: Exception handling for if task does not exist
        // TODO: Fix, should also update tasklist.txt
        String originalTaskString = this.tasks.get(taskId).toTaskListStringFormat();
        this.tasks.get(taskId).markTaskCompleted();
        this.fileUtility.updateFile(this.tasklistFilePath, originalTaskString, this.tasks.get(taskId).toTaskListStringFormat());

        return "Ok! I've marked Task " + (taskId + 1) + " as completed!";
    }

    /**
     * Mark task uncompleted
     *
     * @param taskId the task id
     * @return the string
     */
    public String markTaskUncompleted(int taskId) throws IOException {
        // TODO: Exception handling for if task does not exist
        String originalTaskString = this.tasks.get(taskId).toTaskListStringFormat();
        this.tasks.get(taskId).markTaskNotCompleted();
        this.fileUtility.updateFile(this.tasklistFilePath, originalTaskString, this.tasks.get(taskId).toTaskListStringFormat());

        return "Hmmm, were you teasing me?\n" +
                "Well, I've marked Task " + (taskId + 1) +  " as uncompleted,\n" +
                "But don't do this again, you hear me?";
    }

    /**
     * Gets all tasks.
     *
     * @return All the tasks, formatted in a string and seperated by '\n'
     */
    public String getAllTasks() {
        StringBuilder returnVal = new StringBuilder();

        for (int i = 0; i < this.tasks.size(); i++) {
            returnVal.append(String.format("%d. %s", i + 1, this.tasks.get(i)));

            // Only add new line if its not the last task
            if (i < this.tasks.size() - 1) {
                returnVal.append("\n");
            }
        }

        return returnVal.toString();
    }

    public String findTasks(String matchingValue) {
        StringBuilder returnVal = new StringBuilder();

        for (int i = 0; i < this.tasks.size(); i++) {
            if (this.tasks.get(i).name.contains(matchingValue)) {
                returnVal.append(String.format("%d. %s", i + 1, this.tasks.get(i)));

                // Only add new line if its not the last task
                if (i < this.tasks.size() - 1) {
                    returnVal.append("\n");
                }
            }
        }

        return returnVal.toString();
    }
}

import java.io.IOException;

public class Todo extends Task {

    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

    /**
     * Function to handle a To-do Task. If it's inputs are valid, create a To-do Task.
     * Otherwise, print an error message in the console.
     * @param userInput a valid user input for a To-do Task.
     */
    public static void handleTodoTask(String userInput) throws EmptyDescriptionException, IOException {
        // use \u2639 or U+2639 to insert the sad face icon

        String taskDescription = userInput.trim().replaceFirst("todo", "").trim();
        if (taskDescription.isEmpty()) {
            throw new EmptyDescriptionException("The description of a todo cannot be empty.");
        } else {
            Todo todoTask = new Todo(taskDescription);
            Duke.saveTask(todoTask, true);
            Duke.taskList.add(todoTask); //Todo <: Task

            //Print details in the console
            System.out.println(Duke.HORIZONTAL_LINE);
            System.out.println("     Got it. I've added this task:");
            System.out.printf("       %s\n", todoTask.toString());
            System.out.printf("     Now you have %d tasks in the list.\n", Duke.taskList.size());
            System.out.println(Duke.HORIZONTAL_LINE);
        }
    }
}

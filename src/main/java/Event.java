import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected LocalDate startTime;
    protected LocalDate endTime;
    protected static String HORIZONTAL_LINE = "    ____________________________________________________________"; //60 underscores.

    public Event(String description, LocalDate startTime, LocalDate endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected static void printHorizontalLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    @Override
    public String toString() {
//        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
        return String.format("[E]%s (from: %s to: %s)", super.toString(),
                getStartTime().format(DateTimeFormatter.ofPattern("MMM d yyy")),
                getEndTime().format(DateTimeFormatter.ofPattern("MMM d yyy")));
    }

    public LocalDate getStartTime() {
        return this.startTime;
    }

    public LocalDate getEndTime() {
        return this.endTime;
    }
    public String getSchedule() {
        return String.format("From %s to %s", getStartTime(), getEndTime());
    }
    /**
     * Function to handle an Event Task. If it's inputs are valid, create an Event Task.
     * Otherwise, print an error message in the console.
     * @param userInput a valid user input for an Event Task.
     */
    protected static void handleEventTask(String userInput) throws IOException {
        String[] details = userInput.split("/from | /to");
        //details[0] contains "deadline" plus task description, need to erase "deadline". details[1] contains String deadline timing
        if (details.length == 3) {
            String taskDescription = details[0].trim().replaceFirst("event", "").trim();
            String startTime = details[1].trim();
            String endTime = details[2].trim();

            //Check if input date is valid.
            try {
                if (TaskList.isValidDate(startTime)) {
                    Event eventTask = new Event(taskDescription,
                            LocalDate.parse(startTime),
                            LocalDate.parse(endTime));

                    Storage.saveTask(eventTask, true);
                    Storage.taskList.add(eventTask); //Deadline <: Task

                    //Print details in the console
                    printHorizontalLine();
                    System.out.println("     Got it. I've added this task:");
                    System.out.printf("       %s\n", eventTask.toString());
                    System.out.printf("     Now you have %d tasks in the list.\n", Storage.taskList.size());
                    printHorizontalLine();
                } else {
                    throw new InvalidDateException();
                }
            } catch (InvalidDateException e) {
                System.out.println(e.toString());
            }

        } else {
            printHorizontalLine();
            System.out.println("     Invalid Event Task input.\n"
                    + "     Please input in the following format:\n"
                    + "     event <Task Description> /from <start time> /to <end time>");
            printHorizontalLine();
        }
    }
}

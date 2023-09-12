package max;

import java.time.format.DateTimeParseException;

import max.commands.Command;
import max.exception.MaxException;
import max.parser.Parser;
import max.storage.Storage;
import max.tasks.TaskList;
import max.ui.Ui;

/**
 * Initialises the application.
 */
public class Max  {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initialises Storage, TaskList and Ui.
     * Loads storage from given file location.
     *
     * @param filePath file location of stored task list
     */
    public Max(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (MaxException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public Max() {
        this("./data/max.txt");
    }

    /**
     * Reads user commands and executes until exited.
     */
    public void run() {
        ui.showGreeting();
        boolean isExit = false;
        while (!isExit) {
            try {
                Parser parser = new Parser();
                String fullCommand = ui.readCommand(); // return the first word of input
                Command c = parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (MaxException e) {
                ui.showError(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.showError("Please use yyyy-mm-dd format!");
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * You should have your own function to generate a response to user input.
     * Replace this stub with your completed method.
     */
    public String getResponse(String input) {
        try {
            Parser parser = new Parser();
            Command c = parser.parse(input);
            return c.execute(tasks, ui, storage);
        }  catch (MaxException e) {
            return ui.showError(e.getMessage());
        } catch (DateTimeParseException e) {
            return ui.showError("Please use yyyy-mm-dd format!");
//            return "error";
        } catch (IndexOutOfBoundsException e) {
            return ui.showError("Please use proper formats!");
        }
    }

}

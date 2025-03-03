package max.parser;

import static java.lang.Integer.parseInt;

import java.time.LocalDate;

import max.commands.AddCommand;
import max.commands.Command;
import max.commands.DeleteCommand;
import max.commands.ExitCommand;
import max.commands.FindCommand;
import max.commands.HelpCommand;
import max.commands.ListCommand;
import max.commands.MarkCommand;
import max.commands.UnmarkCommand;
import max.exception.EmptyArgumentException;
import max.exception.InvalidArgumentException;
import max.exception.InvalidFormatException;
import max.exception.MaxException;
import max.tasks.Deadline;
import max.tasks.Event;
import max.tasks.Todo;
/**
 * Parses user input to return given command.
 */
public class Parser {
    private String input;

    /**
     * Initialises parser with given user input.
     *
     * @param input user input
     */
    public Parser(String input) {
        this.input = input;
    }

    /**
     * Public constructor for parser.
     */
    public Parser() {

    }

    /**
     * Parses user input. If the command is invalid, throws exception.
     *
     * @param fullCommand User input read from scanner.
     * @return Command Command given by user.
     * @throws MaxException If command is invalid.
     */
    public Command parse(String fullCommand) throws MaxException {
        assert !fullCommand.equals("") : "Command should not be empty";
        String com = fullCommand.split(" ")[0];

        switch (com) {
        case AddCommand.COMMAND_WORD_DEADLINE:
            return handleDeadline(fullCommand);
        case AddCommand.COMMAND_WORD_EVENT:
            return handleEvent(fullCommand);
        case AddCommand.COMMAND_WORD_TODO:
            return handleTodo(fullCommand);
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        case MarkCommand.COMMAND_WORD:
            return handleMark(fullCommand);
        case UnmarkCommand.COMMAND_WORD:
            return handleUnmark(fullCommand);
        case DeleteCommand.COMMAND_WORD:
            return handleDelete(fullCommand);
        case FindCommand.COMMAND_WORD:
            return handleFind(fullCommand);
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        default:
            throw new MaxException("Invalid command sir.");
        }
    }

    /**
     * Parses a todo command. If input is invalid, throws exception.
     *
     * @param fullCommand User input read from scanner.
     * @return AddCommand Command
     * @throws MaxException If description is empty.
     */
    public Command handleTodo(String fullCommand) throws MaxException {
        // Error checking: empty fields.
        // 6 is the length of the string "todo ".
        // If fullCommand is shorter than 6, then no argument is present.
        if (fullCommand.length() < 6) {
            throw new EmptyArgumentException("Watch out -- todo description cannot be empty.");
        }

        String description = fullCommand.substring(5).trim();
        assert !description.equals("") : "Task description should not be empty";
        return new AddCommand(new Todo(description));
    }
    /**
     * Parses an event command. If input is invalid, throws exception.
     *
     * @param fullCommand User input read from scanner.
     * @return AddCommand Command
     * @throws MaxException If command incomplete.
     */
    public Command handleEvent(String fullCommand) throws MaxException {
        int fromIndex = fullCommand.indexOf("/from");
        int toIndex = fullCommand.indexOf("/to");

        // Error checking: no /from or /to tag
        if (fromIndex == -1 || toIndex == -1) {
            throw new InvalidFormatException("Hey! Event must contain '/from' and '/to' tags.");
        }

        String item = fullCommand.substring(5, fromIndex - 1).trim();
        String from = fullCommand.substring(fromIndex + 5, toIndex - 1).trim();
        String to = fullCommand.substring(toIndex + 3).trim();

        // Error checking: empty fields
        if (item.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new EmptyArgumentException("Oh no! Event item, 'from' date, or 'to' date cannot be empty.");
        }

        assert !item.equals("") : "Event item should not be empty";

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        return new AddCommand(new Event(item, fromDate, toDate));
    }
    /**
     * Parses a deadline command. If input is invalid, throws exception.
     *
     * @param fullCommand User input read from scanner.
     * @return AddCommand Command
     * @throws MaxException If description is empty.
     */
    public Command handleDeadline(String fullCommand) throws MaxException {
        int byIndex = fullCommand.indexOf("/by");

        // Error checking: no /by tag
        if (byIndex == -1) {
            throw new InvalidFormatException("Try again... deadline must include a '/by' tag!");
        }

        String item = fullCommand.substring(8, byIndex - 1).trim();
        String by = fullCommand.substring(byIndex + 3).trim();

        // Error checking: empty fields
        if (item.isEmpty() || by.isEmpty()) {
            throw new EmptyArgumentException("Oops... Deadline item or 'by' date cannot be empty.");
        }

        assert !item.equals("") : "Deadline item should not be empty";

        LocalDate byDate = LocalDate.parse(by);

        return new AddCommand(new Deadline(item, byDate));
    }
    /**
     * Parses a delete command.
     *
     * @param fullCommand User input read from scanner.
     * @return DeleteCommand Command
     */
    public Command handleDelete(String fullCommand) throws MaxException {
        try {
            int deleteNumber = parseInt(fullCommand.substring(7));
            assert deleteNumber > 0 : "Delete number should be positive integer";
            return new DeleteCommand(deleteNumber);
        } catch (StringIndexOutOfBoundsException e) {
            throw new EmptyArgumentException("Please supply some arguments!");
        }
    }
    /**
     * Parses a mark command. If input is invalid, throws exception.
     *
     * @param fullCommand User input read from scanner.
     * @return MarkCommand Command
     * @throws MaxException If mark number is invalid.
     */
    public Command handleMark(String fullCommand) throws MaxException {
        int markNumber;

        try {
            markNumber = parseInt(fullCommand.substring(5));
        } catch (StringIndexOutOfBoundsException e) {
            throw new EmptyArgumentException("Please supply some arguments!");
        }

        // Error checking: negative integer
        if (markNumber <= 0) {
            throw new InvalidArgumentException("Make sure you enter a valid integer!");
        }

        assert markNumber > 0 : "Mark number should be positive integer";

        return new MarkCommand(markNumber);
    }
    /**
     * Parses an unmark command. If input is invalid, throws exception.
     *
     * @param fullCommand User input read from scanner.
     * @return UnmarkCommand Command
     * @throws MaxException If unmark number is invalid.
     */
    public Command handleUnmark(String fullCommand) throws MaxException {
        int unmarkNumber;

        try {
            unmarkNumber = parseInt(fullCommand.substring(7));
        } catch (StringIndexOutOfBoundsException e) {
            throw new EmptyArgumentException("Please supply some arguments!");
        }

        // Error checking: negative integer
        if (unmarkNumber <= 0) {
            throw new InvalidArgumentException("Make sure you enter a valid integer!");
        }

        assert unmarkNumber > 0 : "Unmark number should be positive integer";

        return new UnmarkCommand(unmarkNumber);
    }

    /**
     * Parses user input for phrase to be found.
     *
     * @param fullCommand User input
     * @return Find command
     */
    public Command handleFind(String fullCommand) throws MaxException {
        try {
            String[] items = fullCommand.substring(5).split(" ");
            return new FindCommand(items);
        } catch (StringIndexOutOfBoundsException e) {
            throw new EmptyArgumentException("Please supply some arguments!");
        }
    }
}


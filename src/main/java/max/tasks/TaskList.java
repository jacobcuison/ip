package max.tasks;

import java.util.ArrayList;

import max.exception.InvalidArgumentException;
import max.exception.MaxException;
/**
 * Manages all tasks.
 */
public class TaskList {
    private ArrayList<Task> myList;
    private int numOfItems;

    /**
     * Initialises a new task list.
     */
    public TaskList() {
        myList = new ArrayList<>();
        numOfItems = 0;
    }

    /**
     * Initialises an existing task list from memory.
     *
     * @param myList Existing list of tasks
     */
    public TaskList(ArrayList<Task> myList) {
        this.myList = myList;
        numOfItems = myList.size();
    }

    /**
     * Adds task to list.
     *
     * @param task task to be added
     */
    public void add(Task task) {
        myList.add(task);
    }
    public ArrayList<Task> getList() {
        return myList;
    }

    /**
     * Deletes task from list.
     * @param taskNumber Index of task number to be deleted
     * @throws MaxException If invalid task number given
     */
    public void delete(int taskNumber) throws MaxException {
        if (taskNumber > myList.size() || taskNumber < 0) {
            throw new InvalidArgumentException("Seems like that number is out of range. Check again!");
        }
        Task toDelete = myList.get(taskNumber - 1);
        myList.remove(toDelete);
        numOfItems--;
    }

    /**
     * Marks task as done.
     *
     * @param taskNumber Task to be marked
     * @throws MaxException If invalid task number given
     */
    public void mark(int taskNumber) throws MaxException {
        if (taskNumber > myList.size() || taskNumber < 0) {
            throw new InvalidArgumentException("Seems like that number is out of range. Check again!");
        }
        myList.get(taskNumber - 1).mark();
    }
    /**
     * Unmarks task.
     *
     * @param taskNumber Task to be unmarked
     * @throws MaxException If invalid task number given
     */
    public void unmark(int taskNumber) throws MaxException {
        if (taskNumber > myList.size() || taskNumber < 0) {
            throw new InvalidArgumentException("Seems like that number is out of range. Check again!");
        }
        myList.get(taskNumber - 1).unmark();
    }
}

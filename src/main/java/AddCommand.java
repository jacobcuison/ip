public class AddCommand extends Command {
    static final String COMMAND_WORD_DEADLINE = "deadline";
    static final String COMMAND_WORD_EVENT = "event";
    static final String COMMAND_WORD_TODO = "todo";
    private Task task;
    public AddCommand(Task task) {
        this.task = task;
    }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        tasks.add(task);
        storage.writeToFile(tasks);
        ui.showAdd(task, tasks.getList().size());
    }
    @Override
    public boolean isExit() {
        return false;
    }
}

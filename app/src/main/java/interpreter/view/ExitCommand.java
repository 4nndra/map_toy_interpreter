package interpreter.view;

// Menu command that terminates the interpreter.
public class ExitCommand extends Command {

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    // Executes this statement and returns the updated program state.
    public void execute() {
        System.exit(0);
    }
}

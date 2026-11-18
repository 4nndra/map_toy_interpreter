package interpreter.view;

// Base menu command; each concrete command provides its own key and action.
public abstract class Command {
    private final String key;
    private final String description;

    protected Command(String key, String description) {
        this.key = key;
        this.description = description;
    }

    // Executes this statement and returns the updated program state.
    public abstract void execute();

    // Returns the menu key identifying this command.
    public String getKey() {
        return key;
    }

    // Returns the human-readable command description.
    public String getDescription() {
        return description;
    }
}

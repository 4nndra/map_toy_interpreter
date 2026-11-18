package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;

// Menu command that executes one example program through its controller.
public class RunExampleCommand extends Command {
    private final Controller controller;
    private boolean executed = false;

    public RunExampleCommand(
            String key,
            String description,
            Controller controller
    ) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    // Executes this statement and returns the updated program state.
    public void execute() {
        if (executed) {
            System.out.println("This example has already been executed.");
            return;
        }

        executed = true;

        try {
            controller.allStep();
        } catch (MyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

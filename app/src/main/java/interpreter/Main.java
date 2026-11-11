package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.repository.Repository;
import interpreter.view.Command;
import interpreter.view.ExitCommand;
import interpreter.view.Examples;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<PrgState> programs = List.of(
                Examples.example1(),
                Examples.example2(),
                Examples.example3(),
                Examples.example4(),
                Examples.example5()
        );

        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0", "exit"));

        for (int i = 0; i < programs.size(); i++) {
            PrgState state = programs.get(i);

            Repository repository = new Repository(
                    state,
                    "log" + (i + 1) + ".txt"
            );

            Controller controller = new Controller(repository);

            menu.addCommand(
                    new RunExampleCommand(
                            String.valueOf(i + 1),
                            state.getOriginalProgram().toString(),
                            controller
                    )
            );
        }

        menu.show();
    }
}

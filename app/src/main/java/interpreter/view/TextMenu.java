package interpreter.view;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void addCommand(Command command) {
        commands.put(command.getKey(), command);
    }

    private void printMenu() {
        System.out.println();
        System.out.println("          A3 - TOY INTERPRETER");
        System.out.println("------------------------------------------");
        System.out.println(" Select a program to execute:\n");

        for (Command c : commands.values()) {
            if (c.getKey().equals("0")) {
                System.out.println(" [0]  Exit");
            } else {
                String description = switch (c.getKey()) {
                    case "1" -> "Basic variable declaration and print";
                    case "2" -> "Arithmetic expressions";
                    case "3" -> "Boolean expressions and conditions";
                    case "4" -> "File operations";
                    case "5" -> "Relational expressions";
                    default -> c.getDescription();
                };

                System.out.printf(" [%s]  %s%n", c.getKey(), description);
            }
        }

        System.out.println("------------------------------------------");
        System.out.print(" Your choice: ");
    }

    public void show() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printMenu();

            Command command = commands.get(scanner.nextLine().trim());

            if (command == null) {
                System.out.println("Invalid option");
                continue;
            }

            command.execute();
        }
    }
}

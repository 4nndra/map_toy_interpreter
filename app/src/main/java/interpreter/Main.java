package interpreter;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.repository.Repository;
import interpreter.view.Examples;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Toy Interpreter ===");
            System.out.println("1. Run example 1 (prints 2)");
            System.out.println("2. Run example 2 (prints 18)");
            System.out.println("3. Run example 3 (prints 2)");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> runExample(Examples.example1());
                    case "2" -> runExample(Examples.example2());
                    case "3" -> runExample(Examples.example3());
                    case "0" -> {
                        running = false;
                        System.out.println("Goodbye!");
                    }
                    default -> System.out.println("Invalid option. Please choose 0, 1, 2, or 3.");
                }
            } catch (MyException e) {
                System.out.println("Interpreter error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void runExample(PrgState state) throws MyException {
        Repository repository = new Repository(state);
        Controller controller = new Controller(repository);

        controller.allStep();

        PrgState result = repository.getCrtPrg();

        System.out.println("Output: " + result.getOut());
        System.out.println("Final state:");
        System.out.println(result);
    }
}

package interpreter;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.repository.Repository;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Toy Interpreter ===");
            System.out.println("1. Run example 1 (prints 2)");
            System.out.println("2. Run example 2 (prints 18)");
            System.out.println("3. Run example 3 (prints 2)");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            if (choice.equals("0")) {
                System.out.println("Goodbye!");
                break;
            }

            PrgState program;

            switch (choice) {
                case "1" -> program = Examples.example1();
                case "2" -> program = Examples.example2();
                case "3" -> program = Examples.example3();
                default -> {
                    System.out.println("Invalid option.");
                    continue;
                }
            }

            Controller controller = new Controller(new Repository(program));

            try {
                PrgState result = controller.allStep();
                System.out.println("Output: " + result.getOut());
                System.out.println("Final state:");
                System.out.println(result);
            } catch (MyException e) {
                System.out.println("Execution error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}

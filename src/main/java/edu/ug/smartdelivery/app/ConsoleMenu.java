package edu.ug.smartdelivery.app;

import java.util.Scanner;

public class ConsoleMenu {
    private final Scanner scanner;

    public ConsoleMenu() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            printMainMenu();
            choice = readChoice();
            handleChoice(choice);
        } while (choice != 0);
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("==============================================");
        System.out.println(" SMART FOOD DELIVERY OPERATIONS OPTIMIZER");
        System.out.println("==============================================");
        System.out.println("1. Manage Locations");
        System.out.println("2. Manage Roads");
        System.out.println("3. Manage Restaurants");
        System.out.println("4. Manage Riders");
        System.out.println("5. Manage Food Orders");
        System.out.println("6. Process Orders Using FIFO");
        System.out.println("7. Process Orders Using Priority");
        System.out.println("8. Search Records");
        System.out.println("9. Sort Orders");
        System.out.println("10. Find Shortest Delivery Route");
        System.out.println("11. Display Reachable Locations");
        System.out.println("12. Generate Minimum Connection Network");
        System.out.println("13. Assign Riders Using Greedy Strategy");
        System.out.println("14. Select Orders Using Dynamic Programming");
        System.out.println("15. Run Algorithm Experiments");
        System.out.println("16. View Performance Results");
        System.out.println("17. View Audit and Undo History");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private int readChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private void handleChoice(int choice) {
        if (choice == 0) {
            System.out.println("Exiting SMART FOOD DELIVERY.");
            return;
        }
        System.out.println("Option " + choice + " is ready for implementation in the next milestone.");
    }
}

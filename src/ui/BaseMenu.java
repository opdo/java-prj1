package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BaseMenu {
    protected List<String> listMenu = new ArrayList<>();
    protected String title;

    public void printMenu() {
        System.out.println();
        System.out.println(title);
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < listMenu.size(); i++) {
            System.out.println((i + 1) + ". " + listMenu.get(i));
        }
        System.out.println("-----------------------------------------------------");

        // Wait and handle user input menu
        handleUserInputMenu();
    }

    public int getUserInputMenu() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please select a number for the menu option");
            return scanner.nextInt();
        }
        catch (Exception e) {
            System.out.println("Error: please input an integer!");
            return getUserInputMenu();
        }
    }

    public void handleUserInputMenu() {
        // Will be overwrite in child class
        printMenu();
    }

    public Boolean handleUserYesNoQuestion(String question) {
        Boolean flag = null;
        do {
            System.out.println(question + "\n(Y or N for yes or no)");

            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();

            switch (userInput.toUpperCase()) {
                case "Y":
                    flag = true;
                    break;
                case "N":
                    flag = false;
                    break;
                default:
                    System.out.println("Wrong input, you should input Y or N for yes or no");
            }
        } while (flag == null);

        return flag;
    }
}

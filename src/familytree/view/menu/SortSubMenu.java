package familytree.view.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SortSubMenu {
    private Scanner scanner;
    private ConsoleUI mainMenu;

    public SortSubMenu(ConsoleUI mainMenu) {
        this.mainMenu = mainMenu;
        this.scanner = new Scanner(System.in);
    }

    public void show() {
        while (true) {
            printOptions();
            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        mainMenu.sortByBirthDate();
                        return;
                    case 2:
                        mainMenu.sortByFirstName();
                        return;
                    case 3:
                        mainMenu.sortByChildrenCount();
                        return;
                    case 4:
                        mainMenu.start();
                        return;
                    default:
                        mainMenu.printAnswer("Неверный выбор.");
                        break;
                }
            } catch (InputMismatchException e) {
                mainMenu.printAnswer("Неверный выбор. Введите корректное число.");
                scanner.nextLine();
            }
        }
    }

    private void printOptions() {
        mainMenu.printAnswer("Получить отсортированный список людей:");
        mainMenu.printAnswer("1. Отсортировать список по возрасту");
        mainMenu.printAnswer("2. Отсортировать список по имени");
        mainMenu.printAnswer("3. Отсортировать список по количеству детей");
        mainMenu.printAnswer("4. Назад");
    }
}

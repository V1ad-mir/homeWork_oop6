package familytree.view.commands;


import familytree.view.menu.ConsoleUI;

public class SortByFirstName implements Command {
    private final ConsoleUI consoleUI;

    public SortByFirstName(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public String getDescription() {
        return "Отсортировать список по имени";
    }

    @Override
    public void execute() {
        consoleUI.sortByFirstName();
    }

    @Override
    public String toString() {
        return "Command: " + getDescription();
    }

}
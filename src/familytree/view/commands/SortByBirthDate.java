package familytree.view.commands;


import familytree.view.menu.ConsoleUI;

public class SortByBirthDate implements Command {
    private final ConsoleUI consoleUI;

    public SortByBirthDate(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public String getDescription() {
        return "Отсортировать список по возрасту";
    }

    @Override
    public void execute() {
        consoleUI.sortByBirthDate();
    }

    @Override
    public String toString() {
        return "Command: " + getDescription();
    }

}
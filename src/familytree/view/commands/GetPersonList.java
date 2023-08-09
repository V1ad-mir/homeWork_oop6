package familytree.view.commands;

import familytree.view.menu.ConsoleUI;

public class GetPersonList implements Command {
    private final ConsoleUI consoleUI;

    public GetPersonList(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public String getDescription() {
        return "Получить список всех людей семейного древа";
    }

    @Override
    public void execute() {
        consoleUI.getPersonList();
    }

    @Override
    public String toString() {
        return "Command: " + getDescription();
    }

}
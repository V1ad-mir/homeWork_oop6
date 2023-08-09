package familytree.view.commands;

import familytree.view.menu.ConsoleUI;

public class GetChildren implements Command {
    private final ConsoleUI consoleUI;

    public GetChildren(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public String getDescription() {
        return "Получить список детей определенного человека";
    }

    @Override
    public void execute() {
        consoleUI.getChildren();
    }

    @Override
    public String toString() {
        return "Command: " + getDescription();
    }

}

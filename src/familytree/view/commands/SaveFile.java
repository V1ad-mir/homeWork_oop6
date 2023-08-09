package familytree.view.commands;


import familytree.view.menu.ConsoleUI;

public class SaveFile implements Command {
    private final ConsoleUI consoleUI;

    public SaveFile(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    @Override
    public String getDescription() {
        return "Сохранить данные в файл";
    }

    @Override
    public void execute() {
        consoleUI.saveFile();
    }

    @Override
    public String toString() {
        return "Command: " + getDescription();
    }

}
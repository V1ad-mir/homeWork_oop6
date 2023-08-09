package familytree.view.commands;

import familytree.view.menu.ConsoleUI;

public class AddPerson implements Command {

    private ConsoleUI mainMenu;


    public AddPerson(ConsoleUI mainMenu) {
        this.mainMenu = mainMenu;

    }

    @Override
    public String getDescription() {
        return "Добавить человека в дерево";
    }

    @Override
    public void execute() {
        mainMenu.addChildToPerson();
    }

    @Override
    public String toString() {
        return "Command: " + getDescription();
    }

}
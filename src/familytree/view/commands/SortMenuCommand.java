package familytree.view.commands;


import familytree.view.menu.ConsoleUI;
import familytree.view.menu.SortSubMenu;

public class SortMenuCommand implements Command {
    private ConsoleUI mainMenu;
    private SortSubMenu subMenu;

    public SortMenuCommand(ConsoleUI mainMenu, SortSubMenu subMenu) {
        this.mainMenu = mainMenu;
        this.subMenu = subMenu;
    }

    @Override
    public String getDescription() {
        return "Получить отсортированный список людей";
    }

    @Override
    public void execute() {
        subMenu.show();
    }
}

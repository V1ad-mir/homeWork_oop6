package familytree.service.validator;

import familytree.model.Person;
import familytree.view.menu.ConsoleUI;

import java.util.Optional;

public class FamilyTreeValidator {
    private final ConsoleUI consoleUI;

    public FamilyTreeValidator(ConsoleUI consoleUI) {
        this.consoleUI = consoleUI;
    }

    public boolean personExistsInFamilyTree(String firstName, String lastName) {
        Optional<Person> person = searchPersonInFamilyTree(firstName, lastName);
        return person.isPresent();
    }

    private Optional<Person> searchPersonInFamilyTree(String firstName, String lastName) {
        if (consoleUI.getCurrentFamilyTree() == null || consoleUI.getCurrentFamilyTree().getRoot() == null) {
            return Optional.empty();
        }

        for (Person person : consoleUI.getCurrentFamilyTree()) {
            if (person.getFirstName().equalsIgnoreCase(firstName)
                    && person.getLastName().equalsIgnoreCase(lastName)) {
                return Optional.of(person);
            }
        }
        return Optional.empty();
    }

}

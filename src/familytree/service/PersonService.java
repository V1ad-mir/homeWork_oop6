package familytree.service;

import familytree.model.Person;
import familytree.model.comparator.AgeComparator;
import familytree.model.comparator.ChildrenCountComparator;
import familytree.model.comparator.NameComparator;
import familytree.model.familyTree.FamilyTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PersonService implements PersonManager {
    private final FamilyTree<Person> familyTree;

    public PersonService(FamilyTree<Person> familyTree) {
        this.familyTree = familyTree;
    }

    @Override
    public void setRootPerson(Person rootPerson) {
        familyTree.setRoot(rootPerson);
    }

    public void changeRootNode(Person newRoot) {
        familyTree.changeRoot(newRoot);
    }

    @Override
    public void addChildToParent(Person child, Person parent) {
        parent.getChildren().add(child);
        child.getParents().add(parent);
    }

    @Override
    public boolean deletePerson(Person person) {
        if (!person.getChildren().isEmpty()) {
            return false;
        }
        person.getParents().forEach(parent -> parent.getChildren().remove(person));
        return true;
    }

    @Override
    public Optional<Person> findPersonByName(String firstName) {
        return getAllPersons().stream()
                .filter(person -> person.getFirstName().equals(firstName))
                .findFirst();
    }

    @Override
    public Person findPersonByNameAndSurname(String firstName, String lastName) {
        return getAllPersons().stream()
                .filter(person -> person.getFirstName().equals(firstName) && person.getLastName().equals(lastName))
                .findFirst().orElse(null);
    }


    @Override
    public Optional<Person> findPersonById(int id) {
        return getAllPersons().stream()
                .filter(person -> person.getId() == id)
                .findFirst();
    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();

        if (familyTree != null) {
            familyTree.iterator().forEachRemaining(persons::add);
        }

        return persons;
    }

    @Override
    public List<Person> getChildrenOfPerson(Optional<Person> parent) {
        if (parent.isPresent()) {
            return parent.get().getChildren();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Person> getParentsOfPerson(Person person) {
        return person.getParents();
    }

    @Override
    public List<Person> getPersonsSortedByName() {
        return getAllPersons().stream()
                .sorted(new NameComparator<>())
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getPersonsSortedByChildrenCount() {
        return getAllPersons().stream()
                .sorted(new ChildrenCountComparator<>().reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Person> getPersonsSortedByAge() {
        return getAllPersons().stream()
                .sorted(new AgeComparator<>())
                .collect(Collectors.toList());
    }

}

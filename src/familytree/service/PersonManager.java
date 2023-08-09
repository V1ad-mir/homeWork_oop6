package familytree.service;

import familytree.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonManager {
    void setRootPerson(Person rootPerson);

    void changeRootNode(Person newRoot);

    void addChildToParent(Person child, Person parent);

    boolean deletePerson(Person person);

    Optional<Person> findPersonByName(String firstName);

    Person findPersonByNameAndSurname(String firstName, String lastName);

    Optional<Person> findPersonById(int id);

    List<Person> getAllPersons();

    List<Person> getParentsOfPerson(Person person);

    List<Person> getPersonsSortedByName();

    List<Person> getPersonsSortedByChildrenCount();

    List<Person> getPersonsSortedByAge();

    List<Person> getChildrenOfPerson(Optional<Person> parent);
}



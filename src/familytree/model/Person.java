package familytree.model;

import familytree.model.familyTree.TreeNode;
import familytree.model.comparator.Ageable;
import familytree.model.comparator.Childbearing;
import familytree.model.comparator.Nameable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Person implements Serializable, TreeNode<Person>, Ageable, Nameable, Childbearing {
    private static int ID_COUNTER = 0;
    int id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate dayOfDeath;
    private Gender gender;
    private Person mother;
    private Person father;
    private List<Person> children;

    private Person(Builder builder) {
        this.id = ++ID_COUNTER;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.birthday = builder.birthday;
        this.dayOfDeath = builder.dayOfDeath;
        this.gender = builder.gender;
        this.mother = builder.mother;
        this.father = builder.father;
        this.children = builder.children;
    }

    public static class Builder {
        private int id;
        private String firstName;
        private String lastName;
        private LocalDate birthday;
        private LocalDate dayOfDeath;
        private Gender gender;
        private Person mother;
        private Person father;
        private List<Person> children = new ArrayList<>();

        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder birthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder dayOfDeath(LocalDate dayOfDeath) {
            this.dayOfDeath = dayOfDeath;
            return this;
        }

        public Builder gender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public Builder mother(Person mother) {
            this.mother = mother;
            return this;
        }

        public Builder father(Person father) {
            this.father = father;
            return this;
        }

        public Builder children(List<Person> children) {
            this.children = children;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate dateBirthday) {
        this.birthday = dateBirthday;
    }

    public LocalDate getDayOfDeath() {
        return dayOfDeath;
    }

    public void setDayOfDeath(LocalDate dayOfDeath) {
        this.dayOfDeath = dayOfDeath;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setMother(Person mother) {
        this.mother = mother;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    @Override
    public List<Person> getParents() {
        List<Person> parents = new ArrayList<>();
        if (mother != null) {
            parents.add(mother);
        }
        if (father != null) {
            parents.add(father);
        }
        return parents;
    }

    public void addChild(Person child) {
        if (child != null) {
            children.add(child);
            child.setParent(this);
        }
    }

    @Override
    public Person getParent() {
        return gender == Gender.MALE ? father : mother;
    }

    @Override
    public void setParent(Person parent) {
        if (parent != null) {
            switch (parent.getGender()) {
                case MALE:
                    setFather(parent);
                    break;
                case FEMALE:
                    setMother(parent);
                    break;
                default:
                    throw new IllegalStateException("Гендер не определен.");
            }
        } else {
            setFather(null);
            setMother(null);
        }
    }

    @Override
    public void removeChild(Person child) {
        if (children.remove(child)) {
            child.setParent(null);
        }
    }

    public List<Person> getChildren() {
        return children;
    }


    public int getAge() {
        if (dayOfDeath == null) {
            return getPeriod(birthday, LocalDate.now());
        } else {
            return getPeriod(birthday, dayOfDeath);
        }
    }

    private int getPeriod(LocalDate birthday, LocalDate dayOfDeath) {
        Period diff = Period.between(birthday, dayOfDeath);
        return diff.getYears();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName)
                && Objects.equals(birthday, person.birthday)
                && Objects.equals(dayOfDeath, person.dayOfDeath)
                && gender == person.gender
                && Objects.equals(mother, person.mother)
                && Objects.equals(father, person.father);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthday, dayOfDeath, gender, mother, father);
    }

    @Override
    public String toString() {
        return "\n" + " Имя: " + firstName + '\'' +
                ", Фамилия: " + lastName + '\'' +
                ", Возраст: " + getAge() + '\'' +
                ", Дата рождения:" + (birthday != null ? getBirthday() : "Неизвестна") +
                ", Дата смерти:" + (dayOfDeath == null ? "Отсутствует" : dayOfDeath) +
                ", Пол:" + gender +
                ", Мать:" + (mother != null ? mother.getFirstName() : "Неизвестна") +
                ", Отец:" + (father != null ? father.getFirstName() : "Неизвестен") +
                ", Дети:" + (children.isEmpty() ? "Данные отсутствуют" : children.stream().map(Person::getFirstName)
                .collect(Collectors.joining(", ")));
    }

}
package familytree.view.menu;

import familytree.model.exceptions.SerializationException;
import familytree.model.familyTree.FamilyTree;
import familytree.model.Gender;
import familytree.model.Person;
import familytree.service.PersonManager;
import familytree.service.PersonService;
import familytree.model.storage.FileManager;
import familytree.model.storage.FileStorage;
import familytree.model.storage.SerializationStorage;
import familytree.service.validator.DateValidator;
import familytree.service.validator.FamilyTreeValidator;
import familytree.service.validator.GenderValidator;
import familytree.view.View;
import familytree.view.commands.*;

import java.time.LocalDate;
import java.util.*;

public class ConsoleUI implements View {
    private final Map<Integer, Command> commands = new HashMap<>();
    private final Scanner scanner;
    private boolean work;
    private FamilyTree<Person> currentFamilyTree;
    private PersonManager personManager;
    private final FamilyTreeValidator familyTreeValidator = new FamilyTreeValidator(this);
    private final FileManager fileManager;
    //  private Writable writable;

    public ConsoleUI() {
        showWelcomeMessage();

        currentFamilyTree = new FamilyTree<>();


        this.personManager = new PersonService(currentFamilyTree);
//        this.writable = new SerializationStorage();
        this.fileManager = new FileStorage(new SerializationStorage());
        // Инициализация команд
        commands.put(1, new AddPerson(this));
        commands.put(2, new GetChildren(this));
        commands.put(3, new GetPersonList(this));
        commands.put(4, new SortMenuCommand(this, new SortSubMenu(this)));
        commands.put(5, new LoadFile(this));
        commands.put(6, new SaveFile(this));
        commands.put(7, new Finish(this));

        scanner = new Scanner(System.in);
        work = true;

    }

    @Override
    public void start() {
        while (work) {
            printMenu();
            printAnswer("Выберите действие:");

            try {
                int choice = scanner.nextInt();
                Command command = commands.get(choice);
                if (command != null) {
                    command.execute();
                } else {
                    printAnswer("Команда не найдена.");
                }
            } catch (InputMismatchException e) {
                printAnswer("Неверный выбор. Введите корректное число.");
                scanner.nextLine();
            }
        }
    }

    public void addChildToPerson() {
        Person child = createPerson();
        if (child == null) {
            return;
        }

        if (currentFamilyTree.getRoot() == null) {
            currentFamilyTree.setRoot(child);
            printAnswer("Добавлен новый корневой элемент: " + child);
            return;
        }

        printAnswer("1. Добавить человека как ребенка к существующему человеку\n2. Добавить человека как родителя текущему корневому узлу.");
        int choice = scanner.nextInt();
        clearBuffer();

        switch (choice) {
            case 1:
                addAsChild(child);
                break;
            case 2:
                changeRootNode(child);
                break;
            default:
                printAnswer("Неверный выбор.");
                break;
        }
    }

    public Person createPerson() {
        String firstName = getInput("Введите имя:");
        String lastName = getInput("Введите фамилию:");

        if (familyTreeValidator.personExistsInFamilyTree(firstName, lastName)) {
            printAnswer("Такой человек уже существует в семейном древе!");
            return null;
        }

        printAnswer("Введите дату рождения в формате 2016-12-25:");
        LocalDate birthday = DateValidator.getValidDate();
        clearBuffer();

        printAnswer("Введите дату смерти в формате 2016-12-25 (если человек жив, нажмите 'Enter'):");
        LocalDate dayOfDeath = DateValidator.getValidDate();

        Gender gender = GenderValidator.getValidGender();

        return new Person.Builder(firstName, lastName)
                .birthday(birthday)
                .gender(gender)
                .dayOfDeath(dayOfDeath)
                .build();
    }

    public void addAsChild(Person child) {
        printAnswer("Введите имя родителя:");
        String parentFirstName = scanner.nextLine();
        printAnswer("Введите фамилию родителя:");
        String parentLastName = scanner.nextLine();

        Person parent = personManager.findPersonByNameAndSurname(parentFirstName, parentLastName);

        if (parent != null) {
            parent.addChild(child);
            printAnswer(child.getFirstName() + " успешно добавлен(а) к " + parent.getFirstName());
        } else {
            printAnswer("Родитель не найден в семейном древе.");
        }
    }

    public void changeRootNode(Person newRoot) {
        personManager.changeRootNode(newRoot);
        printAnswer("Корневой элемент был изменен на: " + newRoot.toString());
    }

    public void getChildren() {
        String firstName = getInput("Введите имя:");
        String lastName = getInput("Введите фамилию:");
        printChildren(firstName, lastName);
    }


    public void sortByFirstName() {
        List<Person> sortedByNamePersonList = personManager.getPersonsSortedByName();
        printAnswer(sortedByNamePersonList.toString());
    }

    public void sortByChildrenCount() {
        List<Person> sortedByChildenCountPersonList = personManager.getPersonsSortedByChildrenCount();
        printAnswer(sortedByChildenCountPersonList.toString());
    }

    public void sortByBirthDate() {
        List<Person> sortedByAgePersonList = personManager.getPersonsSortedByAge();
        printAnswer(sortedByAgePersonList.toString());
    }

    public void getPersonList() {
        List<Person> personsList = personManager.getAllPersons();
        printAnswer(personsList.toString());
    }


    public void loadFile() {
        if (currentFamilyTree != null) {
            printAnswer("Предупреждение: если вы продолжите, текущее дерево будет потеряно.");
            printAnswer("Рекомендуется сохранить текущее дерево перед загрузкой нового.");
            printAnswer("1. Продолжить загрузку и заменить текущее дерево");
            printAnswer("2. Вернуться назад");
            printAnswer("3. Сохранить текущее дерево и продолжить загрузку");

            int choice;
            try {
                choice = scanner.nextInt();
                clearBuffer();

                switch (choice) {
                    case 1:
                        break;
                    case 2:
                        return;
                    case 3:
                        saveFile();
                        break;
                    default:
                        printAnswer("Неверный выбор.");
                        return;
                }
            } catch (InputMismatchException e) {
                printAnswer("Неверный ввод. Пожалуйста, введите число.");
                clearBuffer();
                return;
            }
        }

        printAnswer("Введите имя загружаемого файла:");
        String filePath = scanner.next();

        try {
            FamilyTree<Person> loadedTree = fileManager.loadFamilyTree(filePath);
            if (loadedTree != null) {
                currentFamilyTree = loadedTree;
                this.personManager = new PersonService(currentFamilyTree);
                printAnswer("Дерево загружено.");
            } else {
                printAnswer("Проблема при загрузке файла. Дерево не было загружено.");
            }
        } catch (SerializationException e) {
            printAnswer("Ошибка при загрузке файла: " + e.getMessage());
        }
        this.personManager = new PersonService(currentFamilyTree);
    }


    public void saveFile() {
        printAnswer("Введите имя сохраняемого файла:");
        String filePath = scanner.next();

        if (currentFamilyTree.getRoot() != null) {
            try {
                fileManager.saveFamilyTree(currentFamilyTree, filePath);
            } catch (SerializationException e) {
                printAnswer("Ошибка при сохранении файла: " + e.getMessage());
            }
        } else {
            printAnswer("Семейное древо еще не загружено!");
        }
        printAnswer("Дерево сохранено в файл '" + filePath + "'.");

    }


    @Override
    public void printAnswer(String text) {
        System.out.println(text);
    }

    public boolean hasRootPerson() {
        return currentFamilyTree != null && currentFamilyTree.getRoot() != null;
    }

    public FamilyTree<Person> getCurrentFamilyTree() {
        return currentFamilyTree;
    }


    private String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.next();
    }

    private void clearBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
    }

    private void printChildren(String firstName, String lastName) {
        Optional<Person> parent = Optional.ofNullable(personManager.findPersonByNameAndSurname(firstName, lastName));
        List<Person> children = personManager.getChildrenOfPerson(parent);
        System.out.println(children.toString());
    }

    private void printMenu() {
        printAnswer("Меню:");
        for (int key : commands.keySet()) {
            System.out.println(key + ". " + commands.get(key).getDescription());
        }
    }

    private void showWelcomeMessage() {
        printAnswer("Добро пожаловать в приложение Family Tree!");
    }

    public void finish() {
        printAnswer("Окончание работы");
        work = false;
    }
}

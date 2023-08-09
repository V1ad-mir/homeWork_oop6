package familytree.service.validator;

import familytree.model.Gender;

import java.util.Scanner;

public class GenderValidator {
    public static Gender getValidGender() {
        Gender gender = null;
        boolean validGender = false;
        Scanner scanner = new Scanner(System.in);
        while (!validGender) {
            System.out.println("Введите пол ('м'/'ж'):");
            String genderInput = scanner.next();

            switch (genderInput.toLowerCase()) {
                case "м":
                    gender = Gender.MALE;
                    validGender = true;
                    break;
                case "ж":
                    gender = Gender.FEMALE;
                    validGender = true;
                    break;
                default:
                    System.out.println("Некорректный ввод. Пожалуйста, введите 'м' для мужского или 'ж' для женского пола.");
                    break;
            }
        }

        return gender;
    }
}

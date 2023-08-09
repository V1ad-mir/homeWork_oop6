package familytree.service.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateValidator {

    public static LocalDate getValidDate() {
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        boolean validDate = false;

        while (!validDate) {
            String dateInput = scanner.nextLine();
            if (dateInput.trim().isEmpty()) {
                return null;
            }
            try {
                date = LocalDate.parse(dateInput);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Некорректный формат даты. Введите дату в формате 2016-12-25.");
            }
        }

        return date;
    }
}

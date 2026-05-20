package service;

import java.util.Scanner;

import static menu.MainMenu.ps;

public class PetInputProcessor {

    public static final String NOT_INFORMED = "NOT INFORMED";

    public String processAge (String answer) {
        if (answer.isBlank()) return NOT_INFORMED;

        int value = Integer.parseInt(answer);

        if (!ps.isCorrectAge(value)) {
            throw new IllegalArgumentException("insert a valid age");
        }

        return String.valueOf(value);
    }

    public String processWeight (String answer) {
        if (answer.isBlank()) return NOT_INFORMED;

        double value = Double.parseDouble(answer);

        if (!ps.isCorrectWeight(value)) {
            throw new IllegalArgumentException("insert a valid weight");
        }

        return String.valueOf(value);
    }

    public String processName (String answer) {
        if (answer.isBlank()) return NOT_INFORMED;

        if (!ps.validateName(answer)) {
            throw new IllegalArgumentException("insert a valid name");
        }

        return answer;
    }

    public String processRace (String answer) {
        if (answer.isBlank()) return NOT_INFORMED;

        if (!ps.validateRace(answer)) {
            throw new IllegalArgumentException("insert a valid race");
        }

        return answer;
    }

    public String processString (String answer) {
        if (answer.isBlank()) return NOT_INFORMED;

        return answer;
    }

    public boolean processConfirmation (Scanner scanner) {
        System.out.print("\nDo you really wanna do this? (YES/NO) ");
        String yesNo = scanner.nextLine();

        if (yesNo.toUpperCase().equals("YES")) return true;
        else return false;
    }
}

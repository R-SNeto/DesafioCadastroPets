package application;

import menu.MainMenu;

import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Locale.setDefault(Locale.US);

        MainMenu UI = new MainMenu();
        UI.start(scanner);
    }

}

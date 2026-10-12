package framework.util;

import java.util.Scanner;
import java.util.NoSuchElementException;

public class InputUtil {
    private final Scanner scanner = new Scanner(System.in);
    public String readLine(String prompt) {
        System.out.print(prompt);
        try {
            return scanner.nextLine();
        } catch (NoSuchElementException exception) {
            return "x";
        }
    }
}
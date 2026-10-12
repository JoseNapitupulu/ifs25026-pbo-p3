package framework.util;

import java.util.Scanner;

public class InputUtil {
    private final Scanner scanner;

    public InputUtil() {
        scanner = new Scanner(System.in);
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
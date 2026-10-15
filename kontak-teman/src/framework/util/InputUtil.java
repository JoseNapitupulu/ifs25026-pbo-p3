package framework.util;

import java.util.Scanner;

/** Utility untuk membaca input konsol. */
public class InputUtil {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
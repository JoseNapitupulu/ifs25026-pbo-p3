package framework.util;

import java.util.Scanner;

/** Utility untuk membaca input konsol. */
public class InputUtil {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Input konsol telah berakhir.");
        }
        return scanner.nextLine();
    }
}
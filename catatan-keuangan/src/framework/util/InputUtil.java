package framework.util;

import java.util.Scanner;

/**
 * Utility framework untuk membaca input konsol.
 */
public class InputUtil {
    private final Scanner scanner;

    public InputUtil() {
        scanner = new Scanner(System.in);
    }

    /**
     * Menampilkan prompt dan membaca satu baris input.
     * Program dihentikan secara tenang ketika input sudah habis.
     */
    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Input konsol telah berakhir.");
        }
        return scanner.nextLine();
    }
}
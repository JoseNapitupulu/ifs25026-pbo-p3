package framework.util;

import java.util.Scanner;

/**
 * Utility framework untuk membaca input konsol.
 */
public class InputUtil {
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Menampilkan prompt dan membaca satu baris input.
     * Program dihentikan secara tenang ketika input sudah habis.
     */
    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
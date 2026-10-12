package framework.util;
import java.util.NoSuchElementException;
import java.util.Scanner;
public class InputUtil {
    private final Scanner scanner = new Scanner(System.in);
    public String readLine(String prompt) { System.out.print(prompt); try { return scanner.nextLine(); } catch (NoSuchElementException e) { return "x"; } }
}
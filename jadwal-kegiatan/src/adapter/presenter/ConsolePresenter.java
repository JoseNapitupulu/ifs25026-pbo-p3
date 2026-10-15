package adapter.presenter;

import java.util.List;
import java.util.function.Function;

abstract class ConsolePresenter {
    protected void printLine(String message) {
        System.out.println(message);
    }

    protected void printBlankLine() {
        System.out.println();
    }

    protected <T> void printList(List<T> items, Function<T, String> formatter) {
        for (T item : items) {
            printLine(formatter.apply(item));
        }
    }
}

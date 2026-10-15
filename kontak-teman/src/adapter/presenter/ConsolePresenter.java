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

    protected <T> void printList(String header, List<T> items, String emptyMessage,
            Function<T, String> formatter) {
        printLine(header);
        if (items.isEmpty()) {
            printLine(emptyMessage);
        } else {
            printList(items, formatter);
        }
    }
}

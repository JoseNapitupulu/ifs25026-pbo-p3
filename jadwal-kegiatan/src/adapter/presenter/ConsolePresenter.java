package adapter.presenter;

abstract class ConsolePresenter {
    protected void printLine(String message) {
        System.out.println(message);
    }

    protected void printBlankLine() {
        System.out.println();
    }
}

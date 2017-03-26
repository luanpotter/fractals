package xyz.luan.fractals;

import jline.TerminalFactory;
import jline.console.ConsoleReader;

import java.io.IOException;

public class ProgressBar {

    private static final int COLUMNS = TerminalFactory.get().getWidth();

    private int total, current;

    public ProgressBar(int total) {
        this.total = total;
        this.current = 0;
    }

    public void tick() {
        tick(1);
    }

    public void tick(int d) {
        this.current += d;
    }

    public void display() {
        try {
            new ConsoleReader().print(buildLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        try {
            new ConsoleReader().clearScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildLine() {
        String res = "";
        for (int i = 0; i < COLUMNS; i++) {
            res += "\b";
        }
        res += "[";
        int first = (int) (((float) current) / total * COLUMNS);
        int rest = COLUMNS - 3 - first;
        for (int i = 0; i < first; i++) {
            res += "=";
        }
        res += ">";
        for (int i = 0; i < rest; i++) {
            res += " ";
        }
        res += "]";
        return res;
    }
}

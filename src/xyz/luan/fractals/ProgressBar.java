package xyz.luan.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProgressBar {

    private static final int COLUMNS;

    static {
        try {
            Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "tput cols 2> /dev/tty"});
            p.waitFor();
            String result = new BufferedReader(new InputStreamReader(p.getInputStream())).lines().findFirst().orElse("80");
            COLUMNS = Integer.parseInt(result);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

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
        System.out.print(buildLine());
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

    public void clear() {
        try {
            Runtime.getRuntime().exec("clear").waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

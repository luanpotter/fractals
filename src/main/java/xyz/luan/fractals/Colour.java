package xyz.luan.fractals;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toHexString;

public class Colour {
    private int r, g, b;

    public static final Colour BLACK = new Colour(0, 0, 0);
    public static final Colour WHITE = new Colour(255, 255, 255);

    public Colour(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int toInteger() {
        return parseInt(toHexString(r) + toHexString(g) + toHexString(b), 16);
    }
}

package xyz.luan.fractals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;

public class Picture {

    private BitSet data;
    private int w, h;

    public Picture(int w, int h) {
        this.w = w;
        this.h = h;
        this.data = new BitSet(w * h);
    }

    public void set(int x, int y) {
        data.set(x * w + y, true);
    }

    public boolean get(int x, int y) {
        return data.get(x * w + y);
    }

    public void save(String file) {
        try {
            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    Colour colour = get(i, j) ? Colour.BLACK : Colour.WHITE;
                    img.setRGB(i, j, colour.toInteger());
                }
            }
            ImageIO.write(img, "PNG", new File(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

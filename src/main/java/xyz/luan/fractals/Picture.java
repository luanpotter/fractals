package xyz.luan.fractals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.BitSet;

public class Picture {

    private BitSet data;
    private int size;

    public Picture(int size) {
        this.size = size;
        this.data = new BitSet(size * size);
    }

    public void set(int x, int y) {
        data.set(x * size + y, true);
    }

    public boolean get(int x, int y) {
        return data.get(x * size + y);
    }

    public void save(String file) {
        try {
            BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    Colour colour = get(i, j) ? Colour.BLACK : Colour.WHITE;
                    img.setRGB(i, j, colour.toInteger());
                }
            }
            ImageIO.write(img, "PNG", new File(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int size() {
        return size;
    }
}

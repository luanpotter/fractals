package xyz.luan.fractals;

public class Main {

    public static final int ITERATIONS = 200;

    public static boolean mand(Complex z0) {
        Complex z = z0;
        Complex max = Complex.ZERO;
        int lastMax = 0;
        for (int t = 0; t < 100 * ITERATIONS; t++) {
            if (Double.isInfinite(z.abs())) {
                return false;
            }

            if (z.abs() > max.abs()) {
                max = z;
                lastMax = t;
            } else if (t - lastMax > ITERATIONS) {
                return true;
            }

            z = z.times(z).plus(z0);
        }
        return false;
    }

    public static void main(String[] args) {
        Picture picture = generatePicture(false);
        new Thread(() -> countDimension(picture)).start();
        new Thread(() -> savePicture(picture)).start();
    }

    private static void savePicture(Picture picture) {
        picture.save("/home/luan/projects/fractals/fractal.png");
    }

    private static void countDimension(Picture picture) {
        int dimension = boxCount(picture);
        System.out.println("Calculated dimension: " + dimension);
    }

    private static int boxCount(Picture picture) {
        return 0; // TODO
    }

    private static Picture generatePicture(boolean display) {
        double xc = 0d;
        double yc = 0d;
        double size = 5d;

        int n = 128;

        ProgressBar progressBar = new ProgressBar(n * n);
        if (display) progressBar.display();
        Picture picture = new Picture(n, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x0 = xc - size / 2 + size * i / n;
                double y0 = yc - size / 2 + size * j / n;
                Complex z0 = new Complex(x0, y0);
                if (mand(z0)) {
                    picture.set(i, n - 1 - j);
                }
                progressBar.tick();
                if (display) progressBar.display();
            }
        }
        progressBar.clear();
        return picture;
    }
}

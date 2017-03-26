package xyz.luan.fractals;

import org.orangepalantir.leastsquares.Function;
import org.orangepalantir.leastsquares.fitters.LinearFitter;

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
        System.out.println("Generating picture...");
        Picture picture = generatePicture();
        System.out.println("Counting dimensions...");
        countDimension(picture);
        System.out.println("Saving result...");
        savePicture(picture);
        System.out.println("Done!");
    }

    private static void savePicture(Picture picture) {
        picture.save("/home/luan/projects/fractals/fractal.png");
    }

    private static void countDimension(Picture picture) {
        double dimension = boxCount(picture);
        System.out.println("Calculated dimension: " + dimension);
    }

    private static double boxCount(Picture picture) {
        int n = (int) (Math.log(picture.size()) / Math.log(2));
        double[][] xs = new double[n][];
        double[] zs = new double[n];
        for (int i = 0; i < n; i++) {
            int r = (int) Math.pow(2, i);
            int squares = countSquares(picture, r);
            xs[i] = new double[]{Math.log(r)};
            zs[i] = Math.log(squares);
        }
        LinearFitter a = new LinearFitter(new Function() {
            @Override
            public double evaluate(double[] values, double[] parameters) {
                double A = parameters[0];
                double B = parameters[1];
                double x = values[0];
                return A * x + B;
            }

            @Override
            public int getNParameters() {
                return 2;
            }

            @Override
            public int getNInputs() {
                return 1;
            }
        });
        a.setData(xs, zs);
        a.setParameters(new double[]{1d, 0d});
        a.fitData();
        return a.getParameters()[0];
    }

    private static int countSquares(Picture picture, int r) {
        int total = 0;
        for (int i = 0; i < picture.size() / r; i += r) {
            for (int j = 0; j < picture.size() / r; j += r) {
                if (countSubSquare(picture, i, j)) {
                    total++;
                }
            }
        }
        return total;
    }

    private static boolean countSubSquare(Picture picture, int i, int j) {
        for (int dx = 0; dx < picture.size(); dx++) {
            for (int dy = 0; dy < picture.size(); dy++) {
                if (picture.get(i + dx, j + dy)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Picture generatePicture() {
        double xc = 0d;
        double yc = 0d;
        double size = 5d;

        int n = 512;

        ProgressBar progressBar = new ProgressBar(n * n);
        progressBar.display();
        Picture picture = new Picture(n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x0 = xc - size / 2 + size * i / n;
                double y0 = yc - size / 2 + size * j / n;
                Complex z0 = new Complex(x0, y0);
                if (mand(z0)) {
                    picture.set(i, n - 1 - j);
                }
                progressBar.tick();
                progressBar.display();
            }
        }
        progressBar.clear();
        return picture;
    }
}

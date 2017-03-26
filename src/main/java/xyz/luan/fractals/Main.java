package xyz.luan.fractals;

import me.tongfei.progressbar.ProgressBar;
import org.orangepalantir.leastsquares.Function;
import org.orangepalantir.leastsquares.fitters.LinearFitter;

public class Main {

    public static final int ITERATIONS = 200;

    public static boolean mand(Complex z0) {
        Complex z = z0;
        double max = 0;
        int lastMax = 0;
        for (int t = 0; t < 100 * ITERATIONS; t++) {
            double abs = z.abs();
            if (Double.isInfinite(abs)) {
                return false;
            }

            if (abs > max) {
                max = abs;
                lastMax = t;
            } else if (t - lastMax > ITERATIONS) {
                return true;
            }

            z = z.timesPlus(z, z0);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Generating picture...");
        Picture picture = generatePicture(1024);
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
        ProgressBar pb = new ProgressBar("Box Count", n);
        pb.start();
        for (int i = 0; i < n; i++) {
            int r = (int) Math.pow(2, i);
            int squares = countSquares(picture, r);
            xs[i] = new double[]{Math.log(r)};
            zs[i] = Math.log(squares);
            pb.step();
        }
        pb.stop();
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
        int size = picture.size() / r;
        for (int i = 0; i < picture.size(); i += size) {
            for (int j = 0; j < picture.size(); j += size) {
                if (countSubSquare(picture, i, j, size)) {
                    total++;
                }
            }
        }
        return total;
    }

    private static boolean countSubSquare(Picture picture, int i, int j, int s) {
        for (int dx = 0; dx < s; dx++) {
            for (int dy = 0; dy < s; dy++) {
                if (picture.get(i + dx, j + dy)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static Picture generatePicture(int details) {
        double xc = 0d;
        double yc = 0d;
        double size = 5d;

        ProgressBar pb = new ProgressBar("Generating Image", details * details);
        pb.start();
        Picture picture = new Picture(details);
        for (int i = 0; i < details; i++) {
            for (int j = 0; j < details; j++) {
                double x0 = xc - size / 2 + size * i / details;
                double y0 = yc - size / 2 + size * j / details;
                if (mand(new Complex(x0, y0))) {
                    picture.set(i, details - 1 - j);
                }
                pb.step();
            }
        }
        pb.stop();
        return picture;
    }
}

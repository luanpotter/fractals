package xyz.luan.fractals;

import me.tongfei.progressbar.ProgressBar;

import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static final int ITERATIONS = 200;

    public static boolean mand(Complex c) {
        Complex z = c;
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

            z = z.timesPlus(z, c);
        }
        return false;
    }

    public static void main(String[] args) {
        String basePath = "/home/luan/projects/fractals/results/";

        for (int i = 7; ; i++) {
            int details = (int) Math.pow(2, i);
            String fileName = basePath + "fractal_" + details;
            run(fileName, details);
        }
    }

    private static void run(String fileName, int details) {
        System.out.println("-----------------------------------------------");
        System.out.println("Running for " + details);
        System.out.println("Generating picture...");
        Picture picture = generatePicture(details);
        System.out.println("Counting dimensions...");
        double dimension = boxCount(picture);
        System.out.println("Calculated dimension: " + dimension);
        System.out.println("Saving result...");
        writeResultToFile(fileName, details, dimension);
        picture.save(fileName + ".png");
        System.out.println("Done!");
        System.out.println("-----------------------------------------------");
    }

    private static void writeResultToFile(String fileName, int details, double dimension) {
        try (FileWriter w = new FileWriter(fileName + ".result")) {
            w.write("(details, dim): (" + details + ", " + dimension + ")\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static double boxCount(Picture picture) {
        int n = (int) (Math.log(picture.size()) / Math.log(2));
        double[][] xs = new double[n][]; // because the library wants it this way
        double[] ys = new double[n];
        ProgressBar pb = new ProgressBar("Box Count", n);
        pb.start();
        for (int i = 0; i < n; i++) {
            int r = (int) Math.pow(2, i);
            int squares = countSquares(picture, r);
            xs[i] = new double[]{Math.log(r)};
            ys[i] = Math.log(squares);
            pb.step();
        }
        pb.stop();
        System.out.println("Running MMMQ...");
        double[] params = MMQ.run(xs, ys); // ax+b, returns [a, b]
        return params[0];
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

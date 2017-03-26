package xyz.luan.fractals;

import org.orangepalantir.leastsquares.Function;
import org.orangepalantir.leastsquares.fitters.LinearFitter;

public class MMQ {
    public static double[] run(double[][] xs, double[] zs) {
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
        return a.getParameters();
    }
}

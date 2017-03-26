package xyz.luan.fractals;

import java.util.Objects;

public class Complex {
    public static final Complex ZERO = new Complex(0, 0);
    private final double re, im;

    public Complex(double real, double imaginary) {
        re = real;
        im = imaginary;
    }

    public String toString() {
        if (im == 0) return re + "";
        if (re == 0) return im + "i";
        if (im < 0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }

    public double abs() {
        return Math.hypot(re, im);
    }

    public double phase() {
        return Math.atan2(im, re);
    }

    public Complex plus(Complex b) {
        Complex a = this;
        double re = a.re + b.re;
        double im = a.im + b.im;
        return new Complex(re, im);
    }

    public Complex minus(Complex b) {
        Complex a = this;
        double re = a.re - b.re;
        double im = a.im - b.im;
        return new Complex(re, im);
    }

    public Complex timesPlus(Complex b, Complex c) {
        Complex a = this;
        double re = a.re * b.re - a.im * b.im + c.re;
        double im = a.re * b.im + a.im * b.re + c.im;
        return new Complex(re, im);
    }

    public Complex times(Complex b) {
        Complex a = this;
        double re = a.re * b.re - a.im * b.im;
        double im = a.re * b.im + a.im * b.re;
        return new Complex(re, im);
    }

    public Complex scale(double alpha) {
        return new Complex(alpha * re, alpha * im);
    }

    public Complex conjugate() {
        return new Complex(re, -im);
    }

    public Complex reciprocal() {
        double scale = re * re + im * im;
        return new Complex(re / scale, -im / scale);
    }

    public double re() {
        return re;
    }

    public double im() {
        return im;
    }

    public Complex divides(Complex b) {
        return this.times(b.reciprocal());
    }

    public Complex exp() {
        return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
    }

    public Complex sin() {
        return new Complex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
    }

    public Complex cos() {
        return new Complex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
    }

    public Complex tan() {
        return sin().divides(cos());
    }


    public boolean equals(Object x) {
        if (x == null) return false;
        if (this.getClass() != x.getClass()) return false;
        Complex that = (Complex) x;
        return (this.re == that.re) && (this.im == that.im);
    }

    public int hashCode() {
        return Objects.hash(re, im);
    }
}
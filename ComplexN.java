public class ComplexN {
    
    private final double re;
    private final double im;

    public ComplexN (double re, double im) {
        this.re = re;
        this.im = im;
    }

    //сложение
    public ComplexN addition (ComplexN other) {
        return new ComplexN (re + other.re, im + other.im);
    }
    //вычитание
    public ComplexN subtraction (ComplexN other) { 
        return new ComplexN (re - other.re, im - other.im);
    }
    //умножение
    public ComplexN multiplication (ComplexN other) { 
        return new ComplexN (re * other.re - im * other.im, re * other.im + im * other.re);
    }
    //деление 
    public ComplexN division (ComplexN other) { 
        double denom = other.re * other.re + other.im * other.im;
        if (denom <= 1e-10) {
            throw new ArithmeticException("Деление на ноль запрещено");
        }
        return new ComplexN ((re * other.re + im * other.im)/denom, (im * other.re - re * other.im)/denom);
    }
    //модуль 
    public double abs() {
        return Math.sqrt((re * re) + (im * im));
    }
    //преобразование в строку
    @Override
    public String toString() {
        if (im >= 0) {
            return String.format("%.2f + %.2fi", re, im);
        } else {
            return String.format("%.2f - %.2fi", re, -im);
        }
    }
}

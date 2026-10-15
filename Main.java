import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean continueP = true; 
        while (continueP) {  
            
            Matrix m1;
            try {
                m1 = readMatrix(scanner);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                continue;
            } catch (InputMismatchException e) {
                System.out.println("Введено не число.");
                scanner.nextLine();
                continue;
}
            
            boolean chain = true;
            while (chain) {
                System.out.println("Выберите операцию:");
                System.out.println("+ сложение");
                System.out.println("- вычитание");
                System.out.println("* умножение");
                System.out.println("/ деление");
                System.out.println("T транспонирование");
                System.out.println("D определитель");
                System.out.println("exit завершить программу");
                String operation = scanner.next();

                try {
                    switch (operation) {
                        case "+": {
                            Matrix m2 = readMatrix(scanner);
                            m1 = m1.addition(m2);
                            System.out.println("Результат сложения:");
                            System.out.println(m1);
                            break;
                        }
                        case "-": {
                            Matrix m2 = readMatrix(scanner);
                            m1 = m1.subtraction(m2);
                            System.out.println("Результат вычитания:");
                            System.out.println(m1);
                            break;
                        }
                        case "*": {
                            Matrix m2 = readMatrix(scanner);
                            m1 = m1.multiplication(m2);
                            System.out.println("Результат умножения:");
                            System.out.println(m1);
                            break;
                        }
                        case "/": {
                            Matrix m2 = readMatrix(scanner);
                            m1 = m1.division(m2);
                            System.out.println("Результат деления:");
                            System.out.println(m1);
                            break;
                        }
                        case "T": {
                            m1 = m1.transpose();
                            System.out.println("Результат транспонирование:");
                            System.out.println(m1);
                            break;
                        }
                        case "D": {
                            System.out.println("Определитель:");
                            System.out.println(m1.determinant());
                            break;
                        }
                        case "exit": {
                            chain = false;            
                            continueP = false;             
                            break;
                        }
                        default:
                            System.out.println("Операция не определена"); 
                    }
                }
                catch (IllegalArgumentException | ArithmeticException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
        }
        scanner.close();
    }

    private static Matrix readMatrix(Scanner scanner) {
        System.out.println("Введите кол-во строк");
        int rows = scanner.nextInt();
        System.out.println("Введите кол-во столбцов");
        int cols = scanner.nextInt();
        
        Matrix m = new Matrix(rows, cols);

        System.out.println("Введите матрицу:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print("Элемент [" + i + "][" + j + "]:");
                double re = scanner.nextDouble();
                double im = scanner.nextDouble();
                ComplexN c = new ComplexN(re, im);
                m.set(i, j, c);
            }
        }
        return m;
    }
}

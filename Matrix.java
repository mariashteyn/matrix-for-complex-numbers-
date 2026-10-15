public class Matrix {

    private ComplexN [][] data;
    private int rows;
    private int cols;

    public Matrix (int rows, int cols) {
        if (rows <= 0 || cols <= 0) {
            throw new IllegalArgumentException("Размеры должны быть положительными");
        }
        this.rows = rows; this.cols = cols;
        this.data = new ComplexN[rows][cols];
        //заполнение 0
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.data[i][j] = new ComplexN (0, 0);
            }
        }
        
    }

    public Matrix(ComplexN[][] data) {
        if (data == null || data.length <= 0) {
            throw new IllegalArgumentException("Массив не может быть null или пустым");
        }
        int l = data[0].length;
        for (int i = 1; i < data.length; i++) {
            if (data[i].length != l) {
                throw new IllegalArgumentException("Строки должны быть одной длины");
            }
        }
        this.data = data;
        this.rows = data.length;
        this.cols = data[0].length;
    }

    
    //ПЕРЕВОД В СТРОКУ
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                sb.append(data[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    //МЕТОДЫ GET/SET
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    public boolean isSquare() { return rows == cols; }

    public ComplexN get(int i, int j) {
        if (rows <= i || i < 0 || cols <= j || j < 0) {
            throw new IndexOutOfBoundsException("Индекс вне границ");
        }
        return data[i][j];
    }

    public void set(int i, int j, ComplexN v) {
        if (rows <= i || i < 0 || cols <= j || j < 0) {
            throw new IndexOutOfBoundsException("Индекс вне границ");
        }
        data[i][j] = v;
    }

    
    //ОПЕРАЦИИ
    //сложение
    public Matrix addition(Matrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Размеры должны совпадать");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j).addition(other.get(i, j)));
            }
        }
        return result;
    }
    //вычитание
    public Matrix subtraction(Matrix other) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Размеры должны совпадать");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j).subtraction(other.get(i, j)));
            }
        }
        return result;
    }
    //умножение
    public Matrix multiplication(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Размеры должны совпадать");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                ComplexN sum = new ComplexN(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    sum = sum.addition(this.get(i, k).multiplication(other.get(k, j)));
                }
            result.set(i, j, sum);
            }
        }
        return result;
    }
    //деление
    public Matrix division(Matrix other) {
        return this.multiplication(other.inverse()); //проверки в multiplication и inverse 
    }
    //минор 
    private Matrix minor(int row, int col) {
        if (!isSquare()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }
        Matrix result = new Matrix(rows - 1, cols - 1);
        for (int i = 0; i < rows; i++) {
            if (i == row) continue;
            for (int j = 0; j < cols; j++) {
                if (j == col) continue;
                int ri = (i < row) ? i : i - 1;
                int rj = (j < col) ? j : j - 1;
                result.set(ri, rj, this.get(i, j));
            }
        }
        return result;
    }
    //детерминант 
    public ComplexN determinant() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Для нахождения детерминанта нужна квадратная матрица");
        }
        if (rows == 1) {
            return get(0, 0);
        }
        if (rows == 2) {
            return get(0, 0).multiplication(get(1, 1)).subtraction(get(0, 1).multiplication(get(1, 0)));
        }
        ComplexN det = new ComplexN(0, 0);
        for (int i = 0; i < cols; i++) {
            int sign = (i % 2 == 0) ? 1 : -1; //определение знака 
            ComplexN term = new ComplexN(sign, 0).multiplication(get(0, i)).multiplication(minor(0, i).determinant());
            det = det.addition(term);
        }
        return det;

    }
    //транспониование 
    public Matrix transpose() {
        Matrix result = new Matrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(j, i, this.get(i, j));
            }
        }
    return result;
    }
    //обратная матрица
    public Matrix inverse() {
        if (!isSquare()) {
            throw new IllegalArgumentException("Для нахождения обратной матрицы нужна квадратная матрица");
        }
        ComplexN det = determinant();
        if (det.abs() < 1e-10) {
            throw new ArithmeticException("Матрица вырождена, обратной не существует");
        }
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ComplexN cof = minor(j, i).determinant();
                int sign = ((i + j) % 2 == 0) ? 1 : -1;
                ComplexN val = new ComplexN(sign, 0).multiplication(cof).division(det);
                result.set(i, j, val);
            }
        }
        return result;
    }
}

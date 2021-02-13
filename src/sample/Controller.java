package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {
    private int counter;

    @FXML
    private TextField fieldFile;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField varField;

    @FXML
    private TextArea textArea2;

    @FXML
    private Pane pane;

    @FXML
    private Button btnGenerate;

    @FXML
    private TextField countField;

    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextArea textArea;

    @FXML
    private Button btnSave;

    @FXML
    private Label progressTxt;

    @FXML
    private TextField progressInput;

    @FXML
    void initialize() {
        init(); //инициализация программы

        textFieldProp(countField); //Защита текстовых полей
        textFieldProp(varField);

        comboBox.setOnAction(event -> {
            if (comboBox.getValue().equals("Вычислить n-й член арифметической прогрессии")) {
                progressInput.setVisible(true);
                progressTxt.setVisible(true);
                textArea.setText("Ниже в соответствующем поле через\nпробел укажите, какой n-член нужно\nнайти и сколько членов прогрессии\nзаранее известно");
            }
            if (comboBox.getValue().equals("Вычислить сумму первых n членов арифметической прогрессии")) {
                progressInput.setVisible(true);
                progressTxt.setVisible(true);
                textArea.setText("Ниже в соответствующем поле укажите\nсумму скольких n-членов прогрессии\nнужно найти");
            }
            if (comboBox.getValue().equals("Вычислить разницу арифметической прогрессии d")) {
                progressInput.setVisible(true);
                progressTxt.setVisible(true);
                textArea.setText("Ниже в соответствующем поле через\nпробел укажите два индекса заранее\nизвестных элементов прогресии");
            }
        });

        //Кнопка "Генерировать"
        btnGenerate.setOnAction(event -> {
            // 1
            if (comboBox.getValue().equals("Линейное уравнение")) {
                if (border()) linearEquation();
            } else if (comboBox.getValue().equals("Квадратное уравнение")) {
                if (border()) squareEquation();
                // 2
            } else if (comboBox.getValue().equals("Вычислить n-й член арифметической прогрессии")) {
                if (border()) searchNElemOfProgress();
            } else if (comboBox.getValue().equals("Вычислить сумму первых n членов арифметической прогрессии")) {
                if (border()) sumOfNNumOfProgress();
            } else if (comboBox.getValue().equals("Вычислить разницу арифметической прогрессии d")) {
                if (border()) differenceOfProgress();
                // 3
            } else if (comboBox.getValue().equals("Решить систему уравнений из двух линейных уравнений с двумя неизвестными")) {
                if (border()) systLineEquatOfTwoWithTwo();
            } else if (comboBox.getValue().equals("Решить систему уравнений из трех линейных уравнений с тремя неизвестными")) {
                if (border()) systLineEquatOfThreeWithThree();
            } else if (comboBox.getValue().equals("Решить систему уравнений из двух однородных уравнений с двумя неизвестными")) {
                if (border()) systOdnEquatOfTwoWithTwo();
                // 4
            } else if (comboBox.getValue().equals("Решить линейное неравенство")) {
                if (border()) linearInequality();
            } else if (comboBox.getValue().equals("Решить квадратное неравенство")) {
                if (border()) quadraticInequality();
            } else if (comboBox.getValue().equals("Решить рациональное неравенство")) {
                if (border()) rationalInequality();
            }
        });

        //Кнопка "Сохранить"
        btnSave.setOnAction(event -> {
            String name = "Введите имя файла";
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Сохранение...");
            alert.setHeaderText(null);
            String ss = ("Решите уравнение:\n" + textArea.getText() + "\nОтветы:\n" + textArea2.getText());
            ss.replaceAll("²", "^2");
            try {
                if (fieldFile.getText().equals("Введите имя файла")) name = comboBox.getValue();
                else name = fieldFile.getText();
                PrintWriter pw = new PrintWriter(name + ".txt");
                pw.println(ss);
                pw.close();

                alert.setContentText("Сохранение выполнено успешно!");
                alert.showAndWait();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        });
    }

    //границы ввода количества примеров
    private boolean border() {
        if ((Integer.parseInt(varField.getText()) <= 100) && (Integer.parseInt(varField.getText()) != 0) && (Integer.parseInt(countField.getText()) <= 50) && (Integer.parseInt(countField.getText()) != 0)) {
            return true;
        } else {
            textArea.setText("Количество вариантов должно находится \nв промежутке 1-100");
            textArea2.setText("Количество примеров в одном варианте \nдолжно находится в промежутке 1-50");
            return false;
        }
    }

    //генератор квадратных уравнений
    private void squareEquation() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                gen();
            }
        }
    }

    //рекурсивный поиск нужных коэффициентов для квадратного уравнения
    private void gen() {
        int a = rnd();
        int b = rnd();
        int c = rnd();
        String fx1, fx2;
        double res = Math.sqrt(b * b - 4 * a * c);
        if (res == (Math.floor(res))) {
            double x1 = ((-1 * b) + res) / (2 * a);
            double chislitel = ((-1 * b) + res), znamenatel = (2 * a);
            if (x1 % 1 == 0) {
                fx1 = Integer.toString((int) x1);
            } else {
                if (length(x1) < 3) {
                    fx1 = Double.toString(x1);
                } else {
                    fx1 = (int) chislitel + "/" + (int) znamenatel;
                }
            }
            double x2 = ((-1 * b) - res) / (2 * a);
            chislitel = ((-1 * b) - res);
            znamenatel = (2 * a);
            if (x2 % 1 == 0) {
                fx2 = Integer.toString((int) x2);
            } else {
                if (length(x2) < 3) {
                    fx2 = Double.toString(x2);
                } else {
                    fx2 = (int) chislitel + "/" + (int) znamenatel;
                }
            }
            String signB = "", signC = "";
            if (b > 0) signB = "+";
            else signB = " ";
            if (c > 0) signC = "+";
            else signC = " ";
            textArea.setText(textArea.getText() + counter + ") " + a + "x²" + signB + b + "x" + signC + c + "= 0" + "\n");
            textArea2.setText(textArea2.getText() + counter + ") " + "x₁=" + fx1 + "; X₂=" + fx2 + "\n");
        } else {
            gen();
        }
    }

    //считает кол-во знаков после запятой
    private int length(double x) {
        String[] splitter = String.valueOf(x).split("\\.");
        return splitter[1].length();
    }

    //инициализация программы
    private void init() {
        ObservableList<String> langs = FXCollections.observableArrayList("Линейное уравнение", "Квадратное уравнение", "Вычислить n-й член арифметической прогрессии"
                , "Вычислить сумму первых n членов арифметической прогрессии", "Вычислить разницу арифметической прогрессии d", "Решить систему уравнений из двух линейных уравнений с двумя неизвестными"
                , "Решить систему уравнений из трех линейных уравнений с тремя неизвестными", "Решить систему уравнений из двух однородных уравнений с двумя неизвестными"
                , "Решить линейное неравенство", "Решить квадратное неравенство", "Решить рациональное неравенство");
        comboBox.setItems(langs);
        comboBox.setValue("Линейное уравнение"); // устанавливаем выбранный элемент по умолчанию
        fieldFile.setText("Введите имя файла");
        progressTxt.setVisible(false);
        progressInput.setVisible(false);
    }

    //генератор линейных уравнений
    private void linearEquation() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                int i = rnd(-5, 15);
                int x = rnd();
                int a = rnd();
                int b = rnd();
                if (i < 5) {
                    int c = rnd();
                    int e = a * x + b * x + c;
                    String signB = "", signC = "";
                    if (b > 0) signB = "+";
                    else signB = " ";
                    if (c > 0) signC = "+";
                    else signC = " ";
                    textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "x" + signC + c + "=" + e + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X=" + x + "\n");
                } else {
                    int c = a * x + b;
                    String signB = "";
                    if (b > 0) signB = "+";
                    else signB = " ";
                    textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "=" + c + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X = " + x + "\n");
                }
            }
        }
    }

    //Защита текстового поля
    private void textFieldProp(TextField textField) {
        textField.setText("1"); //устанавливаем значение по умолчанию
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    //генерация рандомного числа с округлением до 1 знака после запятой
    private int rnd() {
        return rnd(-10, 20);
    }

    private int rnd(double min, double max) {
        max -= min;
        BigDecimal bd = new BigDecimal(Double.toString((Math.random() * ++max) + min));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        if (bd.intValue() == 0) {
            return rnd();
        } else
            return bd.intValue();
    }

    // поиск n-члена прогрессии
    private void searchNElemOfProgress() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                String inputStr = progressInput.getText();
                int space = inputStr.indexOf(" ");
                int searchingN = Integer.parseInt(inputStr.substring(0, space));
                int nCount = Integer.parseInt(inputStr.substring(space + 1));
                int a1 = rnd(1, 50);
                int d = rnd(1, 10);
                ArrayList<Integer> arrWithA = new ArrayList<>();
                for (int i = 0; i < nCount; i++) {
                    arrWithA.add(a1);
                    a1 += d;
                }
                int an = a1 + (d * (searchingN - 1));
                textArea.setText(textArea.getText() + counter + ") " + arrWithA + "\n");
                textArea2.setText(textArea2.getText() + counter + ") " + an + "\n");
            }
        }
    }

    // сумма первых n членов прогрессии
    private void sumOfNNumOfProgress() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                int a1 = rnd(1, 50);
                int d = rnd(1, 10);
                int result = a1;
                for (int i = 1; i < Integer.parseInt(progressInput.getText()); i++) {
                    a1 += d;
                    result += a1;
                }
                textArea.setText(textArea.getText() + counter + ") " + "a₁=" + a1 + ", d=" + d + "\n");
                textArea2.setText(textArea2.getText() + counter + ") " + result + "\n");
            }
        }
    }

    // вычислить разницу арифметической прогрессии d
    private void differenceOfProgress() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                String inputStr = progressInput.getText();
                int space = inputStr.indexOf(" ");
                int indAn1 = Integer.parseInt(inputStr.substring(0, space));
                int indAn2 = Integer.parseInt(inputStr.substring(space + 1));
                int diffAns;
                double d = 0;
                if (indAn1 > indAn2) {
                    diffAns = indAn1 - indAn2;
                    int an2 = rnd(1, 50);
                    int an1 = rnd(an2 + 1, 50);
                    d += (double) (an1 - an2) / diffAns;
                    textArea.setText(textArea.getText() + counter + ") a" + indAn2 + "=" + an2 + ", a" + indAn1 + "=" + an1 + "\n");
                } else if (indAn2 > indAn1) {
                    diffAns = indAn2 - indAn1;
                    int an1 = rnd(1, 50);
                    int an2 = rnd(an1 + 1, 50);
                    d += (double) (an2 - an1) / diffAns;
                    textArea.setText(textArea.getText() + counter + ") a" + indAn1 + "=" + an1 + ", a" + indAn2 + "=" + an2 + "\n");
                }
                textArea2.setText(textArea2.getText() + counter + ") " + d + "\n");
            }
        }
    }

    // Решить систему уравнений из двух линейных уравнений с двумя неизвестными
    private void systLineEquatOfTwoWithTwo() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                int a = rnd(1, 30);
                int b = rnd(1, 30);
                int c = rnd(1, 30);
                int d = rnd(1, 30);
                int e = rnd(1, 30);
                int f = rnd(1, 30);
                double y = (double) (a * f - c * d) / (a * e - b * d);
                double x = (double) (c * e - b * f) / (a * e - b * d);
                String sY = String.format("%.2f", y);
                String sX = String.format("%.2f", x);
                int i = rnd(1, 12);
                if (i < 6) {
                    textArea.setText(textArea.getText() + counter + ") " + a + "x+" + b + "y=" + c + "\n    " + d + "x+" + e + "y=" + f + "\n");
                } else {
                    textArea.setText(textArea.getText() + counter + ") " + a + "x-" + c + "=-" + b + "y" + "\n    " + d + "x-" + f + "=-" + e + "y" + "\n");
                }
                textArea2.setText(textArea2.getText() + counter + ") X=" + sX + "; Y=" + sY + "\n");
            }
        }
    }

    // Решить систему уравнений из трех линейных уравнений с тремя неизвестными
    private void systLineEquatOfThreeWithThree() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;

                /* Ввод данных */
                int n = 3;
                int m = 3;
                int[][] A = new int[100][100];
                int[] b = new int[100];
                for (int i = 0; i < n; i++) {
                    A[i] = new int[100];
                    for (int j = 0; j < m; j++) {
                        A[i][j] = rnd(1, 30);
                    }
                    b[i] = rnd(1, 30);
                }
                textArea.setText(textArea.getText() + counter + ") " + A[0][0] + "x₁+" + A[0][1] + "x₂+" + A[0][2] + "x₃=" + b[0] + "\n    "
                                                                    + A[1][0] + "x₁+" + A[1][1] + "x₂+" + A[1][2] + "x₃=" + b[1] + "\n    "
                                                                    + A[2][0] + "x₁+" + A[2][1] + "x₂+" + A[2][2] + "x₃=" + b[3] + "\n");
                /* Метод Гаусса */
                int N = n;
                for (int p = 0; p < N; p++) {
                    int max = p;
                    for (int i = p + 1; i < N; i++) {
                        if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                            max = i;
                        }
                    }
                    int[] temp = A[p];
                    A[p] = A[max];
                    A[max] = temp;
                    double t = b[p];
                    b[p] = b[max];
                    b[max] = (int) t;
                    if (Math.abs(A[p][p]) <= 1e-10) {
                        System.out.println("NO");
                        return;
                    }
                    for (int i = p + 1; i < N; i++) {
                        double alpha = A[i][p] / A[p][p];
                        b[i] -= alpha * b[p];
                        for (int j = p; j < N; j++) {
                            A[i][j] -= alpha * A[p][j];
                        }
                    }
                }

                // Обратный проход
                double[] x = new double[N];
                for (int i = N - 1; i >= 0; i--) {
                    double sum = 0.0;
                    for (int j = i + 1; j < N; j++) {
                        sum += A[i][j] * x[j];
                    }
                    x[i] = (b[i] - sum) / A[i][i];
                }

                /* Вывод результатов */
                int j = 1;
                textArea2.setText(textArea2.getText() + counter + ") ");
                for (int i = 0; i < N; i++) {
                    String sX = String.format("%.2f", x[i]);
                    j += i;
                    textArea2.setText(textArea2.getText() + "X" + j + "=" + sX + "  ");
                }
                textArea2.setText(textArea2.getText() + "\n");
            }
        }
    }

    // Решить систему уравнений из двух однородных уравнений с двумя неизвестными
    private void systOdnEquatOfTwoWithTwo() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                //
                textArea.setText(textArea.getText() + counter + ") " + "\n");
                textArea2.setText(textArea2.getText() + counter + ") " + "\n");
            }
        }
    }

    // Решить линейное неравенство
    private void linearInequality() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                int i = rnd(1, 22);
                int x = rnd();
                int a = rnd();
                int b = rnd();
                if (i < 5) {
                    int c = rnd();
                    int e = a * x + b * x + c;
                    String signB = "", signC = "";
                    if (b > 0) signB = "+";
                    else signB = " ";
                    if (c > 0) signC = "+";
                    else signC = " ";
                    textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "x" + signC + c + ">" + e + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X>" + x + "\n");
                } else if ((i > 5) && (i < 12)) {
                    int c = a * x + b;
                    String signB = "";
                    if (b > 0) signB = "+";
                    else signB = " ";
                    textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "<" + c + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X < " + x + "\n");
                } else if ((i > 12) && (i < 17)) {
                    int c = rnd();
                    int e = a * x + b * x + c;
                    String signB = "", signC = "";
                    if (b > 0) signB = "+";
                    else signB = " ";
                    if (c > 0) signC = "+";
                    else signC = " ";
                    textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "x" + signC + c + "<=" + e + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X<=" + x + "\n");
                } else {
                    int c = a * x + b;
                    String signB = "";
                    if (b > 0) signB = "+";
                    else signB = " ";
                    textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + ">=" + c + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X >= " + x + "\n");
                }
            }
        }
    }

    // Решить квадратное неравенство
    private void quadraticInequality() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                fotQuadIneq();
            }
        }
    }

    private void fotQuadIneq() {
        int a = rnd();
        int b = rnd();
        int c = rnd();
        String fx1, fx2;
        double res = Math.sqrt(b * b - 4 * a * c);
        if (res == (Math.floor(res))) {
            double x1 = ((-1 * b) + res) / (2 * a);
            double chislitel = ((-1 * b) + res), znamenatel = (2 * a);
            if (x1 % 1 == 0) {
                fx1 = Integer.toString((int) x1);
            } else {
                if (length(x1) < 3) {
                    fx1 = Double.toString(x1);
                } else {
                    fx1 = (int) chislitel + "/" + (int) znamenatel;
                }
            }
            double x2 = ((-1 * b) - res) / (2 * a);
            chislitel = ((-1 * b) - res);
            znamenatel = (2 * a);
            if (x2 % 1 == 0) {
                fx2 = Integer.toString((int) x2);
            } else {
                if (length(x2) < 3) {
                    fx2 = Double.toString(x2);
                } else {
                    fx2 = (int) chislitel + "/" + (int) znamenatel;
                }
            }
            String signB = "", signC = "", znak = "";
            if (b > 0) signB = "+";
            else signB = " ";
            if (c > 0) {
                signC = "+";
                znak = "<";
            } else {
                signC = " ";
                znak = ">";
            }
            textArea.setText(textArea.getText() + counter + ") " + a + "x²" + signB + b + "x" + signC + c + znak + "0" + "\n");
            if (res > 0) {
                if ((x2 > x1) && (znak.equals(">"))) {
                    textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(-∞;" + fx1 + ")⋃(" + fx2 + "+∞)" + "\n");
                } else if ((x2 > x1) && (znak.equals("<"))) {
                    textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(" + fx1 + ";" + fx2 + ")" + "\n");
                } else if ((x1 > x2) && (znak.equals(">"))) {
                    textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(-∞;" + fx2 + ")⋃(" + fx1 + "+∞)" + "\n");
                } else if ((x1 > x2) && (znak.equals("<"))) {
                    textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(" + fx2 + ";" + fx1 + ")" + "\n");
                }
            } else if ((res == 0) && (znak.equals(">"))) {
                textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(-∞;" + fx1 + ")⋃(" + fx1 + "+∞)" + "\n");
            } else if ((res == 0) && (znak.equals("<"))) {
                textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n");
            } else if ((res < 0) && (znak.equals(">"))) {
                textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    (-∞;+∞)" + "\n");
            } else if ((res < 0) && (znak.equals("<"))) {
                textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n");
            }
        } else {
            fotQuadIneq();
        }
    }

    // Решить рациональное неравенство
    private void rationalInequality() {
        textArea.clear();
        textArea2.clear();
        for (int var = 0; var < Integer.parseInt(varField.getText()); var++) {
            int var2 = var;
            textArea.setText(textArea.getText() + "Вариант - " + ++var + "\n");
            textArea2.setText(textArea2.getText() + "Вариант - " + ++var2 + "\n");
            var--;
            for (int count = 0; count < Integer.parseInt(countField.getText()); count++) {
                counter = ++count;
                count--;
                int iter = rnd(1, 20);
                if (iter < 6) {
                    int i = rnd(1, 22);
                    int x = rnd();
                    int a = rnd();
                    int b = rnd();
                    if (i < 5) {
                        int c = rnd();
                        int e = a * x + b * x + c;
                        String signB = "", signC = "";
                        if (b > 0) signB = "+";
                        else signB = " ";
                        if (c > 0) signC = "+";
                        else signC = " ";
                        textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "x" + signC + c + ">" + e + "\n");
                        textArea2.setText(textArea2.getText() + counter + ") " + "X>" + x + "\n");
                    } else if ((i > 5) && (i < 12)) {
                        int c = a * x + b;
                        String signB = "";
                        if (b > 0) signB = "+";
                        else signB = " ";
                        textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "<" + c + "\n");
                        textArea2.setText(textArea2.getText() + counter + ") " + "X < " + x + "\n");
                    } else if ((i > 12) && (i < 17)) {
                        int c = rnd();
                        int e = a * x + b * x + c;
                        String signB = "", signC = "";
                        if (b > 0) signB = "+";
                        else signB = " ";
                        if (c > 0) signC = "+";
                        else signC = " ";
                        textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + "x" + signC + c + "<=" + e + "\n");
                        textArea2.setText(textArea2.getText() + counter + ") " + "X<=" + x + "\n");
                    } else {
                        int c = a * x + b;
                        String signB = "";
                        if (b > 0) signB = "+";
                        else signB = " ";
                        textArea.setText(textArea.getText() + counter + ") " + a + "x" + signB + b + ">=" + c + "\n");
                        textArea2.setText(textArea2.getText() + counter + ") " + "X >= " + x + "\n");
                    }
                } else if ((iter > 6) && (iter < 12)) {
                    fotQuadIneq();
                }
            }
        }
    }
}

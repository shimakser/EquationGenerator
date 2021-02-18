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
import java.text.DecimalFormat;
import java.util.ArrayList;
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
            if (comboBox.getValue().equals("2.1.Вычислить n-й член арифметической прогрессии")) {
                progressInput.setVisible(true);
                progressTxt.setVisible(true);
                textArea2.clear();
                textArea.setText("Ниже в соответствующем поле через\nпробел укажите, какой n-член нужно\nнайти и сколько членов прогрессии\nзаранее известно");
            } else if (comboBox.getValue().equals("2.2.Вычислить сумму первых n членов арифметической прогрессии")) {
                progressInput.setVisible(true);
                progressTxt.setVisible(true);
                textArea2.clear();
                textArea.setText("Ниже в соответствующем поле укажите\nсумму скольких n-членов прогрессии\nнужно найти");
            } else if (comboBox.getValue().equals("2.3.Вычислить разницу арифметической прогрессии d")) {
                progressInput.setVisible(true);
                progressTxt.setVisible(true);
                textArea2.clear();
                textArea.setText("Ниже в соответствующем поле через\nпробел укажите два индекса заранее\nизвестных элементов прогресии");
            } else {
                progressInput.setVisible(false);
                progressTxt.setVisible(false);
            }
        });


        //Кнопка "Генерировать"
        btnGenerate.setOnAction(event -> {
            if (comboBox.getValue().equals("1.1.Линейное уравнение")) {
                if (border()) linearEquation();
            } else if (comboBox.getValue().equals("1.2.Квадратное уравнение")) {
                if (border()) squareEquation();
            } else if (comboBox.getValue().equals("2.1.Вычислить n-й член арифметической прогрессии")) {
                if (border()) searchNElemOfProgress();
            } else if (comboBox.getValue().equals("2.2.Вычислить сумму первых n членов арифметической прогрессии")) {
                if (border()) sumOfNNumOfProgress();
            } else if (comboBox.getValue().equals("2.3.Вычислить разницу арифметической прогрессии d")) {
                if (border()) differenceOfProgress();
            } else if (comboBox.getValue().equals("3.1.Решить систему уравнений из двух линейных уравнений с двумя неизвестными")) {
                if (border()) systLineEquatOfTwoWithTwo();
            } else if (comboBox.getValue().equals("3.2.Решить систему уравнений из трех линейных уравнений с тремя неизвестными")) {
                if (border()) systLineEquatOfThreeWithThree();
            } else if (comboBox.getValue().equals("3.3.Решить систему уравнений из двух однородных уравнений с двумя неизвестными")) {
                if (border()) systOdnEquatOfTwoWithTwo();
            } else if (comboBox.getValue().equals("4.1.Решить линейное неравенство")) {
                if (border()) linearInequality();
            } else if (comboBox.getValue().equals("4.2.Решить квадратное неравенство")) {
                if (border()) quadraticInequality();
            } else if (comboBox.getValue().equals("4.3.Решить рациональное неравенство")) {
                if (border()) rationalInequality();
            } else if (comboBox.getValue().equals("5.1.Задача про туриста")) {
                if (border()) touristTask();
            } else if (comboBox.getValue().equals("5.2.Задача про самолёт")) {
                if (border()) airplaneTask();
            } else if (comboBox.getValue().equals("5.3.Задача про машину")) {
                if (border()) carTask();
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

    DecimalFormat decimalFormat = new DecimalFormat("#.##");

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

    //1.2.Генератор квадратных уравнений
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
        ObservableList<String> langs = FXCollections.observableArrayList("1.1.Линейное уравнение", "1.2.Квадратное уравнение", "2.1.Вычислить n-й член арифметической прогрессии"
                , "2.2.Вычислить сумму первых n членов арифметической прогрессии", "2.3.Вычислить разницу арифметической прогрессии d", "3.1.Решить систему уравнений из двух линейных уравнений с двумя неизвестными"
                , "3.2.Решить систему уравнений из трех линейных уравнений с тремя неизвестными", "3.3.Решить систему уравнений из двух однородных уравнений с двумя неизвестными"
                , "4.1.Решить линейное неравенство", "4.2.Решить квадратное неравенство", "4.3.Решить рациональное неравенство", "5.1.Задача про туриста", "5.2.Задача про самолёт", "5.3.Задача про машину");
        comboBox.setItems(langs);
        comboBox.setValue("1.1.Линейное уравнение"); // устанавливаем выбранный элемент по умолчанию
        fieldFile.setText("Введите имя файла");
        progressTxt.setVisible(false);
        progressInput.setVisible(false);
    }

    //1.1.Генератор линейных уравнений
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

    // 2.1.Поиск n-члена прогрессии
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
                try {
                    String inputStr = progressInput.getText();
                    int space = inputStr.indexOf(" ");
                    int searchingN = Integer.parseInt(inputStr.substring(0, space));
                    int nCount = Integer.parseInt(inputStr.substring(space + 1));
                    int a1 = rnd(1, 50);
                    int aExe = a1;
                    int d = rnd(1, 10);
                    ArrayList<Integer> arrWithA = new ArrayList<>();
                    for (int i = 0; i < nCount; i++) {
                        arrWithA.add(a1);
                        a1 += d;
                    }
                    int an = aExe + (d * (searchingN - 1));
                    textArea.setText(textArea.getText() + counter + ") " + arrWithA + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + an + "\n");
                } catch (StringIndexOutOfBoundsException ex) {
                    textArea2.clear();
                    textArea.setText("Ниже в соответствующем поле через\nпробел укажите, какой n-член нужно\nнайти и сколько членов прогрессии\nзаранее известно");
                }
            }
        }
    }

    // 2.2.Сумма первых n членов прогрессии
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
                try {
                    int a1 = rnd(1, 50);
                    int d = rnd(1, 10);
                    int n = Integer.parseInt(progressInput.getText());
                    int result = n * (2 * a1 + d * (n - 1)) / 2;

                    textArea.setText(textArea.getText() + counter + ") " + "a₁=" + a1 + ", d=" + d + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + result + "\n");
                } catch (NumberFormatException ex) {
                    textArea2.clear();
                    textArea.setText("Ниже в соответствующем поле укажите\nсумму скольких n-членов прогрессии\nнужно найти");
                }
            }
        }
    }


    // 2.3.Вычислить разницу арифметической прогрессии d
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
                try {
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
                    String dStr = decimalFormat.format(d);
                    textArea2.setText(textArea2.getText() + counter + ") " + dStr + "\n");
                } catch (StringIndexOutOfBoundsException ex) {
                    textArea2.clear();
                    textArea.setText("Ниже в соответствующем поле через\nпробел укажите два индекса заранее\nизвестных элементов прогресии");
                }
            }
        }
    }

    // 3.1.Решить систему уравнений из двух линейных уравнений с двумя неизвестными
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
                String sY = decimalFormat.format(y);
                String sX = decimalFormat.format(x);
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

    // 3.2.Решить систему уравнений из трех линейных уравнений с тремя неизвестными
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

                int n = 3;
                int m = 3;
                double[][] A = new double[100][100];
                double[] b = new double[100];
                for (int i = 0; i < n; i++) {
                    A[i] = new double[100];
                    for (int j = 0; j < m; j++) {
                        A[i][j] = rnd(1, 30);
                    }
                    b[i] = rnd(1, 30);
                }
                textArea.setText(textArea.getText() + counter + ") " + String.format("%.0f", A[0][0]) + "x₁+" + String.format("%.0f", A[0][1]) + "x₂+" + String.format("%.0f", A[0][2]) + "x₃=" + String.format("%.0f", b[0]) + "\n    "
                        + String.format("%.0f", A[1][0]) + "x₁+" + String.format("%.0f", A[1][1]) + "x₂+" + String.format("%.0f", A[1][2]) + "x₃=" + String.format("%.0f", b[1]) + "\n    "
                        + String.format("%.0f", A[2][0]) + "x₁+" + String.format("%.0f", A[2][1]) + "x₂+" + String.format("%.0f", A[2][2]) + "x₃=" + String.format("%.0f", b[2]) + "\n");

                /* Метод Гаусса */
                int N = n;
                for (int p = 0; p < N; p++) {

                    int max = p;
                    for (int i = p + 1; i < N; i++) {
                        if (Math.abs(A[i][p]) > Math.abs(A[max][p])) {
                            max = i;
                        }
                    }
                    double[] temp = A[p];
                    A[p] = A[max];
                    A[max] = temp;
                    double t = b[p];
                    b[p] = b[max];
                    b[max] = t;

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
                if (n < m) {

                } else {
                    int j = 1;
                    textArea2.setText(textArea2.getText() + counter + ") ");
                    for (int i = 0; i < N; i++) {
                        String sX = decimalFormat.format(x[i]);
                        ;
                        textArea2.setText(textArea2.getText() + "X" + j + "=" + sX + "; ");
                        j++;
                    }
                    textArea2.setText(textArea2.getText() + "\n");
                }

            }
        }
    }

    // 3.3.Решить систему уравнений из двух однородных уравнений с двумя неизвестными
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
                double x = rnd(1, 8);
                double y = rnd(1, 8);
                double a1 = rnd(1, 6);
                double a2 = rnd(1, 6);
                double b1 = rnd(1, 6);
                double b2 = rnd(1, 6);
                double c1 = rnd(1, 6);
                double c2 = rnd(1, 6);
                double d1 = (double) (a1 * Math.pow(x, 2) - b1 * x * y + c1 * Math.pow(y, 2));
                double d2 = (double) (a2 * Math.pow(x, 2) + b2 * x * y - c2 * Math.pow(y, 2));
                textArea.setText(textArea.getText() + counter + ") " + (int) a1 + "x²-" + (int) b1 + "xy+" + (int) c1 + "y²=" + (int) d1 + "\n    " +
                        (int) a2 + "x²+" + (int) b2 + "xy-" + (int) c2 + "y²=" + (int) d2 + "\n");
                textArea2.setText(textArea2.getText() + counter + ") X=" + (int) x + ", Y=" + (int) y + ".\n");
            }
        }
    }

    // 4.1.Решить линейное неравенство
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
                int iter = rnd(1, 4);
                int x = rnd(1, 15);
                int a = rnd(1, 15);
                int b = rnd(1, 15);
                int c = rnd(1, 15);
                if (iter == 1) {
                    int y = a * x - b;
                    textArea.setText(textArea.getText() + counter + ") " + a + "x-" + b + "<" + y + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X < " + x + "\n");
                } else if (iter == 2) {
                    int y = a * x + b;
                    textArea.setText(textArea.getText() + counter + ") " + a + "x+" + b + ">=" + y + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X >= " + x + "\n");
                } else if (iter == 3) {
                    int y = a * x + b * x - c;
                    textArea.setText(textArea.getText() + counter + ") " + a + "x+" + b + "x-" + c + ">" + y + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X>" + x + "\n");
                } else if (iter == 4) {
                    int y = a * x + b * x - c;
                    textArea.setText(textArea.getText() + counter + ") " + a + "x+" + b + "x-" + c + "<=" + y + "\n");
                    textArea2.setText(textArea2.getText() + counter + ") " + "X<=" + x + "\n");
                }
            }
        }
    }

    // 4.2.Решить квадратное неравенство
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
        int a = rnd(1, 10);
        int b = rnd(1, 10);
        int c = rnd(1, 10);
        double res = Math.sqrt(b * b - 4 * a * c);
        if (res == (Math.floor(res))) {
            double x1 = ((-1 * b) + res) / (2 * a);
            double x2 = ((-1 * b) - res) / (2 * a);
            String fx1 = decimalFormat.format(x1);
            String fx2 = decimalFormat.format(x2);
            int iter = rnd(1, 2);
            if (iter == 1) {
                textArea.setText(textArea.getText() + counter + ") " + a + "x²+" + b + "x+" + c + ">0.\n");
                if (res <= 0) {
                    textArea2.setText(textArea2.getText() + counter + ") X∈(-∞;+∞).\n");
                } else if (res >= 0) {
                    if (x2 >= x1) {
                        textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(-∞;" + fx1 + ")⋃(" + fx2 + ";+∞).\n");
                    } else if (x1 >= x2) {
                        textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(-∞;" + fx2 + ")⋃(" + fx1 + ";+∞).\n");
                    }
                }
            } else if (iter == 2) {
                textArea.setText(textArea.getText() + counter + ") " + a + "x²+" + b + "x+" + c + "<0.\n");
                if (res > 0) {
                    if (x2 >= x1) {
                        textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(" + fx1 + ";" + fx2 + ").\n");
                    } else if (x1 >= x2) {
                        textArea2.setText(textArea2.getText() + counter + ") " + "X₁=" + fx1 + ", X₂=" + fx2 + "\n    X∈(" + fx2 + ";" + fx1 + ").\n");
                    }
                } else if (res <= 0) textArea2.setText(textArea2.getText() + counter + ") X∈ø.\n");
            }
        } else {
            fotQuadIneq();
        }
    }

    // 4.3.Решить рациональное неравенство
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
                int a = rnd(1, 10);
                int b = rnd(1, 10);
                int iter = rnd(1, 2);
                if (iter == 1) {
                    textArea.setText(textArea.getText() + counter + ") x/(x+" + a + ") < -" + b + "\n");
                    double x2 = ((double) (b * a) / (double) (b + 1));
                    String x2S = decimalFormat.format(x2);
                    if (-a > -x2)
                        textArea2.setText(textArea2.getText() + counter + ") X∈(-" + x2S + ";" + -a + ").\n");
                    else if (-x2 > -a)
                        textArea2.setText(textArea2.getText() + counter + ") X∈(" + -a + ";-" + x2S + ").\n");
                } else if (iter == 2) {
                    textArea.setText(textArea.getText() + counter + ") x(" + a + " - x)/(x-" + b + ") >= 0\n");
                    if (a == b) {
                        textArea2.setText(textArea2.getText() + counter + ") X∈(-∞;0]\n");
                    } else if (a < b) {
                        textArea2.setText(textArea2.getText() + counter + ") X∈(-∞;0]⋃[" + a + ";" + b + ").\n");
                    } else {
                        textArea2.setText(textArea2.getText() + counter + ") X∈(-∞;0]⋃(" + b + ";" + a + "].\n");
                    }
                }
            }
        }
    }

    // 5.1.Задача про туриста
    private void touristTask() {
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
                double v1 = rnd(2, 5); //  скорость и расстояние первого участка пути
                double v2 = rnd(6, 9);
                double t1 = rnd(10, 40);
                double t2 = rnd(10, 40);
                double diffInTime = (double) ((t1 / 60) + (t2 / 60)); // Разница во времени
                double diffSpeed = (double) ((1 / v1) - (1 / v2)); // Разница скоростей
                double secondHulfOfWay = (double) (diffInTime * diffSpeed);
                double allWay = (double) (v1 + secondHulfOfWay);
                String strWay = decimalFormat.format(allWay);
                textArea.setText(textArea.getText() + counter + ")Турист, идущий из деревни на\nжелезнодорожную станцию, пройдя\nза первый час " + String.format("%.0f", v1) + " км, "
                        + "рассчитал, что он\nопоздает к поезду на " + String.format("%.0f", t1) + " мин., если\nбудет двигаться с той же скоростью."
                        + "\nПоэтому остальной путь он проходит со\nскоростью " + String.format("%.0f", v2) + " км/ч и прибывает на\nстанцию за " + String.format("%.0f", t2) + " мин. до отхода поезда."
                        + "\nКаково расстояние от деревни до\nстанции?" + "\n");
                textArea2.setText(textArea2.getText() + counter + ") " + strWay + " км.\n");
            }
        }
    }

    // 5.2.Задача про самолёт
    private void airplaneTask() {
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
                double v1 = rnd(170, 280);
                double v2 = rnd(290, 390);
                double p = rnd(200, 500);
                double s = rnd(700, 950);
                double result = (double) (2 * s - p);

                double ff = (double) ((s / v1) + ((s - p) / v2));
                double av = (double) (result / ff);
                String res = decimalFormat.format(result);
                textArea.setText(textArea.getText() + counter + ")Самолет летел сначала со скоростью\n" + String.format("%.0f", v1) + " км/ч. Когда ему осталось пролететь\nна " + String.format("%.0f", p) + " км меньше, чем он пролетел, "
                        + "он\nизменил скорость и стал двигаться со\nскоростью " + String.format("%.0f", v2) + " км/ч. Средняя скорость\nсамолета на всем пути оказалась\nравной " + String.format("%.2f", av) + " км/ч. "
                        + "Какое расстояние\nпролетел самолет?" + "\n");
                textArea2.setText(textArea2.getText() + counter + ") " + res + " км.\n");
            }
        }
    }

    // 5.3.Задача про машину
    private void carTask() {
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
                int t = rnd(2, 5);
                int v1 = rnd(190, 290);
                int v2 = rnd(130, 180);
                double halfWay = v1 * t;
                double allTime = t + (halfWay / v2);
                String allTimeStr = decimalFormat.format(allTime);
                textArea.setText(textArea.getText() + counter + ")Половину маршрута гоночная\nмашина проехала за " + t + " часа со\nскоростью " + v1 + " км/ч,"
                        + "а остальное\nрасстояние – со скоростью " + v2 + " км/ч."
                        + "\nСколько времени гоночная машина\nпровела в пути?" + "\n");
                textArea2.setText(textArea2.getText() + counter + ") " + allTimeStr + " ч.\n");
            }
        }
    }
}

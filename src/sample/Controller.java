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
    void initialize() {
        init(); //инициализация программы

        textFieldProp(countField); //Защита текстовых полей
        textFieldProp(varField);

        //Кнопка "Генерировать"
        btnGenerate.setOnAction(event -> {
            if (comboBox.getValue().equals("Линейное уравнение")) {
                if (border()) linearEquation();
            } else if (comboBox.getValue().equals("Квадратное уравнение")) {
                if (border()) squareEquation();
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
            textArea2.setText(textArea2.getText() + counter + ") " + "X1=" + fx1 + "; X2=" + fx2 + "\n");
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
        ObservableList<String> langs = FXCollections.observableArrayList("Линейное уравнение", "Квадратное уравнение");
        comboBox.setItems(langs);
        comboBox.setValue("Линейное уравнение"); // устанавливаем выбранный элемент по умолчанию
        fieldFile.setText("Введите имя файла");
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
}

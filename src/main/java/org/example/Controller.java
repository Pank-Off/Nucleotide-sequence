package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private String oldValue = "";
    private char key;

    @FXML
    public TextArea input;
    @FXML
    public TextArea output;

    private Map<Character, Double> map = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        input.textProperty().addListener((observable, oldValue, newValue) -> calculate(newValue));
    }

    private void calculate(String newValue) {

        int newLength = newValue.length();
        int oldLength = oldValue.length();
        if (newLength == 0) {
            output.clear();
            map.clear();
            return;
        }

        if (newLength < oldLength) {
            key = Character.toUpperCase(oldValue.charAt(oldLength - 1));
            map.put(key, map.getOrDefault(key, 1.0) - 1.0);
        } else {
            key = Character.toUpperCase(newValue.charAt(newLength - 1));
            map.put(key, map.getOrDefault(key, 0.0) + 1.0);
        }
        if (map.get(key) == 0) {
            map.remove(key);
        }
        oldValue = newValue;

        output.clear();
        for (Map.Entry<Character, Double> o : map.entrySet()) {
            double value = o.getValue() / newValue.length() * 100;

            DecimalFormat decimalFormatForResult = new DecimalFormat("#.##");
            String result = decimalFormatForResult.format(value);
            output.appendText(o.getKey() + ": " + o.getValue() + " (" + result + "%)\n");
        }

    }

}

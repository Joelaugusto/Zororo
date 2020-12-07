package sample.util;


import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyEvent;

public class TextUtil {

    private final Conversao conversao = new Conversao();

    public TextUtil() {

    }

    public void numberOnly(KeyEvent e){
        int number;
        JFXTextField textField = (JFXTextField) e.getSource();
        number = conversao.StringToInt(manterSomenteNumero(textField.getText()));
        textField.setText(number+"");
        if(number>0){
            textField.setStyle("-jfx-focus-color: green;-fx-text-inner-color: green");
        }else{
            textField.setStyle("-jfx-focus-color: red;-fx-text-inner-color: red");
        }
    }

    public String manterSomenteNumero(String text){
        String newString="";
        for(int i=0; i<text.length();i++){
            if(isNumber(text.charAt(i))){
                newString.concat(text.charAt(i)+"");
            }
        }
        return newString;
    }
    public ChangeListener apenasDigitoInt(JFXTextField textField){
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        };
    }

    public ChangeListener apenasDigitoFloat(JFXTextField textField){
        return (ChangeListener<String>) (observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                textField.setText(oldValue);
            }
        };
    }

    public boolean validarTextFieldNumber(JFXTextField [] textField){

        for (JFXTextField jfxTextField: textField){
            if(!conversao.StringToDouble(jfxTextField.getText())){
                return false;
            }
        }
        return true;
    }

    public boolean validarTextFieldNumberInt(JFXTextField [] textField){

        for (JFXTextField jfxTextField: textField){
            if(!conversao.StringToInteger(jfxTextField.getText())){
                return false;
            }
        }
        return true;
    }

    public boolean validarTextFieldNumber(JFXTextField textField){
        return false;
    }

    private boolean isNumber(char c){
        return conversao.StringToInteger(c+"");
    }





}

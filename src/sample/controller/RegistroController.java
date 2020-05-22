package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;


public class RegistroController implements Initializable {

        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private JFXTextField nomeProduto;

        @FXML
        private JFXTextField preco;

        @FXML
        private JFXTextField quantidade;

        @FXML
        private JFXButton btnRegistro;

        @FXML
        private JFXTextField precoCaixa;

        @FXML
        private Label lblLucro;

        private boolean camposNumeroValidos = false;

        @FXML
        void initialize() {


        }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preco.setOnKeyReleased(e->tfEvent(e));
        precoCaixa.setOnKeyReleased(e->tfEvent(e));
        quantidade.setOnKeyReleased(e->tfEvent(e));
        btnRegistro.setOnAction(e->registrarProduto());
        nomeProduto.setOnKeyReleased(e->validarNomeProduto());
        btnRegistro.setDisable(true);

    }

    public void registrarProduto(){
        System.out.println("vai registrar");
    }

    private void setLucro(){
                int quant;
                float price, priceCaixa;

                try {
                        quant = Integer.parseInt(quantidade.getText());
                        price = Float.parseFloat(preco.getText());
                        priceCaixa = Float.parseFloat(precoCaixa.getText());
                        float lucro = calcularLucro(price,priceCaixa,quant);
                        lblLucro.setText("Lucro : "+lucro+" MT");
                        if(lucro>0){
                                labelColor(true); //muda a cor do label dependendo do lucro
                        }else{
                                labelColor(false);
                        }
                }catch (NumberFormatException n){
                        lblLucro.setText("Não Definido");
                        labelColor(false);
                }

    }

    private float calcularLucro(float preco, float precoCaixa, int quantidade){
                return preco*quantidade-precoCaixa;
    }

    private void labelColor(boolean color){
                if (color){
                        lblLucro.setTextFill(Color.web("#00f"));
                        btnRegistro.setDisable(false);
                        validarNomeProduto();
                        camposNumeroValidos = true;
                }else{
                        lblLucro.setTextFill(Color.web("#f00"));
                        btnRegistro.setDisable(true);
                }
    }

    private void tfEvent(KeyEvent e){
            setLucro();
            validarTextFieldNumero((JFXTextField) e.getSource());
    }

    private void validarTextFieldNumero(JFXTextField txt){
        float valor;
        try {
            valor = Float.parseFloat(txt.getText());
            campoValidado(true, txt);
            if(valor<=0){
                campoValidado(false,txt);
            }
        }catch (NumberFormatException e){
            //System.out.println("Erro na Conversao do numero");
            campoValidado(false,txt);
        }
    }

    private void validarNomeProduto(){
        if(this.nomeProduto.getText().equals(null) || this.nomeProduto.getText().equals("")){
            campoValidado(false, this.nomeProduto);
            btnRegistro.setDisable(true);
        }else{
            campoValidado(true, this.nomeProduto);
            if(camposNumeroValidos){
                btnRegistro.setDisable(false);
            }
        }
    }

    private void campoValidado(boolean x, JFXTextField txt){
        if(x){
            txt.setStyle("-fx-text-inner-color: green");
        }else{
            txt.setStyle("-fx-text-inner-color: red");
        }
    }
}

package sample.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import sample.model.modelo.Categoria;
import sample.model.modelo.Produto;

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

    @FXML
    private JFXComboBox<?> categoria;

    @FXML
    private Label notificacao;

    @FXML
    void initialize() {

    }

        private boolean camposNumeroValidos = false;
        final private ProdutoController pc;
        final private CategoriaController cc;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        preco.setOnKeyReleased(e->tfEvent(e));
        precoCaixa.setOnKeyReleased(e->tfEvent(e));
        quantidade.setOnKeyReleased(e->tfEvent(e));
        btnRegistro.setOnAction(e->registrarProduto());
        nomeProduto.setOnKeyReleased(e->validarNomeProduto());
        //categoria.setOnAction(e->validarCategoria());
        btnRegistro.setDisable(true);
        preencherCB();
        categoria.getSelectionModel().selectFirst();

    }

    public RegistroController(){
            pc = new ProdutoController();
            cc = new CategoriaController();
    }

    public void registrarProduto(){

        Produto p = new Produto();
        p.setNome(nomeProduto.getText());
        p.setPrecoCaixa(Float.parseFloat(precoCaixa.getText()));
        p.setValor(Float.parseFloat(preco.getText()));
        p.setUnidadesPorCaixa(Short.parseShort(quantidade.getText()));

        Categoria c = cc.findByName(categoria.getSelectionModel().getSelectedItem().toString());
        p.setCategoria(c);

        System.out.println(p );
        pc.registrarProduto(p);
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
        if(this.nomeProduto.getText().equals(null) || this.nomeProduto.getText().equals("")||
                pc.verificarExistencia(nomeProduto.getText())){

            campoValidado(false, this.nomeProduto);
            btnRegistro.setDisable(true);

            if(!this.nomeProduto.getText().equals(null) & !this.nomeProduto.getText().equals("")){
                notificacao.setText("O Produto já existe!");
            }
        }else{
            campoValidado(true, this.nomeProduto);
            if(camposNumeroValidos){
                btnRegistro.setDisable(false);
            }
            if(!pc.verificarExistencia(nomeProduto.getText())){
                notificacao.setText("");
            }
        }
    }

    private void campoValidado(boolean x, JFXTextField txt){
        if(x){
            txt.setStyle("-jfx-focus-color: green;-fx-text-inner-color: green");
        }else{
            txt.setStyle("-jfx-focus-color: red;-fx-text-inner-color: red");
        }
    }

    private void preencherCB() {
        categoria.setItems(cc.getAllNomeCategoria());
    }

   /* private void validarCategoria(){
        if(categoria.getSelectionModel().getSelectedItem().equals(null)){
            categoria.setStyle("-fx-text-inner-color: red");
            btnRegistro.setDisable(true);
        }else{
            categoria.setStyle("-fx-text-inner-color: green");
            btnRegistro.setDisable(false);
        }
    }*/

}

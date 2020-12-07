package sample.controller.ui;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.controller.model.CategoriaController;
import sample.controller.model.ProdutoController;
import sample.controller.model.UnidadeControleer;
import sample.model.modelo.Categoria;
import sample.model.modelo.Produto;
import sample.model.modelo.Unidade;
import sample.util.Conversao;
import sample.util.MessageUtil;
import sample.util.TextUtil;

import java.net.URL;
import java.util.ResourceBundle;


public class RegistroController implements Initializable {


    @FXML
    private StackPane pane;

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
    private Label lbl_preco_venda;

    @FXML
    private Label lbl_preco_compra;

    @FXML
    private Label lbl_quantidade;

    @FXML
    private ImageView ivQuantidade;

    @FXML
    private JFXComboBox<String> categoria;

    @FXML
    private JFXComboBox<String> cb_unidade;

    @FXML
    private Label notificacao;

    private final Conversao conversao = new Conversao();

    private boolean camposNumeroValidos = false;

    private final ProdutoController pc = new ProdutoController();
    private final CategoriaController cc = new CategoriaController();
    private final MessageUtil messageUtil = new MessageUtil();
    private final TextUtil textUtil = new TextUtil();
    private final UnidadeControleer unidadeControleer= new UnidadeControleer();
    private ObservableList<Unidade> unidades = unidadeControleer.getAll();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start();
    }

    @FXML
    void initialize() {
        start();
    }

    public void start() {
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'registro.fxml'.";
        preco.setOnKeyReleased(this::tfEvent);
        precoCaixa.setOnKeyReleased(this::tfEvent);
        quantidade.setOnKeyReleased(this::tfEvent);
        btnRegistro.setOnAction(e->registrarProduto());
        desativarTF(true);
        cb_unidade.setOnAction(e->{
            limparCamposMenosNome();
            Unidade unidade = unidades.get(cb_unidade.getSelectionModel().getSelectedIndex());
            desativarTF(false);
            lbl_preco_compra.setText("por cada "+unidade.getUnidade());
            lbl_preco_venda.setText("por cada "+unidade.getUnidade());
            //lblLucro.setText("lucro por cada "+unidade.getUnidade());

            if(unidade.isPermiteQuantidade()){
                lbl_quantidade.setText("em cada "+unidade.getUnidade());
                lbl_preco_venda.setText("por unidade");
                quantidade.setVisible(true);
                ivQuantidade.setVisible(true);
            }else{
                quantidade.setVisible(false);
                ivQuantidade.setVisible(false);
                lbl_quantidade.setText("");
            }
        });
        nomeProduto.setOnKeyReleased(e->validarNomeProduto());
        categoria.setOnAction(e->validarCategoria());
        btnRegistro.setDisable(true);
        preencherCB();
        categoria.getSelectionModel().selectFirst();

        // force the field to be numeric only
        preco.textProperty().addListener(textUtil.apenasDigitoFloat(preco));
        precoCaixa.textProperty().addListener(textUtil.apenasDigitoFloat(precoCaixa));
        quantidade.textProperty().addListener(textUtil.apenasDigitoInt(quantidade));

    }

    public void registrarProduto(){

        Unidade unidade = unidades.get(cb_unidade.getSelectionModel().getSelectedIndex());
        Produto p = new Produto();
        p.setNome(nomeProduto.getText().toUpperCase());
        p.setPrecoCaixa(Float.parseFloat(precoCaixa.getText()));
        p.setValor(Float.parseFloat(preco.getText()));

        if(unidade.isPermiteQuantidade()) {
            p.setUnidadesPorCaixa(Short.parseShort(quantidade.getText()));
        }else{
            p.setUnidadesPorCaixa((short)1);
        }


        p.setUnidade(unidades.get(cb_unidade.getSelectionModel().getSelectedIndex()));
        Categoria c = cc.findByName(categoria.getSelectionModel().getSelectedItem());
        p.setCategoria(c);
        pc.registrarProduto(p);
        limparCampos();
        mostrarMensagem();
    }

    private void limparCampos(){
        nomeProduto.setText("");
        limparCamposMenosNome();
    }

    private void limparCamposMenosNome(){
        preco.setText("");
        precoCaixa.setText("");
        quantidade.setText("");
        lblLucro.setText("");
        btnRegistro.setDisable(true);

    }

    private void setLucro(){

        Unidade unidade = unidades.get(cb_unidade.getSelectionModel().getSelectedIndex());

        if(unidade.isPermiteQuantidade()){
            try {
                int quant = Integer.parseInt(quantidade.getText());
                float price = Float.parseFloat(preco.getText());
                float priceCaixa = Float.parseFloat(precoCaixa.getText());
                float lucro = calcularLucro(price,priceCaixa,quant);

                lblLucro.setText("Lucro : "+lucro+" MT");

                labelColor(lucro > 0); //muda a cor do label dependendo do lucro
            }catch (NumberFormatException n){
                lblLucro.setText("Não Definido");
                labelColor(false);
            }

        }else{
            try {
                //int quant = Integer.parseInt(quantidade.getText());
                float price = Float.parseFloat(preco.getText());
                float priceCaixa = Float.parseFloat(precoCaixa.getText());
                float lucro = price - priceCaixa; //priceCaixa é preço de compra

                lblLucro.setText("Lucro : "+lucro+" MT por "+unidade.getUnidade());
                labelColor(lucro > 0); //muda a cor do label dependendo do lucro
            }catch (NumberFormatException n){
                lblLucro.setText("Não Definido");
                labelColor(false);
            }

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
            camposNumeroValidos = false;
        }
    }

    private void tfEvent(KeyEvent e){
        JFXTextField textField = (JFXTextField) e.getSource();
            setLucro();
            if(conversao.StringToDoub(textField.getText())>0){
                campoValidado(true, (JFXTextField) e.getSource());
            }else{
                campoValidado(false, (JFXTextField) e.getSource());
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
        for(Unidade unidade: unidades){
            cb_unidade.getItems().add(unidade.getUnidade());
        }
    }

    private void desativarTF(boolean desativar){
        preco.setDisable(desativar);
        precoCaixa.setDisable(desativar);
        quantidade.setDisable(desativar);
    }

    private void mostrarMensagem(){
        messageUtil.showMessage(pane,
                "O Produto foi registrado com sucesso!");
    }

   private void validarCategoria(){
        if(categoria.getSelectionModel().getSelectedItem().equals(null)){
            categoria.setStyle("-fx-text-inner-color: red");
            btnRegistro.setDisable(true);
        }else{
            categoria.setStyle("-fx-text-inner-color: green");
            btnRegistro.setDisable(false);
        }
    }

}

package sample.controller;

import com.jfoenix.controls.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.model.modelo.Produto;
import sample.util.Conversao;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LevantamentoController implements Initializable {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField tfQuantidade;

    @FXML
    private JFXButton btnAtualizarStock;

    @FXML
    private Label lblNomeProduto;

    @FXML
    private Label lblPreco;

    @FXML
    private JFXComboBox<String> cbListaProdutos;

    @FXML
    private JFXCheckBox ckbRemoverDepoisAtualizacao;

    @FXML
    private JFXRadioButton rbAdicionarUnidade;

    @FXML
    private JFXRadioButton rbAdicionarCaixa;

    private  ProdutoController pc;
    private Produto produto;
    private Conversao conversao;

    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pc = new ProdutoController();
        produto = new Produto();
        conversao = new Conversao();


        preencherCB();
        rbAdicionarUnidade.setSelected(true);
        rbAdicionarCaixa.setOnAction(e->ativarDesativarRb(e));
        rbAdicionarUnidade.setOnAction(e->ativarDesativarRb(e));
        cbListaProdutos.setOnAction(e->selecionarProduto(e));
        tfQuantidade.setOnKeyTyped(e->{
            validarTF(e);
        });
        btnAtualizarStock.setOnAction(e->atualizarStock());
        btnAtualizarStock.setDisable(true);

    }

    private void validarTF(Event event){
        JFXTextField aux = (JFXTextField) event.getSource();
        if(conversao.StringToInteger(aux.getText())){
            float x = Float.parseFloat(aux.getText());
            if(x>0){
                tfQuantidade.setStyle("-jfx-focus-color: green;-fx-text-inner-color: green");
                btnAtualizarStock.setDisable(false);
            }
        }else {
            tfQuantidade.setStyle("-jfx-focus-color: red;-fx-text-inner-color: red");
            btnAtualizarStock.setDisable(true);
        }

    }

    private void ativarDesativarRb(ActionEvent e){
        if(e.getSource().equals(this.rbAdicionarCaixa)){
            rbAdicionarUnidade.setSelected(false);
        }else{
            rbAdicionarCaixa.setSelected(false);
        }
    }
    private void preencherCB(){
        cbListaProdutos.setItems(pc.findAllNomeProduto());
        cbListaProdutos.getSelectionModel().selectFirst();//seleciona o primeiro produto
        preencherCampos();
    }

    private void selecionarProduto(ActionEvent e){
        if(cbListaProdutos.getSelectionModel().isEmpty()){
            System.out.println("Nada a mostrar");//corrigir depois;
        }else{
            preencherCampos();
        }
    }

    private void preencherCampos(){
        String selected = cbListaProdutos.getSelectionModel().getSelectedItem();
        produto = pc.getProduto(selected);
        lblNomeProduto.setText(produto.getNome());
        lblPreco.setText("Stock Existente : "+produto.getStock());
    }

    private void atualizarStock(){
        if(rbAdicionarUnidade.isSelected()){
           adicionarPorUnidade();
        }else{
            adicicionarPorCaixa();
        }
        reset();
    }

    private void adicionarPorUnidade(){
        produto.setStock(produto.getStock() + conversao.StringToInt(tfQuantidade.getText()));
        pc.update(produto);
    }

    private void adicicionarPorCaixa(){
        int newStock = produto.getStock()+
                conversao.StringToInt(tfQuantidade.getText()) * produto.getUnidadesPorCaixa();
        produto.setStock(newStock);
        pc.update(produto);
    }

    private void reset(){
        tfQuantidade.setText("");
        btnAtualizarStock.setDisable(true);
        preencherCampos();
        if(ckbRemoverDepoisAtualizacao.isSelected()){
            cbListaProdutos.getItems().remove(cbListaProdutos.getSelectionModel().getSelectedItem());
            cbListaProdutos.getSelectionModel().selectNext();
        }
    }
}

package sample.controller;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

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
    private JFXComboBox<?> cbListaProdutos;

    @FXML
    private JFXCheckBox ckbRemoverDepoisAtualizacao;

    @FXML
    private JFXRadioButton rbAdicionarUnidade;

    @FXML
    private JFXRadioButton rbAdicionarCaixa;

    @FXML
    void initialize() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        preencherCB();
        rbAdicionarUnidade.setSelected(true);
        rbAdicionarCaixa.setOnAction(e->ativarDesativarRb(e));
        rbAdicionarUnidade.setOnAction(e->ativarDesativarRb(e));
    }

    private void ativarDesativarRb(ActionEvent e){
        if(e.getSource().equals(this.rbAdicionarCaixa)){
            rbAdicionarUnidade.setSelected(false);
        }else{
            rbAdicionarCaixa.setSelected(false);
        }
    }
    private void preencherCB(){

    }

    private void atualizarStock(){
        if(rbAdicionarUnidade.isSelected()){
           adicionarPorUnidade();
        }else{
            adicicionarPorCaixa();
        }
    }

    private void adicionarPorUnidade(){

    }

    private void adicicionarPorCaixa(){

    }
}

package sample.controller.ui;

import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import sample.controller.model.ProdutoController;
import sample.controller.model.UnidadeControleer;
import sample.model.modelo.Produto;
import sample.model.modelo.Unidade;
import sample.util.Conversao;
import sample.util.TextUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class LevantamentoController implements Initializable {

    @FXML
    private JFXTextField tfQuantidade;

    @FXML
    private JFXButton btnAtualizarStock;

    @FXML
    private Label lblNomeProduto;

    @FXML
    private Label lblStock;

    @FXML
    private JFXComboBox<String> cbListaProdutos;

    @FXML
    private JFXCheckBox ckbRemoverDepoisAtualizacao;

    @FXML
    private JFXRadioButton rbAdicionarUnidade;

    @FXML
    private JFXRadioButton rbAdicionarCaixa;

    private final ProdutoController pc  = new ProdutoController();
    private final Conversao conversao = new Conversao();
    private final TextUtil textUtil = new TextUtil();
    private final UnidadeControleer unidadeControleer = new UnidadeControleer();
    private Produto produto;


    @FXML
    void initialize() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        preencherCB();
        rbAdicionarCaixa.setOnAction(this::ativarDesativarRb);
        rbAdicionarUnidade.setOnAction(this::ativarDesativarRb);
        cbListaProdutos.setOnAction(e->selecionarProduto());
        tfQuantidade.setOnKeyTyped(this::validarTF);
        tfQuantidade.textProperty().addListener(textUtil.apenasDigitoInt(tfQuantidade));
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
            rbAdicionarCaixa.setSelected(true);
        }else{
            rbAdicionarCaixa.setSelected(false);
            rbAdicionarUnidade.setSelected(true);
        }
    }

    private void preencherCB(){
        cbListaProdutos.setItems(pc.findAllNomeProduto());
        cbListaProdutos.getSelectionModel().selectFirst();//seleciona o primeiro produto
        preencherCampos();
    }

    private void selecionarProduto(){
        if(cbListaProdutos.getSelectionModel().isEmpty()){
            System.out.println("Nada a mostrar");//corrigir depois;
        }else{
            preencherCampos();
        }
    }

    private void preencherCampos(){
        String selected = cbListaProdutos.getSelectionModel().getSelectedItem();
        produto = pc.getProduto(selected);
        Unidade unidade = unidadeControleer.getByProdutoId(produto.getId());
        rbAdicionarCaixa.setText("Adicionar "+unidade.getUnidade());
        rbAdicionarUnidade.setVisible(unidade.isPermiteQuantidade());



        rbAdicionarUnidade.setVisible(true);
        if(unidade.isPermiteQuantidade()){
            rbAdicionarCaixa.setVisible(true);
            rbAdicionarUnidade.setText("Adicionar por unidade");
        }else{
            rbAdicionarCaixa.setVisible(false);
            rbAdicionarUnidade.setText("Adicionar em "+unidade.getUnidade());
            rbAdicionarUnidade.setSelected(true);
            rbAdicionarCaixa.setSelected(false);
        }

        lblNomeProduto.setText("Produto : "+produto.getNome());


        if(produto.getStock()%produto.getUnidadesPorCaixa()==0){
            lblStock.setText("Stock : "+produto.getStock()/produto.getUnidadesPorCaixa()
                    + " "+unidade.getUnidade());
        }else{
            if(produto.getStock()<produto.getUnidadesPorCaixa()){
                lblStock.setText("Stock : "+unidade.getUnidade()+" "+ produto.getStock()+ " Unidades");
            }else{
               lblStock.setText("Stock : "+produto.getStock()/produto.getUnidadesPorCaixa()+" "+
                        unidade.getUnidade()+ " " +produto.getStock()%produto.getUnidadesPorCaixa()+" Unidades");
            }
        }

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

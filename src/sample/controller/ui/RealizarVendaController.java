package sample.controller.ui;
import com.jfoenix.controls.*;

import java.util.ArrayList;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;
import org.controlsfx.control.textfield.TextFields;
import sample.controller.model.ProdutoController;
import sample.controller.model.VendaController;
import sample.model.modelo.Factura;
import sample.model.modelo.Produto;
import sample.model.modelo.Venda;
import sample.util.Conversao;

public class RealizarVendaController {

    /*@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;*/

    @FXML
    private JFXTextField tfProduto;

    @FXML
    private JFXTextField tf_quantidades;

    @FXML
    private JFXRadioButton rb_caixas;

    @FXML
    private JFXRadioButton rb_units;

    @FXML
    private JFXButton adicionarLista;

    @FXML
    private JFXButton concluirVenda;

    @FXML
    private Label lbl_existencia_produto;

    @FXML
    private JFXTreeTableView<ProdutoTB> carrinho_pnl;

    @FXML
    private Label total;

    @FXML
    private Label lbl_quantidade;

    @FXML
    private Label stock_existente;

    @FXML
    private Label preco_produto;

    @FXML
    private Label lbl_nomeProduto;

    /*@FXML
    private Label lbl_carrinho;

*/

    private ProdutoController pc;

    private ArrayList<Venda> carrinho;

    private Conversao conversao;

    private  Produto produto;

    private ObservableList<ProdutoTB> produtoTBs;

    private boolean nomeValidado = false, quantidadeValidado = false;

    private final VendaController vendaCOntroller = new VendaController();


    @FXML
    void initialize() {
        pc = new ProdutoController();
        Factura factura = new Factura();
        ObservableList suggestion = pc.findAllNomeProduto();
        carrinho = new ArrayList<Venda>();

        conversao = new Conversao();

        rb_caixas.setOnAction(this::ativarDesativarRB);
        rb_units.setOnAction(this::ativarDesativarRB);
        rb_caixas.setSelected(true);
        concluirVenda.setOnAction(e->finalizarVenda());

        JFXTreeTableColumn<ProdutoTB, String> nomePD = new JFXTreeTableColumn<>("Nome");
        nomePD.setPrefWidth(150);
        nomePD.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProdutoTB, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProdutoTB, String> param) {
                return param.getValue().getValue().nome;
            }
        });

        JFXTreeTableColumn<ProdutoTB, String> quantPD = new JFXTreeTableColumn<>("Quantidade");
        quantPD.setPrefWidth(60);
        quantPD.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProdutoTB, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProdutoTB, String> param) {
                return param.getValue().getValue().quantidade;
            }
        });

        JFXTreeTableColumn<ProdutoTB, String> precoPD = new JFXTreeTableColumn<>("Preço");
        precoPD.setPrefWidth(60);
        precoPD.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ProdutoTB, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<ProdutoTB, String> param) {
                return param.getValue().getValue().preco;
            }
        });


        produtoTBs = FXCollections.observableArrayList();

        TreeItem <ProdutoTB> root = new RecursiveTreeItem<ProdutoTB>(produtoTBs,RecursiveTreeObject::getChildren);

        carrinho_pnl.setMinSize(310,600);


        carrinho_pnl.getColumns().setAll(nomePD,quantPD,precoPD);
        carrinho_pnl.setRoot(root);
        carrinho_pnl.setShowRoot(false);
        carrinho_pnl.autosize();

        tf_quantidades.setOnKeyTyped(e->validarQuantidade());


        TextFields.bindAutoCompletion(tfProduto, suggestion);

        tfProduto.setOnKeyTyped(event -> validarNomeProduto());

        adicionarLista.setOnAction(e->{
            adicionarAoCarrinho();
            tfProduto.setText("");
            tf_quantidades.setText("");

        });

        //solução da internet
        tfProduto.textProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            validarNomeProduto();
        });

    }

    class ProdutoTB extends RecursiveTreeObject<ProdutoTB> {

        StringProperty nome;
        StringProperty quantidade;
        StringProperty preco;

        public ProdutoTB (String nome, String quantidade, String preco){
            this.nome = new SimpleStringProperty(nome);
            this.quantidade = new SimpleStringProperty(quantidade);
            this.preco = new SimpleStringProperty(preco);
        }

        public int getQuantidade(){
            return conversao.StringToInt(quantidade.getValue());
        }

        public float getPreco(){
            return (float) conversao.StringToDoub(preco.getValue());
        }

        //metodo adaptado
        private float precoProduto(){
            return getPreco()/getQuantidade();
        }

        public void adicionarQuantidade(int quantidadeAdicional){
            int quantidadeFinal = getQuantidade() + quantidadeAdicional;
            float precoFinal = quantidadeFinal*precoProduto();
            quantidade.setValue(quantidadeFinal+"");
            preco.setValue(precoFinal+"");
        }
    }

    private void validarNomeProduto(){

        if(pc.verificarExistencia(tfProduto.getText())){
                produto = pc.getProduto(tfProduto.getText());
                preencherLabel();
                tfProduto.setStyle("-jfx-focus-color: green;-fx-text-inner-color: green");
                lbl_existencia_produto.setVisible(false);
                nomeValidado = true;
                if (quantidadeValidado){
                    adicionarLista.setDisable(false);
                }
            }else{
                tfProduto.setStyle("-jfx-focus-color: red;-fx-text-inner-color: red");
                lbl_existencia_produto.setVisible(true);
                adicionarLista.setDisable(true);
            }
    }

    private void preencherLabel(){
        if(produto!=null){
            int stock = 0; // stock que já foi adicionado no carrinho
            for (int i = 0; i <produtoTBs.size() ; i++) {
                if(produtoTBs.get(i).nome.getValue().equalsIgnoreCase(produto.getNome())){
                    stock = produtoTBs.get(i).getQuantidade();
                }
            }
            lbl_nomeProduto.setText("Produto : "+produto.getNome());
            if(rb_caixas.isSelected()){
                stock_existente.setText("Stock Disponível : "+(produto.getStock()-stock)/produto.getUnidadesPorCaixa()+" Cxs");
                preco_produto.setText("Preco : "+produto.getValor()*produto.getUnidadesPorCaixa()+" MT");
            }else{
                stock_existente.setText("Stock Disponível : "+(produto.getStock()-stock)+" Units");
                preco_produto.setText("Preco : "+produto.getValor()+" MT");
            }
        }else{
            stock_existente.setText("Nenhum Produto Selecionado!");
            preco_produto.setText("");
            lbl_nomeProduto.setText("");
        }

    }

    private void validarQuantidade(){

        if(nomeValidado){
            int stock = 0; // stock que já foi adicionado no carrinho
            for (int i = 0; i <produtoTBs.size() ; i++) {
                if(produtoTBs.get(i).nome.getValue().equalsIgnoreCase(produto.getNome())){
                    stock = produtoTBs.get(i).getQuantidade();
                }
            }

            if(conversao.StringToInteger(tf_quantidades.getText()) && conversao.StringToInt(tf_quantidades.getText())>0){
                if(rb_caixas.isSelected()){
                    if(produto.getStock()>=conversao.StringToInt(tf_quantidades.getText())*produto.getUnidadesPorCaixa()+stock){
                        lbl_quantidade.setVisible(false);
                        tf_quantidades.setStyle("-jfx-focus-color: green;-fx-text-inner-color: green");
                        quantidadeValidado = true;
                        adicionarLista.setDisable(false);
                    }else{
                        lbl_quantidade.setText("Não tem Stock suficiente para realizar a venda");
                        lbl_quantidade.setVisible(true);
                        adicionarLista.setDisable(true);
                    }
                }else{
                    if(produto.getStock()>=conversao.StringToInt(tf_quantidades.getText())+stock){
                        lbl_quantidade.setVisible(false);
                        tf_quantidades.setStyle("-jfx-focus-color: green;-fx-text-inner-color: green");
                        quantidadeValidado = true;
                        adicionarLista.setDisable(false);
                    }else{
                        lbl_quantidade.setText("Não tem Stock suficiente para realizar a venda");
                        lbl_quantidade.setVisible(true);
                    }
                }
            }else{
                lbl_quantidade.setText("Quantidade Invalida!");
                lbl_quantidade.setVisible(true);
                tf_quantidades.setStyle("-jfx-focus-color: red;-fx-text-inner-color: red");
                adicionarLista.setDisable(true);
            }
        }else{
            lbl_quantidade.setText("Selecione um produto válido");
        }
    }



    private void adicionarAoCarrinho(){

        produto = pc.getProduto(tfProduto.getText());
        adicionarListaCarrinho();
        totalPagar();
        concluirVenda.setDisable(false);
        preencherLabel();
    }

    private void adicionarListaCarrinho(){

        int posicaoRepeticao = -1;
        int quantidade = conversao.StringToInt(tf_quantidades.getText());
        for(int i=0; i<produtoTBs.size(); i++){
            if(produtoTBs.get(i).nome.getValue().equalsIgnoreCase(produto.getNome())){
                posicaoRepeticao = i;
            }
        }

        if(rb_caixas.isSelected()){
            vendaCaixa(posicaoRepeticao,quantidade);
        }else{
            vendaUnidade(posicaoRepeticao,quantidade);
        }

        produto = null;
    }

    private  void vendaCaixa (int posicaoRepeticao, int quantidade){
        if(posicaoRepeticao==-1){
            produtoTBs.add(new ProdutoTB(produto.getNome(), quantidade*produto.getUnidadesPorCaixa()+"",
                    (quantidade*produto.getValor()*produto.getUnidadesPorCaixa())+""));
        }else{
            ProdutoTB produtoTB = produtoTBs.get(posicaoRepeticao);
            produtoTB.adicionarQuantidade(quantidade*produto.getUnidadesPorCaixa());
            produtoTBs.set(posicaoRepeticao,produtoTB);
        }
    }

    private void vendaUnidade (int posicaoRepeticao, int quantidade){
        if(posicaoRepeticao==-1){
            produtoTBs.add(new ProdutoTB(produto.getNome(), quantidade+"", (quantidade*produto.getValor())+""));
        }else{
            ProdutoTB produtoTB = produtoTBs.get(posicaoRepeticao);
            produtoTB.adicionarQuantidade(quantidade);
            produtoTBs.set(posicaoRepeticao,produtoTB);
        }
    }


    private void totalPagar(){
        float totalApagar = 0;

        for (ProdutoTB produtoTB : produtoTBs) {
            totalApagar += conversao.StringToDoub(produtoTB.preco.getValue());
        }
        total.setText("Total a Pagar : "+totalApagar);
    }

    private void finalizarVenda(){

        addToCarrinho();
        vendaCOntroller.finalizarVendaCarrinho(carrinho);
        carrinho.clear(); //limpa tudo no carrinho
        produtoTBs.clear();//limpa os produtos na tabela
        total.setText("Total a Pagar : 0 MT");
        concluirVenda.setDisable(true);


    }

    private void ativarDesativarRB(ActionEvent event){
        if(event.getSource().equals(rb_caixas)){
            rb_units.setSelected(false);
            rb_caixas.setSelected(true);//para manter selecionado quando for clicado mais de uma vez
        }else{
            rb_caixas.setSelected(false);
            rb_units.setSelected(true); //para manter selecionado quando for clicado mais de uma vez
        }
        preencherLabel();
        tf_quantidades.setText("");
        quantidadeValidado =false;
        adicionarLista.setDisable(true);
    }

    private void addToCarrinho(){
        Factura factura = new Factura();
        for (ProdutoTB produtoTB : produtoTBs) {
            carrinho.add(new Venda(pc.getProduto(produtoTB.nome.getValue()),
                    factura, produtoTB.getQuantidade()));
        }
    }



}
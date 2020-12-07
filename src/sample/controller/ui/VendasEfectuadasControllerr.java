package sample.controller.ui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import sample.controller.model.FacturaController;
import sample.controller.model.ProdutoController;
import sample.controller.model.VendaController;
import sample.controller.model.VendasEfectuadasController;
import sample.model.modelo.Factura;
import sample.model.modelo.Produto;
import sample.model.modelo.Venda;
import sample.util.Conversao;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VendasEfectuadasControllerr {

    /*@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;*/

    @FXML
    private JFXTreeTableView<Vendas> tbl_vendas;

    @FXML
    private JFXTreeTableView<Facturas> tbl_facturas;


    @FXML
    private JFXDatePicker data;

    @FXML
    private JFXTimePicker hora;

    @FXML
    private JFXButton btn_buscar;

    @FXML
    private Label lbl_tot_pago;

    /*@FXML
    private HBox pnl_factura;*/

    private ObservableList<Vendas> vendas;
    private ObservableList<Facturas> facturas;
    private float totPago = 0;

    private final VendasEfectuadasController vec = new VendasEfectuadasController();
    private final VendaController vendaController = new VendaController();
    private final ProdutoController produtoController = new ProdutoController();
    private final FacturaController facturaController = new FacturaController();

    private final Conversao conversao = new Conversao();


    @FXML
    void initialize() {
        assert tbl_vendas != null : "fx:id=\"tbl_vendas\" was not injected: check your FXML file 'vendasEfectuadas.fxml'.";
        assert data != null : "fx:id=\"data\" was not injected: check your FXML file 'vendasEfectuadas.fxml'.";
        assert hora != null : "fx:id=\"hora\" was not injected: check your FXML file 'vendasEfectuadas.fxml'.";
        assert btn_buscar != null : "fx:id=\"btn_buscar\" was not injected: check your FXML file 'vendasEfectuadas.fxml'.";
        //assert pnl_factura != null : "fx:id=\"pnl_factura\" was not injected: check your FXML file 'vendasEfectuadas.fxml'.";
        assert tbl_facturas != null : "fx:id=\"tbl_facturas\" was not injected: check your FXML file 'vendasEfectuadas.fxml'.";
        assert lbl_tot_pago != null : "fx:id=\"lbl_tot_pago\" was not injected: check your FXML file 'vendasEfectuadas.fxml'.";
        start();
    }

    public VendasEfectuadasControllerr(){

    }

    private void start(){

        //criacão da tabela produtos vendidos, colunas, etc
        criacaoDaTabelaProdutosVendidos();

        criacaoDaTabelaFacturas();

        hora.set24HourView(true);
        data.setValue(LocalDate.now());

        data.setOnAction(e->{
            adicionarFacturaData();
        });



        //configurar evento no botão
        btn_buscar.setOnAction(e->{
            System.out.println("Ola Tá Jobar");
        });

        adicionarFacturaData();

        //evento clique do mouse na tabela
        tbl_facturas.setOnMouseClicked(e->{
            adicionarProdutosNaTabela();
        });


    }

    private void criacaoDaTabelaProdutosVendidos(){
        JFXTreeTableColumn<VendasEfectuadasControllerr.Vendas,
            String> nomeProduto
            = new JFXTreeTableColumn<>("Produto");
        nomeProduto.setPrefWidth(150);
        nomeProduto.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Vendas, String> param) {
                return param.getValue().getValue().nome;
            }
        });

        JFXTreeTableColumn<VendasEfectuadasControllerr.Vendas, String> quantVendida
                = new JFXTreeTableColumn<>("Quantidade Vendida");
        quantVendida.setPrefWidth(100);
        quantVendida.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Vendas, String> param) {
                return param.getValue().getValue().quantidade;
            }
        });

        JFXTreeTableColumn<VendasEfectuadasControllerr.Vendas, String> precoTB
                = new JFXTreeTableColumn<>("Preço");
        precoTB.setPrefWidth(100);
        precoTB.setCellValueFactory(new Callback<>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Vendas, String> param) {
                return param.getValue().getValue().preco;
            }
        });

        JFXTreeTableColumn<VendasEfectuadasControllerr.Vendas, String> total_gasto
                = new JFXTreeTableColumn<>("Total Gasto");
        total_gasto.setPrefWidth(100);
        total_gasto.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Vendas, String>,
                ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Vendas, String> param) {
                return param.getValue().getValue().totalGasto;
            }
        });


        vendas = FXCollections.observableArrayList();
        TreeItem<VendasEfectuadasControllerr.Vendas> root
                = new RecursiveTreeItem<VendasEfectuadasControllerr.Vendas>(vendas,RecursiveTreeObject::getChildren);

        tbl_vendas.setMinSize(300,600);
        tbl_vendas.getColumns().setAll(nomeProduto,quantVendida, precoTB, total_gasto);

        tbl_vendas.setRoot(root);
        tbl_vendas.setShowRoot(false);
        tbl_vendas.autosize();}

    private void criacaoDaTabelaFacturas(){
        JFXTreeTableColumn<VendasEfectuadasControllerr.Facturas, String> id_factura
            = new JFXTreeTableColumn<>("ID da Factura");
        id_factura.setPrefWidth(60);
        id_factura.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Facturas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Facturas, String> param) {
                return param.getValue().getValue().id;
            }
        });

        JFXTreeTableColumn<VendasEfectuadasControllerr.Facturas, String> data
                = new JFXTreeTableColumn<>("Data");
        data.setPrefWidth(180);
        data.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Facturas, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Facturas, String> param) {
                return param.getValue().getValue().data;
            }
        });


        facturas = FXCollections.observableArrayList();
        TreeItem<VendasEfectuadasControllerr.Facturas> root
                = new RecursiveTreeItem<VendasEfectuadasControllerr.Facturas>(facturas,RecursiveTreeObject::getChildren);

        tbl_facturas.setMinSize(250,600);
        tbl_facturas.getColumns().setAll(id_factura, data);

        tbl_facturas.setRoot(root);
        tbl_facturas.setShowRoot(false);
        tbl_facturas.autosize();}

    private void adicionarProdutosNaTabela(){
            totPago = 0;
            int id = conversao.StringToInt(tbl_facturas.getSelectionModel().getSelectedItem().getValue().id.getValue());
            List<Venda> faturas = vendaController.findByFacturaId(id);

            Produto p = null;
            int quantidadeVendida = 0;
            vendas.clear();
            for (int i = 0; i < faturas.size(); i++) {
                p = produtoController.getProduto(faturas.get(i).getProduto().getId());
                quantidadeVendida = faturas.get(i).getQuantidadeVendida();

                System.out.println(p.getNome()+", "+quantidadeVendida+", ");
                vendas.add(new Vendas(p.getNome(),quantidadeVendida,p.getValor()));
                totPago+=quantidadeVendida*p.getValor();

            }
            lbl_tot_pago.setText("Total Pago: "+totPago);
        }

    private  void adicionarFacturasNaTabela(){

        if(data.getPromptText().equals(null)){
           adicionarFacturasSemRestricao();
        }
    }

    private void adicionarFacturasSemRestricao(){
        //lista sem restrição
        reset();

        List<Factura> fts = vec.getAllSells();
        Factura f = null;
        for(int i = 0; i < fts.size(); i++){
            f = fts.get(i);
            facturas.add(new Facturas(f.getId()+"", f.getData().toString()));

        }
    }

    private void adicionarFacturaData(){
        reset();
        List<Factura> fts = facturaController.getAll(data.getValue().toString());
        Factura f = null;
        for(int i = 0; i < fts.size(); i++){
            f = fts.get(i);
            facturas.add(new Facturas(f.getId()+"", f.getData().toString()));
        }
    }

    private void reset(){
        facturas.clear();
        vendas.clear();
        totPago = 0;
        lbl_tot_pago.setText("");
    }



    class Vendas extends RecursiveTreeObject<VendasEfectuadasControllerr.Vendas> {

        StringProperty nome;
        StringProperty quantidade;
        StringProperty preco;
        StringProperty totalGasto;


        public Vendas (String nome, int quantidade, float preco){
            this.nome = new SimpleStringProperty(nome);
            this.quantidade = new SimpleStringProperty(quantidade+"");
            this.preco = new SimpleStringProperty(preco+"");
            this.totalGasto = new SimpleStringProperty((quantidade*preco)+"");
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

    class Facturas extends RecursiveTreeObject<VendasEfectuadasControllerr.Facturas> {
        StringProperty id;
        StringProperty data;

        public Facturas(String id, String data){
           this.id = new SimpleStringProperty(id);
           this.data = new SimpleStringProperty(data);
        }
    }



}

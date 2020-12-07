package sample.controller.ui;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import sample.controller.model.VendaController;
import sample.model.modeloAux.ProdutoEstatistica;
import sample.util.Conversao;
import sample.util.ProdutoUtil;

import java.util.List;

public class EstatisticaControllerr {


  @FXML
  private BorderPane pane;

  @FXML
  private JFXButton btnLucro;

  @FXML
  private JFXButton btnSaida;

  @FXML
  private JFXButton btnReceita;

  @FXML
  private JFXComboBox<String> cbGrafico;
  private final VendaController vendaController = new VendaController();
  private final Conversao conversao = new Conversao();
  private  final ProdutoUtil produtoUtil = new ProdutoUtil();

  private final ObservableList<PieChart.Data> pieChartDataLucro = FXCollections.observableArrayList();
  private final  ObservableList<PieChart.Data> pieChartDataReceita = FXCollections.observableArrayList();
  private final ObservableList<PieChart.Data> pieChartDataSaida = FXCollections.observableArrayList();

  private final ObservableList<BarChart.Data> barChartDataLucro = FXCollections.observableArrayList();
  private final ObservableList<BarChart.Data> barChartDataReceita = FXCollections.observableArrayList();
  private final ObservableList<BarChart.Data> barChartDataSaida = FXCollections.observableArrayList();
  private ObservableList<ProdutoEstatistica> produtoEstatisticas;
  private byte lastSelected = 0;
  /*
  * 0 - lucro
  * 1 - Receita
  * 2 - Saida
  * */

  @FXML
  void initialize() {
    assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'estatistica.fxml'.";
    assert btnLucro != null : "fx:id=\"btnLucro\" was not injected: check your FXML file 'estatistica.fxml'.";
    assert btnSaida != null : "fx:id=\"btnSaida\" was not injected: check your FXML file 'estatistica.fxml'.";
    assert btnReceita != null : "fx:id=\"btnReceita\" was not injected: check your FXML file 'estatistica.fxml'.";
    assert cbGrafico != null : "fx:id=\"cbGrafico\" was not injected: check your FXML file 'estatistica.fxml'.";

    start();
  }

  private void start(){

    combo();
    produtoEstatisticas = FXCollections.observableArrayList();
    addDataToLists();



    addPieChart(pieChartDataLucro, "Grafico Pizza");
    btnLucro.setDisable(true);

    cbGrafico.setOnAction(e->{
      switch (lastSelected){
          case 0 :
              addLucroContent();
              break;
          case 1:
              addReceitaContent();
              break;
          case 2:
              addSaidaContent();
              break;
      }
    });
    btnLucro.setOnAction(e->{
        btnLucro.setDisable(true);
        btnSaida.setDisable(false);
        btnReceita.setDisable(false);
        addLucroContent();
    });
    btnReceita.setOnAction(e->{
        btnReceita.setDisable(true);
        btnSaida.setDisable(false);
        btnLucro.setDisable(false);
        addReceitaContent();
    });
    btnSaida.setOnAction(e->{
        btnLucro.setDisable(false);
        btnSaida.setDisable(true);
        btnReceita.setDisable(false);
        addSaidaContent();
    });
  }

  private void addDataToLists(){
      List <Object[]> vendasEfectuadas = vendaController.vendasEfectuadas();
      int quantidade, quantidadeCaixa;
      float lucro, receita,preco, precoCaixa;
      String nome;

      //[id,nome,valor,precoCaixa,unidadesPorCaixa,quantidadeVendida]
      for (Object[] vendaEfectuada : vendasEfectuadas){
          nome = vendaEfectuada[1].toString();
          preco = (float)conversao.StringToDoub(vendaEfectuada[2].toString());
          precoCaixa = (float)conversao.StringToDoub(vendaEfectuada[3].toString());
          quantidadeCaixa = conversao.StringToInt(vendaEfectuada[4].toString());
          quantidade = conversao.StringToInt(vendaEfectuada[5].toString());


          lucro = produtoUtil.lucro(preco, precoCaixa, quantidadeCaixa)*quantidade;
          receita = quantidade*preco;
          pieChartDataLucro.add(new PieChart.Data(nome,lucro));
          barChartDataLucro.add(new BarChart.Data(nome,lucro));

          pieChartDataSaida.add(new PieChart.Data(nome,quantidade));
          barChartDataSaida.add(new BarChart.Data(nome,quantidade));

          pieChartDataReceita.add(new PieChart.Data(nome,receita));
          barChartDataReceita.add(new BarChart.Data(nome,receita));

          produtoEstatisticas.add(new ProdutoEstatistica(nome,
                  quantidade,lucro, receita));
      }

  }

  private void addLucroContent(){
      lastSelected = 0;
      String txtCB = cbGrafico.getSelectionModel().getSelectedItem();
      if(txtCB.equalsIgnoreCase("Grafico de Pizza")){
          addPieChart(pieChartDataLucro, "Lucro");
      }else if(txtCB.equalsIgnoreCase("Grafico de Barras")){
          addBarChart(barChartDataLucro,"Lucro");
      }else{
          addTable(produtoEstatisticas);
      }
  }

  private void addReceitaContent(){
      lastSelected = 1;
      String txtCB = cbGrafico.getSelectionModel().getSelectedItem();
      if(txtCB.equalsIgnoreCase("Grafico de Pizza")){
          addPieChart(pieChartDataReceita, "Receita");
      }else if(txtCB.equalsIgnoreCase("Grafico de Barras")){
          addBarChart(barChartDataReceita,"Receita");
      }else{
          addTable(produtoEstatisticas);
      }
  }

  private void addSaidaContent(){
      lastSelected = 2;
      String txtCB = cbGrafico.getSelectionModel().getSelectedItem();
      if(txtCB.equalsIgnoreCase("Grafico de Pizza")){
          addPieChart(pieChartDataSaida, "Produtos Vendidos");
      }else if(txtCB.equalsIgnoreCase("Grafico de Barras")){
          addBarChart(barChartDataSaida,"Produtos Vendidos");
      }else{
          addTable(produtoEstatisticas);
      }
  }

  private void combo(){
    cbGrafico.getItems().add("Grafico de Pizza");
    cbGrafico.getItems().add("Grafico de Barras");
    cbGrafico.getItems().add("Tabela");
    cbGrafico.getSelectionModel().select(0);
  }

  private void addPieChart(ObservableList<PieChart.Data> pieChartData, String title){

      PieChart pieChart = new PieChart(pieChartData);
      pieChart.setTitle(title);
      pieChart.setLegendVisible(true);
      pieChart.setStyle("-fx-background-insets: 0;-fx-border-width: 0;");
     pane.setCenter(pieChart);
  }

  private void addBarChart(ObservableList<BarChart.Data> barChartData, String title){

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    //xAxis.setLabel("Produto");
    yAxis.setLabel("Valor");

    BarChart<String, Number> barChart = new BarChart<>(xAxis,yAxis);
    barChart.setTitle(title);

    XYChart.Series series1 = new XYChart.Series(barChartData);
    barChart.getData().add(series1);
    pane.setCenter(barChart);

  }

  private void addTable(ObservableList<ProdutoEstatistica> produtoEstatisticas){

    JFXTreeTableView tabela = new JFXTreeTableView();

    JFXTreeTableColumn<ProdutoEstatistica,
            String> nomeProduto
            = new JFXTreeTableColumn<>("Produto");
    nomeProduto.setPrefWidth(150);
    nomeProduto.setCellValueFactory(param -> param.getValue().getValue().nomeProperty());

    JFXTreeTableColumn<ProdutoEstatistica,
            String> quantidadeVendida
            = new JFXTreeTableColumn<>("Quantidade Vendida");
    quantidadeVendida.setPrefWidth(150);
    quantidadeVendida.setCellValueFactory(param -> param.getValue().getValue().quantidadeVendidaProperty());

    JFXTreeTableColumn<ProdutoEstatistica,
            String> lucroObtido
            = new JFXTreeTableColumn<>("Lucro Obtido");
    lucroObtido.setPrefWidth(150);
    lucroObtido.setCellValueFactory(param -> param.getValue().getValue().lucroObtidoProperty());

    JFXTreeTableColumn<ProdutoEstatistica,
            String> receitaTotal
            = new JFXTreeTableColumn<>("Receita Total");
    receitaTotal.setPrefWidth(150);
    receitaTotal.setCellValueFactory(param -> param.getValue().getValue().receitaTotalProperty());

    TreeItem<ProdutoEstatistica> root
            = new RecursiveTreeItem<>(produtoEstatisticas, RecursiveTreeObject::getChildren);

    tabela.setMinSize(300,600);
    tabela.getColumns().setAll(nomeProduto,quantidadeVendida,receitaTotal,lucroObtido);

    tabela.setRoot(root);
    tabela.setShowRoot(false);
    tabela.autosize();

    pane.setCenter(tabela);
  }


}

package sample.controller.ui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private JFXButton btn_realizarVenda;

    @FXML
    private JFXButton btn_registrar;

    @FXML
    private JFXButton btn_levantamento;

    @FXML
    private JFXButton btn_stock;

    @FXML
    private JFXButton btn_estatistica;

    @FXML
    private AnchorPane pnl_visualizar;

    @FXML
    private JFXButton btn_definicao;

    @FXML
    private ImageView ivDefinicao;

    @FXML
    private ImageView ivRegistro;

    @FXML
    private ImageView ivLevantamento;

    @FXML
    private ImageView ivRealizarVenda;

    @FXML
    private ImageView ivEstatistica;

    @FXML
    private ImageView ivStock;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_registrar.setOnAction(e->btnClique("/sample/view/registro.fxml",e));
        btn_levantamento.setOnAction(e->btnClique("/sample/view/levantamento.fxml",e));
        btn_realizarVenda.setOnAction(e->btnClique("/sample/view/realizarVenda.fxml",e));
        btn_stock.setOnAction(e->btnClique("/sample/view/vendasEfectuadas.fxml",e));
        btn_estatistica.setOnAction(e->btnClique("/sample/view/estatistica.fxml",e));
        btn_definicao.setOnAction(e->btnClique("/sample/view/definicoes.fxml",e));
        iniciarEmVendas();
    }


    //temporario, só para teste
    private void iniciarEmVendas(){
        adicionarConteudoPainelVisualizar("/sample/view/realizarVenda.fxml");
         btnPressed(btn_realizarVenda);
    }

    private void btnClique(String txt, ActionEvent e){
        adicionarConteudoPainelVisualizar(txt);
        mouseClicked(e);
    }

    private void mouseClicked(ActionEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
        btnPressed(btn);
    }

    private void btnPressed(JFXButton btn){
        otherBtn();

        btn.setStyle("-fx-background-color:   #ebecf0 ; -fx-text-fill: #0052cc");
        if(btn.equals(this.btn_estatistica)){
            ivEstatistica.setImage(new Image("sample/assets/icons/esta.png"));
        }else{
            if (btn.equals(this.btn_levantamento)){
                ivLevantamento.setImage(new Image("sample/assets/icons/lev.png"));
            }else{
                if(btn.equals(this.btn_registrar)){
                    ivRegistro.setImage(new Image("sample/assets/icons/create.png"));
                }else{
                    if(btn.equals(this.btn_realizarVenda)){
                        ivRealizarVenda.setImage(new Image("sample/assets/icons/sell.png"));
                    }else{
                        if(btn.equals(btn_stock)){
                            ivStock.setImage(new Image("sample/assets/icons/stock.png"));
                        }else{
                            ivDefinicao.setImage(new Image("sample/assets/icons/settings.png"));
                        }
                    }

                }
            }
        }
    }

    private void setUnselectedOption(JFXButton btn){
        btn.setStyle("-fx-background-color:   #f4f5f7");
        btn.setStyle("-fx-text-fill: #8791a3");
    }

    private void otherBtn(){
        setUnselectedOption(this.btn_estatistica);
        ivEstatistica.setImage(new Image("sample/assets/icons/esta2.png"));

        setUnselectedOption(this.btn_levantamento);
        ivLevantamento.setImage(new Image("sample/assets/icons/lev2.png"));

        setUnselectedOption(this.btn_registrar);
        ivRegistro.setImage(new Image("sample/assets/icons/create2.png"));

        setUnselectedOption(this.btn_stock);
        ivStock.setImage(new Image("sample/assets/icons/stock2.png"));

        setUnselectedOption(this.btn_realizarVenda);
        ivRealizarVenda.setImage(new Image("sample/assets/icons/sell2.png"));

        setUnselectedOption(this.btn_definicao);
        ivDefinicao.setImage(new Image("sample/assets/icons/settings2.png"));
    }

    private void adicionarConteudoPainelVisualizar(String directorio){
        try {
            pnl_visualizar.getChildren().clear();
            pnl_visualizar.getChildren().add(FXMLLoader.load(getClass().getResource(directorio)));
        }catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        } catch (NullPointerException e) {
            System.out.println("Nulo");
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

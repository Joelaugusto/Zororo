package sample.controller.ui;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    private ImageView ivRegistro;

    @FXML
    private ImageView ivLevantamento;

    @FXML
    private ImageView ivStock;

    @FXML
    private ImageView ivEstatistica;


    @FXML
    void mouseEntered(MouseEvent event) {

    }

    @FXML
    void mouseExited(MouseEvent event) {
        JFXButton btn = (JFXButton) event.getSource();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btn_registrar.setOnAction(e->btnClique("/sample/view/registro.fxml",e));
        btn_levantamento.setOnAction(e->btnClique("/sample/view/levantamento.fxml",e));
        btn_realizarVenda.setOnAction(e->btnClique("/sample/view/realizarVenda.fxml",e));
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
            //mudar a cor o Icon
        }else{
            if (btn.equals(this.btn_levantamento)){
                ivLevantamento.setImage(new Image("sample/assets/icons/lev.png"));
            }else{
                if(btn.equals(this.btn_registrar)){
                    ivRegistro.setImage(new Image("sample/assets/icons/create.png"));
                }else{
                    //mudar a cor o Icon
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
        //colocar para estatistica
        setUnselectedOption(this.btn_levantamento);
        ivLevantamento.setImage(new Image("sample/assets/icons/lev2.png"));
        setUnselectedOption(this.btn_registrar);
        ivRegistro.setImage(new Image("sample/assets/icons/create2.png"));
        setUnselectedOption(this.btn_stock);
        setUnselectedOption(this.btn_realizarVenda);
    }

    private void adicionarConteudoPainelVisualizar(String directorio){
        try {
            pnl_visualizar.getChildren().clear();
            pnl_visualizar.getChildren().add((AnchorPane)FXMLLoader.load(getClass().getResource(directorio)));
        }catch (FileNotFoundException e) {
            System.out.println("Ficheiro não encontrado!");
        } catch (NullPointerException e) {
            System.out.println("Nulo");
        }catch(javafx.fxml.LoadException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}

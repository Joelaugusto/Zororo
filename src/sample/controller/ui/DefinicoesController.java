package sample.controller.ui;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DefinicoesController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton btn_propriedade_produto;

    @FXML
    private AnchorPane pane;

    @FXML
    void initialize() {
        assert stackPane != null : "fx:id=\"stackPane\" was not injected: check your FXML file 'definicoes.fxml'.";
        assert btn_propriedade_produto != null : "fx:id=\"btn_propriedade_produto\" was not injected: check your FXML file 'definicoes.fxml'.";
        assert pane != null : "fx:id=\"pane\" was not injected: check your FXML file 'definicoes.fxml'.";

    }

    public DefinicoesController(){

    }
}

package sample.controller.ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.ResourceBundle;

public class VendasEfectuadasController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTreeTableView<?> tbl_vendas;

    @FXML
    private JFXDatePicker data;

    @FXML
    private JFXTimePicker hora;

    @FXML
    private JFXButton btn_buscar;

    @FXML
    private JFXTreeTableView<?> tbl_facturas;


    public VendasEfectuadasController(){

    }
}

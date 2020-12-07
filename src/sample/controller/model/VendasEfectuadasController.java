package sample.controller.model;

import javafx.collections.ObservableList;
import sample.model.DAO.FacturaDAO;

import java.util.List;

public class VendasEfectuadasController {

    private final FacturaDAO facturaDAO = new FacturaDAO();

    public VendasEfectuadasController(){

    }

    public List getAllSells(){

        return facturaDAO.findAll();
    }

    public List getAllSellsWithHour(){

        return facturaDAO.findAllWithHour();
    }
}

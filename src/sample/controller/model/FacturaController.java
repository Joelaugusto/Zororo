package sample.controller.model;

import sample.model.DAO.FacturaDAO;
import sample.model.DAO.VendaDAO;
import sample.model.modelo.Factura;

import java.util.List;

public class FacturaController {

    private final VendaDAO vendaDao = new VendaDAO();
    private final FacturaDAO facturaDAO = new FacturaDAO();


    public FacturaController () {

    }

    public void store(Factura factura){

    }

    public List<Factura> getAll(String date){
        return facturaDAO.findAll(date);
    }

}
package sample.controller.model;

import sample.model.DAO.VendaDAO;
import sample.model.modelo.Factura;

public class FacturaController {

    private final VendaDAO vendaDao;
    FacturaController () {
        vendaDao = new VendaDAO();
    }

    public void store(Factura factura){

    }

}
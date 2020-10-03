package sample.controller.model;

import sample.model.DAO.VendaDAO;
import sample.model.modelo.Venda;

import java.util.ArrayList;

public class VendaController {

    private final VendaDAO vendaDAO = new VendaDAO();
    public VendaController(){

    }

    public void finalizarVendaCarrinho(ArrayList<Venda> vendas){
        vendaDAO.efectuarVenda(vendas);
    }

}

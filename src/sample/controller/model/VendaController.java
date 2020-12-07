package sample.controller.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.DAO.VendaDAO;
import sample.model.modelo.Venda;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VendaController {

    private final VendaDAO vendaDAO = new VendaDAO();
    public VendaController(){

    }

    public void finalizarVendaCarrinho(ArrayList<Venda> vendas){
        vendaDAO.efectuarVenda(vendas);
    }

    public List findWithFactura(Date data){
        return vendaDAO.findWithFactura(data);
    }

    public List findAll(){return  vendaDAO.findAll();}

    public List<Venda> findByFacturaId(int id){
        return vendaDAO.findById(id);
    }

    public List<Venda> findByFacuraDate(String date){
        return vendaDAO.findByDate(date);
    }

    public List<Object[]> vendasEfectuadas(){

        return vendaDAO.quantidadeVendida();
    }

}

package sample.controller.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.DAO.UnidadeDAO;
import sample.model.modelo.Unidade;

public class UnidadeControleer {

    private final UnidadeDAO unidadeDAO = new UnidadeDAO();

    public UnidadeControleer(){

    }

    public ObservableList<String> getAllNames(){
        return FXCollections.observableArrayList(unidadeDAO.findAllNomes());
    }

    public Unidade getByProdutoId(int id){
        return unidadeDAO.findByProdutoId(id);
    }

    public ObservableList<Unidade> getAll(){
        return FXCollections.observableArrayList(unidadeDAO.findAll());
    }
}

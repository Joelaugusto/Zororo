package sample.controller.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.DAO.CategoriaDAO;
import sample.model.modelo.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaController {

    private CategoriaDAO categoriaDAO;

    public CategoriaController(){
        categoriaDAO = new CategoriaDAO();
    }

    public ObservableList getAllProduto(){  //resolver isso depois
        List<Categoria> list = categoriaDAO.findAll();
        return FXCollections.observableArrayList(list);
    }

    public ObservableList getAllNomeCategoria(){
        return FXCollections.observableArrayList(categoriaDAO.findAllNomes());
    }

    public Categoria findByName(String nome){
        List<Categoria> list = categoriaDAO.findByName(nome);
        return list.get(0);
    }


}

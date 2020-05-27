package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.DB.Persistencia;
import sample.model.modelo.Produto;

import java.util.ArrayList;
import java.util.List;


public class ProdutoController {

    ProdutoController(){

    }

    public void registrarProduto(Produto p){
        Persistencia.sessao().save(p);
        Persistencia.sessao().beginTransaction().commit();
    }

    public void excluirProduto(int id){

    }

    public boolean verificarExistencia(String nome){
        List<Produto> list = Persistencia.sessao().createQuery("select nome from Produto" +
                " where nome ='"+nome+"'").list();
        return !list.isEmpty(); // se a lista estiver vazia significa que nao ha nenhum registro com mesmo nome
    }
    
    public Produto getProduto(int id){
        return Persistencia.sessao().find(Produto.class, id);
    }

    public ObservableList getAllProduto(){
        List<Produto> list = Persistencia.sessao().createQuery("from Produto").list();
        ObservableList p = FXCollections.observableArrayList(list);
        return p;
    }

    public ObservableList getAllNomeProduto(){
        List<Produto> list = Persistencia.sessao().createQuery("select nome from Produto").list();
        ObservableList p = FXCollections.observableArrayList(list);
        return p;
    }





}

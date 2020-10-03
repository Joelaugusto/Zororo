package sample.controller.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.DAO.ProdutoDAO;
import sample.model.modelo.Produto;

import java.util.List;


public class ProdutoController {

    ProdutoDAO produtoDAO;

    public ProdutoController(){
        produtoDAO = new ProdutoDAO();
    }

    public void registrarProduto(Produto produto){
        System.out.println(produto);
        produtoDAO.save(produto);
    }

    public void excluirProduto(int id){
        produtoDAO.remove(id);
    }

    public boolean verificarExistencia(String nome){
        return !produtoDAO.findByName(nome).isEmpty();
        // se a lista estiver vazia significa que nao ha nenhum registro com mesmo nome
    }
    
    public Produto getProduto(int id){
        return produtoDAO.findById(id);
    }

    public ObservableList getAllProduto(){
        List<Produto> list = produtoDAO.findAll();
        return FXCollections.observableArrayList(list);
    }

    public ObservableList findAllNomeProduto(){
        return FXCollections.observableArrayList(produtoDAO.findAllNames());
    }

    public Produto getProduto(String nome){
        return produtoDAO.findByName(nome).get(0);
    }

    public void update(Produto produto){
        produtoDAO.update(produto);
    }





}

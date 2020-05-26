package sample.controller;

import sample.model.DB.Persistencia;
import sample.model.modelo.Produto;

import java.util.ArrayList;


public class ProdutoController {

    ProdutoController(){

    }

    public void registrarProduto(Produto p){
        Persistencia.sessao().save(p);
        Persistencia.sessao().beginTransaction().commit();
    }

    public void excluirProduto(int id){

    }

    public boolean verificarExistencia(){
        return false;
    }
    
    public Produto getProduto(int id){
        return Persistencia.sessao().find(Produto.class, id);
    }





}

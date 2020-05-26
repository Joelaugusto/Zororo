package sample.controller;

import sample.model.DB.Persistencia;
import sample.model.modelo.Produto;


public class ProdutoController {

    ProdutoController(){

    }

    public void registrarProduto(Produto p){
        Persistencia.sessao().save(p);
        Persistencia.sessao().beginTransaction().commit();
    }

    public void excluirProduto(int id){

    }




}

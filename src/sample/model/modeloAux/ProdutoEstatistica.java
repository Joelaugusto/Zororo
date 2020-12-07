package sample.model.modeloAux;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProdutoEstatistica extends RecursiveTreeObject<ProdutoEstatistica> {

    StringProperty nome;
    StringProperty quantidadeVendida;
    StringProperty lucroObtido;
    StringProperty receitaTotal;


    public ProdutoEstatistica(String nome, int quantidadeVendida, float lucroObtido, float receitaTotal ){

        this.nome = new SimpleStringProperty(nome);
        this.quantidadeVendida = new SimpleStringProperty(quantidadeVendida+"");
        this.lucroObtido = new SimpleStringProperty(lucroObtido+"");
        this.receitaTotal = new SimpleStringProperty(receitaTotal+"");
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /*public String getQuantidadeVendida() {
        return quantidadeVendida.get();
    }*/

    public StringProperty quantidadeVendidaProperty() {
        return quantidadeVendida;
    }

    /*public void setQuantidadeVendida(String quantidadeVendida) {
        this.quantidadeVendida.set(quantidadeVendida);
    }

    public String getLucroObtido() {
        return lucroObtido.get();
    }/*/

    public StringProperty lucroObtidoProperty() {
        return lucroObtido;
    }

    /*public void setLucroObtido(String lucroObtido) {
        this.lucroObtido.set(lucroObtido);
    }

    public String getReceitaTotal() {
        return receitaTotal.get();
    }*/

    public StringProperty receitaTotalProperty() {
        return receitaTotal;
    }

    /*public void setReceitaTotal(String receitaTotal) {
        this.receitaTotal.set(receitaTotal);
    }*/
}



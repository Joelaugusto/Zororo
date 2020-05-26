package sample.model.modelo;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "produto")
public class Produto {
    private String nome;
    private float valor;
    private float precoCaixa;
    private short unidadesPorCaixa;
    private int id;

    public Produto(String nome, float valor, float precoCaixa, short unidadesPorCaixa) {
        super();
        this.nome = nome;
        this.valor = valor;
        this.precoCaixa = precoCaixa;
        this.unidadesPorCaixa = unidadesPorCaixa;
    }

    public Produto(){
        super();
    }

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(length = 30, nullable = false, name = "nome")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "preco", nullable = false)
    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @Column(name = "precoCaixa", nullable = false)
    public float getPrecoCaixa() {
        return precoCaixa;
    }

    public void setPrecoCaixa(float precoCaixa) {
        this.precoCaixa = precoCaixa;
    }

    @Column(name = "unidadesCaixa", nullable = false)
    public short getUnidadesPorCaixa() {
        return unidadesPorCaixa;
    }

    public void setUnidadesPorCaixa(short unidadesPorCaixa) {
        this.unidadesPorCaixa = unidadesPorCaixa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Float.compare(produto.valor, valor) == 0 &&
                Float.compare(produto.precoCaixa, precoCaixa) == 0 &&
                unidadesPorCaixa == produto.unidadesPorCaixa &&
                nome.equals(produto.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, valor, precoCaixa, unidadesPorCaixa);
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", precoCaixa=" + precoCaixa +
                ", unidadesPorCaixa=" + unidadesPorCaixa +
                '}';
    }
}

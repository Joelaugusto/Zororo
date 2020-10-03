package sample.model.modelo;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table  (name = "produto",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_nome_cotegoria",
        columnNames = {
                "nome",
                "categoria_id"
        }
))
public class Produto {

    @Column(length = 30, nullable = false, name = "nome")
    private String nome;

    @Column(name = "preco", nullable = false)
    private float valor;

    @Column(name = "precoCaixa", nullable = false)
    private float precoCaixa;

    @Column(name = "unidadesCaixa", nullable = false)
    private short unidadesPorCaixa;

    @Column(name = "stock", nullable = false, columnDefinition = "int default 0")
    private int stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "categoria_id",
            foreignKey = @ForeignKey(name = "fk_categoria_id")
    )
    private Categoria categoria;

    @Id
    @GeneratedValue
    private int id;

    public Produto(String nome, float valor, float precoCaixa, short unidadesPorCaixa, int stock) {
        super();
        this.nome = nome;
        this.valor = valor;
        this.precoCaixa = precoCaixa;
        this.unidadesPorCaixa = unidadesPorCaixa;
        this.stock = stock;
    }

    public Produto(){
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    /*public float getPrecoCaixa() {
        return precoCaixa;
    }*/

    public void setPrecoCaixa(float precoCaixa) {
        this.precoCaixa = precoCaixa;
    }

    public short getUnidadesPorCaixa() {
        return unidadesPorCaixa;
    }

    public void setUnidadesPorCaixa(short unidadesPorCaixa) {
        this.unidadesPorCaixa = unidadesPorCaixa;
    }

    /*public Categoria getCategoria() {
        return categoria;
    }*/

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
                ", categoria=" + categoria +
                ", id=" + id +
                '}';
    }
}

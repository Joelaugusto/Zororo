package sample.model.modelo;

import javax.persistence.*;
import java.util.Calendar;



@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "dataVenda", nullable = false)
    private Calendar data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_produto",
            foreignKey = @ForeignKey(name = "fk_id_produto")
    )
    private Produto produto;

    @Column(name = "quantidadeVendida", nullable = false)
    private int quantidadeVendida;

    public Venda(long id, Calendar data,Produto produto, int quantidadeVendida) {
        this.id = id;
        this.data = data;
        this.produto = produto;
        this.quantidadeVendida = quantidadeVendida;
    }

    public  Venda(){
        this(0,null,null,0);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setIdProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", data=" + data +
                ", produto=" + produto +
                ", quantidadeVendida=" + quantidadeVendida +
                '}';
    }
}

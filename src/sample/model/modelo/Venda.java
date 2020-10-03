package sample.model.modelo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;



@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "dataVenda", nullable = false)
    @CreationTimestamp
    private final Date data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_produto",
            foreignKey = @ForeignKey(name = "fk_id_produto")
    )
    private Produto produto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_factura",
            foreignKey = @ForeignKey(name = "fk_id_factura")
    )
    private final Factura factura;

    @Column(name = "quantidadeVendida", nullable = false)
    private final int quantidadeVendida;

    public Venda(Produto produto,Factura factura, int quantidadeVendida) {
        this.data = new Date();
        this.produto = produto;
        this.factura = factura;
        this.quantidadeVendida = quantidadeVendida;
    }

    public  Venda(){
        this(null,null,0);
    }

    public long getId() {
        return id;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Factura getFactura() {
        return factura;
    }

    /*public void setFactura(Factura factura) {
        this.factura = factura;
    }*/

    public void setId(long id) {
        this.id = id;
    }

    /*public Date getData() {
        return data;
    }*/

    /*public void setData(Date data) {
        this.data = data;
    }*/

    public Produto getProduto() {
        return produto;
    }

    /*public void setIdProduto(Produto produto) {
        this.produto = produto;
    }*/

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    /*public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }*/

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

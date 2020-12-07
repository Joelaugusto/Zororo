package sample.model.modelo;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "factura")
public class Factura {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "dataVenda", nullable = false, columnDefinition = "timestamp default now()")
    @CreationTimestamp
    private final Date data;

    public Factura() {
        this.data = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    /*
    public void setData(Date data) {
        this.data = data;
    }*/

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", data=" + data +
                '}';
    }
}

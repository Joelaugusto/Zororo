package sample.model.modelo;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "unidade")
public class Unidade {

    @Id
    @GeneratedValue
    private byte id;

    @Column(length = 30, nullable = false, name = "unidade")
    private String unidade;

    @Column(columnDefinition =  "boolean default false", nullable = false, name = "permiteQuantidade")
    private boolean permiteQuantidade;


    public Unidade(){
        
    }
    public Unidade(byte id, String unidade, boolean permiteQuantidade) {
        this.id = id;
        this.unidade = unidade;
        this.permiteQuantidade = permiteQuantidade;
    }

    public boolean isPermiteQuantidade() {
        return permiteQuantidade;
    }

    public void setPermiteQuantidade(boolean permiteQuantidade) {

        this.permiteQuantidade = permiteQuantidade;
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    @Override
    public String toString() {
        return "Unidade{" +
                "id=" + id +
                ", unidade='" + unidade + '\'' +
                ", permiteQuantidade=" + permiteQuantidade +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unidade unidade1 = (Unidade) o;
        return id == unidade1.id &&
                Objects.equals(unidade, unidade1.unidade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, unidade);
    }

}

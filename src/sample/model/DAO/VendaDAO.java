package sample.model.DAO;

import sample.dataBase.ConnectionFactory;
import sample.model.modelo.Venda;

import javax.persistence.EntityManager;
import java.util.ArrayList;

public class VendaDAO {

    private final FacturaDAO facturaDAO = new FacturaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    public  VendaDAO(){

    }

    /*public void efectuarVenda(Venda venda){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(venda);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }*/

    public void efectuarVenda(ArrayList<Venda> vendas){

        //pegamos a factura presente no primeiro produto, que é a mesma em todos outros
        facturaDAO.registrarFactura(vendas.get(0).getFactura());
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            for (Venda venda : vendas) {
                entityManager.persist(venda);
                produtoDAO.subtrairStock(venda.getProduto(), venda.getQuantidadeVendida());
            }
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    /*
    public Venda lastSell(){
        return null;
    }*/

}

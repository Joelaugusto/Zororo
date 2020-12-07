package sample.model.DAO;

import sample.dataBase.ConnectionFactory;
import sample.model.modelo.Venda;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<Venda> findAll(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Venda> vendas = null;

        try{
            vendas = entityManager.createQuery("from Venda v").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return vendas;
    }

    public List<Venda> findById(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Venda> vendas = null;

        try{
            vendas = entityManager.createQuery("from Venda v where id_factura = "+id).getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return vendas;
    }

    public List<Venda> findByDate(String date){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Venda> vendas = null;

        try{
            vendas = entityManager.createQuery("from Venda v where cast(dataVenda = "+date+") ").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return vendas;
    }

    public List findWithFactura(Date data){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List vendas = null;

        try{
            vendas = entityManager.createQuery("from Venda v where data = '"+
                    data.toString()+"'").getResultList(); //não é o melhor jeito
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return vendas;
    }

    public List<Object[]> quantidadeVendida(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List <Object[]>produtosVendidos = null;

        try{
            produtosVendidos = entityManager.createQuery("select v.produto.id, v.produto.nome, v.produto.valor, v.produto.precoCaixa,v.produto.unidadesPorCaixa, sum(v.quantidadeVendida) as quantidade from " +
                    "Venda v  group by v.produto.id").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return produtosVendidos;
    }

    /*
    public Venda lastSell(){
        return null;
    }*/

}

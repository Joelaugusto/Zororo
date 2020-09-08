package sample.model.DAO;

import sample.dataBase.ConnectionFactory;
import sample.model.modelo.Produto;
import sample.model.modelo.Produto;

import javax.persistence.EntityManager;
import java.util.List;

public class ProdutoDAO {

    public void save (Produto produto){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(produto);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public void update (Produto produto){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.merge(produto);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public Produto findById(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        Produto produto = null;

        try{
            produto = entityManager.find(Produto.class,id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return produto;
    }

    public List<Produto> findAll(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Produto> produtos = null;

        try{
            produtos = entityManager.createQuery("from Produto p").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return produtos;
    }

    public List<String> findAllNames(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<String> produtos = null;
        try{
            produtos = entityManager.createQuery("select nome from Produto p").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return produtos;
    }

    public List<Produto> findByName( String name){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Produto> produtos = null;

        try{
            produtos = entityManager.createQuery("from Produto p where nome = '"+name+ "'", Produto.class).getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return produtos;
    }

    public void remove(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        Produto produto = null;

        try{
            produto = entityManager.find(Produto.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(produto);
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

}

package sample.model.DAO;

import sample.dataBase.ConnectionFactory;
import sample.model.modelo.Unidade;

import javax.persistence.EntityManager;
import java.util.List;

public class UnidadeDAO {

    public void save (Unidade unidade){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(unidade);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public void update (Unidade unidade){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.merge(unidade);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public Unidade findById(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        Unidade unidade = null;

        try{
            unidade = entityManager.find(Unidade.class,id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return unidade;
    }

    public List<Unidade> findAll(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Unidade> unidade = null;
        try{
            unidade = entityManager.createQuery("from Unidade u").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return unidade;
    }

    //SELECT u.id from unidade u, produto p WHERE p.unidade_id = u.id and p.id = 5
    public Unidade findByProdutoId(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        Unidade unidade = null;

        String query2 = "SELECT p.unidade.id from Produto p WHERE p.id = "+id;
        try{
            Object object = entityManager.createQuery(query2).getSingleResult();
            unidade = entityManager.find(Unidade.class, object);
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return unidade;
    }

    public List<String> findAllNomes(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<String> unidade = null;

        try{
            unidade = entityManager.createQuery("select unidade from Unidade u").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return unidade;
    }

    public List<Unidade> findByName(String nome){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Unidade> categorias = null;

        try{
            categorias = entityManager.createQuery("from Unidade where unidade = '"+nome+"'").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return categorias;
    }


    public void remove(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        Unidade unidade = null;

        try{
            unidade = entityManager.find(Unidade.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(unidade);
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }
}

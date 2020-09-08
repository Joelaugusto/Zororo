package sample.model.DAO;

import sample.dataBase.ConnectionFactory;
import sample.model.modelo.Categoria;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public class CategoriaDAO {

    public void save (Categoria categoria){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(categoria);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public void update (Categoria categoria){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.merge(categoria);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public Categoria findById(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        Categoria categoria = null;

        try{
            categoria = entityManager.find(Categoria.class,id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return categoria;
    }

    public List<Categoria> findAll(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Categoria> categorias = null;
        try{
            categorias = entityManager.createQuery("from Categoria c").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return categorias;
    }

    public List<String> findAllNomes(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<String> categorias = null;

        try{
            categorias = entityManager.createQuery("select nome from Categoria c").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return categorias;
    }

    public List<Categoria> findByName(String nome){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Categoria> categorias = null;

        try{
            categorias = entityManager.createQuery("from Categoria where nome = '"+nome+"'").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
        return categorias;
    }


    public void remove(int id){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        Categoria categoria = null;

        try{
            categoria = entityManager.find(Categoria.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(categoria);
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }
}

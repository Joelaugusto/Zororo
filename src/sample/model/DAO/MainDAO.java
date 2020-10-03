package sample.model.DAO;

import sample.dataBase.ConnectionFactory;

import javax.persistence.EntityManager;

public class MainDAO {

    public MainDAO(){

    }

    public void save(Object object){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    public void update (Object object){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.merge(object);
            entityManager.getTransaction().commit();
        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }


}

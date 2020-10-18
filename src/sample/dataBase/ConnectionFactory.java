package sample.dataBase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class ConnectionFactory {

    private final static EntityManagerFactory emf = Persistence.createEntityManagerFactory("zororoPU");

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

}

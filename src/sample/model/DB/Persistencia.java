package sample.model.DB;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Persistencia {





    private  static Session session;
    private static EntityManagerFactory factory;
    static {
        factory = Persistence.createEntityManagerFactory("Zororo");
        session = factory.createEntityManager().unwrap(Session.class);
    }

    public static EntityManager getEntityManager(){
        return factory.createEntityManager();
    }
    public static Session sessao(){
        return session;
    }

    public static void close(){
        factory.close();
    }
}

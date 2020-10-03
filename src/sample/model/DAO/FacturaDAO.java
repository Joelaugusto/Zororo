package sample.model.DAO;


import sample.dataBase.ConnectionFactory;
import sample.model.modelo.Factura;

import javax.persistence.EntityManager;

public class FacturaDAO {

    public  FacturaDAO (){

    }

    public void registrarFactura(Factura factura){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();

        try{
            entityManager.getTransaction().begin();
            entityManager.persist(factura);
            entityManager.getTransaction().commit();

        }catch(Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
        }finally {
            entityManager.close();
        }
    }

    /*public List<Factura> findAll(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Factura> facturas = null;

        try{
            facturas = entityManager.createQuery("from Factura f").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return facturas;
    }*/

    //Buscar MelhorJeito
    /*public Factura findLast(){
        EntityManager entityManager = new ConnectionFactory().getEntityManager();
        List<Factura> facturas = null;

        try{
            facturas = entityManager.createQuery("from Factura f").getResultList();
        }catch(Exception e){
            System.err.println(e.getMessage());
        }finally {
            entityManager.close();
        }

        return facturas.get(facturas.size() - 1);
    }*/
}

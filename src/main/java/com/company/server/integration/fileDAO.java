//package com.company.server.integration;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.EntityTransaction;
//import javax.persistence.Persistence;
//import java.io.File;
//
//public class fileDAO {
//    private final EntityManagerFactory fact;
//    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
//
//    public fileDAO() {
//        fact = Persistence.createEntityManagerFactory("filesPersistenceUnit");
//    }
//
////    public File findFile (String fileName, boolean endOfTransaction) {
//////        if (fileName == null) {
//////            return null;
//////        }
//////
//////        try{
//////            EntityManager entityManager = beginTransaction();
//////            try {
//////                return entityManager.createNamedQuery("findFile", File.class).
//////                        setParameter("fileName", fileName).getSingleResult();
//////            }
//////        }
////
////    }
//
//    private EntityManager beginTransaction() {
//        EntityManager entityManager = fact.createEntityManager();
//        entityManagerThreadLocal.set(entityManager);
//        EntityTransaction transaction = entityManager.getTransaction();
//        if (!transaction.isActive()) {
//            transaction.begin();
//        }
//        return entityManager;
//    }
//}

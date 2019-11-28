package com.company.server.integration;

import com.company.server.model.FilesEntity;
import com.company.server.model.UserEntity;

import javax.persistence.*;

public class FileDAO {
    private final EntityManagerFactory fact;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public FileDAO() {
        fact = Persistence.createEntityManagerFactory("filesPersistenceUnit");
    }

    public void addFile(FilesEntity filesEntity) {
        System.out.println("add file metadata to db" + filesEntity);
        try {
            EntityManager entityManager = beginTransaction();
            entityManager.persist(filesEntity);
        } finally {
            commitTransaction();
        }
    }

    public FilesEntity findFile (String fileName, boolean endOfTransaction) {
        if (fileName == null) {
            return null;
        }

        try{
            EntityManager entityManager = beginTransaction();
            try {
                return entityManager.createNamedQuery("findFile", FilesEntity.class).
                        setParameter("fileName", fileName).getSingleResult();
            }catch (NoResultException noFileWithThisName) {
                return null;
            }
        }finally {
            if (endOfTransaction){

            }
        }

    }

    private EntityManager beginTransaction() {
        EntityManager entityManager = fact.createEntityManager();
        entityManagerThreadLocal.set(entityManager);
        EntityTransaction transaction = entityManager.getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        return entityManager;
    }

    private void commitTransaction() {
        entityManagerThreadLocal.get().getTransaction().commit();

    }
}

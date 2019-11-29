package com.company.server.integration;

import com.company.server.model.FilesEntity;
import com.company.server.model.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public void deleteFile (String fileName) {
        try {
            EntityManager entityManager = beginTransaction();
            entityManager.createNamedQuery("deleteFile", FilesEntity.class).setParameter("name", fileName).executeUpdate();
        } finally {
            commitTransaction();
        }
    }

    public FilesEntity findFile (String fileName, boolean endOfTransaction) {
        FilesEntity find;
        if (fileName == null) {
            find= null;
        }

        try{
            EntityManager entityManager = beginTransaction();
            try {
                find= entityManager.createNamedQuery("findFile", FilesEntity.class).
                        setParameter("fileName", fileName).getSingleResult();
            }catch (NoResultException noFileWithThisName) {
                find= null;
            }
        }finally {
            if (endOfTransaction){
                commitTransaction();
            }
        }
        return find;

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
    public void updateFile(){
        commitTransaction();
    }

    public List<FilesEntity> getAllFile() {
        List<FilesEntity> arr;
        try {
            EntityManager em = beginTransaction();
            try {
                arr = em.createNamedQuery("getAllFiles", FilesEntity.class).getResultList();
            } catch (NoResultException noSuchAccount) {

                arr = new ArrayList<>();
            } catch (Exception e){
                arr = new ArrayList<>();
                System.out.println(e);
            }
        } finally {
            commitTransaction();
        }
        return arr;
    }
}

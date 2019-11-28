package com.company.server.integration;

//import com.company.common.UserDTO;
import com.company.server.model.FilesEntity;
import com.company.server.model.UserEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO{
    private final EntityManagerFactory fact;
    private final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();

    public UserDAO() {
        fact = Persistence.createEntityManagerFactory("filesPersistenceUnit");
    }

//    public void registerUser(UserDTO user) {
//        try {
//            EntityManager em = beginTransaction();
//            em.persist(user);
//        } finally {
//            commitTransaction();
//        }
//    }

    public UserEntity findUser(String userName, boolean endTransactionAfterSearching){
        UserEntity user;
        if (userName == null){
            user= null;
        }
        try {
            EntityManager entityManager = beginTransaction();
            try {
                user= (UserEntity) entityManager.createNamedQuery("findUser", UserEntity.class).setParameter("username", userName).getSingleResult();
            } catch (NoResultException userNotExist) {
                user= null;
            }
        } finally {
            if (endTransactionAfterSearching){
                commitTransaction();
            }
        }
        return user;
    }

    public UserEntity checkPassword(String password, boolean endTransactionAfterSearching) {
        UserEntity user;
        if (password == null) {
            user= null;
        }
        try {
            EntityManager entityManager = beginTransaction();
            try {
                user= entityManager.createNamedQuery("checkPassword", UserEntity.class).setParameter("password", password).getSingleResult();
            }catch (NoResultException passwordWrong) {
                user= null;
            }
        }finally {
            if (endTransactionAfterSearching){
                commitTransaction();
            }
        }
        return user;
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

    public void registerUser(UserEntity userEntity) {
        System.out.println("register user" + userEntity);
        try {
            EntityManager entityManager = beginTransaction();
            entityManager.persist(userEntity);
        } finally {
            commitTransaction();
        }
    }



}

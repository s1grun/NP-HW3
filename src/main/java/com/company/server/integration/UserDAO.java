package com.company.server.integration;

import com.company.common.UserDTO;
import com.company.server.model.UserEntity;

import javax.persistence.*;

public class UserDAO {
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
        if (userName == null){
            return null;
        }
        try {
            EntityManager entityManager = beginTransaction();
            try {
                return (UserEntity) entityManager.createNamedQuery("findUser", UserEntity.class).setParameter("username", userName).getSingleResult();
            } catch (NoResultException userNotExist) {
                return null;
            }
        } finally {
            if (endTransactionAfterSearching){
                commitTransaction();
            }
        }
    }

    public UserEntity checkPassword(String password, boolean endTransactionAfterSearching) {
        if (password == null) {
            return null;
        }
        try {
            EntityManager entityManager = beginTransaction();
            try {
                return entityManager.createNamedQuery("checkPassword", UserEntity.class).setParameter("password", password).getSingleResult();
            }catch (NoResultException passwordWrong) {
                return null;
            }
        }finally {
            if (endTransactionAfterSearching){
                commitTransaction();
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

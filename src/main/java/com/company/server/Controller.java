package com.company.server;

import com.company.common.*;
//import com.company.server.integration.fileDAO;
import com.company.server.integration.*;
import com.company.server.model.UserEntity;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by weng on 2019/11/25.
 */
public class Controller extends UnicastRemoteObject implements FileServer {
//    private final fileDAO filesDb;
    private final UserDAO userDAO;

    protected Controller() throws RemoteException {
        super();
//        filesDb = new fileDAO();
        userDAO = new UserDAO();
    }

    @Override
    public void userLogin(String userName, String pw) throws RemoteException {
        System.out.println(userName+pw);
    }

    @Override
    public boolean register(String userName, String pw) throws Exception {
        System.out.println(111111111);
        try {
            if (userDAO.findUser(userName, true) != null) {
                throw new Exception("User: " + userName + " already exists");
            }
            userDAO.registerUser( new UserEntity(userName, pw));
            return true;
        } catch (Exception e) {
            throw new Exception("Could not create user: " + userName, e);
        }
    }

    @Override
    public int uploadFile(String name) throws RemoteException {
        FileHandler handler = new FileHandler(1);
        ForkJoinPool.commonPool().execute(handler);

        return 200;

    }

    @Override
    public void downloadFile(String name) throws RemoteException {

    }
}

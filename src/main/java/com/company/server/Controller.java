package com.company.server;

import com.company.common.*;
//import com.company.server.integration.fileDAO;
import com.company.server.integration.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by weng on 2019/11/25.
 */
public class Controller extends UnicastRemoteObject implements FileServer {
    private final fileDAO filesDb;
    protected Controller() throws RemoteException {
        super();
        filesDb = new fileDAO();
    }

    @Override
    public void userLogin(String userName, String pw) throws RemoteException {
        System.out.println(userName+pw);
    }

    @Override
    public void register(String userName, String pw) throws RemoteException {

    }

    @Override
    public void uploadFile(String name) throws RemoteException {

    }

    @Override
    public void downloadFile(String name) throws RemoteException {

    }
}

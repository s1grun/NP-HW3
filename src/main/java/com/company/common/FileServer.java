package com.company.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by weng on 2019/11/25.
 */
public interface FileServer extends Remote {

    public boolean userLogin(String userName, String pw) throws Exception;
    public boolean register(String userName, String pw) throws Exception;
    int uploadFile(String name, String owner, int size) throws Exception;
    int downloadFile(String name) throws RemoteException;

}

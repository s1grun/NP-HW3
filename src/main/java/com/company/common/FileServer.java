package main.java.com.company.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by weng on 2019/11/25.
 */
public interface FileServer extends Remote {

    public void userLogin(String userName, String pw)throws RemoteException;
    public void register(String userName,String pw)throws RemoteException;
    void uploadFile(String name) throws RemoteException;
    void downloadFile(String name) throws RemoteException;

}

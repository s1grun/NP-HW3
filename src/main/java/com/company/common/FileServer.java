package com.company.common;

import com.company.client.Client;
import com.company.client.ClientController;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by weng on 2019/11/25.
 */
public interface FileServer extends Remote {

    public UserDTO userLogin(String userName, String pw) throws Exception;
    public boolean register(String userName, String pw) throws Exception;
    CommunicateStatus uploadFile(String name, String owner, long size, int permission) throws Exception;
    int downloadFile(String name) throws RemoteException;
    public List<? extends FileDTO> getFileList() throws RemoteException;

}

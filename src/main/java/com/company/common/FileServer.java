package com.company.common;

import com.company.server.model.FilesEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by weng on 2019/11/25.
 */
public interface FileServer extends Remote {

    public UserDTO userLogin(String userName, String pw) throws Exception;
    public boolean register(String userName, String pw) throws Exception;
    int uploadFile(String name) throws RemoteException;
    int downloadFile(String name) throws RemoteException;
    public List<FilesEntity> getFileList();

}

package com.company.server.controller;

import com.company.common.*;
//import com.company.server.integration.fileDAO;
import com.company.server.FileHandler;
import com.company.server.integration.*;
import com.company.server.model.FilesEntity;
import com.company.server.model.UserEntity;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import javax.security.auth.login.AccountException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by weng on 2019/11/25.
 */
public class Controller extends UnicastRemoteObject implements FileServer {
    private final FileDAO filesDAO;
    private final UserDAO userDAO;
    private List<ClientNotification> notificationList = new ArrayList<>();

    public Controller() throws RemoteException {
        super();
        filesDAO = new FileDAO();
        userDAO = new UserDAO();
    }

    public void addNotification(String servicename) throws RemoteException, NotBoundException, MalformedURLException {
        ClientNotification noti_service = (ClientNotification) Naming.lookup("notification"+servicename);
        System.out.println("aaaaaaaaaaaa");
        notificationList.add(noti_service);
    }




    @Override
    public UserDTO userLogin(String userName, String pw) throws Exception {
        System.out.println(userName+pw);
        try {
            if (userDAO.findUser(userName, true) != null) {
                UserDTO new_user = (UserDTO) userDAO.checkPassword(pw, true);
                if( new_user!= null) {
//                    addNotification(new_user.toString());
//                    notificationList.get(0).sendNotification("test notification");
                    return new_user;
                }
            }
        }catch (Exception e) {
            throw new Exception("login failed " + e);
        }
        return null;
    }

    @Override
    public boolean register(String userName, String pw) throws Exception {
        try {
            if (userDAO.findUser(userName, true) != null) {
                return false;
            }
            userDAO.registerUser( new UserEntity(userName, pw));
            return true;
        } catch (Exception e) {
            throw new Exception("Could not create user: " + userName, e);
        }
    }


    @Override
    public CommunicateStatus uploadFile(String name, String owner, long size, int permission) throws Exception {
        FileHandler handler =null;
        try{
            handler = new FileHandler(1);
            ForkJoinPool.commonPool().execute(handler);
        }catch (Exception e){
            System.out.println("server close "+e);
        }

        try {
            FilesEntity the_file = filesDAO.findFile(name, false);
            if (the_file != null) {
//                System.out.println(the_file);
                if(the_file.getOwner() == owner){
//                    filesDAO.addFile(new FilesEntity(name, owner, size,permission));
                    the_file.setOwner(owner);
                    the_file.setPermission(permission);
                    the_file.setSize(size);
                    filesDAO.updateFile();
                    System.out.println("File with this name: " + name + "already exists, but we will update");
                    return CommunicateStatus.CONNECTION_BUILD;
                }else{
                    if(the_file.getPermission() == 1){
                        System.out.println("no permission");
                        try{
                            handler.stop_thread();
                        }catch (Exception e){
                            System.out.println("close server "+e);
                        }

                        throw new Exception("File with this name: " + name + " already exists, no permission to update");
                    }
                    the_file.setOwner(owner);
                    the_file.setPermission(permission);
                    the_file.setSize(size);
                    filesDAO.updateFile();
//                    filesDAO.addFile(new FilesEntity(name, owner, size,permission));
                    return CommunicateStatus.CONNECTION_BUILD;
                }

            }else{
                filesDAO.updateFile();
                filesDAO.addFile(new FilesEntity(name, owner, size,permission));
                return CommunicateStatus.CONNECTION_BUILD;
            }

        } catch (Exception e) {
            System.out.println(e);
            try{
                handler.stop_thread();
            }catch (Exception e2){
                System.out.println("close server "+e2);
            }
            throw new Exception("Failed to create file in db " + e);
        }

//        return 200;

    }

    @Override
    public int downloadFile(String name) throws RemoteException {
        FileHandler handler = new FileHandler(2, name);
        ForkJoinPool.commonPool().execute(handler);
        return 200;
    }

    @Override
    public List<? extends FileDTO> getFileList() {
        try{
            return filesDAO.getAllFile();
        }catch (Exception e) {
            System.out.println("get file list failed:"+ e);
        }
        return new ArrayList<>();

    }

    @Override
    public boolean deleteFile(String name) throws RemoteException {
        try {
            if (filesDAO.findFile(name, true) == null)
                throw new RemoteException("File with this name does not exist");
            filesDAO.deleteFile(name);
            return true;
        } catch (RemoteException re) {
            throw new RemoteException("Cloud not delete file", re);
        }
    }
}

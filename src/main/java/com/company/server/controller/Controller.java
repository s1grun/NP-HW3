package com.company.server.controller;

import com.company.common.*;
//import com.company.server.integration.fileDAO;
import com.company.server.integration.*;
import com.company.server.model.FilesEntity;
import com.company.server.model.UserEntity;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by weng on 2019/11/25.
 */
public class Controller extends UnicastRemoteObject implements FileServer {
    private final FileDAO filesDAO;
    private final UserDAO userDAO;
    private Map<String ,ClientNotification> notificationList = new HashMap<>();

    public Controller() throws RemoteException {
        super();
        filesDAO = new FileDAO();
        userDAO = new UserDAO();
    }

    public void addNotification(String servicename) throws RemoteException, NotBoundException, MalformedURLException {
        ClientNotification noti_service = (ClientNotification) Naming.lookup("notification"+servicename);
//        System.out.println("aaaaaaaaaaaa");
        notificationList.put(servicename,noti_service);
    }




    @Override
    public UserDTO userLogin(String userName, String pw) throws Exception {
        System.out.println(userName+pw);
        try {
            UserEntity finduser = userDAO.findUser(userName, true);
            if ( finduser!= null) {
                if(finduser.equals(new UserEntity(userName,pw))){
                    try{
                        addNotification(userName);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    return finduser;
                }

//                UserDTO new_user = (UserDTO) userDAO.checkPassword(pw, true);
//                if( new_user!= null) {
//
//                }
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
                if(the_file.getOwner().equals(owner) ){
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
                        return CommunicateStatus.NO_PERMISSION;
                    }
                    sendNotification(the_file.getOwner(),owner+" update file");
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
    public CommunicateStatus downloadFile(String user,String name) throws RemoteException {
        try {
            FilesEntity the_file = filesDAO.findFile(name, true);

            if(the_file!=null){
                sendNotification(the_file.getOwner(),user+" download file");
                FileHandler handler = new FileHandler(2, name);
                ForkJoinPool.commonPool().execute(handler);
                return CommunicateStatus.CONNECTION_BUILD;
            }else{
                System.out.println(the_file);
                return CommunicateStatus.NO_SUCH_FILE;
            }

        }catch (Exception e){
            System.out.println("Server: find file error "+e);
            return CommunicateStatus.NO_SUCH_FILE;
        }


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
    public boolean deleteFile(String user,String name) throws RemoteException {
        try {
            FilesEntity the_file = filesDAO.findFile(name, true);
            if (the_file == null){
                throw new RemoteException("File with this name does not exist");

            }
            if( the_file.getOwner().equals(user)){
                filesDAO.deleteFile(name);
                return true;
            }else{
                return false;
            }


        } catch (RemoteException re) {
            throw new RemoteException("Cloud not delete file", re);
        }
    }



    public void sendNotification(String name,String msg) throws RemoteException {
        if(notificationList.get(name)!=null){
            notificationList.get(name).sendNotification(msg);
        }
    }
}

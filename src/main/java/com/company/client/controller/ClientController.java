package com.company.client.controller;

import com.company.common.ClientNotification;
import com.company.common.CommunicateStatus;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by weng on 2019/11/29.
 */
public class ClientController extends UnicastRemoteObject implements ClientNotification {
    public ClientController() throws RemoteException {
    }

    @Override
    public void sendNotification(String msg) throws RemoteException {
        System.out.println("NOTIFICATION: "+msg);
//        switch (msg){
//            case :
//                System.out.println("");
//                break;
//        }
    }
}

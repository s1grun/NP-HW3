package com.company.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by weng on 2019/11/29.
 */
public interface ClientNotification extends Remote{
    void sendNotification(String msg) throws RemoteException;
}

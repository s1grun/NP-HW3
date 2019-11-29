package com.company.client;
import com.company.common.ClientNotification;
import com.company.common.CommunicateStatus;
import com.company.common.FileServer;
import com.company.server.controller.Controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by weng on 2019/11/25.
 */
public class Client{

    FileServer server;

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // write your code here
        Client client = new Client();
        Command cmd = new Command(client.server,client);
        new Thread(cmd).start();
    }

    public Client() throws RemoteException, NotBoundException, MalformedURLException {

//        ClientController clientController = new ClientController();
//
//        UnicastRemoteObject.exportObject(clientController);
//
//        Registry registry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
//
//        server = (FileServer) registry.lookup("fileServer");
//
//        server.register(clientController);


        server = (FileServer) Naming.lookup("fileServer");
        System.out.println("get connection with fileServer method");

    }

    public void startReceivingNotification(String identify_name) throws RemoteException, NotBoundException, MalformedURLException {
        try {
            LocateRegistry.getRegistry().list();
        } catch (RemoteException noRegistryRunning) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
        ClientController clientController = new ClientController();
        Naming.rebind("notification"+identify_name, clientController);
        System.out.println("start notification service");
    }


}

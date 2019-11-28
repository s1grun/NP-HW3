package com.company.server.startup;

import com.company.server.controller.Controller;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Created by weng on 2019/11/25.
 */
public class Server {
    public static void main(String[] args) {
        // write your code here
        Server server = new Server();
        try {
            server.startServer();
        } catch (RemoteException e) {
            System.out.println("failed to create RMI server "+e);
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }



    public Server(){

    }


    void startServer() throws RemoteException, MalformedURLException {
        try {
            LocateRegistry.getRegistry().list();
        } catch (RemoteException noRegistryRunning) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
        Controller controller = new Controller();
        Naming.rebind("fileServer", controller);

    }


}

package com.company.server;

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
            System.out.println("failed to create RMI server");
        }
    }



    public Server(){

    }


    void startServer() throws RemoteException {
        try {
            LocateRegistry.getRegistry().list();
        } catch (RemoteException noRegistryRunning) {
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        }
//        Controller controller = new Controller();
//        Naming.rebind("catalog", controller);

    }


}

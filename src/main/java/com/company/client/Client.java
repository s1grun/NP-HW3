package main.java.com.company.client;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by weng on 2019/11/25.
 */
public class Client {

    main.java.com.company.common.FileServer server;

    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        // write your code here
        Client client = new Client();
        Command cmd = new Command(client.server);
        new Thread(cmd).start();
    }

    public Client() throws RemoteException, NotBoundException, MalformedURLException {
        server = (main.java.com.company.common.FileServer) Naming.lookup("fileServer");
        System.out.println("get connection with fileServer method");
    }



}

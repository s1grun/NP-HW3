package com.company.client;

/**
 * Created by weng on 2019/11/25.
 */
public class Client {
    public static void main(String[] args) {
        // write your code here
        Client client = new Client();
        Command cmd = new Command();
        new Thread(cmd).start();
    }

    public Client(){

    }



}

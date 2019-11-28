package com.company.client;


import com.company.client.view.ClientView;
import com.company.common.FileServer;

import java.io.File;
import java.util.Scanner;

/**
 * Created by weng on 2019/11/25.
 */
public class Command implements Runnable{
    private Scanner console = new Scanner(System.in);
    private FileServer server ;
    public Command(FileServer server){
        this.server = server;
    }


    @Override
    public void run() {
        System.out.println("client start reading command");
        while (true){
            try {

                String cmd= console.nextLine();
                String cmd_type = cmd.split(" ")[0];

                switch (cmd_type) {
                    case "disconnect":
                        new ClientView(0);
                        break;
                    case "upload":
                        String cmd_text = cmd.split(" ")[1];
                       try{
                           File f = new File(cmd_text);
                       }catch (Exception e){
                           System.out.println("file error");
                           System.out.println(e);
                           break;
                       }

                        int status = server.uploadFile(cmd_text);
                        System.out.println(status);

                        if (status == 200){
                            ClientFileHandler handler = new ClientFileHandler();
                            handler.uploadSocket(cmd_text);
                            new ClientView(200);
                        }

                        break;
                    case "register":
                        boolean res = server.register("qingtao","123");
                        if(res){
                            new ClientView(110);
                        }else {
                            new ClientView(500);
                        }
                    case "login":
                        boolean log = server.userLogin("qingtao","123");
                        if (log){
                            new ClientView(210);
                        }else {
                            new ClientView(440);
                        }
                        break;
                    default:
                        System.out.println("Unknown command");
                        break;

                }
            } catch (Exception e) {
                System.out.println("client read cmd failed "+e);
            }
        }
    }
}



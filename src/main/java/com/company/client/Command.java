package com.company.client;


import com.company.client.view.ClientView;
import com.company.common.FileServer;
import com.company.common.UserDTO;


import java.io.File;
import java.util.Scanner;

/**
 * Created by weng on 2019/11/25.
 */
public class Command implements Runnable{
    private Scanner console = new Scanner(System.in);
    private FileServer server ;
    private UserDTO user = null;
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
                        String filename = cmd.split(" ")[1];
                        File file;
                        try{
                            file = new File(filename);
                        }catch (Exception e){
                            System.out.println("file error");
                            System.out.println(e);
                            break;
                        }

                        int status = server.uploadFile(filename, "qingtao", file.length());
                        System.out.println(status);

                        if (status == 200){
                            ClientFileHandler handler = new ClientFileHandler();
                            handler.uploadSocket(filename);
                            new ClientView(200);
                        }

                        break;

                    case "download":
                        String fname = cmd.split(" ")[1];
                        if(server.downloadFile(fname)==200){
                            ClientFileHandler handler = new ClientFileHandler();
                            handler.downloadFile();
                            System.out.println("download successfully");
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
                        user = server.userLogin("qingtao","123");
                        if (user!= null){
                            new ClientView(210);
                        }else {
                            new ClientView(440);
                        }
                        break;
                    case "fileList":
                        if(user!=null){
                            server.getFileList();
                        }else{
                            new ClientView(441);
                        }

                    default:
                        System.out.println("Unknown command");
                        break;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}



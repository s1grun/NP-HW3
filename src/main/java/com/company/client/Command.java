package com.company.client;


import com.company.client.view.ClientView;
import com.company.common.CommunicateStatus;
import com.company.common.FileDTO;
import com.company.common.FileServer;
import com.company.common.UserDTO;


import java.io.File;
import java.rmi.ServerError;
import java.rmi.ServerException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by weng on 2019/11/25.
 */
public class Command implements Runnable{
    private Scanner console = new Scanner(System.in);
    private FileServer server ;
    private UserDTO user = null;
    final int READ_ONLY=1;
    final int WRITE=0;
    private Client client;
    public Command(FileServer server,Client client){
        this.server = server;
        this.client = client;
    }


    @Override
    public void run() {
        System.out.println("Please register or login with username and password. Example: register/login john 1234");
        while (true){
            try {

                String cmd= console.nextLine();
                String cmd_type = cmd.split(" ")[0];

                switch (cmd_type) {
                    case "logout":
                        user = null;
                        new ClientView(0);
                        break;
                    case "upload":
                        if(user!=null){
                            String filename = cmd.split(" ")[1];
                            int permission = WRITE;
                            try{
                                permission=Integer.getInteger(cmd.split(" ")[2]) ;
                            }catch (Exception e){
                                System.out.println("permission default WRITE");
                            }

                            File file;

                            file = new File(filename);
                            if(!file.exists()){
                                System.out.println("file error");
                                break;
                            }

                            try{
                                CommunicateStatus status = server.uploadFile(filename, user.getUsername(), file.length(),permission);
                                System.out.println(status);
                                if (status == CommunicateStatus.CONNECTION_BUILD){
                                    ClientFileHandler handler = new ClientFileHandler();
                                    handler.uploadSocket(filename);
                                    new ClientView(200);
                                }
                            }catch (Exception e){
                                System.out.println(e);
                            }


                        }else {
                            new ClientView(441);
                        }


                        break;

                    case "download":
                        if(user!=null){
                            String fname = cmd.split(" ")[1];
                            if(server.downloadFile(fname)==200){
                                ClientFileHandler handler = new ClientFileHandler();
                                handler.downloadFile();
                                System.out.println("download successfully");
                            }
                        }else{
                            new ClientView(441);
                        }

                        break;
                    case "register":
                        String username = cmd.split(" ")[1];
                        String password = cmd.split(" ")[2];
                        boolean res = server.register(username, password);
                        if(res){
                            new ClientView(110);
                        }else {
                            new ClientView(500);
                        }
                        break;
                    case "login":
                        String usern = cmd.split(" ")[1];
                        String passw = cmd.split(" ")[2];
                        user = server.userLogin(usern, passw);
                        if (user!= null){
                            new ClientView(210);
                        }else {
                            new ClientView(440);
                        }
                        break;
                    case "fileList":
                        if(user!=null){
                            List<? extends FileDTO> file_list = server.getFileList();
                            for(FileDTO files:file_list){
                                System.out.println(files.getName()+" upload by: "+ files.getOwner());
                            }
                        }else{
                            new ClientView(441);
                        }
                        break;
                    case "deleteFile":
                        String fname = cmd.split(" ")[1];
                        if (user != null){
                            if(server.deleteFile(fname)){
                                new ClientView(250);
                            }

                        }
                        break;
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



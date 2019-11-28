package com.company.server;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by weng on 2019/11/28.
 */
public class FileHandler implements Runnable{

    private int portForFile =3001;
    private int model; //1 for upload, 2 for download;
    ServerSocket server = null;
    public FileHandler(int model) {
        this.model = model;
    }

    public void receiveFile() throws IOException {
        try {
            server= new ServerSocket(portForFile);
            while (true){
                Socket socket = server.accept();
                System.out.println("log: client connected.");
                DataInputStream in_stream = new DataInputStream(socket.getInputStream());
                OutputStream out = socket.getOutputStream();
                String filename="./Storage/";
                filename += in_stream.readUTF();
                System.out.println("start transfer:"+ filename);
                FileOutputStream fileOut = new FileOutputStream(filename);
                byte[] b = new byte[2048];
                int length = 0;
                while((length=in_stream.read(b))!=-1){
                    fileOut.write(b, 0, length);
                }
                fileOut.flush();
                fileOut.close();
                in_stream.close();
                socket.close();
                server.close();
                Thread.currentThread().stop();
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(server!=null){
                server.close();
            }
            Thread.currentThread().stop();
        }
    }




    public void downloadFile(){

    }

    @Override
    public void run() {
        if(this.model==1){
            try {
                receiveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

package com.company.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by weng on 2019/11/28.
 */
public class FileHandler implements Runnable{

    private int portForFile =3001;
    private int model; //1 for upload, 2 for download;
    ServerSocket server = null;
    private String fname;
    public FileHandler(int model) {
        this.model = model;
    }
    public FileHandler(int model, String fname) {
        this.model = model;
        this.fname = fname;
    }

    public void receiveFile() throws IOException {
        try {
            server= new ServerSocket(portForFile);
            while (true){
                Socket socket = server.accept();
                System.out.println("log: client connected.");
                DataInputStream in_stream = new DataInputStream(socket.getInputStream());
//                OutputStream out = socket.getOutputStream();
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




    public void downloadFile(String path){
        boolean status =true;
        try {
            server= new ServerSocket(portForFile);

            while (status){
                String filename="./Storage/"+path;
                File f = new File(filename);
                Socket socket = server.accept();
                DataInputStream fileStream = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                out.writeUTF(f.getName());
                out.flush();

                byte[] buf = new byte[1024];

                int read=0;
                while((read=fileStream.read(buf))!=-1){
                    out.write(buf, 0, read);
                }

                out.flush();
                status=false;
                fileStream.close();
                try {
                    socket.close();
                    server.close();
                }catch (Exception e){
                    System.out.println(e);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().stop();
        }
    }

    @Override
    public void run() {
        if(this.model==1){
            try {
                receiveFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(this.model==2){
            downloadFile(fname);
        }

    }


}

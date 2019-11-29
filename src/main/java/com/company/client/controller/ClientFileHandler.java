package com.company.client.controller;



import java.io.*;
import java.net.Socket;

/**
 * Created by weng on 2019/11/28.
 */
public class ClientFileHandler {
    int port=3001;
    String addr = "localhost";
    boolean status= true;


    public ClientFileHandler() {

    }

    public ClientFileHandler(int port) {
        this.port = port;
    }

    public void uploadSocket(String file_path) throws IOException {
        Socket socket = null;
        socket = new Socket(addr,port);
        status = true;
        while (status){
            File f = new File(file_path);
            DataInputStream fileStream = new DataInputStream(new BufferedInputStream(new FileInputStream(f)));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(f.getName());
            out.flush();

            byte[] buf = new byte[1024];
            while (true) {
                int read = 0;
                read = fileStream.read(buf);
                if (read == -1) {
                    break;
                }
                out.write(buf, 0, read);
            }

            out.flush();
            status=false;
            fileStream.close();
            try {
                socket.close();
            }catch (Exception e){
                System.out.println(e);
            }

        }


    }


    public void downloadFile() throws IOException {
        Socket socket = null;
        socket = new Socket(addr,port);
        status = true;
        while (status){
            DataInputStream in_stream = new DataInputStream(socket.getInputStream());
            OutputStream out = socket.getOutputStream();
            String filename="/Users/weng/Downloads/";
            filename += in_stream.readUTF();
            System.out.println("start transfer:"+ filename);
            FileOutputStream fileOut = new FileOutputStream(filename);
            byte[] b = new byte[2048];
            int length = 0;
            while((length=in_stream.read(b))!=-1){
                fileOut.write(b, 0, length);
            }
            status =false;
            fileOut.flush();
            fileOut.close();
            in_stream.close();
            socket.close();

        }


    }


}

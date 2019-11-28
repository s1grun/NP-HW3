package com.company.client;



import java.io.*;
import java.net.Socket;

/**
 * Created by weng on 2019/11/28.
 */
public class ClientFileHandler implements Runnable {
    int port=3001;
    String addr = "localhost";
    boolean status= true;


    public ClientFileHandler() {

    }

    public ClientFileHandler(int port) {
        this.port = port;
    }

    public void uploadSocket(String path) throws IOException {
        Socket socket = null;
        socket = new Socket(addr,port);

        while (status){
            File f = new File(path);
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

    @Override
    public void run() {

    }
}

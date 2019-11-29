package com.company.client.view;

public class ClientView {

    public ClientView(int status) {
        switch (status){
            case 210:
                System.out.println("Login was successful!");
                System.out.println("To see a list of all files do: fileList");
                System.out.println("To upload a file do: upload filename");
                System.out.println("To download a file do: download filename");
                System.out.println("To delete a file do: deleteFile filename");
                break;
            case 110:
                System.out.println("Registration was successful!");
                break;
            case 500:
                System.out.println("Registration failed. Please use a unique username");
                break;
            case 0:
                System.out.println("Disconnected");
                break;
            case 200:
                System.out.println("Uploading file was successful!");
                break;
            case 440:
                System.out.println("Login failed");
                break;
            case 441:
                System.out.println("Please login");
                break;
            case 250:
                System.out.println("File was deleted successfully!");
                break;

        }

    }
}

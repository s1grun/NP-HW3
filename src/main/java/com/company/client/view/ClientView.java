package com.company.client.view;

import com.company.common.CommunicateStatus;

public class ClientView {

    public ClientView(CommunicateStatus status) {
        switch (status){
            case LOGIN_SUCCEED:
                System.out.println("Login was successful!");
                System.out.println("To see a list of all files do: fileList");
                System.out.println("To upload a file do: upload filename");
                System.out.println("To download a file do: download filename");
                System.out.println("To delete a file do: deleteFile filename");
                break;
            case REGISTER_SUCCEED:
                System.out.println("Registration was successful!");
                break;
            case REGISTER_FAILED:
                System.out.println("Registration failed. Please use a unique username");
                break;
            case LOG_OUT:
                System.out.println("Disconnected");
                break;
            case FILE_TRANSFER_SUCCEED:
                System.out.println("file transfer was successful!");
                break;
            case LOGIN_FAILED:
                System.out.println("Login failed");
                break;
            case LOG_IN_NEEDED:
                System.out.println("Please login");
                break;
            case DELETE_SUCCESSFULLY:
                System.out.println("File was deleted successfully!");
                break;
            case NO_SUCH_FILE:
                System.out.println("No such file");
                break;
            case NO_PERMISSION:
                System.out.println("NO PERMISSION");
                break;
            case DELETE_FAILED:
                System.out.println("DELETE FAILED");
                break;

        }

    }
}

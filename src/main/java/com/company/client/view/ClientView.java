package com.company.client.view;

public class ClientView {

    public ClientView(int status) {
        switch (status){
            case 210:
                System.out.println("Login was successful!");
                break;
            case 110:
                System.out.println("Registration was successful!");
                break;
            case 500:
                System.out.println("Registration failed");
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

        }

    }
}

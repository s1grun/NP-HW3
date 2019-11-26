package com.company.client;

import java.util.Scanner;

/**
 * Created by weng on 2019/11/25.
 */
public class Command implements Runnable{
    private Scanner console = new Scanner(System.in);


    @Override
    public void run() {
        System.out.println("client start reading command");
        while (true){
            try {

                String cmd= console.nextLine();
                String cmd_type = cmd.split(" ")[0];

                switch (cmd_type) {
                    case "disconnect":

                        break;
                    case "try":
                        String cmd_text = cmd.split(" ")[1];


                        break;
                    case "start":

                        break;
                    default:
                        System.out.println("Unknown command");
                        break;

                }
            } catch (Exception e) {
                System.out.println("client read cmd failed");
            }
        }
    }
}



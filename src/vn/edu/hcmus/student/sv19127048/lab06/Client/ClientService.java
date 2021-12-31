package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 8:20 PM<br> Description: JDK16<br>
 */
public class ClientService {

  private Socket socket;
  private String privateIp;
  private HashMap<String, ArrayList<String>> clients;

  public ClientService(String privateIp) {
    try {
      socket = new Socket("localhost", 9999);
      this.privateIp = privateIp;
      clients = new HashMap<>();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void connect() {
    while (true) {
      getInputConnect();
      getOutputConnect();
    }
  }

  private void addMess(String ip, String mess) {
    System.out.println(ip);
    System.out.println(mess);
    if (clients.containsKey(ip)) {
      clients.get(ip).add(mess);
    } else {
      ArrayList<String> messArrayList = new ArrayList<>(List.of(mess));
      clients.put(ip, messArrayList);
    }
  }

  private void getInputConnect() {
    new Thread(() -> {
      try {
        Scanner in = new Scanner(socket.getInputStream());

        while (in.hasNextLine()) {
          System.out.println(in.nextLine());
          String[] data = in.nextLine().split(": ");
          addMess(data[0], data[1]);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }

  private void getOutputConnect() {
    new Thread(new Runnable() {
      Scanner sc = new Scanner(System.in);
      String msg = sc.nextLine();

      @Override
      public void run() {
        try {
          if (msg.equalsIgnoreCase("bye")) {
            System.exit(1);
          }
          PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
          out.println(String.format("%s: %s", privateIp, msg));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}

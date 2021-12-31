package vn.edu.hcmus.student.sv19127048.lab06.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Server<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 9:08 PM<br> Description: JDK16<br>
 */
public class ServerService {

  private ServerSocket serverSocket;
  private String message = "";
  private ArrayList<Socket> sockets;

  public ServerService() {
    try {
      this.serverSocket = new ServerSocket(9999);
      sockets = new ArrayList<>();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listeningClient() {
    while (true) {
      try {
        final Socket soc = serverSocket.accept();
        sockets.add(soc);
        System.out.println("number of client is " + sockets.size());

        new Thread(() -> {
          try {
            Scanner in = new Scanner(soc.getInputStream());
            while (in.hasNextLine()) {
              message = in.nextLine();
              for (Socket s : sockets) {
                if (s == null) {
                  sockets.remove(s);
                } else if (!s.equals(soc)) {
                  PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                  out.println(message);
                }
              }
            }
          } catch (IOException e) {
            e.printStackTrace();
          }
        }).start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}

package vn.edu.hcmus.student.sv19127048.lab06.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import vn.edu.hcmus.student.sv19127048.lab06.Client.ClientAccount;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Server<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 9:08 PM<br> Description: JDK16<br>
 */
public class ServerService {

  private ServerSocket serverSocket;
  private String message = "";
  private ArrayList<ClientAccount> clients;

  public ServerService() {
    try {
      this.serverSocket = new ServerSocket(9999);
      clients = new ArrayList<>();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listeningClient() {
    while (true) {
      try {
        final Socket soc = serverSocket.accept();
        clients.add(new ClientAccount(soc));
        System.out.println("number of client is " + clients.size());

        clients.forEach(e -> System.out.println(e.getPrivatePort()));

        new Thread(() -> {
          try {
            Scanner in = new Scanner(soc.getInputStream());
            while (in.hasNextLine()) {
              message = in.nextLine();
              checkInputAction(message);
              clients.forEach(e -> System.out.println(e.getUsername()));
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

  private void checkInputAction(String message) throws IOException {
    if (message.startsWith("message=")) {
      int startIdx = "message=".length();
      String substr = message.substring(startIdx);
      handleChatAction(substr);

    } else if (message.startsWith("connect=")) {
      int startIdx = "connect=".length();
      String substr = message.substring(startIdx);
      handleConnectAction(substr);
    } else if (message.startsWith("disconnect=")) {
      int startIdx = "disconnect=".length();
      String substr = message.substring(startIdx);
      handleDisconnectAction(substr);
    }
  }

  private void handleChatAction(String message) throws IOException {
    // Tách String từ client để lấy mess và port muốn gửi tới
    String[] data = message.split(" - ");
    String fromClient = data[0];
    String temp = data[1];

    data = temp.split(": ");
    int toClient = Integer.parseInt(data[0]);
    String mess = data[1];

    for (ClientAccount ca : clients) {
      if (ca.getSocket() == null) {
        clients.remove(ca);
      } else if (ca.getPrivatePort() == toClient) {
        PrintWriter out = new PrintWriter(ca.getSocket().getOutputStream(), true);
        String str = String.format("%s: %s", fromClient, mess);
        System.out.println(str);
        out.println(str);
      }
    }
  }

  private void handleConnectAction(String message) throws IOException {
    String[] token = message.split(" - ");

    for (ClientAccount ca: clients) {
      if (ca.getSocket() == null) {
        clients.remove(ca);
      } else if (ca.getPrivatePort() == Integer.parseInt(token[0])) {
        ca.setUsername(token[1]);
      }
    }

    for (ClientAccount client: clients) {
      PrintWriter out = new PrintWriter(client.getSocket().getOutputStream(), true);
      for (ClientAccount clientAccount : clients) {
        if (client.equals(clientAccount)) {
          continue;
        }
        out.println(String.format("online=%s - %s", clientAccount.getPrivatePort(),
            clientAccount.getUsername()));
      }
    }
  }

  private void handleDisconnectAction(String substr) throws IOException {
    ClientAccount offlineClient = null;

    for (int i = 0; i < clients.size(); ++i) {
      ClientAccount ca = clients.get(i);
      if (ca.getPrivatePort() == Integer.parseInt(substr)) {
        offlineClient = clients.get(i);
        break;
      }
    }
    clients.remove(offlineClient);

    if (offlineClient != null) {
      for (ClientAccount ca: clients) {
        PrintWriter out = new PrintWriter(ca.getSocket().getOutputStream(), true);
        out.println(String.format("disconnect=%s - %s", offlineClient.getPrivatePort(), offlineClient.getUsername()));
      }
    }
  }
}

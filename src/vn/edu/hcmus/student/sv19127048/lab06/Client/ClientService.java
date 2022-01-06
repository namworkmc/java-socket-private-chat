package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.swing.AbstractListModel;
import javax.swing.JList;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 8:20 PM<br> Description: JDK16<br>
 */
public class ClientService {

  private Socket socket;
  private HashMap<Integer, ArrayList<String>> messHistory;

  private final JList<String> chatHistory;

  private Integer sendTo;

  private ClientAccount clientAccount;
  private final ArrayList<ClientAccount> onlineAccount;

  private final OnlineUserView onlineUserView;

  public ClientService(JList<String> chatHistory, OnlineUserView onlineUserView, String username) {
    this.chatHistory = chatHistory;
    this.onlineAccount = new ArrayList<>();

    this.onlineUserView = onlineUserView;
    onlineUserView.renderOnlineUserView();

    try {
      socket = new Socket("localhost", 9999);
      clientAccount = new ClientAccount(socket.getLocalPort(), username);

      PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
      printWriter.println(String.format("connect=%d - %s", clientAccount.getPrivatePort(), clientAccount.getUsername()));

//      Scanner sc = new Scanner(System.in);
//      sendTo = Integer.parseInt(sc.nextLine());
      messHistory = new HashMap<>();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setSendTo(String sendTo) {
    for (ClientAccount clientAccount : onlineAccount) {
      if (clientAccount.getUsername().equals(sendTo)) {
        this.sendTo = clientAccount.getPrivatePort();
        break;
      }
    }
    System.out.println(this.sendTo);
  }

  public void connect() {
    getInputConnect();
  }

  private void addMess(Integer privatePort, String mess) {
    if (messHistory.containsKey(privatePort)) {
      messHistory.get(privatePort).add(mess);
    } else {
      ArrayList<String> messArrayList = new ArrayList<>(List.of(mess));
      messHistory.put(privatePort, messArrayList);
    }
  }

  /**
   *  Thread xử lý input từ client khác vào
   */
  private void getInputConnect() {
    new Thread(() -> {
      try {
        Scanner in = new Scanner(socket.getInputStream());
        checkInputAction(in);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }

  /**
   * Lấy lịch sử chat
   * @return mảng {@link String}
   */
  public synchronized String[] getHistory(Integer privatePort) {
    if (messHistory.isEmpty()) {
      return new String[1];
    }
    return messHistory.get(privatePort).toArray(new String[1]);
  }

  // Thread xử lý output
  public String[] sendMessage(String msg) {
    Thread sendMessThread = new Thread(() -> {
      try {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // Gửi message kèm với client muốn gửi đến
        addMess(sendTo, String.format("You-%s", msg));
        out.println(String.format("message=%s - %s: From %s-%s", socket.getLocalPort(), sendTo, clientAccount.getUsername(), msg));
      } catch (IOException e) {
        e.printStackTrace();
      }
    });

    sendMessThread.start();
    try {
      sendMessThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return getHistory(sendTo);
  }

  public void disconnect() {
    try {
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      out.println(String.format("disconnect=%s", socket.getLocalPort()));

      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void checkInputAction(Scanner in) {
    while (in.hasNextLine()) {
      String message = in.nextLine();

      if (message.startsWith("online=")) {
        int startIdx = "online=".length();
        String content = message.substring(startIdx);
        handleConnectAction(content);
      } else if (message.startsWith("disconnect=")) {
        int startIdx = "disconnect=".length();
        String content = message.substring(startIdx);
        handleDisconnectAction(content);
      } else {
        String line = in.nextLine();
        System.out.println(line);
        String[] data = line.split(": ");
        addMess(Integer.valueOf(data[0]), data[1]);

        String[] strings = getHistory(sendTo);
        EventQueue.invokeLater(() -> chatHistory.setModel(new AbstractListModel<>() {

          public int getSize() {
            return strings.length;
          }

          public String getElementAt(int i) {
            return strings[i];
          }
        }));
      }
    }
  }

  private void handleConnectAction(String content) {
    String[] inputData = content.split(" - ");
    int port = Integer.parseInt(inputData[0]);
    String username = inputData[1];
    for (ClientAccount account : onlineAccount) {
      if (account.getPrivatePort() == port) {
        return;
      }
    }

    onlineAccount.add(new ClientAccount(port, username));

    String[] strings = new String[onlineAccount.size()];
    for (int i = 0; i < onlineAccount.size(); i++) {
      strings[i] = onlineAccount.get(i).getUsername();
    }
    onlineUserView.reRenderOnlineList(strings);
  }

  private void handleDisconnectAction(String content) {
    String[] inputData = content.split(" - ");
    int port = Integer.parseInt(inputData[0]);

    for (int i = 0; i < onlineAccount.size(); i++) {
      if (onlineAccount.get(i).getPrivatePort() == port) {
        onlineAccount.remove(i);
        break;
      }
    }

    String[] strings = new String[onlineAccount.size()];
    for (int i = 0; i < onlineAccount.size(); i++) {
      strings[i] = onlineAccount.get(i).getUsername();
    }
    onlineUserView.reRenderOnlineList(strings);
  }
}

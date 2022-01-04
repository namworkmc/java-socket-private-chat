package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 8:20 PM<br> Description: JDK16<br>
 */
public class ClientService {

  private Socket socket;
  private HashMap<Integer, ArrayList<String>> messHistory;

  private String sendTo;

  public ClientService() {
    try {
      socket = new Socket("localhost", 9999);

      Scanner sc = new Scanner(System.in);
      sendTo = sc.nextLine();
      messHistory = new HashMap<>();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void connect() {
    while (true) {
      getInputConnect();
    }
  }

  private void addMess(Integer privatePort, String mess) {
    System.out.println(privatePort);
    System.out.println(mess);
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

        while (in.hasNextLine()) {
          String line = in.nextLine();
          String[] data = line.split(": ");
          addMess(Integer.valueOf(data[0]), data[1]);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();
  }

  /**
   * Lấy lịch sử chat
   * @return mảng {@link String}
   */
  public synchronized String[] getHistory() {
    if (messHistory.isEmpty()) {
      return new String[1];
    }
    return messHistory.get(socket.getLocalPort()).toArray(new String[1]);
  }

  // Thread xử lý output
  public String[] sendMessage(String msg) {
    new Thread(() -> {
      try {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        // Gửi message kèm với client muốn gửi đến
        addMess(socket.getLocalPort(), msg);
        out.println(String.format("%s - %s: %s", socket.getLocalPort(), sendTo, msg));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }).start();

    return getHistory();
  }
}

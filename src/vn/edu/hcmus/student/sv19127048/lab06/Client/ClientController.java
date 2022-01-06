package vn.edu.hcmus.student.sv19127048.lab06.Client;

import javax.swing.JList;

public class ClientController {

  private final ClientService clientService;

  public ClientController(JList<String> chatHistory, OnlineUserView onlineUserView, String username) {
    clientService = new ClientService(chatHistory, onlineUserView, username);
  }

  public void setSendTo(String sendTo) {
    clientService.setSendTo(sendTo);
  }

  public String[] sendMessage(String msg) {
    return clientService.sendMessage(msg);
  }

  public void connect() {
    clientService.connect();
  }

  public void disconnect() {
    clientService.disconnect();
  }
}

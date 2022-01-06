package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.net.Socket;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 5:57 PM<br> Description: JDK16<br>
 */

public class ClientAccount {
  private Integer privatePort;
  private String username;
  private String password;
  private Socket socket;

  public ClientAccount() {
  }

  public ClientAccount(Socket socket) {
    this.socket = socket;
    this.privatePort = socket.getPort();
  }

  public ClientAccount(Integer privatePort, String username) {
    this.privatePort = privatePort;
    this.username = username;
  }

  public Integer getPrivatePort() {
    return privatePort;
  }

  public void setPrivatePort(Integer privatePort) {
    this.privatePort = privatePort;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Socket getSocket() {
    return socket;
  }
}
package vn.edu.hcmus.student.sv19127048.lab06.Client;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 5:57 PM<br> Description: JDK16<br>
 */

public class Client {
  private Integer privatePort;
  private String username;
  private String password;

  public Client() {
  }

  public Client(Integer privatePort, String username, String password) {
    this.privatePort = privatePort;
    this.username = username;
    this.password = password;
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
}
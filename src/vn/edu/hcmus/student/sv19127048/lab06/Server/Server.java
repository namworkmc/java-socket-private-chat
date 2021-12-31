package vn.edu.hcmus.student.sv19127048.lab06.Server;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Server<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 8:04 PM<br> Description: JDK16<br>
 */
public class Server {

  public static void main(String[] args) {
    ServerService serverService = new ServerService();
    serverService.listeningClient();
  }
}

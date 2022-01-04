package vn.edu.hcmus.student.sv19127048.lab06.Client;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 5:57 PM<br> Description: JDK16<br>
 */
public class MainClient {

  public static void main(String[] args) {
    ClientService clientService = new ClientService();
    clientService.connect();
  }
}

package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 12/28/2021 - 5:57 PM<br> Description: JDK16<br>
 */
public class Client1 {

  public static void main(String[] args) {
    ClientService clientService = new ClientService("127.0.0.3");
    clientService.connect();
  }
}

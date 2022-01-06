package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 1/6/2022 - 5:15 PM<br> Description: JDK16<br>
 */
public class ClientDAO {
  public Boolean isAccountExisted(String account) {
    BufferedReader bufferedReader = null;
    try {
      if (Files.notExists(Path.of("account.txt"))) {
        Files.createFile(Path.of("account.txt"));
      }

      bufferedReader = new BufferedReader(new FileReader("account.txt"));
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        if (account.equals(line)) {
          return true;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    return false;
  }

  public void createAccount(String account) {
    FileWriter fileWriter = null;
    try {
      fileWriter = new FileWriter("account.txt", true);
      fileWriter.write(account);
      fileWriter.write("\n");
      fileWriter.flush();
      fileWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (fileWriter != null) {
        try {
          fileWriter.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}

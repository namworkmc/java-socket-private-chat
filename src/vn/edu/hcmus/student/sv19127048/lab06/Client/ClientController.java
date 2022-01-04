package vn.edu.hcmus.student.sv19127048.lab06.Client;

public class ClientController {
  private Client client;
  private final ClientService clientService;

  public ClientController() {
    clientService = new ClientService();
  }

  public String[] getHistory() {
    return clientService.getHistory();
  }

    public String[] sendMessage(String msg) {
    return clientService.sendMessage(msg);
  }
}

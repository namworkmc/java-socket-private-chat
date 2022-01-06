package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.awt.EventQueue;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class ClientView extends JFrame {

  /**
   * Creates new form ChatGUI
   */
  public ClientView(String username) {
    this.onlineUserView = new OnlineUserView();
    initComponents();
    clientController = new ClientController(chatHistoryList, onlineUserView, username);
    clientController.connect();
  }

  private void initComponents() {
    JButton connectButton = onlineUserView.getConnectButton();
    JList<String> onlineList = onlineUserView.getOnlineList();
    connectButton.addActionListener(e -> {
      String selectedValue = onlineList.getSelectedValue();
      if (!selectedValue.isEmpty()) {
        clientController.setSendTo(selectedValue);
      }
    });

    // Variables declaration - do not modify
    JPanel chatBoxPanel = new JPanel();
    JTextField inputTextField = new JTextField();
    JButton submitBtn = new JButton();
    JScrollPane sp = new JScrollPane();

    chatHistoryList = new JList<>();

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        clientController.disconnect();
        System.exit(0);
      }
    });

    setPreferredSize(new Dimension(500, 500));

    inputTextField.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
    inputTextField.setHorizontalAlignment(JTextField.CENTER);

    submitBtn.setText("SEND");
    submitBtn.addActionListener(event -> {
      String msg = inputTextField.getText();
      String[] chatHistory = clientController.sendMessage(msg);
      renderList(chatHistory);
      inputTextField.setText("");
    });

    chatHistoryList.setFont(new Font("Segoe UI", 0, 18)); // NOI18N
    String[] strings = {""};
    renderList(strings);
    chatHistoryList.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    sp.setViewportView(chatHistoryList);

    GroupLayout chatBoxPanelLayout = new GroupLayout(chatBoxPanel);
    chatBoxPanel.setLayout(chatBoxPanelLayout);
    chatBoxPanelLayout.setHorizontalGroup(
        chatBoxPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(chatBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatBoxPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(sp)
                    .addGroup(chatBoxPanelLayout.createSequentialGroup()
                        .addComponent(inputTextField, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(submitBtn, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)))
                .addContainerGap())
    );
    chatBoxPanelLayout.setVerticalGroup(
        chatBoxPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, chatBoxPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(chatBoxPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputTextField, GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(submitBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
    );

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chatBoxPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chatBoxPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
    );

    pack();
  }

  private void renderList(String[] strings) {
    chatHistoryList.setModel(new AbstractListModel<>() {

      public int getSize() {
        return strings.length;
      }

      public String getElementAt(int i) {
        return strings[i];
      }
    });
  }

  // </editor-fold>
  public void renderClientView() {
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(ClientView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    EventQueue.invokeLater(() -> setVisible(true));
  }

  // Variables declaration - do not modify
  private JList<String> chatHistoryList;
  private final OnlineUserView onlineUserView;


  private final ClientController clientController;
  // End of variables declaration
}

package vn.edu.hcmus.student.sv19127048.lab06.Client;

import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

/**
 * vn.edu.hcmus.student.sv19127048.lab06.Client<br> Created by 19127048 - Nguyen Duc Nam<br> Date
 * 1/5/2022 - 11:12 PM<br> Description: JDK16<br>
 */
public class OnlineUserView extends JFrame {

  /**
   * Creates new form OnlineFrame
   */
  public OnlineUserView() {
    initComponents();
  }

  private void initComponents() {

    OnlinePanel = new JPanel();
    sp = new JScrollPane();
    onlineList = new JList<>();
    connectButton = new JButton();
    onlineLabel = new JLabel();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    onlineList.setModel(new AbstractListModel<>() {
      final String[] strings = {""};

      public int getSize() {
        return strings.length;
      }

      public String getElementAt(int i) {
        return strings[i];
      }
    });
    sp.setViewportView(onlineList);

    connectButton.setText("CONNECT");

    GroupLayout OnlinePanelLayout = new GroupLayout(OnlinePanel);
    OnlinePanel.setLayout(OnlinePanelLayout);
    OnlinePanelLayout.setHorizontalGroup(
        OnlinePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(OnlinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp)
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, OnlinePanelLayout.createSequentialGroup()
                .addContainerGap(146, Short.MAX_VALUE)
                .addComponent(connectButton)
                .addGap(143, 143, 143))
    );
    OnlinePanelLayout.setVerticalGroup(
        OnlinePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(OnlinePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sp, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(connectButton)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    onlineLabel.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
    onlineLabel.setHorizontalAlignment(SwingConstants.CENTER);
    onlineLabel.setText("ONLINE USERS");

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(OnlinePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(onlineLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(onlineLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(OnlinePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
    );

    pack();
  }// </editor-fold>

  private void connectButtonActionPerformed(ActionEvent evt) {
    // TODO add your handling code here:
  }

  public void renderOnlineUserView() {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
    /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
     */
    try {
      for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException | IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(OnlineUserView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(() -> setVisible(true));
  }

  public void reRenderOnlineList(String[] strings) {
    onlineList.setModel(new AbstractListModel<>() {

      public int getSize() {
        return strings.length;
      }

      public String getElementAt(int i) {
        return strings[i];
      }
    });
  }

  public JButton getConnectButton() {
    return connectButton;
  }

  public JList<String> getOnlineList() {
    return onlineList;
  }

  // Variables declaration - do not modify
  private JPanel OnlinePanel;
  private JButton connectButton;
  private JLabel onlineLabel;

  private JList<String> onlineList;
  private JScrollPane sp;
  // End of variables declaration
}


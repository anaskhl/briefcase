package org.opendatakit.briefcase.delivery.cli.launchgui;

import static java.awt.event.KeyEvent.VK_ESCAPE;
import static javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT;
import static javax.swing.KeyStroke.getKeyStroke;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import org.opendatakit.briefcase.delivery.ui.reused.events.WindowAdapterBuilder;

public class LegacyPrefsDecisionDialogForm extends JDialog {
  private JPanel contentPane;
  private JPanel actionPane;
  private JPanel mainPane;
  private JButton importButton;
  private JButton ignoreButton;
  private JButton askAgainButton;
  private JTextPane dialogText;

  public LegacyPrefsDecisionDialogForm(Runnable onImport, Runnable onIgnore, Runnable onAskAgain) {
    $$$setupUI$$$();

    importButton.addActionListener(__ -> {
      onImport.run();
      exit();
    });
    ignoreButton.addActionListener(__ -> {
      onIgnore.run();
      exit();
    });
    askAgainButton.addActionListener(__ -> {
      onAskAgain.run();
      exit();
    });

    setContentPane(contentPane);
    setModal(true);
    setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    addWindowListener(new WindowAdapterBuilder().onClosing(e -> exit()).build());
    contentPane.registerKeyboardAction(e -> exit(), getKeyStroke(VK_ESCAPE, 0), WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
  }

  private void exit() {
    dispose();
  }

  public void open() {
    setSize(400, 300);
    setLocationRelativeTo(null);
    pack();
    setVisible(true);
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    contentPane = new JPanel();
    contentPane.setLayout(new GridBagLayout());
    mainPane = new JPanel();
    mainPane.setLayout(new CardLayout(0, 0));
    GridBagConstraints gbc;
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.weighty = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    contentPane.add(mainPane, gbc);
    dialogText = new JTextPane();
    dialogText.setContentType("text/html");
    dialogText.setText("<html>\n  <head>\n    \n  </head>\n  <body>\n    <h1>\n      Legacy preferences from Briefcase v1.x detected!\n    </h1>\n    <p>\n      Briefcase can read preferences from older versions and import them into \n      the database.\n    </p>\n    <p>\n      What do you want to do?\n    </p>\n  </body>\n</html>\n");
    mainPane.add(dialogText, "Card1");
    final JPanel spacer1 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.VERTICAL;
    contentPane.add(spacer1, gbc);
    actionPane = new JPanel();
    actionPane.setLayout(new GridBagLayout());
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.BOTH;
    contentPane.add(actionPane, gbc);
    importButton = new JButton();
    importButton.setText("Import");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 0;
    actionPane.add(importButton, gbc);
    ignoreButton = new JButton();
    ignoreButton.setText("Ignore");
    gbc = new GridBagConstraints();
    gbc.gridx = 3;
    gbc.gridy = 0;
    actionPane.add(ignoreButton, gbc);
    askAgainButton = new JButton();
    askAgainButton.setText("Ask again next time");
    gbc = new GridBagConstraints();
    gbc.gridx = 5;
    gbc.gridy = 0;
    actionPane.add(askAgainButton, gbc);
    final JPanel spacer2 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    actionPane.add(spacer2, gbc);
    final JPanel spacer3 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 0;
    gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    actionPane.add(spacer3, gbc);
    final JPanel spacer4 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 4;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    actionPane.add(spacer4, gbc);
    final JPanel spacer5 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    actionPane.add(spacer5, gbc);
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() { return contentPane; }

}

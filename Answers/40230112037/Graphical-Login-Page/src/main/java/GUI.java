import com.sun.jdi.request.DuplicateRequestException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class GUI extends JFrame {
    BoxLayout layout = new BoxLayout(getContentPane(), BoxLayout.Y_AXIS);
    private final LoginPanel loginPanel = new LoginPanel();
    private final MainPanel mainPanel = new MainPanel();
    private final RegisterPanel registerPanel = new RegisterPanel();
    private JPanel currentPanel = mainPanel;
    private final UserHandler userHandler;

    GUI() {
        try {
            userHandler = new UserHandler("users.txt");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "couldn't read file!");
            throw new RuntimeException(e);
        }
        setLayout(layout);
        add(mainPanel);
        loginPanel.setVisible(false);
        add(loginPanel);
        registerPanel.setVisible(false);
        add(registerPanel);
        pack();
        setLocationRelativeTo(null);
        mainPanel.loginButton.addActionListener(actionEvent -> changePanel(loginPanel));
        mainPanel.registerButton.addActionListener(actionEvent -> changePanel(registerPanel));
        loginPanel.mainMenuButton.addActionListener(actionEvent -> changePanel(mainPanel));
        registerPanel.mainMenuButton.addActionListener(actionEvent -> changePanel(mainPanel));
        registerPanel.registerButton.addActionListener(actionEvent -> {
            try {
                userHandler.addUser(new User(registerPanel.usernameField.getText(), new Password(Arrays.toString(registerPanel.passwordField.getPassword())), registerPanel.emailField.getText()));
                JOptionPane.showMessageDialog(null, "Register Successful!");
            } catch (IOException e) {
                // Todo
            } catch (DuplicateRequestException e){
                JOptionPane.showMessageDialog(null, "Register Failed!(Username is taken)");

            }
        });
        loginPanel.loginButton.addActionListener(actionEvent -> {
            User loginuser = userHandler.getUser(loginPanel.usernameField.getText());
            JOptionPane.showMessageDialog(null, ((loginuser == null || !loginuser.password.verifyPassword(Arrays.toString(loginPanel.passwordField.getPassword()))) ? "login failed" : "login Successful"));
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    void changePanel(JPanel newPanel) {
        currentPanel.setVisible(false);
        newPanel.setVisible(true);
        fit();
        currentPanel = newPanel;
    }

    void fit() {
        setMinimumSize(new Dimension(0, 0));
        pack();
        setMinimumSize(getSize());
    }
}

class MainPanel extends JPanel {
    FlowLayout layout = new FlowLayout();
    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    MainPanel() {
        add(loginButton);
        add(registerButton);
        setLayout(layout);
    }
}


class LoginPanel extends JPanel {
    boolean isUsernameField = false;
    boolean isPasswordField = false;

    LoginPanel() {
        panel1.add(new JLabel("Username: "));
        panel1.add(usernameField);
        panel1.setLayout(new FlowLayout());
        panel3.add(new JLabel("Password:  "));
        panel3.add(passwordField);
        panel3.setLayout(new FlowLayout());
        panel4.add(loginButton);
        loginButton.setEnabled(false);
        panel4.add(mainMenuButton);
        panel4.setLayout(new FlowLayout());
        //setMaximumSize(new Dimension(1000, 1006));
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                usernameFieldChange();
            }

            public void removeUpdate(DocumentEvent e) {
                usernameFieldChange();
            }

            public void insertUpdate(DocumentEvent e) {
                usernameFieldChange();
            }
        });

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                passwordFieldChange();
            }

            public void removeUpdate(DocumentEvent e) {
                passwordFieldChange();
            }

            public void insertUpdate(DocumentEvent e) {
                passwordFieldChange();
            }
        });
        setLayout(panelsLayout);
        add(panel1);
        add(panel3);
        add(panel4);
    }

    JButton loginButton = new JButton("Login");
    JButton mainMenuButton = new JButton("Back");
    JTextField usernameField = new JTextField(20);

    void usernameFieldChange() {
        isUsernameField = User.verifyUserName(usernameField.getText());
        fieldsUpdate();
    }

    JPasswordField passwordField = new JPasswordField(20);

    void passwordFieldChange() {
        isPasswordField = (Password.passwordLevel(new String(passwordField.getPassword())) > 3);
        fieldsUpdate();
    }

    JPanel panel1 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    GridLayout panelsLayout = new GridLayout(3, 1);

    void fieldsUpdate() {
        loginButton.setEnabled(isUsernameField && isPasswordField);
    }
}

class RegisterPanel extends JPanel {
    JButton registerButton = new JButton("Register");
    JButton mainMenuButton = new JButton("Back");
    JTextField usernameField = new JTextField(20);
    JTextField emailField = new JTextField(22);
    JPasswordField passwordField = new JPasswordField(20);
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();
    JPanel panel3 = new JPanel();
    JPanel panel4 = new JPanel();

    GridLayout panelsLayout = new GridLayout(4, 1);
    boolean isUsernameField = false;
    boolean isPasswordField = false;
    boolean isEmailField = false;

    RegisterPanel() {

        panel1.add(new JLabel("Username: "));
        panel1.add(usernameField);
        panel1.setLayout(new FlowLayout());
        panel2.add(new JLabel("Email:  "));
        panel2.add(emailField);
        panel2.setLayout(new FlowLayout());
        panel3.add(new JLabel("Password:  "));
        panel3.add(passwordField);
        panel3.setLayout(new FlowLayout());
        panel4.add(registerButton);
        registerButton.setEnabled(false);
        panel4.add(mainMenuButton);
        panel4.setLayout(new FlowLayout());
        //setMaximumSize(new Dimension(1000, 1006));
        setLayout(panelsLayout);
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);
        usernameField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                usernameFieldChange();
            }

            public void removeUpdate(DocumentEvent e) {
                usernameFieldChange();
            }

            public void insertUpdate(DocumentEvent e) {
                usernameFieldChange();
            }
        });

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                passwordFieldChange();
            }

            public void removeUpdate(DocumentEvent e) {
                passwordFieldChange();
            }

            public void insertUpdate(DocumentEvent e) {
                passwordFieldChange();
            }
        });
        emailField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                emailFieldChange();
            }

            public void removeUpdate(DocumentEvent e) {
                emailFieldChange();
            }

            public void insertUpdate(DocumentEvent e) {
                emailFieldChange();
            }
        });
    }

    void usernameFieldChange() {
        isUsernameField = User.verifyUserName(usernameField.getText());
        fieldsUpdate();
    }

    void passwordFieldChange() {
        isPasswordField = (Password.passwordLevel(new String(passwordField.getPassword())) > 3);
        fieldsUpdate();
    }

    void emailFieldChange() {
        isEmailField = (EmailValidator.validate(emailField.getText()));
        fieldsUpdate();
    }

    void fieldsUpdate() {
        registerButton.setEnabled(isUsernameField && isPasswordField && isEmailField);
    }
}
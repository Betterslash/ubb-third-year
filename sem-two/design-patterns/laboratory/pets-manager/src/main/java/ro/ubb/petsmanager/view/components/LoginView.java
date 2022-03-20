package ro.ubb.petsmanager.view.components;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
public class LoginView {
    private JPasswordField passwordField1;
    private JPanel panel1;
    private JTextField textField1;
    private JButton loginButton;

    public void refreshFields(){
        textField1.setText("");
        passwordField1.setText("");
    }
}

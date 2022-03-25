package ro.ubb.petsmanager.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ro.ubb.petsmanager.dto.LoginDto;
import ro.ubb.petsmanager.view.components.AnimalsMainView;
import ro.ubb.petsmanager.view.components.LoginView;
import ro.ubb.petsmanager.view.core.UiService;

import javax.swing.*;

@Component
@RequiredArgsConstructor
public class MainFrame extends JFrame {

    private String currentUser;
    private final UiService uiService;

    public void initLoginForm(){
        var loginForm = new LoginView();
        loginForm.getLoginButton()
                .addActionListener(e -> {
                    var username = loginForm.getTextField1().getText();
                    var password = loginForm.getPasswordField1().getText();
                    var response = uiService.login(LoginDto
                            .builder()
                                    .username(username)
                                    .password(password)
                            .build());
                    if((Boolean) response.getResponse()){
                        this.currentUser = username;
                        this.initAnimalsView();
                        this.rootPane.updateUI();
                    }else{
                        loginForm.refreshFields();
                        this.rootPane.updateUI();
                    }
                });
        this.rootPane.setContentPane(loginForm.getPanel1());

    }

    public void initAnimalsView() {
        var animalsView = new AnimalsMainView(uiService);
        animalsView.setCurrentUser(currentUser);
        animalsView.initializeAvailableAnimalsList();
        animalsView.initializeComboBox();
        animalsView.initializeAdoptButton();
        animalsView.initializeAddAnimalButton();
        this.rootPane.setContentPane(animalsView.getTabbedPane1());
    }

    public void initUi(){
        var quitButton = new JButton("Quit");
        quitButton.addActionListener((event) -> System.exit(0));

        setTitle("Pets Manager");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.initLoginForm();
    }
}

package ro.ubb.petsmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@PropertySource("classpath:application.properties")
@SpringBootApplication
public class PetsManagerApplication extends JFrame {

    public PetsManagerApplication() {

        initUI();
    }

    private void initUI() {

        var quitButton = new JButton("Quit");

        quitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        createLayout(quitButton);

        setTitle("Quit button");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void createLayout(JComponent... arg) {

        var pane = getContentPane();
        var gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0])
        );
    }

    public static void main(String[] args) {

        var ctx = new SpringApplicationBuilder(PetsManagerApplication.class)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> {

            var ex = ctx.getBean(PetsManagerApplication.class);
            ex.setVisible(true);
        });
    }

}

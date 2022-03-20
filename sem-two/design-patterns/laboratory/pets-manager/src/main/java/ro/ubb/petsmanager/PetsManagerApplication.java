package ro.ubb.petsmanager;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import ro.ubb.petsmanager.view.MainFrame;

import java.awt.*;

@PropertySource("classpath:application.properties")
@SpringBootApplication
public class PetsManagerApplication{

    public static void main(String[] args) {

        var ctx = new SpringApplicationBuilder(PetsManagerApplication.class)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> {
            var ex = ctx.getBean(MainFrame.class);
            ex.initUi();
            ex.setVisible(true);
        });
    }

}

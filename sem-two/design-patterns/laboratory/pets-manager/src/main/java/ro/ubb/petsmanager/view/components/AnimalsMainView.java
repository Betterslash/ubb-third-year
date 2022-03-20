package ro.ubb.petsmanager.view.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import ro.ubb.petsmanager.model.Animal;
import ro.ubb.petsmanager.model.Dog;
import ro.ubb.petsmanager.view.core.UiService;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
@Component
public class AnimalsMainView {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JList<String> list1;
    private JList<String> list2;
    private JButton adoptButton;
    private JTextField textField2;
    private JTextField textField3;
    private JButton addAnimalButton;
    private JComboBox<String> comboBox1;
    private JList<String> list3;
    private UiService uiService;

    public AnimalsMainView(UiService uiService) {
        this.uiService = uiService;
    }

    public void addAnimal(){}

    public void initializeAvailableAnimalsList() {
        try {
            var connection = uiService.createConnection("dogs");
            connection.setRequestMethod(HttpMethod.GET.name());
            var reponse = uiService
                    .readResponse(connection)
                    .responseToList(Dog[].class);
            populateAnimalsList(reponse);
        } catch (IOException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }

    public void initializeComboBox(){
        var comboBoxModel = new DefaultComboBoxModel<String>();
        comboBoxModel.addElement("Dog");
        comboBoxModel.addElement("Cat");
        comboBox1.setModel(comboBoxModel);
    }

    public void initializeAdoptButton(){
        this.adoptButton.addActionListener(e -> {
            var toBeAdoptedListModel = this.list3.getModel();
            var newModel = new DefaultListModel<String>();
            for (int i = 0; i < toBeAdoptedListModel.getSize(); i++) {
                newModel.addElement(toBeAdoptedListModel.getElementAt(i));
            }
            newModel.addElement(this.list1.getSelectedValue());
            this.list3.setModel(newModel);
        });
        this.list3.updateUI();
    }

    private void populateAnimalsList(List<? extends Animal> reponse) {
        var animalsListModel = new DefaultListModel<String>();
        reponse.forEach(e -> animalsListModel.addElement(toListFormat(e)));
        list1.setModel(animalsListModel);
    }

    private String toListFormat(Animal e) {
        return "Dog" + " : Name -> " + e.getName() + " Race -> " + e.getRace();
    }
}

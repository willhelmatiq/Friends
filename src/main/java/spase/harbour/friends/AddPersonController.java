package spase.harbour.friends;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import spase.harbour.friends.model.Person;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

public class AddPersonController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNubmer;
    @FXML
    private TextField address;
    @FXML
    private TextField birth;
    private int id;

    public void setIdForNewPerson(int lastId) {
        this.id = lastId + 1;
    }

    private List<Person> personList;


    private Controller controller;

    public void setController (Controller controller) {
        this.controller = controller;
    }


    public void setPersonList(List<Person> personList) {
        this.personList = personList;

    }
    public void addPerson(ActionEvent actionEvent) {
        Stage stage = (Stage) anchorPane.getScene().getWindow();

        String nameP = name.getText();
        String surnameP = surname.getText();
        String emailP = email.getText();
        String phoneNumberP = phoneNubmer.getText();
        String addressP = address.getText();
        try {
            LocalDate birthDateP = LocalDate.parse(birth.getText());
            stage.close();
            personList.add(new Person(id, nameP, surnameP, emailP, phoneNumberP, addressP, birthDateP));
            controller.reloadTableView();
        } catch (DateTimeParseException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add person");
            alert.setHeaderText("Wrong date format");
            alert.setContentText("Please use next format: yyyy-MM-dd");
            alert.showAndWait().get();
        }
    }

}

package spase.harbour.friends;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import spase.harbour.friends.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static spase.harbour.friends.util.FileUtils.getPersonsFromFile;
import static spase.harbour.friends.util.FileUtils.writeIntoJson;
import static spase.harbour.friends.util.FilterUtils.filterPersonList;

public class Controller {

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Person, String> id;
    @FXML
    private TableColumn<Person, String> name;
    @FXML
    private TableColumn<Person, String> surname;
    @FXML
    private TableColumn<Person, String> email;
    @FXML
    private TableColumn<Person, String> phoneNumber;
    @FXML
    private TableColumn<Person, String> address;
    @FXML
    private TableColumn<Person, String> birthday;

    @FXML
    private TextField textField;

    private List<Person> personList;

    private boolean isFileUploaded = false;

    public Controller() {
    }

    private void setCellValue() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
    }

    @FXML
    public void loadJSONFile(ActionEvent actionEvent) throws IOException {
        setCellValue();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src\\main\\resources\\spase\\harbour\\friends"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        personList = getPersonsFromFile(file);
        tableView.setItems(FXCollections.observableList(personList));
        isFileUploaded = true;
    }

    @FXML
    public void addPerson(ActionEvent event) throws IOException {
        if(isFileUploaded) {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add-friend-form.fxml"));
            Parent root = loader.load();
            AddPersonController addPersonController = loader.getController();
            addPersonController.setIdForNewPerson(personList.get(personList.size()-1).getId());
            addPersonController.setPersonList(personList);
            addPersonController.setController(this);
            Scene scene = new Scene(root);
            stage.setTitle("FriendList");
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Add person");
            alert.setHeaderText("Warning");
            alert.setContentText("You need to upload .json file first");
            alert.showAndWait().get();
        }
    }

    @FXML
    public void searchPerson(ActionEvent event) {
        tableView.setItems(FXCollections.observableList(filterPersonList(personList, textField.getText())));
    }

    @FXML
    public void remove(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Deleting data");
        alert.setContentText("Do you really want to delete this friend?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            Person personToDelete = (Person) tableView.getSelectionModel().getSelectedItem();
            personList.remove(personToDelete);
            tableView.setItems(FXCollections.observableList(filterPersonList(personList, textField.getText())));
        }
    }

    @FXML
    private void saveData(ActionEvent actionEvent) throws IOException {
        writeIntoJson(personList, "target\\outPutData.json");
    }

    public void reloadTableView() {
        tableView.setItems(FXCollections.observableList(personList));
    }


}
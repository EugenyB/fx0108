package fxapp;

import fxapp.db.Student;
import fxapp.logic.Logic;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class Controller {
    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, Integer> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> lastNameColumn;
    @FXML private TableColumn<Student, Integer> ageColumn;

    @FXML private TextField nameField;
    @FXML private TextField lastNameField;
    @FXML private TextField ageField;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
    }

    public void showStudents() {
        Logic logic = Logic.getInstance();
        List<Student> students = logic.findAllStudents();
        table.setItems(FXCollections.observableArrayList(students));
    }

    public void addStudent() {
        String name = nameField.getText();
        String lastName = lastNameField.getText();
        try {
            int age = Integer.parseInt(ageField.getText());
            Logic.getInstance().addStudent(name, lastName, age);
            clearFields(nameField, lastNameField, ageField);
            showStudents();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,  ageField.getText() + " is not a number!" );
            alert.show();
        }
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public void deleteStudent() {
        Student student = table.getSelectionModel().getSelectedItem();
        if (student == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No student selected!");
            alert.show();
        } else {
            Logic.getInstance().deleteStudent(student);
            showStudents();
        }
    }
}

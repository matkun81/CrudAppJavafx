package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Move;
import sample.dao.DatabaseHandler;

public class EditController extends MainController implements Move {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editFormButton;

    @FXML
    private Button cancelFormButton;

    @FXML
    private TextField editText;

    @FXML
    private TextField editAge;

    @FXML
    private TextField editBirthday;

    @FXML
    void initialize() {
        DatabaseHandler databaseHandler = new DatabaseHandler();
    editText.setText(user.getName());
    editAge.setText(String.valueOf(user.getAge()));
    editBirthday.setText(user.getBirthday());

    editFormButton.setOnAction(event -> {
        user.setName(editText.getText());
        user.setAge(Integer.parseInt(editAge.getText()));
        user.setBirthday(editBirthday.getText());
        try {
            databaseHandler.upDateUser(user);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        editFormButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/main.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    });
    cancelFormButton.setOnAction(event -> {
        cancelFormButton.getScene().getWindow().hide();

        movingToWindow("/view/main.fxml");
    });



    }

    @Override
    public void movingToWindow(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}


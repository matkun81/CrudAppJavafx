package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.Model.User;
import sample.dao.DatabaseHandler;

public class AddController extends Main {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addUser;

    @FXML
    private Button cancelAddUser;

    @FXML
    private TextField addTextName;

    @FXML
    private TextField addTextBirthday;

    @FXML
    private TextField addTextAge;

    Alert alert = new Alert(Alert.AlertType.ERROR);
    @FXML
    void initialize() {
        if (addTextBirthday.getText()==null|addTextAge.getText()==null|addTextName.getText()==null) {

            alert.setTitle("!!!");
            alert.setHeaderText("Предупржедение");
            alert.setContentText("Выберите запись которую хотите удалить");
            alert.show();
        }
        DatabaseHandler dbHandler = new DatabaseHandler();
        addUser.setOnAction(event -> {
            String  name = addTextName.getText();
            Integer age = Integer.parseInt(addTextAge.getText());
            String birthDay =addTextBirthday.getText();

            User user = new User(name,age,birthDay);

            try {
                dbHandler.addUser(user);
                System.out.println("Запись успешно добавлена");
                alert.setContentText("Успешно!");
                addUser.getScene().getWindow().hide();
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

            } catch (SQLException e) {
                e.printStackTrace();


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cancelAddUser.setOnAction(event -> {
            cancelAddUser.getScene().getWindow().hide();
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
    }
}

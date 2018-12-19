package sample.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.stage.Stage;
import sample.Model.User;
import sample.dao.DatabaseHandler;

public class MainController {
    public static User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TreeTableView<User> tableUsers = new TreeTableView<>();

    @FXML
    private TreeTableColumn<User, String> nameColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<User, Integer> ageColumn = new TreeTableColumn<>();

    @FXML
    private TreeTableColumn<User, String> BirthDayColumn = new TreeTableColumn<>();

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    private Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    void NAME(ActionEvent event) {

    }

    @FXML
    void initialize() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        nameColumn.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("name"));
        ageColumn.setCellValueFactory(new TreeItemPropertyValueFactory<User, Integer>("age"));
        BirthDayColumn.setCellValueFactory(new TreeItemPropertyValueFactory<User, String>("Birthday"));
        tableUsers.getColumns().addAll(nameColumn, ageColumn, BirthDayColumn);
        List<User> a = databaseHandler.getAllUser();
        TreeItem <User> item = new TreeItem<>(new User());
        for (User user : a) {
         item.getChildren().add(new TreeItem<>(user));
        }
        tableUsers.setRoot(item);
        tableUsers.setShowRoot(false);






        tableUsers.setOnMouseClicked(event -> {
            if(event.getClickCount() == 2) {
                User user = tableUsers.getSelectionModel().getSelectedItem().getValue();
                String birthday = user.getBirthday();
                birthday = birthday.substring(5);
                String dataNow = String.valueOf(LocalDate.now());
               dataNow = dataNow.substring(5);

                if (birthday.equals(dataNow)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("ДЕНЬ РОЖДЕНИЕ");
                    alert.setHeaderText("ДЕНЬ РОЖДЕНИЕ!!!");
                    alert.setContentText("CЕГОДНЯ У " + user.getName() + " ДЕНЬ РОЖДЕНИЕ!");
                    alert.show();
                }
            }
        });




            addButton.setOnAction(event -> {
                addButton.getScene().getWindow().hide();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/addForm.fxml"));
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


            editButton.setOnAction((event -> {
                if (tableUsers.getSelectionModel().getSelectedItem()== null) {
                    alert.setTitle("!!!");
                    alert.setHeaderText("Предупржедение");
                    alert.setContentText("Выберите запись которую хотите изменить");
                    alert.show();
                }
               user = tableUsers.getSelectionModel().getSelectedItem().getValue();


                editButton.getScene().getWindow().hide();


                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/view/editForm.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }));


        deleteButton.setOnAction(event -> {
            if (tableUsers.getSelectionModel().getSelectedItem()== null) {
                alert.setTitle("!!!");
                alert.setHeaderText("Предупржедение");
                alert.setContentText("Выберите запись которую хотите удалить");
                alert.show();
            }
           User usr = tableUsers.getSelectionModel().getSelectedItem().getValue();
            try {
                databaseHandler.deleteUser(usr.getId());
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            deleteButton.getScene().getWindow().hide();
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

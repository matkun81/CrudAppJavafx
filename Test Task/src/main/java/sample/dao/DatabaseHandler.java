package sample.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.User;
import sample.config.Configs;
import sample.controller.Const;

import java.sql.*;
import java.util.List;

public class DatabaseHandler extends Configs {
    User user;
    private ObservableList<User> usersData = FXCollections.observableArrayList();
    Connection dbconnection;

    public Connection getDbconnection()throws ClassNotFoundException,SQLException {
        String connection = "jdbc:mysql://localhost:3306/matkun"+
                "?verifyServerCertificate=false"+
                "&useSSL=false"+
                "&requireSSL=false"+
                "&useLegacyDatetimeCode=false"+
                "&amp"+
                "&serverTimezone=UTC";
        dbconnection = DriverManager.getConnection(connection,dbUser,dbPass);
        return dbconnection;
    }




    public void addUser(User user) throws SQLException, ClassNotFoundException {
        String insert = "INSERT INTO" + " " + Const.USER_TABLE + "("+ Const.USER_NAME + "," + Const.USER_AGE + ","+
                Const.USER_BIRTHDAY + ")" + "VALUES(?,?,?)";


            PreparedStatement prst = getDbconnection().prepareStatement(insert);
            prst.setString(1,user.getName());
            prst.setInt(2,user.getAge());
            prst.setString(3,user.getBirthday());
            prst.executeUpdate();


    }
    public void deleteUser(Integer id) throws SQLException, ClassNotFoundException {
        String SQL = "DELETE FROM " + Const.USER_TABLE +  " WHERE " + Const.USER_ID  + "= ? ";

// get a connection and then in your try catch for executing your delete...\
        PreparedStatement p = getDbconnection().prepareStatement(SQL);
        p.setString(1, String.valueOf(id));
        p.executeUpdate();
    }
    public void upDateUser (User user) throws SQLException, ClassNotFoundException {
        String queru = "UPDATE " +  Const.USER_TABLE +  " SET name = ?, age = ?, birthday = ? WHERE idusers = ?";

        PreparedStatement ps = getDbconnection().prepareStatement(queru);
        ps.setString(4, String.valueOf(user.getId()));
        ps.setString(1,user.getName());
        ps.setString(2, String.valueOf(user.getAge()));
        ps.setString(3,user.getBirthday());
        ps.executeUpdate();
    }

    public List<User> getAllUser(){
        String query = "select * from " + Const.USER_TABLE;
        try {
            Statement statement = getDbconnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setAge(resultSet.getInt(3));
                user.setBirthday(resultSet.getString(4));
                usersData.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usersData;
    }
}

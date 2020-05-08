package Controlleres;

import Models.UserModel;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UsersPageController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField nationalID;
    @FXML
    private TextField mobileNumber;
    @FXML
    private ComboBox<String> userType;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private TableColumn<?, ?> userId_col;
    @FXML
    private TableColumn<?, ?> name_col;
    @FXML
    private TableColumn<?, ?> nationalID_col;
    @FXML
    private TableColumn<?, ?> mobileNumber_col;
    @FXML
    private TableColumn<?, ?> userType_col;
    @FXML
    private TableColumn<?, ?> userName_col;
    @FXML
    private TableView<UserModel> userTable;

    ObservableList<UserModel> userlist = FXCollections.observableArrayList();
    String[] usertypeItem = {"معلم", "إداري", "مشرف"};
    String userid = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboBoxFill.fillComboBox(userType, usertypeItem);
        refreshUsertable();

        userTable.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<UserModel> list = FXCollections.observableArrayList();
                list = userTable.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    name.setText(list.get(0).getName());
                    nationalID.setText(list.get(0).getNationalid());
                    mobileNumber.setText(list.get(0).getMobilenumber());
                    userType.setValue(list.get(0).getUsertype());
                    userName.setText(list.get(0).getUsername());
                    password.setText(list.get(0).getPassword());
                    userid = list.get(0).getUserid();
                }
            }
        });
    }
    /*`USERID`,`NATIONALID`,`MOBILENUMBER`,`USERTYPE`,`USERNAME`,`PASSWORD`*/

    public void clearField() {
        name.setText("");
        nationalID.setText("");
        mobileNumber.setText("");
        userType.setValue("");
        userName.setText("");
        password.setText("");
        userid = null;
    }

    @FXML
    private void saveData(ActionEvent event) {
        String tabelNme = "users";
        String fieldName = "`NAME`,`NATIONALID`,`MOBILENUMBER`,`USERTYPE`,`USERNAME`,`PASSWORD`";
        String[] data = {name.getText(), nationalID.getText(), mobileNumber.getText(), userType.getValue(), userName.getText(), password.getText()};
        String valuenumbers = "?,?,?,?,?,?";
        boolean namestatus = Validation.textFieldNotEmpty(name, "ادخل الاسم");
        boolean nationalIDstatus = Validation.textFieldNotEmpty(nationalID, "ادخل رقم الهوية");
        boolean nationalIDnumberOnly = Validation.textFieldNumberOnly(nationalID, "ادخل ارقام فقط");
        boolean mobileNumberstatus = Validation.textFieldNotEmpty(mobileNumber, "ادخل رقم الجوال");
        boolean mobileNumberOnly = Validation.textFieldNumberOnly(mobileNumber, "ادخل ارقام فقط");
        boolean userTypestatus = Validation.comboBoxNotEmpty(userType, "اختر نوع المستخدم");
        boolean userNamestatus = Validation.textFieldNotEmpty(userName, "ادخل اسم المستخدم");
        boolean passwordstatus = Validation.textFieldNotEmpty(password, "ادخل كلمة المرور");

        if (namestatus && nationalIDstatus && mobileNumberstatus && userTypestatus && userNamestatus
                && passwordstatus && nationalIDnumberOnly && mobileNumberOnly) {
            try {
                DatabaseAccess.insert(tabelNme, fieldName, valuenumbers, data);
                refreshUsertable();
                clearField();
            } catch (IOException ex) {
                Logger.getLogger(UsersPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        String tabelNme = "users";
        String fieldName = "`NAME`=?,`NATIONALID`=?,`MOBILENUMBER`=?,`USERTYPE`=?,`USERNAME`=?,`PASSWORD`=?";
        String[] data = {name.getText(), nationalID.getText(), mobileNumber.getText(), userType.getValue(), userName.getText(), password.getText()};
        boolean namestatus = Validation.textFieldNotEmpty(name, "ادخل الاسم");
        boolean nationalIDstatus = Validation.textFieldNotEmpty(nationalID, "ادخل رقم الهوية");
        boolean nationalIDnumberOnly = Validation.textFieldNumberOnly(nationalID, "ادخل ارقام فقط");
        boolean mobileNumberstatus = Validation.textFieldNotEmpty(mobileNumber, "ادخل رقم الجوال");
        boolean mobileNumberOnly = Validation.textFieldNumberOnly(mobileNumber, "ادخل ارقام فقط");
        boolean userTypestatus = Validation.comboBoxNotEmpty(userType, "اختر نوع المستخدم");
        boolean userNamestatus = Validation.textFieldNotEmpty(userName, "ادخل اسم المستخدم");
        boolean passwordstatus = Validation.textFieldNotEmpty(password, "ادخل كلمة المرور");

        if (namestatus && nationalIDstatus && mobileNumberstatus && userTypestatus && userNamestatus
                && passwordstatus && nationalIDnumberOnly && mobileNumberOnly) {
            try {
                DatabaseAccess.updat(tabelNme, fieldName, data, "USERID = '" + userid + "'");
                refreshUsertable();
                clearField();
            } catch (IOException ex) {
                Logger.getLogger(UsersPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteData(ActionEvent event) {
        String tabelNme = "users";
        try {
            DatabaseAccess.delete(tabelNme, "USERID = '" + userid + "'");
            refreshUsertable();
            clearField();
        } catch (IOException ex) {
            Logger.getLogger(UsersPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void userTableView() {
        try {
            ResultSet rs = DatabaseAccess.select("users");
            while (rs.next()) {
                userlist.add(new UserModel(
                        rs.getString("USERID"),
                        rs.getString("NAME"),
                        rs.getString("NATIONALID"),
                        rs.getString("MOBILENUMBER"),
                        rs.getString("USERTYPE"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }//userid,name,nationalid,mobilenumber,usertype,username,password
        userId_col.setCellValueFactory(new PropertyValueFactory<>("userid"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        nationalID_col.setCellValueFactory(new PropertyValueFactory<>("nationalid"));
        mobileNumber_col.setCellValueFactory(new PropertyValueFactory<>("mobilenumber"));
        userType_col.setCellValueFactory(new PropertyValueFactory<>("usertype"));
        userName_col.setCellValueFactory(new PropertyValueFactory<>("username"));

        userTable.setItems(userlist);
    }

    private void refreshUsertable() {
        userlist.clear();
        userTableView();
    }
}

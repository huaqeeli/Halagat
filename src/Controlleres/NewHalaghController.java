package Controlleres;

import Models.HalagatModel;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class NewHalaghController implements Initializable {

    @FXML
    private TextField halagahName;
    @FXML
    private ComboBox<String> teacherName;
    @FXML
    private ComboBox<String> level;
    @FXML
    private TableColumn<?, ?> halagahId_col;
    @FXML
    private TableColumn<?, ?> halagahName_col;
    @FXML
    private TableColumn<?, ?> teacherName_col;
    @FXML
    private TableColumn<?, ?> level_col;
    @FXML
    private TableView<HalagatModel> halagatTable;

    ObservableList<HalagatModel> halagatlist = FXCollections.observableArrayList();
    ObservableList<String> teacherlist = FXCollections.observableArrayList();
    String[] levelitem = {"المستوى الاول", "المستوى الثاني", "المستوى الثالث", "المستوى الرابع", "المستوى الخامس", "المستوى السادس"};
    String halagahid = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getTeacherName();
        refreshHalagatTable();
        ComboBoxFill.fillComboBox(level, levelitem);

        halagatTable.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<HalagatModel> list = FXCollections.observableArrayList();
                list = halagatTable.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    halagahName.setText(list.get(0).getHalagatname());
                    teacherName.setValue(list.get(0).getTeachername());
                    level.setValue(list.get(0).getLevel());
                    halagahid = list.get(0).getHalagatid();
                }
            }
        });
    }

    public void clearField() {
        halagahName.setText("");
        teacherName.setValue("");
        level.setValue("");
        halagahid = null;
    }
    /*`HALAGATID`,`HALAGANAME`,`TEACHER`,`LEVEL`*/

    @FXML
    private void saveData(ActionEvent event) {
        String tabelNme = "halagat";
        String fieldName = "`HALAGANAME`,`TEACHER`,`LEVEL`";
        String[] data = {halagahName.getText(), teacherName.getValue(), level.getValue()};
        String valuenumbers = "?,?,?";
        boolean halagahNamestatus = Validation.textFieldNotEmpty(halagahName, "ادخل اسم الحلقة");
        boolean teacherNamestatus = Validation.comboBoxNotEmpty(teacherName, "اختر اسم المعلم");
        boolean levelstatus = Validation.comboBoxNotEmpty(level, "اختر مستوى الحلقة");

        if (halagahNamestatus && teacherNamestatus && levelstatus) {
            try {
                DatabaseAccess.insert(tabelNme, fieldName, valuenumbers, data);
                refreshHalagatTable();
                clearField();
            } catch (IOException ex) {
                Logger.getLogger(NewHalaghController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        String tabelNme = "halagat";
        String fieldName = "`HALAGANAME`=?,`TEACHER`=?,`LEVEL`=?";
        String[] data = {halagahName.getText(), teacherName.getValue(), level.getValue()};
        boolean halagahNamestatus = Validation.textFieldNotEmpty(halagahName, "ادخل اسم الحلقة");
        boolean teacherNamestatus = Validation.comboBoxNotEmpty(teacherName, "اختر اسم المعلم");
        boolean levelstatus = Validation.comboBoxNotEmpty(level, "اختر مستوى الحلقة");

        if (halagahNamestatus && teacherNamestatus && levelstatus) {
            try {
                DatabaseAccess.updat(tabelNme, fieldName,  data,"HALAGATID = '" + halagahid + "'");
                refreshHalagatTable();
                clearField();
            } catch (IOException ex) {
                Logger.getLogger(NewHalaghController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteData(ActionEvent event) {
        String tabelNme = "halagat";
        try {
            DatabaseAccess.delete(tabelNme, "HALAGATID = '" + halagahid + "'");
            refreshHalagatTable();
            clearField();
        } catch (IOException ex) {
            Logger.getLogger(UsersPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void halagatTableView() {
        try {
            ResultSet rs = DatabaseAccess.select("halagat");
            while (rs.next()) {
                halagatlist.add(new HalagatModel(
                        rs.getString("HALAGATID"),
                        rs.getString("HALAGANAME"),
                        rs.getString("TEACHER"),
                        rs.getString("LEVEL")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }//halagatid,halagatname,teachername,level
        halagahId_col.setCellValueFactory(new PropertyValueFactory<>("halagatid"));
        halagahName_col.setCellValueFactory(new PropertyValueFactory<>("halagatname"));
        teacherName_col.setCellValueFactory(new PropertyValueFactory<>("teachername"));
        level_col.setCellValueFactory(new PropertyValueFactory<>("level"));

        halagatTable.setItems(halagatlist);
    }

    private void refreshHalagatTable() {
        halagatlist.clear();
        halagatTableView();
    }

    public void getTeacherName() {
        try {
            ResultSet rs = DatabaseAccess.select("users", "USERTYPE = 'معلم'");
            while (rs.next()) {
                teacherlist.add(rs.getString("NAME"));
            }
            teacherName.setItems(teacherlist);
        } catch (IOException | SQLException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
    }
}

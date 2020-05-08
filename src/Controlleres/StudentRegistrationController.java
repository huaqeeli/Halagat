package Controlleres;

import Models.StudentModel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StudentRegistrationController implements Initializable {

    @FXML
    private TextField name;
    @FXML
    private TextField age;
    @FXML
    private DatePicker birthDate;
    @FXML
    private TextField nationality;
    @FXML
    private TextField nationalID;
    @FXML
    private TextField issuePlace;
    @FXML
    private TextField mobileNumber;
    @FXML
    private ComboBox<String> halagahName;
    @FXML
    private TextField level;
    @FXML
    private ComboBox<String> paymentStatus;
    @FXML
    private TextField receiptNumber;
    @FXML
    private TextField imageUrl;
    @FXML
    private TableView<StudentModel> studentTable;
    @FXML
    private TableColumn<?, ?> studentId_col;
    @FXML
    private TableColumn<?, ?> name_col;
    @FXML
    private TableColumn<?, ?> age_col;
    @FXML
    private TableColumn<?, ?> birthDate_col;
    @FXML
    private TableColumn<?, ?> nationality_col;
    @FXML
    private TableColumn<?, ?> nationalID_col;
    Stage stage = new Stage();

    ObservableList<String> halagatlist = FXCollections.observableArrayList();
    ObservableList<StudentModel> studentlist = FXCollections.observableArrayList();
    File imagefile = null;
    String[] item = {"تم السداد", "لم يتم السداد"};
    String selectedstudentId = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboBoxFill.fillComboBox(paymentStatus, item);
        getHalagatName();
        refreshStudentTable();
        getRowData(studentTable);
        birthDate.setValue(LocalDate.now());
    }

    @FXML
    private File getImageUrle(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(ext1, ext2);
        imagefile = fileChooser.showOpenDialog(stage);
        imageUrl.setText(imagefile.getPath());
        return imagefile;
    }

    @FXML
    private void saveData(ActionEvent event) throws IOException {
        /*`STUDENTSID`,`NAME`,`AGE`,`BIRTHDATE`,`NATIONALITY`,`NATIONAID`,`PLACEOFISSUE`,`MOBILENUMBER`,`HALAGANAME`,`LEVEL`,`PAYMENTSTATUS`,`INVOICENUMBER`,`IMAGE`*/
        String tableName = "students";
        String fieldName = null;
        String halagahid = gethalagahId(halagahName.getValue());
        String[] data = {name.getText(), age.getText(), birthDate.getValue().toString(), nationality.getText(), nationalID.getText(), issuePlace.getText(), mobileNumber.getText(), halagahid, level.getText(), paymentStatus.getValue(), receiptNumber.getText()};
        String valuenumbers = "";
        if (imagefile != null) {
            fieldName = "`NAME`,`AGE`,`BIRTHDATE`,`NATIONALITY`,`NATIONAID`,`PLACEOFISSUE`,`MOBILENUMBER`,`HALAGATID`,`LEVEL`,`PAYMENTSTATUS`,`INVOICENUMBER`,`IMAGE`";
            valuenumbers = "?,?,?,?,?,?,?,?,?,?,?,?";
        } else {
            fieldName = "`NAME`,`AGE`,`BIRTHDATE`,`NATIONALITY`,`NATIONAID`,`PLACEOFISSUE`,`MOBILENUMBER`,`HALAGATID`,`LEVEL`,`PAYMENTSTATUS`,`INVOICENUMBER`";
            valuenumbers = "?,?,?,?,?,?,?,?,?,?,?";
        }

        boolean namestatus = Validation.textFieldNotEmpty(name, "ادخل الاسم");
        boolean nationalitystatus = Validation.textFieldNotEmpty(nationality, "ادخل الجنسية");
        boolean birthDatestatus = Validation.datePickerNotEmpty(birthDate, "ادخل تاريخ الميلاد");
        boolean nationalIDstatus = Validation.textFieldNotEmpty(nationalID, "ادخل رقم الهوية");
        boolean nationalIDNumberOnly = Validation.textFieldNumberOnly(nationalID, "ادخل رقم الهوية");
        boolean nationalIDunique = Validation.unique(tableName, "NATIONAID", "NATIONAID='" + nationalID.getText() + "'", "تم التسجيل برقم الهوية مسبقا ");
        boolean mobileNumberstatus = Validation.textFieldNotEmpty(mobileNumber, "ادخل رقم الهوية");
        boolean mobileNumberOnle = Validation.textFieldNumberOnly(mobileNumber, "ادخل ارقام فقط");
        boolean halagahNamestatus = Validation.comboBoxNotEmpty(halagahName, "اختر اسم الحلقة");

        if (namestatus && nationalitystatus && birthDatestatus && nationalIDstatus && nationalIDNumberOnly && mobileNumberstatus && mobileNumberOnle && halagahNamestatus && nationalIDunique) {
            try {
                DatabaseAccess.insert(tableName, fieldName, valuenumbers, data, imagefile);
                refreshStudentTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        try {
            String tableName = "students";
            String fieldName = null;
            String halagahid = gethalagahId(halagahName.getValue());
            String[] data = {name.getText(), age.getText(), birthDate.getValue().toString(), nationality.getText(), nationalID.getText(), issuePlace.getText(), mobileNumber.getText(), halagahid, level.getText(), paymentStatus.getValue(), receiptNumber.getText()};
            if (imagefile != null) {
                fieldName = "`NAME` = ?,`AGE` = ?,`BIRTHDATE` = ?,`NATIONALITY` = ?,`NATIONAID` = ?,`PLACEOFISSUE` = ?,`MOBILENUMBER` = ?,`HALAGATID` = ?,`LEVEL` = ?,`PAYMENTSTATUS` = ?,`INVOICENUMBER` = ?,`IMAGE`=?";
            } else {
                fieldName = "`NAME` = ?,`AGE` = ?,`BIRTHDATE` = ?,`NATIONALITY` = ?,`NATIONAID` = ?,`PLACEOFISSUE` = ?,`MOBILENUMBER` = ?,`HALAGATID` = ?,`LEVEL` = ?,`PAYMENTSTATUS` = ?,`INVOICENUMBER` = ?";
            }
            boolean namestatus = Validation.textFieldNotEmpty(name, "ادخل الاسم");
            boolean nationalitystatus = Validation.textFieldNotEmpty(nationality, "ادخل الجنسية");
            boolean birthDatestatus = Validation.datePickerNotEmpty(birthDate, "ادخل تاريخ الميلاد");
            boolean nationalIDstatus = Validation.textFieldNotEmpty(nationalID, "ادخل رقم الهوية");
            boolean nationalIDNumberOnly = Validation.textFieldNumberOnly(nationalID, "ادخل ارقام فقط");
            boolean mobileNumberstatus = Validation.textFieldNotEmpty(mobileNumber, "ادخل رقم الهوية");
            boolean mobileNumberOnle = Validation.textFieldNumberOnly(mobileNumber, "ادخل ارقام فقط");
            boolean halagahNamestatus = Validation.comboBoxNotEmpty(halagahName, "اختر اسم الحلقة");
            boolean studentIdstatus = Validation.stringNotNull(selectedstudentId, "اختر السجل من الجدول");

            if (studentIdstatus && namestatus && nationalitystatus && birthDatestatus && nationalIDstatus && nationalIDNumberOnly && mobileNumberstatus && mobileNumberOnle && halagahNamestatus) {
                try {
                    DatabaseAccess.updat(tableName, fieldName, data, "`STUDENTSID` ='" + selectedstudentId + "'", imagefile);
                    refreshStudentTable();
                    clearData();
                } catch (IOException ex) {
                    Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void deleteData(ActionEvent event) {
        try {
            String tableName = "students";
            DatabaseAccess.delete(tableName, "`STUDENTSID` ='" + selectedstudentId + "'");
            refreshStudentTable();
            clearData();
        } catch (IOException ex) {
            Logger.getLogger(StudentRegistrationController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void studentsTableView() {
        try {
            ResultSet rs = DatabaseAccess.select("students");
            while (rs.next()) {
                studentlist.add(new StudentModel(
                        rs.getString("STUDENTSID"),
                        rs.getString("NAME"),
                        rs.getString("AGE"),
                        rs.getString("BIRTHDATE"),
                        rs.getString("NATIONALITY"),
                        rs.getString("NATIONAID")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        studentId_col.setCellValueFactory(new PropertyValueFactory<>("studentid"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        age_col.setCellValueFactory(new PropertyValueFactory<>("age"));
        birthDate_col.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        nationality_col.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        nationalID_col.setCellValueFactory(new PropertyValueFactory<>("nationalid"));

        studentTable.setItems(studentlist);
    }

    private void refreshStudentTable() {
        studentlist.clear();
        studentsTableView();
    }

    @FXML
    private void getAge(ActionEvent event) {
        LocalDate now = LocalDate.now();
        LocalDate pdate = birthDate.getValue();
        Period period = Period.between(pdate, now);
        int diff = period.getYears();
        String ageco = Integer.toString(diff);
        age.setText(ageco);
    }

    public void getHalagatName() {
        try {
            ResultSet rs = DatabaseAccess.select("halagat");
            while (rs.next()) {
                halagatlist.add(rs.getString("HALAGANAME"));
            }
            halagahName.setItems(halagatlist);
        } catch (IOException | SQLException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
    }

    @FXML
    private void getlevel(ActionEvent event) {
        try {
            ResultSet rs = DatabaseAccess.select("halagat", "HALAGANAME='" + halagahName.getValue() + "'");
            while (rs.next()) {
                level.setText(rs.getString("LEVEL"));
            }
        } catch (IOException | SQLException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
    }
    public String gethalagahId(String halagahaName) {
       String halagahid = null;
        try {
            ResultSet rs = DatabaseAccess.select("halagat", "HALAGANAME='" + halagahaName + "'");
            while (rs.next()) {
                halagahid = rs.getString("HALAGATID");
            }
        } catch (IOException | SQLException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        return halagahid;
    }
    public String getHalagatName(String halagahaid) {
       String halagahName = null;
        try {
            ResultSet rs = DatabaseAccess.select("halagat", "HALAGATID='" + halagahaid + "'");
            while (rs.next()) {
                halagahName = rs.getString("HALAGANAME");
            }
        } catch (IOException | SQLException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        return halagahName;
    }

    private void getRowData(TableView table) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<StudentModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    try {
                        ResultSet rs = DatabaseAccess.select("students", "STUDENTSID='" + list.get(0).getStudentid() + "'");
                        if (rs.next()) {
                            name.setText(rs.getString("NAME"));
                            nationality.setText(rs.getString("NATIONALITY"));
                            nationalID.setText(rs.getString("NATIONAID"));
                            issuePlace.setText(rs.getString("PLACEOFISSUE"));
                            mobileNumber.setText(rs.getString("MOBILENUMBER"));
                            age.setText(rs.getString("AGE"));
                            birthDate.setValue(LocalDate.parse(rs.getString("BIRTHDATE")));
                            halagahName.setValue(getHalagatName(rs.getString("HALAGATID")));
                            level.setText(rs.getString("LEVEL"));
                            receiptNumber.setText(rs.getString("INVOICENUMBER"));
                            paymentStatus.setValue(rs.getString("PAYMENTSTATUS"));
                        }
                        selectedstudentId = list.get(0).getStudentid();
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(StudentIdentityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    private void clearData() {
        name.setText("");
        nationality.setText("");
        nationalID.setText("");
        issuePlace.setText("");
        mobileNumber.setText("");
        age.setText("");
        birthDate.setValue(LocalDate.now());
        halagahName.setValue("");
        level.setText("");
        receiptNumber.setText("");
        paymentStatus.setValue("");
    }

}

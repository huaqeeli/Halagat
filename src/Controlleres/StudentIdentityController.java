package Controlleres;

import Models.StudentModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class StudentIdentityController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label nationality;
    @FXML
    private Label nationalID;
    @FXML
    private Label issuePlace;
    @FXML
    private Label mobileNumber;
    @FXML
    private Label halagahName;
    @FXML
    private Label studentId;
    @FXML
    private Label age;
    @FXML
    private Label birthDate;
    @FXML
    private Label level;
    @FXML
    private TableView<StudentModel> identityTeble;
    @FXML
    private TableColumn<?, ?> studentId_col;
    @FXML
    private TableColumn<?, ?> name_col;
    @FXML
    private TableColumn<?, ?> halagahName_col;
    @FXML
    private ImageView studentImage;

    ObservableList<StudentModel> identitylist = FXCollections.observableArrayList();
    String selectedStudentid = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        identityTebleView();
        getRowData(identityTeble);
    }

    @FXML
    private void printIdentity(ActionEvent event) {
        try {
            String reportSrcFile = "C:\\Users\\ابو ريان\\Documents\\Halagat\\src\\Reports\\IdentityReport.jrxml";
            Connection con = DatabaseConnector.dbConnector();

            JasperDesign jasperReport = JRXmlLoader.load(reportSrcFile);
            Map parameters = new HashMap();
            parameters.put("studentid", selectedStudentid);

            JasperReport jrr = JasperCompileManager.compileReport(jasperReport);
            JasperPrint print = JasperFillManager.fillReport(jrr, parameters, con);

//        JasperPrintManager.printReport(print, false);
            JasperViewer.viewReport(print, false);
        } catch (JRException | IOException ex) {
            Logger.getLogger(StudentIdentityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void printCard(ActionEvent event) {
        try {
            String reportSrcFile = "C:\\Users\\ابو ريان\\Documents\\Halagat\\src\\Reports\\StudentCard.jrxml";
            Connection con = DatabaseConnector.dbConnector();

            JasperDesign jasperReport = JRXmlLoader.load(reportSrcFile);
            Map parameters = new HashMap();
            parameters.put("studentid", selectedStudentid);

            JasperReport jrr = JasperCompileManager.compileReport(jasperReport);
            JasperPrint print = JasperFillManager.fillReport(jrr, parameters, con);

//        JasperPrintManager.printReport(print, false);
            JasperViewer.viewReport(print, false);
        } catch (JRException | IOException ex) {
            Logger.getLogger(StudentIdentityController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void identityTebleView() {
        try {
            ResultSet rs = DatabaseAccess.select("students");
            while (rs.next()) {
                identitylist.add(new StudentModel(
                        rs.getString("STUDENTSID"),
                        rs.getString("NAME"),
                        getHalagatName(rs.getString("HALAGATID"))
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        studentId_col.setCellValueFactory(new PropertyValueFactory<>("studentid"));
        name_col.setCellValueFactory(new PropertyValueFactory<>("name"));
        halagahName_col.setCellValueFactory(new PropertyValueFactory<>("halagahaname"));

        identityTeble.setItems(identitylist);
    }

    private void refreshStudentTable() {
        identitylist.clear();
        identityTebleView();
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
                            studentId.setText(rs.getString("STUDENTSID"));
                            age.setText(rs.getString("AGE"));
                            birthDate.setText(rs.getString("BIRTHDATE"));
                            halagahName.setText(getHalagatName(rs.getString("HALAGATID")));
                            level.setText(rs.getString("LEVEL"));

                            InputStream is = rs.getBinaryStream("IMAGE");
                            if (is != null) {
                                OutputStream os = new FileOutputStream(new File("photo.jpg"));
                                byte[] content = new byte[1024];
                                int size = 0;
                                while ((size = is.read(content)) != -1) {
                                    os.write(content, 0, size);
                                }
                                os.close();
                                is.close();
                                Image imagex = new Image("file:photo.jpg", 250, 250, true, true);
                                studentImage.setImage(imagex);
                            } else {
                                studentImage.setImage(null);
                            }
                        }
                        selectedStudentid = list.get(0).getStudentid();
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(StudentIdentityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
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
}

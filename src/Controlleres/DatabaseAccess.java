package Controlleres;

import Messages.Controllers.ShowMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class DatabaseAccess {

    public static void insert(String tapleName, String fildName, String valueNamber, String[] data) throws IOException {

        Connection con = DatabaseConnector.dbConnector();
        String guiry = "INSERT INTO " + tapleName + "(" + fildName + ")VALUES(" + valueNamber + ")";
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            int e = data.length;
            for (int i = 1; i <= e; i++) {
                psm.setString(i, data[i - 1]);
            }
            int t = psm.executeUpdate();
            if (t > 0) {
            } else {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("حدث خطاء في عملية الحفظ الرجاء المحاولة مرة اخرى");
            }
            con.close();
            psm.close();
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static void insert(String tapleName, String fildName, String valueNamber, String[] data, File imagefile) throws IOException {

        Connection con = DatabaseConnector.dbConnector();
        String guiry = "INSERT INTO " + tapleName + "(" + fildName + ")VALUES(" + valueNamber + ")";
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            int e = data.length;
            for (int i = 1; i <= e; i++) {
                psm.setString(i, data[i - 1]);
            }
            if (imagefile != null) {
                FileInputStream fin = new FileInputStream(imagefile);
                int len = (int) imagefile.length();
                psm.setBinaryStream(12, fin, len);
            }
            int t = psm.executeUpdate();
            if (t > 0) {
            } else {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("حدث خطاء في عملية الحفظ الرجاء المحاولة مرة اخرى");
            }
            con.close();
            psm.close();
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static void updat(String tapleName, String fildNameAndValue, String[] data, String condition) throws IOException {
        Connection con = DatabaseConnector.dbConnector();
        String guiry = "UPDATE " + tapleName + " SET " + fildNameAndValue + " WHERE" + " " + condition;
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            int e = data.length;
            for (int i = 1; i <= e; i++) {
                psm.setString(i, data[i - 1]);
            }
            int t = psm.executeUpdate();
            if (t > 0) {
                ShowMessage showMessage = new ShowMessage();
                showMessage.success("تم تحديث البيانات");
            }
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static void updat(String tapleName, String fildNameAndValue, float[] data, String condition) throws IOException {
        Connection con = DatabaseConnector.dbConnector();
        String guiry = "UPDATE " + tapleName + " SET " + fildNameAndValue + " WHERE" + " " + condition;
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            int e = data.length;
            for (int i = 1; i <= e; i++) {
                psm.setFloat(i, data[i - 1]);
            }
            psm.executeUpdate();
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static void updat(String tapleName, String fildNameAndValue, String[] data, String condition, File imagefile) throws IOException {
        Connection con = DatabaseConnector.dbConnector();
        String guiry = "UPDATE " + tapleName + " SET " + fildNameAndValue + " WHERE" + " " + condition;
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            int e = data.length;
            for (int i = 1; i <= e; i++) {
                psm.setString(i, data[i - 1]);
            }
            if (imagefile != null) {
                FileInputStream fin = new FileInputStream(imagefile);
                int len = (int) imagefile.length();
                psm.setBinaryStream(12, fin, len);
            }
            int t = psm.executeUpdate();
            if (t > 0) {
                ShowMessage showMessage = new ShowMessage();
                showMessage.success("تم تحديث البيانات");
            }
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static void updat(String tapleName, String fildNameAndValue, String condition) throws IOException {
        Connection con = DatabaseConnector.dbConnector();
        String guiry = "UPDATE " + tapleName + " SET " + fildNameAndValue + " WHERE" + condition;
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            psm.executeUpdate();

        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static void delete(String tapleName, String condition) throws IOException {
        Connection con = DatabaseConnector.dbConnector();
        String guiry = "DELETE FROM " + tapleName + " WHERE " + condition;
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            Alert alert = Validation.confirmationDilog("تاكيد الحذف", "سوف يتم حذف السجل هل تريد المتابعة");
            if (alert.getResult() == ButtonType.YES) {
                psm.executeUpdate();
            }
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static void delete(String quiry) throws IOException {
        Connection con = DatabaseConnector.dbConnector();
        try {
            PreparedStatement psm = con.prepareStatement(quiry);
            Alert alert = Validation.confirmationDilog("تاكيد الحذف", "سوف يتم حذف السجل هل تريد المتابعة");
            if (alert.getResult() == ButtonType.YES) {
                psm.executeUpdate();
            }
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
    }

    public static ResultSet select(String tapleName, String condition) throws IOException {
        ResultSet rs = null;
        String guiry = "SELECT * FROM " + tapleName + " " + "WHERE" + " " + condition;
        Connection con = DatabaseConnector.dbConnector();
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            rs = psm.executeQuery();
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
        return rs;
    }

    public static ResultSet select(String tapleName) throws IOException {
        ResultSet rs = null;
        String guiry = "SELECT * FROM " + tapleName;
        Connection con = DatabaseConnector.dbConnector();
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            rs = psm.executeQuery();
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
        return rs;
    }

    public static ResultSet getDataJoinTable(String guiry) throws IOException {
        ResultSet rs = null;
        Connection con = DatabaseConnector.dbConnector();
        try {
            PreparedStatement psm = con.prepareStatement(guiry);
            rs = psm.executeQuery();
        } catch (SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
        return rs;
    }

    public static boolean checkEmpty(String text) {
        boolean state = false;
        if (text.isEmpty()) {
            state = true;
        } else {
            state = false;
        }
        return state;
    }

    public static void showAlert(String titel, String massage, AlertType t) {
        Alert alert = new Alert(t);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    public static void showAlert(String titel, String massage) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

}

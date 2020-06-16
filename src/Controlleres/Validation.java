package Controlleres;

import Messages.Controllers.ShowMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

public class Validation {

    public static boolean textFieldNotEmpty(TextField t) {
        boolean state = false;

        if (t.getText() != null && !t.getText().isEmpty()) {
            state = true;
        }

        return state;
    }

    public static boolean textFieldNotEmpty(TextField t, String validationmassage) {
        boolean state = true;
        if (!textFieldNotEmpty(t)) {
            state = false;
            t.setPromptText(validationmassage);
            t.setStyle(" -fx-border-color: #DC4D41; -fx-background-color:#F8DCDA");
        } else {
            t.setStyle(" -fx-border-color: #344242;-fx-background-color:#FFFFFF");
        }

        return state;
    }

    public static boolean loginTextFieldNotEmpty(TextField t, String validationmassage) {
        boolean state = true;
        if (!textFieldNotEmpty(t)) {
            state = false;
            t.setStyle(" -fx-border-color: #DD4F43;");
            t.setPromptText(validationmassage);
        } else {
            t.setStyle(" -fx-border-color: #6BBAD5;");
        }

        return state;
    }

    public static boolean datePickerNotEmpty(DatePicker t) {
        boolean state = false;

        if (t.getValue() != null && !t.getValue().equals("")) {
            state = true;
        }

        return state;
    }

    public static boolean datePickerNotEmpty(DatePicker t, String validationmassage) {
        boolean state = true;
        if (!datePickerNotEmpty(t)) {
            state = false;
            t.setPromptText(validationmassage);
            t.setStyle(" -fx-border-color: #DC4D41; -fx-background-color:#F8DCDA");
        } else {
            t.setStyle(" -fx-border-color: #344242;-fx-background-color:#FFFFFF");
        }

        return state;
    }

    public static boolean comboBoxNotEmpty(ComboBox t) {
        boolean state = false;

        if (t.getValue() != null && !t.getValue().equals("")) {
            state = true;
        }

        return state;
    }

    public static boolean comboBoxNotEmpty(ComboBox t, String validationmassage) {
        boolean state = true;
        if (!comboBoxNotEmpty(t)) {
            state = false;
            t.setPromptText(validationmassage);
            t.setStyle(" -fx-border-color: #DC4D41; -fx-background-color:#F8DCDA");
        } else {
            t.setStyle(" -fx-border-color: #344242;-fx-background-color:#FFFFFF");
        }

        return state;
    }

    public static boolean textFieldTypeDate(TextField t) {
        boolean state = false;

        if (t.getText().matches("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)")) {
            state = true;
        }

        return state;
    }

    public static boolean textFieldTypeDate(TextField t, String validationmassage) {
        boolean state = true;
        if (!textFieldTypeDate(t)) {
            state = false;
            t.setPromptText(validationmassage);
            t.setStyle(" -fx-border-color: #DC4D41; -fx-background-color:#F8DCDA");
        } else {
            t.setStyle(" -fx-border-color: #344242;-fx-background-color:#FFFFFF");
        }
        return state;
    }

    public static boolean textFieldNumberOnly(TextField t) {
        boolean state = false;
        if (t.getText().matches("([0-9]+(\\.[0-9]+)?)+")) {
            state = true;
        }
        return state;
    }

    public static boolean textFieldNumberOnly(TextField t, String validationmassage) {
        boolean state = true;
        if (!textFieldNumberOnly(t)) {
            state = false;
            t.setText("");
            t.setPromptText(validationmassage);
            t.setStyle(" -fx-border-color: #DC4D41; -fx-background-color:#F8DCDA");
        } else {
            t.setStyle(" -fx-border-color: #344242;-fx-background-color:#FFFFFF");
        }
        return state;
    }

    public static Alert confirmationDilog(String titel, String massage) {
        Alert alert = new Alert(AlertType.CONFIRMATION, massage, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.showAndWait();
        return alert;
    }

    public static void showAlert(String titel, String massage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    public static void showAlert(String titel, SQLException massage) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(massage.toString());
        alert.showAndWait();
    }

    public static void showAlert(String titel, String massage, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(titel);
        alert.setHeaderText(null);
        alert.setContentText(massage);
        alert.showAndWait();
    }

    public static boolean unique(String tapleName, String fildName, String condition, String validationmassage) throws IOException {
        boolean state = true;
        try {
            ResultSet rs = null;
            String guiry = "SELECT " + " " + fildName + " " + " FROM " + " " + tapleName + " " + " WHERE" + " " + condition;
            Connection con = DatabaseConnector.dbConnector();
            PreparedStatement psm = con.prepareStatement(guiry);
            rs = psm.executeQuery();
            if (rs.next()) {
                state = false;
                ShowMessage showMessage = new ShowMessage();
                showMessage.error(validationmassage);
            }
            con.close();
            psm.close();
            rs.close();
        } catch (IOException | SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
        return state;
    }

    public static boolean ifexisting(String tapleName, String fildName, String condition, String validationmassage) throws IOException {
        boolean state = true;
        try {
            ResultSet rs = null;
            String guiry = "SELECT " + " " + fildName + " " + " FROM " + " " + tapleName + " " + " WHERE" + " " + condition;
            Connection con = DatabaseConnector.dbConnector();
            PreparedStatement psm = con.prepareStatement(guiry);
            rs = psm.executeQuery();
            if (rs.next()) {
                state = false;
                ShowMessage showMessage = new ShowMessage();
                showMessage.error(validationmassage);
            }
            con.close();
            psm.close();
            rs.close();
        } catch (IOException | SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
        return state;
    }

    public static boolean isMatching(String tapleName, String fildName, String condition, String validationmassage) throws IOException {
        boolean state = false;
        try {
            ResultSet rs = null;
            String guiry = "SELECT " + " " + fildName + " " + " FROM " + " " + tapleName + " " + " WHERE" + " " + condition;
            Connection con = DatabaseConnector.dbConnector();
            PreparedStatement psm = con.prepareStatement(guiry);
            rs = psm.executeQuery();
            if (rs.next()) {
                state = true;
            } else {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error(validationmassage);
            }
            con.close();
            psm.close();
            rs.close();
        } catch (IOException | SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
        return state;
    }

    public static boolean ifexisting(String tapleName, String fildName, String condition) throws IOException {
        boolean state = true;
        try {
            ResultSet rs = null;
            String guiry = "SELECT " + " " + fildName + " " + " FROM " + " " + tapleName + " " + " WHERE" + " " + condition;
            Connection con = DatabaseConnector.dbConnector();
            PreparedStatement psm = con.prepareStatement(guiry);
            rs = psm.executeQuery();
            if (rs.next()) {
                state = false;
            }
            con.close();
            psm.close();
            rs.close();
        } catch (IOException | SQLException ex) {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(ex.toString());
        }
        return state;
    }

    public static boolean stringNotNull(String string, String validationmassage) throws IOException {
        boolean state = true;
        if (string == null) {
            state = false;
            ShowMessage showMessage = new ShowMessage();
            showMessage.error(validationmassage);
        }
        return state;
    }

    public static int daysBetween(java.util.Date d1, java.util.Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
}

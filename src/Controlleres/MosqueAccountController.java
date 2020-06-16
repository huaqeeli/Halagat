package Controlleres;

import Models.MosqueAccountModel;
import com.jfoenix.controls.JFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class MosqueAccountController implements Initializable {

    @FXML
    private JFXDatePicker oprationdate;
    @FXML
    private ComboBox<String> oprationtype;
    @FXML
    private TextField amount;
    @FXML
    private TextField depositorname;
    @FXML
    private TextField notes;
    @FXML
    private Label totalpaymanet;
    @FXML
    private Label totalcost;
    @FXML
    private Label totalbalance;
    @FXML
    private TableView<MosqueAccountModel> mosqueaccountTable;
    @FXML
    private TableColumn<?, ?> oprationid_col;
    @FXML
    private TableColumn<?, ?> oprationdate_col;
    @FXML
    private TableColumn<?, ?> oprationtype_col;
    @FXML
    private TableColumn<?, ?> amount_col;
    @FXML
    private TableColumn<?, ?> depositorname_col;
    @FXML
    private TableColumn<?, ?> notes_col;
    String balanceId = BalanceController.getBalanceId();

    String oprationId = null;
    String oprationType = null;
    float curentAmount = 0;
    ObservableList<MosqueAccountModel> moaquelist = FXCollections.observableArrayList();
    String[] oprationtypeitem = {"ايداع", "سحب"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oprationdate.setValue(LocalDate.now());
        ComboBoxFill.fillComboBox(oprationtype, oprationtypeitem);
        refreshmosqueaccountTable();
        getRowData(mosqueaccountTable);
    }

    @FXML
    private void saveData(ActionEvent event) {
        String tableName = "mosqueopration";
        /*`OPRATIONID`,`OPRATIONDATE`,`OPRATIONTYPE`,`AMOUNT`,`DEPOSITORNAME`,`NOTES`,`BALANCEID`*/
        String fieldName = "`OPRATIONDATE`,`OPRATIONTYPE`,`AMOUNT`,`DEPOSITORNAME`,`NOTES`,`BALANCEID`";
        String[] data = {oprationdate.getValue().toString(), oprationtype.getValue(), amount.getText(), depositorname.getText(), notes.getText(), balanceId};
        String valuenumbers = "?,?,?,?,?,?";

        boolean oprationdateStatus = Validation.datePickerNotEmpty(oprationdate, "ادخل التاريخ");
        boolean oprationtypeStatus = Validation.comboBoxNotEmpty(oprationtype, "اختر نوع العملية");
        boolean amountStatus = Validation.textFieldNotEmpty(amount, "ادخل المبلغ");
        boolean amountNumberOnle = Validation.textFieldNumberOnly(amount, "لا يقبل الا ارقام فقط");
        boolean depositornameStatus = Validation.textFieldNotEmpty(depositorname, "ادخل اسم المودع");

        if (oprationdateStatus && oprationtypeStatus && amountStatus && amountNumberOnle && depositornameStatus) {
            try {
                DatabaseAccess.insert(tableName, fieldName, valuenumbers, data);
                if ("ايداع".equals(oprationtype.getValue())) {
                    MosqueAccountModel.incrementBalance(Float.parseFloat(amount.getText()));
                } else {
                    MosqueAccountModel.decrementBalance(Float.parseFloat(amount.getText()));
                }
                refreshmosqueaccountTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(MosqueAccountModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        String tableName = "mosqueopration";
        String fieldName = "`OPRATIONDATE`=?,`OPRATIONTYPE`=?,`AMOUNT`=?,`DEPOSITORNAME`=?,`NOTES`=?";
        String[] data = {oprationdate.getValue().toString(), oprationtype.getValue(), amount.getText(), depositorname.getText(), notes.getText()};

        boolean oprationdateStatus = Validation.datePickerNotEmpty(oprationdate, "ادخل التاريخ");
        boolean oprationtypeStatus = Validation.comboBoxNotEmpty(oprationtype, "اختر نوع العملية");
        boolean amountStatus = Validation.textFieldNotEmpty(amount, "ادخل المبلغ");
        boolean amountNumberOnle = Validation.textFieldNumberOnly(amount, "لا يقبل الا ارقام فقط");
        boolean depositornameStatus = Validation.textFieldNotEmpty(depositorname, "ادخل اسم المودع");

        if (oprationdateStatus && oprationtypeStatus && amountStatus && amountNumberOnle && depositornameStatus) {
            try {
                DatabaseAccess.updat(tableName, fieldName, data, "OPRATIONID = '" + oprationId + "'");
                if ("ايداع".equals(oprationtype.getValue())) {
                    MosqueAccountModel.decrementBalance(curentAmount);
                    MosqueAccountModel.incrementBalance(Float.parseFloat(amount.getText()));
                } else {
                    MosqueAccountModel.incrementBalance(curentAmount);
                    MosqueAccountModel.decrementBalance(Float.parseFloat(amount.getText()));
                }
                refreshmosqueaccountTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(MosqueAccountModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteData(ActionEvent event) {
        try {
            boolean oprationIdStatus = Validation.stringNotNull(oprationId, "اختر السجل من الجدول");
            if (oprationIdStatus) {
                DatabaseAccess.delete("mosqueopration", "OPRATIONID = '" + oprationId + "'");
                if ("ايداع".equals(oprationType)) {
                    MosqueAccountModel.decrementBalance(curentAmount);
                } else {
                    MosqueAccountModel.incrementBalance(curentAmount);
                }
                refreshmosqueaccountTable();
                clearData();
            }

        } catch (IOException ex) {
            Logger.getLogger(MosqueAccountModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void mosqueaccountTableView() {
        try {
            ResultSet rs = DatabaseAccess.select("mosqueopration", "BALANCEID = '" + balanceId + "'");
            while (rs.next()) {
                moaquelist.add(new MosqueAccountModel(
                        rs.getString("OPRATIONID"),
                        rs.getString("OPRATIONDATE"),
                        rs.getString("OPRATIONTYPE"),
                        rs.getString("AMOUNT"),
                        rs.getString("DEPOSITORNAME"),
                        rs.getString("NOTES")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        oprationid_col.setCellValueFactory(new PropertyValueFactory<>("oprationid"));
        oprationdate_col.setCellValueFactory(new PropertyValueFactory<>("oprationdate"));
        oprationtype_col.setCellValueFactory(new PropertyValueFactory<>("oprationtype"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        depositorname_col.setCellValueFactory(new PropertyValueFactory<>("depositorname"));
        notes_col.setCellValueFactory(new PropertyValueFactory<>("notes"));

        totalcost.setText(Float.toString(MosqueAccountModel.getTotalcost()));
        totalpaymanet.setText(Float.toString(MosqueAccountModel.getTotalpaymanet()));
        totalbalance.setText(Float.toString(MosqueAccountModel.getTotalbalance()));

        mosqueaccountTable.setItems(moaquelist);
    }

    private void refreshmosqueaccountTable() {
        moaquelist.clear();
        mosqueaccountTableView();
    }

    private void clearData() {
        oprationdate.setValue(LocalDate.now());
        oprationtype.setValue("");
        amount.setText("");
        depositorname.setText("");
        notes.setText("");
    }

    private void getRowData(TableView table) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<MosqueAccountModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    oprationdate.setValue(LocalDate.parse(list.get(0).getOprationdate()));
                    oprationtype.setValue(list.get(0).getOprationtype());
                    amount.setText(list.get(0).getAmount());
                    depositorname.setText(list.get(0).getDepositorname());
                    notes.setText(list.get(0).getNotes());
                    oprationId = list.get(0).getOprationid();
                    oprationType = list.get(0).getOprationtype();
                    curentAmount = Float.parseFloat(list.get(0).getAmount());
                }
            }
        });
    }

}

package Controlleres;

import Models.BankAccountModel;
import Models.FurnitureModel;
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

public class BankAccountController implements Initializable {

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
    private TextField Initialbalance;
    @FXML
    private Label initialbalanceview;
    @FXML
    private Label totalcost;
    @FXML
    private Label totalpaymanet;
    @FXML
    private Label totalbalance;
    @FXML
    private TableView<BankAccountModel> bankaccountTable;
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
    String balanceId = BalanceController.getBalanceId();;
    String oprationId = null;
    String oprationType = null;
    float curentAmount = 0;
    ObservableList<BankAccountModel> banklist = FXCollections.observableArrayList();
    String[] oprationtypeitem = {"ايداع", "سحب"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        oprationdate.setValue(LocalDate.now());
        ComboBoxFill.fillComboBox(oprationtype, oprationtypeitem);
        refreshbankaccountTable();
        getRowData(bankaccountTable);
    }

    @FXML
    private void saveData(ActionEvent event) {
        String tableName = "bankopration";
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
                    BankAccountModel.incrementBalance(Float.parseFloat(amount.getText()));
                } else {
                    BankAccountModel.decrementBalance(Float.parseFloat(amount.getText()));
                }
                refreshbankaccountTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        String tableName = "bankopration";
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
                    BankAccountModel.decrementBalance(curentAmount);
                    BankAccountModel.incrementBalance(Float.parseFloat(amount.getText()));
                } else {
                    BankAccountModel.incrementBalance(curentAmount);
                    BankAccountModel.decrementBalance(Float.parseFloat(amount.getText()));
                }
                refreshbankaccountTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteData(ActionEvent event) {
        try {
            boolean oprationIdStatus = Validation.stringNotNull(oprationId, "اختر السجل من الجدول");
            if (oprationIdStatus) {
                DatabaseAccess.delete("bankopration", "OPRATIONID = '" + oprationId + "'");
                if ("ايداع".equals(oprationType)) {
                    BankAccountModel.decrementBalance(curentAmount);
                } else {
                    BankAccountModel.incrementBalance(curentAmount);
                }
                refreshbankaccountTable();
                clearData();
            }

        } catch (IOException ex) {
            Logger.getLogger(BankAccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void saveInitialBalance(ActionEvent event) {
        String tableName = "bankaccount";
        String fieldName = "`INITIALBALANCE`=?";
        String[] data = {Initialbalance.getText()};
        
        boolean InitialbalanceStatus = Validation.textFieldNotEmpty(Initialbalance, "ادخل الرصيد الافتتاحي");

        if (InitialbalanceStatus ) {
            try {
                BankAccountModel.decrementBalance(BankAccountModel.getInitialbalance());
                BankAccountModel.incrementBalance(Float.parseFloat(Initialbalance.getText()));
                DatabaseAccess.updat(tableName, fieldName, data, "BALANCEID = '" + balanceId + "'");
                refreshbankaccountTable();
                Initialbalance.setText("");
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void bankaccountTableView() {
        try {
            ResultSet rs = DatabaseAccess.select("bankopration", "BALANCEID = '" + balanceId + "'");
            while (rs.next()) {
                banklist.add(new BankAccountModel(
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

        totalcost.setText(Float.toString(BankAccountModel.getTotalcost()));
        initialbalanceview.setText(Float.toString(BankAccountModel.getInitialbalance()));
        totalpaymanet.setText(Float.toString(BankAccountModel.getTotalpaymanet()));
        totalbalance.setText(Float.toString(BankAccountModel.getTotalbalance()));

        bankaccountTable.setItems(banklist);
    }

    private void refreshbankaccountTable() {
        banklist.clear();
        bankaccountTableView();
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
                ObservableList<BankAccountModel> list = FXCollections.observableArrayList();
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

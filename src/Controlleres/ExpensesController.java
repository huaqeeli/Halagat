package Controlleres;

import Models.ExpensesModel;
import Models.RevenuesModel;
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
import javafx.scene.input.MouseEvent;

public class ExpensesController implements Initializable {

    @FXML
    private JFXDatePicker expensesdata;
    @FXML
    private ComboBox<String> clause;
    @FXML
    private TextField amount;
    @FXML
    private TextField amountText;
    @FXML
    private TextField cashOrCheque;
    @FXML
    private TextField bankName;
    @FXML
    private TextField expensessTo;
    @FXML
    private Label totalExpenses;
    @FXML
    private Label totalbalance;
    @FXML
    private TableView<ExpensesModel> expensesTable;
    @FXML
    private TableColumn<?, ?> expensesid_col;
    @FXML
    private TableColumn<?, ?> expensesdata_col;
    @FXML
    private TableColumn<?, ?> clause_col;
    @FXML
    private TableColumn<?, ?> amount_col;
    @FXML
    private TableColumn<?, ?> expensesTo_col;

    ObservableList<ExpensesModel> expenseslist = FXCollections.observableArrayList();
    String[] clauseitem = {"جوائز", "رواتب", "اصل ثابت", "مكافات", "نثريات", "مطبوعات", "صيانة", "مواصلات", "ضيافة", "مصروفات ادارية", "عهدة", "استرداد", "لاغي", "بنك البلاد"};
    String balanceId = null;
    float tableAmount = 0;
    String expensesId = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ComboBoxFill.fillComboBox(clause, clauseitem);
        expensesdata.setValue(LocalDate.now());
        refreshExpensesTable();
        getRowData(expensesTable);
    }

    @FXML
    private void saveDate(MouseEvent event) {
        String tableName = "expenses";
        balanceId = BalanceController.getBalanceId();
        String bankNameText = null;
        if (bankName.getText() == null) {
            bankNameText = "ـــــ";
        } else {
            bankNameText = bankName.getText();
        }
        /*`EXPENSESDATE`,`CLAUSE`,`AMOUNT`,`EXPENSESTO`,`ACCOUNTTYPE`,`BALANCEID`,`AMOUNTTEXT`,`CASHORCHEQUENO`,`BANKNAME*/
        String fieldName = "`EXPENSESDATE`,`CLAUSE`,`AMOUNT`,`EXPENSESTO`,`BALANCEID`,`AMOUNTTEXT`,`CASHORCHEQUENO`,`BANKNAME`";
        String[] data = {expensesdata.getValue().toString(), clause.getValue(), amount.getText(), expensessTo.getText(), balanceId, amountText.getText(), cashOrCheque.getText(), bankNameText};
        String valuenumbers = "?,?,?,?,?,?,?,?";

        boolean revenuesdataStatus = Validation.datePickerNotEmpty(expensesdata, "ادخل التاريخ");
        boolean clauseStatus = Validation.comboBoxNotEmpty(clause, "ادخل البند");
        boolean amountStatus = Validation.textFieldNotEmpty(amount, "ادخل المبلغ");
        boolean amountNumberOnle = Validation.textFieldNotEmpty(amount, "لا يقبل الا ارقام فقط");
        boolean fromStatus = Validation.textFieldNotEmpty(expensessTo, "ادخل جهة الصادر ");
        boolean amountTextStatus = Validation.textFieldNotEmpty(amountText, "ادخل المبلغ كتابة");
        boolean cashOrChequeStatus = Validation.textFieldNotEmpty(cashOrCheque, "ادخل نقدا ار رقم الشيك ");

        if (revenuesdataStatus && clauseStatus && amountStatus && amountNumberOnle && fromStatus && amountTextStatus && cashOrChequeStatus) {
            try {
                DatabaseAccess.insert(tableName, fieldName, valuenumbers, data);
                BalanceController.decrementBalance(Float.parseFloat(amount.getText()));
                refreshExpensesTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(MouseEvent event) {
        String tableName = "expenses";
        String bankNameText = null;
        if (bankName.getText() == null) {
            bankNameText = "ـــــ";
        } else {
            bankNameText = bankName.getText();
        }
        
        String fieldName = "`EXPENSESDATE`=?,`CLAUSE`=?,`AMOUNT`=?,`EXPENSESTO`=?,`AMOUNTTEXT`=?,`CASHORCHEQUENO`=?,`BANKNAME`=?";
        String[] data = {expensesdata.getValue().toString(), clause.getValue(), amount.getText(), expensessTo.getText() , amountText.getText(), cashOrCheque.getText(), bankNameText};
      

        boolean revenuesdataStatus = Validation.datePickerNotEmpty(expensesdata, "ادخل التاريخ");
        boolean clauseStatus = Validation.comboBoxNotEmpty(clause, "ادخل البند");
        boolean amountStatus = Validation.textFieldNotEmpty(amount, "ادخل المبلغ");
        boolean amountNumberOnle = Validation.textFieldNotEmpty(amount, "لا يقبل الا ارقام فقط");
        boolean fromStatus = Validation.textFieldNotEmpty(expensessTo, "ادخل جهة الصادر ");
        boolean amountTextStatus = Validation.textFieldNotEmpty(amountText, "ادخل المبلغ كتابة");
        boolean cashOrChequeStatus = Validation.textFieldNotEmpty(cashOrCheque, "ادخل نقدا ار رقم الشيك ");

        if (revenuesdataStatus && clauseStatus && amountStatus && amountNumberOnle && fromStatus && amountTextStatus && cashOrChequeStatus) {
            try {
                DatabaseAccess.updat(tableName, fieldName, data, "EXPENSESID = '"+expensesId+"'");
                BalanceController.decrementBalance(tableAmount);
                BalanceController.incrementBalance(Float.parseFloat(amount.getText()));
                refreshExpensesTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void deleteData(MouseEvent event) {
         try {
            String tableName = "expenses";
            DatabaseAccess.delete(tableName, "EXPENSESID = '"+expensesId+"'");
            BalanceController.incrementBalance(tableAmount);
            refreshExpensesTable();
            clearData();
        } catch (IOException ex) {
            Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void printCatchReceipt(ActionEvent event) {
    }

    private void expensesTableView() {
        try {
            balanceId = BalanceController.getBalanceId();
            ResultSet rs = DatabaseAccess.select("expenses", "BALANCEID = '" + balanceId + "'");
            while (rs.next()) {
                expenseslist.add(new ExpensesModel(
                        rs.getString("EXPENSESID"),
                        rs.getString("EXPENSESDATE"),
                        rs.getString("CLAUSE"),
                        rs.getString("AMOUNT"),
                        rs.getString("EXPENSESTO")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        expensesid_col.setCellValueFactory(new PropertyValueFactory<>("expensesid"));
        expensesdata_col.setCellValueFactory(new PropertyValueFactory<>("expensesdata"));
        clause_col.setCellValueFactory(new PropertyValueFactory<>("clause"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        expensesTo_col.setCellValueFactory(new PropertyValueFactory<>("expensesto"));

        totalbalance.setText(Float.toString(BalanceController.getTotalBalance()));
        totalExpenses.setText(Float.toString(BalanceController.getTotalExpenses()));

        expensesTable.setItems(expenseslist);
    }

    private void refreshExpensesTable() {
        expenseslist.clear();
        expensesTableView();
    }

    private void clearData() {
        amount.setText("");
        expensessTo.setText("");
        expensesdata.setValue(LocalDate.now());
        clause.setValue("");
        amountText.setText("");
        cashOrCheque.setText("");
        bankName.setText("");
    }

    private void getRowData(TableView table) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<ExpensesModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    try {
                        ResultSet rs = DatabaseAccess.select("expenses", "EXPENSESID='" + list.get(0).getExpensesid()+ "'");
                        expensesdata.setValue(LocalDate.parse(list.get(0).getExpensesdata()));
                        clause.setValue(list.get(0).getClause());
                        amount.setText(list.get(0).getAmount());
                        expensessTo.setText(list.get(0).getExpensesto());
                        tableAmount = Float.parseFloat(list.get(0).getAmount());
                        expensesId = list.get(0).getExpensesid();
                        if (rs.next()) {
                            amountText.setText(rs.getString("AMOUNTTEXT"));
                            cashOrCheque.setText(rs.getString("CASHORCHEQUENO"));
                            bankName.setText(rs.getString("BANKNAME"));
                        }
                    } catch (IOException | SQLException ex) {
                        Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

}

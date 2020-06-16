package Controlleres;

import Messages.Controllers.ShowMessage;
import Models.BalanceModel;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BudgetController implements Initializable {

    @FXML
    private TextField fiscalyearname;
    @FXML
    private TableView<BalanceModel> balance;
    @FXML
    private TableColumn<?, ?> fiscalyearname_col;
    @FXML
    private TableColumn<?, ?> year_col;
    @FXML
    private TableColumn<?, ?> currentbalance_col;
    @FXML
    private TableColumn<?, ?> carryoverbalance_col;
    @FXML
    private TableColumn<?, ?> totalbalane_col;
    ObservableList<BalanceModel> balancelist = FXCollections.observableArrayList();
    String year = null;
    String userId = null;
    @FXML
    private JFXDatePicker startDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshbalanceTable();
        getRowData(balance);
        startDate.setValue(LocalDate.now());
//        startDate.setChronology(HijrahChronology.INSTANCE);

    }
    /*`FISCALYEARNAME`,`FISCALYEAR`,`CURRENTBALANCE`,`CARRYOVERBALANCE`,`TOTALBALANCE`*/

    @FXML
    private void creatAccount(ActionEvent event) {
        String tableName = "balance";
        String fieldName = "`FISCALYEARNAME`,`FISCALYEARSTARTDATE`";
        String[] data = {fiscalyearname.getText(), startDate.getValue().toString()};
        String valuenumbers = "?,?";
        boolean existing = BalanceController.isBalanceExisting("يجب اغلاق الحساب الحالي قبل انشاء الحساب");
        boolean fiscalyearnameStatus = Validation.textFieldNotEmpty(fiscalyearname, "ادخل مسمى السنة المالية");
        if (existing && fiscalyearnameStatus) {
            try {
                int lastid = DatabaseAccess.insert(tableName, fieldName, valuenumbers, data);
                String[] data2 = {Integer.toString(lastid)};
                DatabaseAccess.insert("bankaccount", "`BALANCEID`", "?", data2);
                refreshbalanceTable();
            } catch (IOException ex) {
                Logger.getLogger(BudgetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void closeYearBudgetAccount(ActionEvent event) throws IOException {
        if (UserPowersController.getPower(userId, "105")) {
            boolean existing = BalanceController.isNewBalanceExisting("لا يوجد حساب جديد الرجاء انشاء حساب");
            if (existing) {
                BalanceController.closeBalance();
                refreshbalanceTable();
            }
        } else {
            ShowMessage showMessage = new ShowMessage();
            showMessage.error("ليس لديك صلاحية لاغلاق الحساب");
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        String tableName = "balance";
        String fieldName = "`FISCALYEARNAME`=?,`FISCALYEARSTARTDATE`=?";
        String[] data = {fiscalyearname.getText(), startDate.getValue().toString()};
        boolean fiscalyearnameStatus = Validation.textFieldNotEmpty(fiscalyearname, "ادخل مسمى السنة المالية");

        if (fiscalyearnameStatus) {
            try {
                DatabaseAccess.updat(tableName, fieldName, data, "STATUSE ='opened'");
                refreshbalanceTable();
            } catch (IOException ex) {
                Logger.getLogger(BudgetController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void balanceTableView() {
        try {
            ResultSet rs = DatabaseAccess.select("balance");

            while (rs.next()) {
                balancelist.add(new BalanceModel(
                        rs.getString("FISCALYEARNAME"),
                        rs.getString("FISCALYEARSTARTDATE"),
                        rs.getInt("CURRENTBALANCE"),
                        rs.getInt("CARRYOVERBALANCE"),
                        rs.getInt("TOTALBALANCE")
                ));
            }

            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        fiscalyearname_col.setCellValueFactory(new PropertyValueFactory<>("fiscalyearname"));
        year_col.setCellValueFactory(new PropertyValueFactory<>("year"));
        currentbalance_col.setCellValueFactory(new PropertyValueFactory<>("currentbalance"));
        carryoverbalance_col.setCellValueFactory(new PropertyValueFactory<>("carryoverbalance"));
        totalbalane_col.setCellValueFactory(new PropertyValueFactory<>("totalbalane"));

        balance.setItems(balancelist);
    }

    private void refreshbalanceTable() {
        balancelist.clear();
        balanceTableView();
    }

    private void getRowData(TableView table) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<BalanceModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    year = list.get(0).getYear();
                    fiscalyearname.setText(list.get(0).getFiscalyearname());
                }
            }
        });
    }

    private void clearData() {
//        amount.setText("");
//        from.setText("");
//        revenuesdata.setValue(LocalDate.now());
//        clause.setValue("");
//        accounttype.setValue("");
    }

    public void setUserId(String userid) {
        this.userId = userid;
    }

}

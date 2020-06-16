package Controlleres;

import Messages.Controllers.ShowMessage;
import Models.ExpensesModel;
import Models.LoansModel;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import static java.time.LocalDate.from;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class LoanController implements Initializable {

    @FXML
    private DatePicker loansdate;
    @FXML
    private TextField loansamount;
    @FXML
    private TextField details;
    @FXML
    private TextField notes_;
    @FXML
    private TextField paymentamount;
    @FXML
    private Label totalOfloans;
    @FXML
    private Label totalOfPaymentAmount;
    @FXML
    private Label totalOfremainingAmount;
    @FXML
    private TableView<LoansModel> loansTabel;
    @FXML
    private TableColumn<?, ?> loansid_col;
    @FXML
    private TableColumn<?, ?> loansdate_col;
    @FXML
    private TableColumn<?, ?> loansamount_col;
    @FXML
    private TableColumn<?, ?> details_col;
    @FXML
    private TableColumn<?, ?> paymentamount_col;
    @FXML
    private TableColumn<?, ?> remainingamount_col;
    @FXML
    private TableColumn<?, ?> notes_col;
    String balanceId = null;

    ObservableList<LoansModel> loanslist = FXCollections.observableArrayList();
    String loansId = null;
    float oldPayment = 0;
    float loanAmount = 0;
    float paymentAmount = 0;
    float reminingAmount = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshLoansTable();
        getRowData(loansTabel);
    }

    @FXML
    private void saveData(ActionEvent event) {
        String tableName = "loans";
        balanceId = BalanceController.getBalanceId();
        /*`LOANSID`,`LOANSDATE`,`LOANSAMOUNT`,`DETAILS`,`PAYMENTAMOUNT`,`REMAININGAMOUNT`,`BALANCEID`*/
        String fieldName = "`LOANSDATE`,`LOANSAMOUNT`,`DETAILS`,`REMAININGAMOUNT`,`NOTES`,`BALANCEID`";
        String[] data = {loansdate.getValue().toString(), loansamount.getText(), details.getText(), loansamount.getText(), notes_.getText(), balanceId};
        String valuenumbers = "?,?,?,?,?,?";

        String extableName = "expenses";
        String exfieldName = "`EXPENSESDATE`,`CLAUSE`,`AMOUNT`,`EXPENSESTO`,`BALANCEID`";
        String[] exdata = {loansdate.getValue().toString(), "عهدة", loansamount.getText(), details.getText(), balanceId};
        String exvaluenumbers = "?,?,?,?,?";

        boolean loansdateStatus = Validation.datePickerNotEmpty(loansdate, "ادخل التاريخ");
        boolean loansamountStatus = Validation.textFieldNotEmpty(loansamount, "ادخل مبلغ العهدة");
        boolean loansamountNumberOnle = Validation.textFieldNotEmpty(loansamount, "لا يقبل الا ارقام فقط");
        boolean detailsStatus = Validation.textFieldNotEmpty(details, "ادخل بيان العهدة ");

        if (loansdateStatus && loansamountStatus && loansamountNumberOnle && loansamountNumberOnle && detailsStatus) {
            try {
                int lastLoansId = DatabaseAccess.insert(tableName, fieldName, valuenumbers, data);
                int lastexpensesId = DatabaseAccess.insert(extableName, exfieldName, exvaluenumbers, exdata);
                String[] idesData = {Integer.toString(lastLoansId), Integer.toString(lastexpensesId)};
                DatabaseAccess.insert("expensesopration", "`LOANSID`,`EXPENSESID`", "?,?", idesData);
                BalanceController.decrementBalance(Float.parseFloat(loansamount.getText()));
                refreshLoansTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void editData(ActionEvent event) {
        String tableName = "loans";
        String fieldName = "`LOANSDATE`=?,`LOANSAMOUNT`=?,`DETAILS`=?,`REMAININGAMOUNT`=?,`NOTES`=?";
        float getremingAmount = Float.parseFloat(loansamount.getText()) - oldPayment ;
        String rmingAmount = Float.toString(getremingAmount);
        System.out.println(rmingAmount);
        String[] data = {loansdate.getValue().toString(), loansamount.getText(), details.getText(),rmingAmount , notes_.getText()};

        String extableName = "expenses";
        String exfieldName = "`EXPENSESDATE`=?,`AMOUNT`=?,`EXPENSESTO`=?";
        String[] exdata = {loansdate.getValue().toString(), loansamount.getText(), details.getText()};

        boolean loansdateStatus = Validation.datePickerNotEmpty(loansdate, "ادخل التاريخ");
        boolean loansamountStatus = Validation.textFieldNotEmpty(loansamount, "ادخل مبلغ العهدة");
        boolean loansamountNumberOnle = Validation.textFieldNotEmpty(loansamount, "لا يقبل الا ارقام فقط");
        boolean detailsStatus = Validation.textFieldNotEmpty(details, "ادخل بيان العهدة ");

        if (loansdateStatus && loansamountStatus && loansamountNumberOnle && loansamountNumberOnle && detailsStatus) {
            try {
                DatabaseAccess.updat(tableName, fieldName, data, "LOANSID='" + loansId + "'");
                DatabaseAccess.updat(extableName, exfieldName, exdata, "EXPENSESID = '" + getExpensesId(loansId) + "'");
                BalanceController.incrementBalance(loanAmount);
                BalanceController.decrementBalance(Float.parseFloat(loansamount.getText()));
                refreshLoansTable();
                clearData();
            } catch (IOException ex) {
                Logger.getLogger(RevenuesController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private String getExpensesId(String loanId) {
        String expensesId = null;
        try {
            ResultSet rs = DatabaseAccess.select("expensesopration", "LOANSID='" + loanId + "'");
            if (rs.next()) {
                expensesId = rs.getString("EXPENSESID");
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return expensesId;
    }

    private String[] getRevenuesId(String loanId) {
        String[] expensesId = null;
        try {
            ResultSet rs = DatabaseAccess.select("paymentopration", "LOANSID='" + loanId + "'");

            while (rs.next()) {
                expensesId[0] = rs.getString("EXPENSESID");
            }

        } catch (IOException | SQLException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return expensesId;
    }

    @FXML
    private void deleteData(ActionEvent event) {
        try {
            DatabaseAccess.delete("loans", "LOANSID='" + loansId + "'","سوف يتم حذف سجل العهدة هل تريد المتابعة");
            BalanceController.incrementBalance(reminingAmount);
            DatabaseAccess.delete("expenses", "EXPENSESID = '" + getExpensesId(loansId) + "'","سوف يتم حذف سجل الصرف هل تريد المتابعة");
            DatabaseAccess.deletem("DELETE paymentopration, revenues FROM paymentopration INNER JOIN revenues ON paymentopration.REVENUESID = revenues.REVENUESID WHERE  paymentopration.LOANSID='" + loansId + "'","سوف يتم حذف سجل السداد هل تريد المتابعة");
            refreshLoansTable();
        } catch (IOException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void payment(ActionEvent event) {
        String tableName = "loans";
        float newPayment = 0;
        float newRemaining = 0;
        String fieldName = "`PAYMENTAMOUNT` = ?,`REMAININGAMOUNT`= ? ";
        boolean paymentamountStatus = Validation.textFieldNotEmpty(paymentamount, "ادخل ميلغ السداد");

        if (paymentamountStatus) {
            newPayment = oldPayment + Float.parseFloat(paymentamount.getText());
            newRemaining = loanAmount - newPayment;
        }

        String[] data = {Float.toString(newPayment), Float.toString(newRemaining)};

        String retableName = "revenues";
        balanceId = BalanceController.getBalanceId();
        String refieldName = "`REVENUESDATE`,`CLAUSE`,`AMOUNT`,`FROM`,`BALANCEID`";
        String[] redata = {LocalDate.now().toString(), "تسديد عهدة", paymentamount.getText(), "العهد", balanceId};
        String revaluenumbers = "?,?,?,?,?";

        try {
            boolean loansIdStatus = Validation.stringNotNull(loansId, "اختر السجل من الجدول");

            if (newRemaining < 0) {
                ShowMessage showMessage = new ShowMessage();
                showMessage.error("تم سداد كامل العهدة او المبلغ اكثر من المبلغ المطلوب");
            } else {
                if (loansIdStatus && paymentamountStatus) {
                    DatabaseAccess.updat(tableName, fieldName, data, "LOANSID='" + loansId + "'");
                    BalanceController.incrementBalance(Float.parseFloat(paymentamount.getText()));
                    int lastRevenuesId = DatabaseAccess.insert(retableName, refieldName, revaluenumbers, redata);
                    String[] idesData = {loansId, Integer.toString(lastRevenuesId)};
                    DatabaseAccess.insert("paymentopration", "`LOANSID`,`REVENUESID`", "?,?", idesData);
                    refreshLoansTable();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(LoanController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editPaymentAmount(ActionEvent event) {
    }

    private void loansTableView() {
        try {
            balanceId = BalanceController.getBalanceId();
            ResultSet rs = DatabaseAccess.select("loans", "BALANCEID = '" + balanceId + "'");
            while (rs.next()) {

                loanslist.add(new LoansModel(
                        rs.getString("LOANSID"),
                        rs.getString("LOANSDATE"),
                        rs.getString("LOANSAMOUNT"),
                        rs.getString("DETAILS"),
                        rs.getString("PAYMENTAMOUNT"),
                        rs.getString("REMAININGAMOUNT"),
                        rs.getString("NOTES")
                ));
            }
            rs.close();
        } catch (SQLException | IOException ex) {
            Validation.showAlert("خطاء", (SQLException) ex);
        }
        loansid_col.setCellValueFactory(new PropertyValueFactory<>("loansid"));
        loansdate_col.setCellValueFactory(new PropertyValueFactory<>("loansdate"));
        loansamount_col.setCellValueFactory(new PropertyValueFactory<>("loansamount"));
        details_col.setCellValueFactory(new PropertyValueFactory<>("details"));
        paymentamount_col.setCellValueFactory(new PropertyValueFactory<>("paymentamount"));
        remainingamount_col.setCellValueFactory(new PropertyValueFactory<>("remainingamount"));
        notes_col.setCellValueFactory(new PropertyValueFactory<>("notes"));

        totalOfloans.setText(Float.toString(BalanceController.getTotalOfloans()));
        totalOfPaymentAmount.setText(Float.toString(BalanceController.getTotalOfPaymentAmount()));
        totalOfremainingAmount.setText(Float.toString(BalanceController.getTotalOfremainingAmount()));

        loansTabel.setItems(loanslist);
    }

    private void refreshLoansTable() {
        loanslist.clear();
        loansTableView();
    }

    private void clearData() {
        loansdate.setValue(LocalDate.now());
        loansamount.setText("");
        details.setText("");
        loansamount.setText("");
        notes_.setText("");
    }

    private void getRowData(TableView table) {
        table.setOnMouseClicked(new EventHandler() {
            @Override
            public void handle(Event event) {
                ObservableList<LoansModel> list = FXCollections.observableArrayList();
                list = table.getSelectionModel().getSelectedItems();
                if (list.isEmpty()) {
                    Validation.showAlert("", "لاتوجد بيانات");
                } else {
                    loansdate.setValue(LocalDate.parse(list.get(0).getLoansdate()));
                    loansamount.setText(list.get(0).getLoansamount());
                    details.setText(list.get(0).getDetails());
                    notes_.setText(list.get(0).getNotes());
                    loanAmount = Float.parseFloat(list.get(0).getLoansamount());
                    oldPayment = Float.parseFloat(list.get(0).getPaymentamount());
                    reminingAmount = Float.parseFloat(list.get(0).getRemainingamount());
                    loansId = list.get(0).getLoansid();
                }
            }
        });
    }

}

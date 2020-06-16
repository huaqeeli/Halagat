
package Models;


public class ExpensesModel {
    String expensesid, expensesdata, clause, amount, expensesto;

    public ExpensesModel(String expensesid, String expensesdata, String clause, String amount, String expensesto) {
        this.expensesid = expensesid;
        this.expensesdata = expensesdata;
        this.clause = clause;
        this.amount = amount;
        this.expensesto = expensesto;
    }

    public String getExpensesid() {
        return expensesid;
    }

    public void setExpensesid(String expensesid) {
        this.expensesid = expensesid;
    }

    public String getExpensesdata() {
        return expensesdata;
    }

    public void setExpensesdata(String expensesdata) {
        this.expensesdata = expensesdata;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getExpensesto() {
        return expensesto;
    }

    public void setExpensesto(String expensesto) {
        this.expensesto = expensesto;
    }
    
}


package Models;

/*loansid,loansdate,loansamount,details,paymentamount,remainingamount ,notes*/
public class LoansModel {
    String loansid,loansdate,loansamount,details,paymentamount,remainingamount ,notes;

    public LoansModel(String loansid, String loansdate, String loansamount, String details, String paymentamount, String remainingamount, String notes) {
        this.loansid = loansid;
        this.loansdate = loansdate;
        this.loansamount = loansamount;
        this.details = details;
        this.paymentamount = paymentamount;
        this.remainingamount = remainingamount;
        this.notes = notes;
    }

    public String getLoansid() {
        return loansid;
    }

    public void setLoansid(String loansid) {
        this.loansid = loansid;
    }

    public String getLoansdate() {
        return loansdate;
    }

    public void setLoansdate(String loansdate) {
        this.loansdate = loansdate;
    }

    public String getLoansamount() {
        return loansamount;
    }

    public void setLoansamount(String loansamount) {
        this.loansamount = loansamount;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPaymentamount() {
        return paymentamount;
    }

    public void setPaymentamount(String paymentamount) {
        this.paymentamount = paymentamount;
    }

    public String getRemainingamount() {
        return remainingamount;
    }

    public void setRemainingamount(String remainingamount) {
        this.remainingamount = remainingamount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    
}

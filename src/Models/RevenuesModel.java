package Models;

public class RevenuesModel {

    String revenuesid, revenuesdata, clause, amount, from;

    public RevenuesModel(String revenuesid, String revenuesdata, String clause, String amount, String from) {
        this.revenuesid = revenuesid;
        this.revenuesdata = revenuesdata;
        this.clause = clause;
        this.amount = amount;
        this.from = from;
    }

    

    public String getRevenuesid() {
        return revenuesid;
    }

    public void setRevenuesid(String revenuesid) {
        this.revenuesid = revenuesid;
    }

    public String getRevenuesdata() {
        return revenuesdata;
    }

    public void setRevenuesdata(String revenuesdata) {
        this.revenuesdata = revenuesdata;
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

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}

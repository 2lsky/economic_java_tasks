import java.util.Calendar;

public class paymentTableConstructor {
    private final Double credit;
    private final Double annualRate;
    private final Calendar initDate;
    private final Integer loanTerm;
    private final Integer payDay;

    public Double getCredit() {
        return credit;
    }

    public Double getAnnualRate() {
        return annualRate;
    }

    public Calendar getInitDate() {
        return initDate;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public Integer getPayDay() {
        return payDay;
    }

    public paymentTableConstructor(Double credit, Double annualRate, Calendar initDate, Integer loanTerm, Integer payDay) {
        this.credit = credit;
        this.annualRate = annualRate;
        this.initDate = initDate;
        this.loanTerm = loanTerm;
        this.payDay = payDay;
    }
}

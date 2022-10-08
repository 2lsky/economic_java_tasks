import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.math3.util.Precision.round;

public class paymentTable{
    private Integer leapYear(int year)
    {

        if ((year % 4 == 0) && year % 100 != 0)
        {
            return 366;
        }
        else if ((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0))
        {
            return 366;
        }
        else
        {
            return 365;
        }
    }
    private Calendar getFirstPayDay(Calendar initDate, Integer payDay){
        if (initDate.get(Calendar.DAY_OF_MONTH) <= payDay) {
            return new GregorianCalendar(initDate.get(Calendar.YEAR),
                    initDate.get(Calendar.MONTH), payDay);
        }else {
            Calendar firstPayDay = new GregorianCalendar(initDate.get(Calendar.YEAR),
                    initDate.get(Calendar.MONTH), payDay);
            firstPayDay.add(Calendar.MONTH, 1);
            return firstPayDay;
        }
    }
    private Object[] payDaysAndPeriods(Calendar initDate, Integer payDay, Integer loanTerm){
        ArrayList<Calendar> payDays = new ArrayList<>();
        ArrayList<Long> periods = new ArrayList<Long>();
        Calendar currentDay = this.getFirstPayDay(initDate, payDay);
        payDays.add(new GregorianCalendar(currentDay.get(Calendar.YEAR),
                currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DAY_OF_MONTH)));
        for(int i = 0; i < loanTerm - 1; i++){
            currentDay.add(Calendar.MONTH, 1);
            payDays.add(new GregorianCalendar(currentDay.get(Calendar.YEAR),
                    currentDay.get(Calendar.MONTH), currentDay.get(Calendar.DAY_OF_MONTH)));
        }
        periods.add(TimeUnit.MILLISECONDS.toDays(Math.abs(payDays.get(0).getTimeInMillis() - initDate.getTimeInMillis())));
        for (int j = 0; j < payDays.toArray().length - 1; j++){
            long end = payDays.get(j+1).getTimeInMillis();
            long start = payDays.get(j).getTimeInMillis();
            periods.add(TimeUnit.MILLISECONDS.toDays(Math.abs(end - start)));

        }
        return new Object[]{payDays, periods};
    }
    public Object[] payments(Double credit,
                         Double annualRate,
                         Calendar initDate,
                         Integer loanTerm,
                         Integer payDay){
        ArrayList<String> paymentsForAnnuity = new ArrayList<String>();
        ArrayList<String> paymentsForDiff = new ArrayList<String>();
        ArrayList<String> percentsForAnnuity = new ArrayList<String>();
        ArrayList<String> percentsForDiff = new ArrayList<String>();
        ArrayList<String> debtAnnuity = new ArrayList<String>();
        ArrayList<String> debtDiff = new ArrayList<String>();
        ArrayList<String> paymentsDebtDiff = new ArrayList<String>();
        ArrayList<String> paymentsDebtAnnuity = new ArrayList<String>();
        ArrayList<String> payDaysString = new ArrayList<String>();
        ArrayList<String> index= new ArrayList<String>();
        ArrayList<String> periodsString = new ArrayList<String>();
        Object[] paydaysAndPeriods = this.payDaysAndPeriods(initDate, payDay, loanTerm);
        ArrayList<Calendar> payDays = (ArrayList<Calendar>) paydaysAndPeriods[0];
        ArrayList<Long> periods = (ArrayList<Long>) paydaysAndPeriods[1];
        String[][] tableAnnuity = new String[payDays.toArray().length][7];
        String[][] tableDiff = new String[payDays.toArray().length][7];
        debtAnnuity.add(String.valueOf(round(credit,2)));
        debtDiff.add(String.valueOf(round(credit,2)));
        double percentInMonth = annualRate/(100*12.0);
        if(periods.toArray().length !=0){
            Long firstPeriod = periods.get(0);
            Calendar firstPayDay = payDays.get(0);
            double leapYearInitDate = this.leapYear(initDate.get(Calendar.YEAR)) * 1.0;
            double leapYearFirstPayDay = this.leapYear(firstPayDay.get(Calendar.YEAR)) * 1.0;
            if (leapYearInitDate == leapYearFirstPayDay){
                String firstPayString = String.valueOf(round(credit * (Math.pow((1 + annualRate/(100.0*leapYearInitDate)), firstPeriod) - 1),
                        2));
                paymentsForAnnuity.add(firstPayString);
                paymentsForDiff.add(firstPayString);
                percentsForDiff.add(firstPayString);
                percentsForAnnuity.add(firstPayString);
                paymentsDebtAnnuity.add("0.00");
                paymentsDebtDiff.add("0.00");
            }else{
                long firstTerm = 31 - initDate.get(Calendar.DAY_OF_MONTH);
                long secondTerm = firstPeriod - firstTerm;
                String firstPayString = String.valueOf(round(credit * (Math.pow((1 + annualRate*firstTerm/(100.0*leapYearInitDate) + annualRate*secondTerm/(100.0*leapYearFirstPayDay)), firstPeriod) - 1), 2));
                paymentsForAnnuity.add(firstPayString);
                paymentsForDiff.add(firstPayString);
                percentsForDiff.add(firstPayString);
                percentsForAnnuity.add(firstPayString);
                paymentsDebtAnnuity.add("0.00");
                paymentsDebtDiff.add("0.00");
            }
            for (int k = 0; k < payDays.toArray().length; k ++){
                index.add(String.valueOf(k + 1));
                String year = String.valueOf(payDays.get(k).get(Calendar.YEAR));
                String month = String.valueOf(1 + payDays.get(k).get(Calendar.MONTH));
                String day = String.valueOf(payDays.get(k).get(Calendar.DAY_OF_MONTH));
                payDaysString.add(year+'.'+month+'.'+day);
                periodsString.add(String.valueOf(periods.get(k)));
            }
            double a = Math.pow(1 + percentInMonth, payDays.toArray().length - 1);
            double coeffAnnuity = percentInMonth*a/(a - 1.0);
            double constPayAnnuity = credit * coeffAnnuity;
            double constPayDiff = credit/(1.0*(payDays.toArray().length - 1));
            double debtSumAnnuity = credit;
            double debtSumDiff = credit;
            for (int i = 0; i < payDays.toArray().length - 1; i++){
                double percent = debtSumAnnuity * percentInMonth;
                percentsForAnnuity.add(String.valueOf(round(percent, 2)));
                paymentsDebtAnnuity.add(String.valueOf(round(constPayAnnuity - percent, 2)));
                debtSumAnnuity = debtSumAnnuity - (constPayAnnuity - percent);
                debtAnnuity.add(String.valueOf(round(debtSumAnnuity, 2)));
                paymentsForAnnuity.add(String.valueOf(round(constPayAnnuity, 2)));
            }
            String[][] dataAnnuity = new String[][]{index.toArray(new String[0]), periodsString.toArray(new String[0]),
                    payDaysString.toArray(new String[0]), paymentsForAnnuity.toArray(new String[0]),
                    percentsForAnnuity.toArray(new String[0]), paymentsDebtAnnuity.toArray(new String[0]), debtAnnuity.toArray(new String[0])};
            for (int i = 0; i < payDays.toArray().length - 1; i++){
                double percent = debtSumDiff * percentInMonth;
                percentsForDiff.add(String.valueOf(round(percent, 2)));
                paymentsDebtDiff.add(String.valueOf(round(constPayDiff, 2)));
                debtSumDiff = debtSumDiff - constPayDiff;
                debtDiff.add(String.valueOf(round(debtSumDiff,2)));
                paymentsForDiff.add(String.valueOf(round(constPayDiff + percent, 2)));
            }
            String [][] dataDiff = new String[][]{index.toArray(new String[0]), periodsString.toArray(new String[0]),
                    payDaysString.toArray(new String[0]), paymentsForDiff.toArray(new String[0]),
                    percentsForDiff.toArray(new String[0]), paymentsDebtDiff.toArray(new String[0]), debtDiff.toArray(new String[0])};
            for (int j = 0; j < 7; j ++){
                for (int i = 0; i < payDays.toArray().length; i ++){
                    tableAnnuity[i][j] = dataAnnuity[j][i];
                    tableDiff[i][j] = dataDiff[j][i];
                }
            }
        }
        return new Object[]{tableAnnuity, tableDiff};
    }
}


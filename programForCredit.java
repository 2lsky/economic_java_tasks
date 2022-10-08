import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import org.apache.logging.log4j.Logger;


public class programForCredit {
    private XSSFWorkbook chooseFile() {
        JFileChooser fileopen = new JFileChooser();
        String[] extensions = {"xlsx", "xls", "csv"};
        FileFilter filter = new FileNameExtensionFilter("excel_files", extensions);
        fileopen.addChoosableFileFilter(filter);
        int ret = fileopen.showDialog(null, "Открыть файл");
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            try (FileInputStream excelFile = new FileInputStream(file)) {
                return new XSSFWorkbook(excelFile);
            } catch (IOException e) {
                return null;
            }
        } else {
            return null;
        }
    }
    private Map<String, Object> getParams(){
        XSSFWorkbook wb = this.chooseFile();
        Map<String, Object> map = new HashMap<String, Object>();
        if (wb != null) {
            XSSFSheet sheet = wb.getSheetAt(0);
            XSSFRow name_row = sheet.getRow(0);
            XSSFRow param_row = sheet.getRow(1);
            for(int i = 0; i < 5; i++){
                if (name_row.getCell(i).getStringCellValue().equals("Дата получения кредита")){
                    Date firstDay_date = param_row.getCell(i).getDateCellValue();
                    Calendar firstDay_cal = new GregorianCalendar();
                    firstDay_cal.setTime(firstDay_date);
                    map.put("Дата получения кредита", firstDay_cal);
                }else if (name_row.getCell(i).getStringCellValue().equals("Кредит")){
                    Double main_debt = Double.parseDouble(String.valueOf(param_row.getCell(i)));
                    map.put("Кредит", main_debt);
                }else if (name_row.getCell(i).getStringCellValue().equals("Годовая процентная ставка")) {
                    Double yearPercent = Double.parseDouble(String.valueOf(param_row.getCell(i)));
                    map.put("Годовая процентная ставка", yearPercent);
                } else if (name_row.getCell(i).getStringCellValue().equals("Срок в месяцах")) {
                    Integer loanTerm = (int)Float.parseFloat(String.valueOf(param_row.getCell(i)));
                    map.put("Срок в месяцах", loanTerm);
                } else if (name_row.getCell(i).getStringCellValue().equals("Дата платежа в месяце")) {
                    int payDay = (int)Float.parseFloat(String.valueOf(param_row.getCell(i)));
                    map.put("Дата платежа в месяце", payDay);
                }
            }
        }
        return map;
    }
    public Object[] getOutputData(){
        Map<String, Object> params = this.getParams();
        paymentTableConstructor table = new paymentTableConstructor((Double) params.get("Кредит"),
                (Double) params.get("Годовая процентная ставка"),
                (Calendar) params.get("Дата получения кредита"),
                (Integer) params.get("Срок в месяцах"),
                (Integer) params.get("Дата платежа в месяце"));
        paymentTable tables = new paymentTable();
        Double credit = table.getCredit();
        Double rate = table.getAnnualRate();
        Calendar initDate = table.getInitDate();
        Integer loanTerm = table.getLoanTerm();
        Integer payDay = table.getPayDay();
        return tables.payments(credit, rate, initDate, loanTerm, payDay);
    }
}

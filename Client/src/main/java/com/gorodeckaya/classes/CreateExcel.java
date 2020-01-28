package com.gorodeckaya.classes;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateExcel {
    private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFFont font = workbook.createFont();
        font.setBold(true);
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    public static void createPayrollSheet(int month,int year) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Month payroll sheet");

        List<Employee> list = new ArrayList<>();

        List<Object> listE = Employee.loadEmployeeForTotal(month, year);

        for (Object object:listE) {
            list.add((Employee) object);
            //Employee employee = (Employee) object;
        }

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);


        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Месяц");
        cell.setCellStyle(style);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Год");
        cell.setCellStyle(style);

        cell = row.createCell(15, CellType.STRING);
        cell.setCellValue("Начисления по окладу");
        cell.setCellStyle(style);

        cell = row.createCell(16, CellType.STRING);
        cell.setCellValue("Доп начисления");
        cell.setCellStyle(style);

        cell = row.createCell(17, CellType.STRING);
        cell.setCellValue("Всего начислено");
        cell.setCellStyle(style);

        cell = row.createCell(18, CellType.STRING);
        cell.setCellValue("Удержания по налогам");
        cell.setCellStyle(style);

        cell = row.createCell(19, CellType.STRING);
        cell.setCellValue("Всего удержано");
        cell.setCellStyle(style);

        cell = row.createCell(20, CellType.STRING);
        cell.setCellValue("Итого");
        cell.setCellStyle(style);

        // Emp
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("№");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("ФИО");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Должность");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Отдел");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Ставка");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Раб. дни");
        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Оклад");
        cell.setCellStyle(style);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Начисления по окладу");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Доп начисления");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Всего начислено");
        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Удержания");
        cell.setCellStyle(style);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Всего удержано");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Итого");
        cell.setCellStyle(style);

        for (Employee emp : list) {
            rownum++;
            row = sheet.createRow(rownum);

            // EmpNo (A)
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(emp.getID());
            // EmpName (B)
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue(emp.getSurname()+" "+emp.getName()+" "+emp.getPatronymic());
            // Salary (C)
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue(emp.getPost().getName());
            // Grade (D)
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue(emp.getPost().getDepartment());
            // Bonus (E)
            cell = row.createCell(4, CellType.NUMERIC);
            cell.setCellValue(emp.getRate());

            cell = row.createCell(5, CellType.NUMERIC);
            cell.setCellValue(emp.getWasWorkedDays());

            cell = row.createCell(6, CellType.NUMERIC);
            cell.setCellValue(emp.getPost().getSalary());

            cell = row.createCell(7, CellType.NUMERIC);
            cell.setCellValue(emp.getPayrollList().get(0).getTotal().getSalaryAccruals());

            cell = row.createCell(8, CellType.NUMERIC);
            cell.setCellValue(emp.getPayrollList().get(0).getTotal().getOtherCharges());

            cell = row.createCell(9, CellType.NUMERIC);
            cell.setCellValue(emp.getPayrollList().get(0).getTotal().getAllCharges());

            cell = row.createCell(10, CellType.NUMERIC);
            cell.setCellValue(emp.getPayrollList().get(0).getTotal().getAllDeducation());

            cell = row.createCell(11, CellType.NUMERIC);
            cell.setCellValue(emp.getPayrollList().get(0).getTotal().getAllDeducation());

            cell = row.createCell(12, CellType.NUMERIC);
            cell.setCellValue(emp.getPayrollList().get(0).getTotal().getTotal());
        }
        List<Object> listTotal = new ArrayList<>();
        listTotal = Total.loadTotal(month, year);

        for (Object object:listTotal) {
            Total total1 = (Total) object;

            cell = row.createCell(13, CellType.NUMERIC);
            cell.setCellValue(month);

            cell = row.createCell(14, CellType.NUMERIC);
            cell.setCellValue(year);

            cell = row.createCell(15, CellType.NUMERIC);
            cell.setCellValue(total1.getSalaryAccruals());

            cell = row.createCell(16, CellType.NUMERIC);
            cell.setCellValue(total1.getOtherCharges());

            cell = row.createCell(17, CellType.NUMERIC);
            cell.setCellValue(total1.getAllCharges());

            cell = row.createCell(18, CellType.NUMERIC);
            cell.setCellValue(total1.getAllDeducation());

            cell = row.createCell(19, CellType.NUMERIC);
            cell.setCellValue(total1.getAllDeducation());

            cell = row.createCell(20, CellType.NUMERIC);
            cell.setCellValue(total1.getTotal());
        }

       // File file = new File("employee.xls");
//        file.getParentFile().mkdirs();

        FileOutputStream outFile = new FileOutputStream("employee"+month+"-"+year+".xls");
        workbook.write(outFile);
        //System.out.println("Created file: " + file.getAbsolutePath());

    }
    public static void createPayrollSheetForEmployee(int month, int year, int id) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Employees sheet");

        List<Employee> list = new ArrayList<>();

        List<Object> listE = Employee.loadEmployeeForTotal(month, year);

        for (Object object : listE) {
            list.add((Employee) object);
            //Employee employee = (Employee) object;
        }

        int rownum = 0;
        Cell cell;
        Row row;
        //
        HSSFCellStyle style = createStyleForTitle(workbook);

        row = sheet.createRow(rownum);


        cell = row.createCell(13, CellType.STRING);
        cell.setCellValue("Месяц");
        cell.setCellStyle(style);

        cell = row.createCell(14, CellType.STRING);
        cell.setCellValue("Год");
        cell.setCellStyle(style);


        // Emp
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("№");
        cell.setCellStyle(style);

        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("ФИО");
        cell.setCellStyle(style);

        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Должность");
        cell.setCellStyle(style);

        cell = row.createCell(3, CellType.STRING);
        cell.setCellValue("Отдел");
        cell.setCellStyle(style);

        cell = row.createCell(4, CellType.STRING);
        cell.setCellValue("Ставка");
        cell.setCellStyle(style);

        cell = row.createCell(5, CellType.STRING);
        cell.setCellValue("Раб. дни");
        cell.setCellStyle(style);

        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Оклад");
        cell.setCellStyle(style);

        cell = row.createCell(7, CellType.STRING);
        cell.setCellValue("Начисления по окладу");
        cell.setCellStyle(style);

        cell = row.createCell(8, CellType.STRING);
        cell.setCellValue("Доп начисления");
        cell.setCellStyle(style);

        cell = row.createCell(9, CellType.STRING);
        cell.setCellValue("Всего начислено");
        cell.setCellStyle(style);

        cell = row.createCell(10, CellType.STRING);
        cell.setCellValue("Удержания");
        cell.setCellStyle(style);

        cell = row.createCell(11, CellType.STRING);
        cell.setCellValue("Всего удержано");
        cell.setCellStyle(style);

        cell = row.createCell(12, CellType.STRING);
        cell.setCellValue("Итого");
        cell.setCellStyle(style);

        for (Employee emp : list) {
            if (emp.getId() == id) {
                rownum++;
                row = sheet.createRow(rownum);

                // EmpNo (A)
                cell = row.createCell(0, CellType.STRING);
                cell.setCellValue(emp.getID());
                // EmpName (B)
                cell = row.createCell(1, CellType.STRING);
                cell.setCellValue(emp.getSurname() + " " + emp.getName() + " " + emp.getPatronymic());
                // Salary (C)
                cell = row.createCell(2, CellType.STRING);
                cell.setCellValue(emp.getPost().getName());
                // Grade (D)
                cell = row.createCell(3, CellType.STRING);
                cell.setCellValue(emp.getPost().getDepartment());
                // Bonus (E)
                cell = row.createCell(4, CellType.NUMERIC);
                cell.setCellValue(emp.getRate());

                cell = row.createCell(5, CellType.NUMERIC);
                cell.setCellValue(emp.getWasWorkedDays());

                cell = row.createCell(6, CellType.NUMERIC);
                cell.setCellValue(emp.getPost().getSalary());

                cell = row.createCell(7, CellType.NUMERIC);
                cell.setCellValue(emp.getPayrollList().get(0).getTotal().getSalaryAccruals());

                cell = row.createCell(8, CellType.NUMERIC);
                cell.setCellValue(emp.getPayrollList().get(0).getTotal().getOtherCharges());

                cell = row.createCell(9, CellType.NUMERIC);
                cell.setCellValue(emp.getPayrollList().get(0).getTotal().getAllCharges());

                cell = row.createCell(10, CellType.NUMERIC);
                cell.setCellValue(emp.getPayrollList().get(0).getTotal().getAllDeducation());

                cell = row.createCell(11, CellType.NUMERIC);
                cell.setCellValue(emp.getPayrollList().get(0).getTotal().getAllDeducation());

                cell = row.createCell(12, CellType.NUMERIC);
                cell.setCellValue(emp.getPayrollList().get(0).getTotal().getTotal());

                cell = row.createCell(13, CellType.NUMERIC);
                cell.setCellValue(month);

                cell = row.createCell(14, CellType.NUMERIC);
                cell.setCellValue(year);


                FileOutputStream outFile = new FileOutputStream(emp.getSurname() + emp.getName() + emp.getPatronymic() + month + "-" + year + ".xls");
                workbook.write(outFile);
            }
        }

    }
}

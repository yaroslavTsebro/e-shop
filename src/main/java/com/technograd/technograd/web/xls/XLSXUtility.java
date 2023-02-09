package com.technograd.technograd.web.xls;

import com.technograd.technograd.dao.entity.Condition;
import com.technograd.technograd.dao.entity.Intend;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class XLSXUtility {

    //private static final Logger logger = LogManager.getLogger(XLSXUtility.class.getName());
    public XLSXUtility() {}

    public void writeIntendsInXLS(List<Intend> intendList, String filePath, ResourceBundle rb) throws IOException, InvalidFormatException {
        File file = new File(filePath);
        Workbook workbook = new XSSFWorkbook();
        divideIntendsByConditionAndWriteIt(workbook, intendList, rb);
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }

    private void divideIntendsByConditionAndWriteIt(Workbook workbook, List<Intend> intendList,ResourceBundle rb){
        List<Intend> accepted = new ArrayList<>();
        List<Intend> completed = new ArrayList<>();
        List<Intend> denied = new ArrayList<>();
        List<Intend> turnedBack = new ArrayList<>();
        List<Intend> inWay = new ArrayList<>();

        for (Intend i: intendList) {
            if(i.getCondition().equals(Condition.ACCEPTED)){
                accepted.add(i);
            } else if (i.getCondition().equals(Condition.COMPLETED)) {
                completed.add(i);
            } else if (i.getCondition().equals(Condition.DENIED)) {
                denied.add(i);
            } else if (i.getCondition().equals(Condition.TURNED_BACK)) {
                turnedBack.add(i);
            } else if (i.getCondition().equals(Condition.IN_WAY)) {
                inWay.add(i);
            }
        }
        try{
            writeIntendByCondition(workbook, accepted, Condition.ACCEPTED, rb);
            writeIntendByCondition(workbook, completed, Condition.COMPLETED, rb);
            writeIntendByCondition(workbook, denied, Condition.DENIED, rb);
            writeIntendByCondition(workbook, turnedBack, Condition.TURNED_BACK, rb);
            writeIntendByCondition(workbook, inWay, Condition.IN_WAY, rb);
        } catch (Exception e){

        }
    }


    private void writeIntendByCondition (Workbook workbook, List<Intend> intendList, Condition condition,ResourceBundle rb){
        Sheet sheet = workbook.createSheet(condition.toString());
       //writeHeaderByCondition(sheet, condition);
        writeTableHeader(sheet,rb);
        writeBody(sheet, intendList);
    }

    private void writeBody(Sheet sheet, List<Intend> intendList) {
        for (int i = 0; i < 14; i++) {
            sheet.setColumnWidth(i, 25 * 256);
        }
        try{
            int size = intendList.size();
            int totalSize = size;
            for (Intend i :  intendList) {
                if(i.getListIntends() != null){
                    totalSize += i.getListIntends().size();
                } else{
                    intendList.remove(i);
                }
            }

            createRowsAndCells(sheet, 2, 2 + totalSize + 1, 0, 13);
            int baseRow = 3;
            for (int i = 0; i < intendList.size(); i++) {
                for (int j = 0; j < intendList.get(i).getListIntends().size(); j++) {
                    Row row = sheet.getRow(baseRow + i);
                    for (int k = 0; k < 13; k++) {
                        Cell cell = row.getCell(k);
                        Intend intend = intendList.get(i);
                        writeIntendInRow(sheet, cell.getRowIndex(), cell.getColumnIndex(), intend, j);
                    }
                    if(j + 1 != intendList.get(i).getListIntends().size()){
                        baseRow++;
                    }
                }
            }
            if(intendList.size() != 0){
                Cell cell = sheet.getRow(totalSize + 2).getCell(12);
                String formula= "SUM(M4:M" + (2+totalSize) + ")";
                cell.setCellFormula(formula);
            }


        } catch (Exception e){
         System.out.println(e);
        }
    }

    private void writeIntendInRow(Sheet sheet, int rowIndex, int cellIndex, Intend i, int liNum){
        switch (cellIndex){
            case 0:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getId());
                break;
            case 1:
                writeDate(sheet, i.getStartDate(), rowIndex, cellIndex);
                break;
            case 2:
                writeDate(sheet, i.getEndDate(), rowIndex, cellIndex);
                break;
            case 3:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getUserId());
                break;
            case 4:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getSupplierId());
                break;
            case 5:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getEmployeeId());
                break;
            case 6:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getSendingOrReceiving().toString());
                break;
            case 7:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getAddress());
                break;
            case 8:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getCondition().toString());
                break;
            case 9:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getListIntends().get(liNum).getId());
                break;
            case 10:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getListIntends().get(liNum).getCount());
                break;
            case 11:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getListIntends().get(liNum).getProductPrice().doubleValue());
                break;
            case 12:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue((i.getListIntends().get(liNum).getProductPrice()
                        .multiply(new BigDecimal(i.getListIntends().get(liNum).getCount()))).doubleValue());
                break;
        }
    }

    private void writeDate(Sheet sheet, Timestamp i, int rowIndex, int cellIndex){
        CreationHelper createHelper1 = sheet.getWorkbook().getCreationHelper();

        CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
        cellStyle1.setDataFormat(
                createHelper1.createDataFormat().getFormat("m/d/yy h:mm"));

        Timestamp value1 = i;
        Cell cell1 = sheet.getRow(rowIndex).getCell(cellIndex);
        cell1.setCellValue(value1);
        cell1.setCellStyle(cellStyle1);
    }
    private void writeHeaderByCondition(Sheet sheet, Condition condition){
        createRowsAndCells(sheet, 0, 1, 0, 12);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 12));
        Cell cell = sheet.getRow(0).getCell(0);
        CellStyle style = cell.getCellStyle();
        Font font = cell.getSheet().getWorkbook().createFont();
        font.setFontHeight((short) (15 * 20));
        style.setFont(font);
        cell.setCellValue(condition.toString());
        CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
    }
    private void createRowsAndCells(Sheet sheet,int firstRowIndex, int lastRowIndex,int firstCellIndex , int lastCellIndex){
        for(int i = firstRowIndex; i <= lastRowIndex; i++){
            Row row = sheet.createRow(i);
            for (int j = firstCellIndex; j <= lastCellIndex; j++){
                Cell cell =row.createCell(j);
            }
        }
    }

    private void writeTableHeader(Sheet sheet, ResourceBundle rb){
        createRowsAndCells(sheet,1, 2, 0, 12);
        final int ROW_INDEX = 1;
        for (int i = 0; i <= 12; i++) {
            switch (i){
                case 0:
                    String s = rb.getString("xls.header.id");
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(s);
                    break;
                case 1:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.start.date"));
                    break;
                case 2:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.end.date"));
                    break;
                case 3:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.user.id"));
                    break;
                case 4:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.supplier.id"));
                    break;
                case 5:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.employee.id"));
                    break;
                case 6:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.sending.or.receiving"));
                    break;
                case 7:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.address"));
                    break;
                case 8:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.condition"));
                    break;
                case 9:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.li.id"));
                    break;
                case 10:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.li.count"));
                    break;
                case 11:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.price"));
                    break;
                case 12:
                    sheet.getRow(ROW_INDEX).getCell(i).setCellValue(rb.getString("xls.header.sum"));
                    break;
            }
        }
    }

}

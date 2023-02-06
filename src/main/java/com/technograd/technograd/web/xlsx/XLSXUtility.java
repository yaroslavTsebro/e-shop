package com.technograd.technograd.web.xlsx;

import com.technograd.technograd.dao.IntendDAO;
import com.technograd.technograd.dao.entity.Condition;
import com.technograd.technograd.dao.entity.Intend;
import com.technograd.technograd.web.command.manager.characteristic.CreateCharacteristic;
import com.technograd.technograd.web.exeption.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class XLSXUtility {

    //private static final Logger logger = LogManager.getLogger(XLSXUtility.class.getName());
    public XLSXUtility() {}

    public void writeIntendsInXLS(List<Intend> intendList, String filePath, ResourceBundle rb) throws IOException, InvalidFormatException {
        File file = new File(filePath);
        Workbook workbook = new XSSFWorkbook();
        writeIntendByCondition(workbook, intendList, Condition.COMPLETED, (short) 32);
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }

    private void writeIntendByCondition (Workbook workbook, List<Intend> intendList, Condition condition, short color){
        Sheet sheet = workbook.createSheet(condition.toString());
        writeHeaderByCondition(sheet, condition, color);
        writeBody(sheet, intendList);
    }

    private void writeBody(Sheet sheet, List<Intend> intendList) {
        int size = intendList.size();
        createRowsAndCells(sheet, 2, 2 + size, 0, 13);
        for (int i = 2; i < 2 + size; i++) {
            Row row = sheet.getRow(i);
            for (int k = 0; k < intendList.get(i-2).getListIntends().size(); k++) {
                for (int j = 0; j <= 13; j++) {
                    Cell cell = row.getCell(j);
                    writeIntendInRow(sheet, i, j, intendList.get(i-2), k);
                }
            }
        }
    }

    private void writeIntendInRow(Sheet sheet, int rowIndex, int cellIndex, Intend i, int liNum){
        switch (cellIndex){
            case 0:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getId());
                break;
            case 1:
                writeDate(sheet, i, rowIndex, cellIndex);
                break;
            case 2:
                writeDate(sheet, i, rowIndex, cellIndex);
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
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getListIntends().get(liNum).getProduct().getCount());
                break;
            case 12:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue(i.getListIntends().get(liNum).getProductPrice().doubleValue());
                break;
            case 13:
                sheet.getRow(rowIndex).getCell(cellIndex).setCellValue((i.getListIntends().get(liNum).getProductPrice()
                        .multiply(new BigDecimal(i.getListIntends().get(liNum).getCount()))).doubleValue());
                break;
        }
    }

    private void writeDate(Sheet sheet, Intend i, int rowIndex, int cellIndex){
        CreationHelper createHelper1 = sheet.getWorkbook().getCreationHelper();

        CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
        cellStyle1.setDataFormat(
                createHelper1.createDataFormat().getFormat("m/d/yy h:mm"));

        Timestamp value1 = i.getStartDate();
        Cell cell1 = sheet.getRow(rowIndex).getCell(cellIndex);
        cell1.setCellValue(value1);
        cell1.setCellStyle(cellStyle1);
    }
    private void writeHeaderByCondition(Sheet sheet, Condition condition, short color){
        createRowsAndCells(sheet, 0, 1, 0, 13);
        sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, 13));
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

}

package com.GOTEL.SchoolCar.Service.IMPL;

import com.GOTEL.SchoolCar.Service.ExcelService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@Service
public class ExcelServiceIMPL implements ExcelService {
    @Override
    public List<List<String>> parseExcelFromMultiFile(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            List<List<String>> result = new ArrayList<>();
            Iterator<Row> rowIterator = sheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                List<String> rowData = new ArrayList<>();
                Iterator<Cell> cellIterator = row.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getCellType()) {
                        case STRING:
                            rowData.add(cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            DecimalFormat df = new DecimalFormat("0");
                            rowData.add(String.valueOf(df.format(cell.getNumericCellValue())));
                            break;
                        case BOOLEAN:
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case FORMULA:
                            switch (cell.getCachedFormulaResultType()) {
                                case STRING:
                                    rowData.add(cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    DecimalFormat df1 = new DecimalFormat("0");
                                    rowData.add(String.valueOf(df1.format(cell.getNumericCellValue())));
                                    break;
                                case BOOLEAN:
                                    rowData.add(String.valueOf(cell.getBooleanCellValue()));
                                    break;
                            }
                            break;
                    }
                }
                result.add(rowData);
            }
            return result;
        } catch (Exception e) {
            throw new Exception("文件格式错误");
        } finally {
            inputStream.close();
        }
    }
    public XSSFWorkbook createExcel(List<List<String>> data) {
        try {
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
            XSSFSheet xssfSheet = xssfWorkbook.createSheet();
            for (int i = 0; i < data.size(); i++) {
                XSSFRow row = xssfSheet.createRow(i);
                for (int i1 = 0; i1 < data.get(i).size(); i1++) {
                    XSSFCell cell = row.createCell(i1);
                    cell.setCellValue(data.get(i).get(i1));
                }
            }
            return xssfWorkbook;
        } catch (Exception e) {
            Logger.getLogger("gotel").info(e.getMessage());
        }
        return null;
    }
}

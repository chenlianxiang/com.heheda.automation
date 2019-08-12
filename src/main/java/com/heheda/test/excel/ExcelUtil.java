package com.heheda.test.excel;

import com.heheda.test.anno.DescAnno;
import com.heheda.test.domain.RequestBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @program: test
 * @description: 工具类
 * @author: clx
 * @create: 2019-08-10 22:50
 */

@Service
public class ExcelUtil {
    private static final String XLS="xls";
    private static final String XLSX="xlsx";


    public <T> List<T> readData(String path,int chartAt) throws Exception {
        return readData(path,chartAt,null);
    }

    public <T> List<T> readData(String path, int chartAt, T t) throws Exception {

        File file=new File(path);
        InputStream inputStream=new FileInputStream(file);
        Workbook workbook=null;
        if (file.getName().endsWith(XLS)){
            workbook=new HSSFWorkbook(inputStream);
        }
        else if (file.getName().endsWith(XLSX)){
            workbook=new XSSFWorkbook(inputStream);
        }
        else {
            throw new Exception("这不是一个Excel文件");
        }
        Sheet sheet=workbook.getSheetAt(chartAt);
        return readData(workbook,sheet,t);
    }


    public <T> List<T> readData(String path,String sheetName) throws Exception {
        return  readData(path,sheetName,null);
    }

    public <T> List<T> readData(String path, String sheetName, T t) throws Exception {

        File file=new File(path);
        InputStream inputStream=new FileInputStream(file);
        Workbook workbook=null;
        if (file.getName().endsWith(XLS)){
            workbook=new HSSFWorkbook(inputStream);
        }
        else if (file.getName().endsWith(XLSX)){
            workbook=new XSSFWorkbook(inputStream);
        }
        else {
            throw new Exception("这不是一个Excel文件");
        }
        Sheet sheet=workbook.getSheet(sheetName);
        return  readData(workbook,sheet,t);
    }



//    private <T> List<List<String>> readData(Workbook workbook, Sheet sheet,T t) throws Exception {
//
//        Object instance=null;
//        if (t==null){
//            instance=RequestBean.class.newInstance();
//        }
//        else {
//            instance=t.getClass().newInstance();
//        }
//
//        List<List<String>> lists=new ArrayList<>();
//        List<String> cells=null;
//        //指的是行数，一共有多少行 最后一行行标，比行数小1
////        int rows=sheet.getLastRowNum();
//
//        int rows=sheet.getPhysicalNumberOfRows();
//        if (rows==0){
//            throw new Exception("Excel里面没有数据");
//        }
//
//        int totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
//        for (int i=0;i<rows;i++){
//            Row row=sheet.getRow(i);
//            if (row==null){
//                continue;
//            }
//
//
//            cells=new ArrayList<>();
//            for (int j=0;j<totalCells;j++){
//
//                Cell cell=row.getCell(j);
//                if (cell==null){
//                    continue;
//                }
//                String cellValue=getCellValue(cell);
//                cells.add(cellValue);
//            }
//            lists.add(cells);
//        }
//        return lists;
//    }


    private List<RequestBean> ts=new ArrayList<RequestBean>();
    private RequestBean requestBean=null;
    private <T> List<T> readData(Workbook workbook, Sheet sheet, T t) throws Exception {

        int rows=sheet.getPhysicalNumberOfRows();
//        if (rows==0){
//            throw new Exception("Excel里面没有数据");
//        }
//        for (int i=1;i<rows;i++){
//
//        }

//        for (Field field:fields){
//            DescAnno annotation = field.getAnnotation(DescAnno.class);
//            if (annotation!=null){
//                System.out.println(annotation.desc()+":"+field.getName());
//                System.out.println("");
//            }
//        }

//        for (int i=1;i<rows;i++){
//            Row row = sheet.getRow(i);
//            for (Field field:fields){
//                DescAnno annotation = field.getAnnotation(DescAnno.class);
//                if (annotation!=null){
////                    System.out.println(annotation.desc()+":"+field.getName());
////                    System.out.println("");
//                    Cell cell=row.getCell();
//                }
//            }
//        }

        getHeadFieldIndex(sheet);
//        System.out.println(JSON.toJSONString(fieldIndex));
//         for (int i=1;i<rows;i++){
//            Row row = sheet.getRow(i);
//            for (Field field:fields){
//                DescAnno annotation = field.getAnnotation(DescAnno.class);
//                if (annotation!=null){
////                    System.out.println(annotation.desc()+":"+field.getName());
////                    System.out.println("");
//                    Cell cell=row.getCell();
//                }
//            }
//        }

        List<T> listt = new ArrayList<T>();
        for (int i=1;i<rows;i++){

            Object instance=null;
            if (t==null){
                instance=RequestBean.class.newInstance();
            }
            else {
                instance=t.getClass().newInstance();
            }

            //获取所有的字段
            Field[] fields = instance.getClass().getDeclaredFields();

            for (Field field:fields){
                String fieldName=field.getName();
                Integer index = fieldIndex.get(fieldName);
                Cell cell = sheet.getRow(i).getCell(index);
                String cellValue=getCellValue(cell);
                field.setAccessible(true);
                PropertyUtils.setProperty(instance,fieldName,cellValue);
            }
            listt.add((T)instance);
        }
        return listt;
    }
    private Map<String,Integer> fieldIndex=new HashMap<>();

    public void getHeadFieldIndex(Sheet sheet){
        Row row = sheet.getRow(0);

        int cells = row.getPhysicalNumberOfCells();

        Field[] fields = RequestBean.class.getDeclaredFields();
        for (Field field:fields){
            DescAnno descAnno = field.getAnnotation(DescAnno.class);
            for (int i=0;i<cells;i++){
                String cellValue=getCellValue(row.getCell(i));
//                if (descAnno.desc().equals(cellValue)){
//                    fieldIndex.put(descAnno.desc(),i);
//                }
                if (field.getName().equals(cellValue)){
                    fieldIndex.put(field.getName(),i);
                }
            }
        }
    }


    /**
     * 根据Excel表格中的数据判断类型得到值
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell){
        String cellValue="";
        if (cell!=null){
            switch (cell.getCellType()){
                case STRING:
                    cellValue=cell.getStringCellValue();
                    break;
                case BOOLEAN:
                    cellValue=String.valueOf(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    cellValue="";
                    break;
                case FORMULA:
                    cellValue=String.valueOf(cell.getCellFormula());
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)){
                        Date theDate = cell.getDateCellValue();
                        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd");
                        cellValue = dff.format(theDate);
                    }
                    else
                    {
                        DecimalFormat df = new DecimalFormat("0");
                        cellValue = df.format(cell.getNumericCellValue());
                    }
                    break;
                case ERROR:
                    cellValue = "非法字符";
                    break;
                default:
                    cellValue = "未知类型";
                    break;
            }
        }
        return cellValue;
    }
}

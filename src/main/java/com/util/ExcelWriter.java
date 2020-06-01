package com.util;

import com.entity.ExcelCases;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ExcelWriter {

    private static List<String> CELL_HEADS; //列头

    static {
        // 类装载时就载入指定好的列头信息，如有需要，可以考虑做成动态生成的列头
        CELL_HEADS = new ArrayList<String>();
        CELL_HEADS.add("用例ID");
        CELL_HEADS.add("用例名称");
        CELL_HEADS.add("是否执行");
        CELL_HEADS.add("前置条件");
        CELL_HEADS.add("依赖key");
        CELL_HEADS.add("url");
        CELL_HEADS.add("method");
        CELL_HEADS.add("data");
        CELL_HEADS.add("cookie操作");
        CELL_HEADS.add("header操作");
        CELL_HEADS.add("预期结果方式");
        CELL_HEADS.add("预期结果");
        CELL_HEADS.add("result");
        CELL_HEADS.add("数据");
        CELL_HEADS.add("备注");
        
    }

    /**
     * 生成Excel并写入数据信息
     *
     * @param dataList 数据列表
     * @return 写入数据后的工作簿对象
     */
    public static Workbook exportData(List<ExcelCases> dataList) {
        // 生成xlsx的Excel
        Workbook workbook = new SXSSFWorkbook();

        // 如需生成xls的Excel，请使用下面的工作簿对象，注意后续输出时文件后缀名也需更改为xls
        //Workbook workbook = new HSSFWorkbook();

        // 生成Sheet表，写入第一行的列头
        Sheet sheet = buildDataSheet(workbook);
        //构建每行的数据内容
        int rowNum = 1;
        for (Iterator<ExcelCases> it = dataList.iterator(); it.hasNext(); ) {
            ExcelCases data = it.next();
            if (data == null) {
                continue;
            }
            //输出行数据
            Row row = sheet.createRow(rowNum++);
            convertDataToRow(data, row);
        }
        return workbook;
    }


    /**
     * 生成sheet表，并写入第一行数据（列头）
     *
     * @param workbook 工作簿对象
     * @return 已经写入列头的Sheet
     */
    private static Sheet buildDataSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet();
        // 设置列头宽度
        for (int i = 0; i < CELL_HEADS.size(); i++) {
            sheet.setColumnWidth(i, 4000);
        }
        // 设置默认行高
        sheet.setDefaultRowHeight((short) 400);
        // 构建头单元格样式
        CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
        // 写入第一行各列的数据
        Row head = sheet.createRow(0);
        for (int i = 0; i < CELL_HEADS.size(); i++) {
            Cell cell = head.createCell(i);
            cell.setCellValue(CELL_HEADS.get(i));
            cell.setCellStyle(cellStyle);
        }
        return sheet;
    }

    /**
     * 设置第一行列头的样式
     *
     * @param workbook 工作簿对象
     * @return 单元格样式对象
     */
    private static CellStyle buildHeadCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        //对齐方式设置
        style.setAlignment(HorizontalAlignment.CENTER);
        //边框颜色和宽度设置
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
        //设置背景颜色
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //粗体字设置
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    /**
     * 将数据转换成行
     *
     * @param data 源数据
     * @param row  行对象
     * @return
     */
    private static void convertDataToRow(ExcelCases data, Row row) {
        int cellNum = 0;
        Cell cell;

        //写入用例编号
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getCaseID() ? "" : data.getCaseID());

        //写入用例名称
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getCaseName() ? "" : data.getCaseName());

        //写入是否执行
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getIsRun() ? "" : data.getIsRun());

        //写入前置条件
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getPremise() ? "" : data.getPremise());

        //写入依赖
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getDependentOn() ? "" : data.getDependentOn());

        //写入URL
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getUrl() ? "" : data.getUrl());

        //写入请求方式
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getMethod() ? "" : data.getMethod());

        //写入请求值
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getRequestData() ? "" : data.getRequestData());

        //写入cookie
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getRequestCookie() ? "" : data.getRequestCookie());

        //写入请求头
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getRequestHeaders() ? "" : data.getRequestHeaders());

        //写入断言方式
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getExpectType() ? "" : data.getExpectType());

        //写入断言预期值
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getExpectValue() ? "" : data.getExpectValue());

        //写入断言结果
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getResult() ? "" : data.getResult());

        //写入响应体
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getResponseData() ? "" : data.getResponseData());

        //写入备注
        cell = row.createCell(cellNum++);
        cell.setCellValue(null == data.getRemark() ? "" : data.getRemark());


    }


}

package com.junyangcompany.demo.utils;

import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.poi.excel.ExcelUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ExportExcelUtils {

    private static final Log LOGGER=LogFactory.getLog(ExcelUtil.class);

    /**
     * Excel 导出文件
     * @param
     * @param desName  excel 名称
     * @param response
     * @param
     */
    public static void export(String desName, HttpServletResponse response, String sheetName, List<String> field, @SuppressWarnings("rawtypes")List<List> data) throws FileNotFoundException {
        if(response ==null ){
            response= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
        }
        response.setContentType("Application/msexcel;charset=UTF-8");

        String fileName=desName+getDateStr()+".xls"; // 拼接文件名称， 由业务文件名和时间戳组成
        try {
            fileName = new String(fileName.getBytes("GBK"),"GBK");
        } catch (UnsupportedEncodingException e1) {
            LOGGER.error(e1.getMessage(),e1);
        }
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//        response.setContentType("application/vnd.ms-excel");
        //默认输出到桌面
        File file = new File("C:\\Users\\sunwukong\\Desktop");
        OutputStream outputStream = new FileOutputStream(new File(file,fileName));
        try {
            writeExcel(outputStream,sheetName,field, data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public  static void writeExcel(OutputStream os,String sheetName,List<?> headList,@SuppressWarnings("rawtypes") List<List> data) throws IOException{
        HSSFWorkbook wb = new HSSFWorkbook();
        //Workbook workbook = new SXSSFWorkbook(500);//每次缓存500条到内存，其余写到磁盘。
        CellStyle style = getCellStyle(wb);
        HSSFSheet sheet = wb.createSheet(sheetName);
        /**
         * 设置Excel表的第一行即表头
         */
        Row row =sheet.createRow(0);
        sheet.autoSizeColumn(0);
        for(int i=0;i<headList.size();i++){
            Cell headCell = row.createCell(i);
            headCell.setCellType(Cell.CELL_TYPE_STRING);
            headCell.setCellStyle(style);//设置表头样式
            headCell.setCellValue(String.valueOf(headList.get(i)));
        }

        for (int i = 0; i < data.size(); i++) {
            Row rowdata = sheet.createRow(i+1);//创建数据行
            sheet.autoSizeColumn(i+1);
            @SuppressWarnings("unchecked")
            List<Object> mapdata = data.get(i);
            int j=0;
            for(Object tt:mapdata){
                Cell celldata = rowdata.createCell(j);
                if(tt==null){
                    tt="";
                }
                if(tt!=null && tt instanceof BigDecimal){
                    celldata.setCellType(Cell.CELL_TYPE_NUMERIC);
                }else{
                    celldata.setCellType(Cell.CELL_TYPE_STRING);
                }
                celldata.setCellValue( String.valueOf(tt));
                j++;
            }
        }
        os.flush();
        wb.write(os);
        wb.close();
    }

    /**
     *
     * @Title: getCellStyle
     * @Description: （设置表头样式）
     * @param wb
     * @return
     */
    public static CellStyle getCellStyle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)12);//设置字体大小
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗
//        style.setFillForegroundColor(HSSFColor.LIME.index);// 设置背景色
//        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
//        style.setAlignment(HSSFCellStyle.SOLID_FOREGROUND);//让单元格居中

        style.setFont(font);
        return style;
    }


    /**
     * 生成当前的时间戳
     * @return
     */
    public static  String  getDateStr(){
        SimpleDateFormat  sdf=new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.format(new Date());
    }


    /**
     * 图片插入Excel
     * @param
     * @param wb
     * @param sheet
     */
    private  static  void  writeImg(int yindex,int xindex,Workbook  wb,HSSFSheet sheet){
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File("D:/未来一周气温变化.png"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);
            //画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            //anchor主要用于设置图片的属性    最后 4个参数   col1  左上角几列，row1 左上角几行  col2 右下角几列 ，row2 右下角几行
            HSSFClientAnchor  anchor=new HSSFClientAnchor(0, 0, 1023, 250,  (short)0, yindex+1, (short)(xindex+1),yindex+1);
            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

//	            CellRangeAddress cra=new CellRangeAddress(yindex+2, yindex+10, 0, xindex+1);
//	            //在sheet里增加合并单元格
//	            sheet.addMergedRegion(cra);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
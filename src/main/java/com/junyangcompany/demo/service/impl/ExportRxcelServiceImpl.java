package com.junyangcompany.demo.service.impl;

import com.junyangcompany.demo.bean.response.Response;
import com.junyangcompany.demo.entity.EnrollCollege;
import com.junyangcompany.demo.entity.EnrollCollegeScoreLine;
import com.junyangcompany.demo.repository.EnrollCollegeRepo;
import com.junyangcompany.demo.repository.EnrollCollegeScoreLineRepo;
import com.junyangcompany.demo.repository.ExamineeRepo;
import com.junyangcompany.demo.service.ExportRxcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * author:pan le
 * Date:2019/5/20
 * Time:19:16
 * 导出表格
 */
@Service
@Slf4j
public class ExportRxcelServiceImpl implements ExportRxcelService {

    private final EnrollCollegeRepo enrollCollegeRepo;

    private final ExamineeRepo examineeRepo;

    private final EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo;

    @Autowired
    public ExportRxcelServiceImpl(EnrollCollegeRepo enrollCollegeRepo, ExamineeRepo examineeRepo, EnrollCollegeScoreLineRepo enrollCollegeScoreLineRepo) {
        this.enrollCollegeRepo = enrollCollegeRepo;
        this.examineeRepo = examineeRepo;
        this.enrollCollegeScoreLineRepo = enrollCollegeScoreLineRepo;
    }

    public void exportExcel(HttpServletResponse response, List<Response> responses, Integer scienceAndArt) throws IOException {
        if (response == null) {
            response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        }
        assert response != null;
        String fileName = "精选志愿表" + getDateStr() + ".xls"; // 拼接文件名称， 由业务文件名和时间戳组成
        try {
            fileName = new String(fileName.getBytes("GBK"), StandardCharsets.ISO_8859_1);
        } catch (UnsupportedEncodingException e1) {
            log.error(e1.getMessage(), e1);
        }
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("UTF-8");

        HSSFWorkbook workbook = new HSSFWorkbook();
        List<String> collegeNames = responses.stream().map(response1 -> response1.getEnrollCollege().getName()).distinct().collect(Collectors.toList());
        //每一个大学创建一个工作表
        for (String collegeName : collegeNames) {
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setWrapText(true);
            cellStyle.setBorderBottom(BorderStyle.THIN);
//            cellStyle.setBorderLeft(HSSFCellStyle);//左边框
//            cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
//            cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            // 用大学名做表名
            HSSFSheet sheet = workbook.createSheet(collegeName);
            HSSFRow row1 = sheet.createRow(0);
            //写最上面一行的表头
            row1.createCell(0).setCellValue("院校信息");
            row1.createCell(1).setCellValue("2018年计划");
            row1.createCell(2).setCellValue(" ");
            row1.createCell(3).setCellValue("2018");
            row1.createCell(4).setCellValue("2017");
            row1.createCell(5).setCellValue("2016");
            row1.createCell(6).setCellValue("2015");
            // 合并单元格 写第二行到第五行的数据
            sheet.addMergedRegion(new CellRangeAddress(1, 4, 0, 0));
            HSSFRow row2 = sheet.createRow(1);
            // 合并单元格,插入大学招生计划
            sheet.addMergedRegion(new CellRangeAddress(1, 4, 1, 1));
//            Optional<EnrollCollege> enrollCollege = responses.stream().map(Response::getEnrollCollege).findFirst();
            Optional<EnrollCollege> enrollCollege = responses.stream().map(Response::getEnrollCollege).collect(Collectors.toList()).stream().filter(enrollCollege1 -> enrollCollege1.getName().equals(collegeName)).findFirst();
            if (enrollCollege.isPresent()) {
                EnrollCollege enrollCollege1 = enrollCollege.get();
                String enrollCollegeContent;
                if (Objects.nonNull(enrollCollege1.getGrade()) && Objects.nonNull(enrollCollege1.getGrade().getName())) {
                    enrollCollegeContent = enrollCollege1.getName() + ";" + enrollCollege1.getCode() + ";" + enrollCollege1.getProvince().getName() + ";" + enrollCollege1.getCity() + ";" + enrollCollege1.getGrade().getName();
                } else {
                    enrollCollegeContent = enrollCollege1.getName() + ";" + enrollCollege1.getCode() + ";" + enrollCollege1.getProvince().getName() + ";" + enrollCollege1.getCity() + ";";
                }
//                        + enrollCollege1.getCollegeLevel().stream().toString() + enrollCollege1.getIsPublic() ;
                HSSFCell cell1 = row2.createCell(0);
                HSSFCell cell2 = row2.createCell(1);
                cell1.setCellValue(enrollCollegeContent);
                cell2.setCellValue(enrollCollege1.getPlanCount());
                row2.createCell(2).setCellValue("人数");
                //设置格式
                CellUtil.setAlignment(cell1, HorizontalAlignment.CENTER);
                CellUtil.setVerticalAlignment(cell1, VerticalAlignment.CENTER);
                CellUtil.setAlignment(cell2, HorizontalAlignment.CENTER);
                CellUtil.setVerticalAlignment(cell2, VerticalAlignment.CENTER);

                HSSFRow row3 = sheet.createRow(2);
                row3.createCell(2).setCellValue("最低分");
                HSSFRow row4 = sheet.createRow(3);
                row4.createCell(2).setCellValue("线差");
                HSSFRow row5 = sheet.createRow(4);
                row5.createCell(2).setCellValue("位次");
                // TODO:大学2018年招生计划 的数据重新从enrollCollege中查询
                AtomicReference<Long> enrollCollegeEnrollBatchId = new AtomicReference<>(0L);
                Optional<Response> first = responses.stream().filter(response1 -> response1.getEnrollCollege().getName().equals(collegeName)).findFirst();
                first.ifPresent(f -> {
                    enrollCollegeEnrollBatchId.set(f.getSecondBean().getEnrollCollegeId());
                });
                //重新查询的大学招生计划里面的当前大学的多年分数线
                List<EnrollCollegeScoreLine> enrollCollegeScoreLines = enrollCollegeScoreLineRepo.findByEnrollCollegeEnrollBatchAndScienceArt(enrollCollegeEnrollBatchId.get(), scienceAndArt);
                //循环判断年份,填充信息
                enrollCollegeScoreLines.forEach(enrollCollegeScoreLine -> {
                    if (enrollCollegeScoreLine.getYear().equals(2018)) {
                        row2.createCell(3).setCellValue(enrollCollegeScoreLine.getEnrollCount());
                        row3.createCell(3).setCellValue(enrollCollegeScoreLine.getMinScore());
                        row4.createCell(3).setCellValue(enrollCollegeScoreLine.getScoreLineDiff());
                        row5.createCell(3).setCellValue(enrollCollegeScoreLine.getMinRank());
                    }
                    if (enrollCollegeScoreLine.getYear().equals(2017)) {
                        row2.createCell(4).setCellValue(enrollCollegeScoreLine.getEnrollCount());
                        row3.createCell(4).setCellValue(enrollCollegeScoreLine.getMinScore());
                        row4.createCell(4).setCellValue(enrollCollegeScoreLine.getScoreLineDiff());
                        row5.createCell(4).setCellValue(enrollCollegeScoreLine.getMinRank());
                    }
                    if (enrollCollegeScoreLine.getYear().equals(2016)) {
                        row2.createCell(5).setCellValue(enrollCollegeScoreLine.getEnrollCount());
                        row3.createCell(5).setCellValue(enrollCollegeScoreLine.getMinScore());
                        row4.createCell(5).setCellValue(enrollCollegeScoreLine.getScoreLineDiff());
                        row5.createCell(5).setCellValue(enrollCollegeScoreLine.getMinRank());
                    }
                    if (enrollCollegeScoreLine.getYear().equals(2015)) {
                        row2.createCell(6).setCellValue(enrollCollegeScoreLine.getEnrollCount());
                        row3.createCell(6).setCellValue(enrollCollegeScoreLine.getMinScore());
                        row4.createCell(6).setCellValue(enrollCollegeScoreLine.getScoreLineDiff());
                        row5.createCell(6).setCellValue(enrollCollegeScoreLine.getMinRank());
                    }
                });

                //第六行 专业名称的表头
                HSSFRow row6 = sheet.createRow(5);
                row6.createCell(0).setCellValue("专业名称");
                row6.createCell(1).setCellValue("2018年计划");
                row6.createCell(2).setCellValue(" ");
                row6.createCell(3).setCellValue("2018");
                row6.createCell(4).setCellValue("2017");
                row6.createCell(5).setCellValue("2016");
                row6.createCell(6).setCellValue("2015");

                //TODO:根据选取的专业数量做循环
                List<List<Map<String, Object>>> collect = responses.stream().filter(response1 -> response1.getEnrollCollege().getName().equals(collegeName)).map(Response::getScoreInformation).collect(Collectors.toList());
                int i = 0;
                for (List<Map<String, Object>> maps : collect) {
                    //单个大学专业
                    String majorName = (String) maps.get(0).get("majorName");
                    Long enrollMajorScoreLinId = (Long) maps.get(0).get("enrollMajorScoreLinId");
                    // TODO:专业具体信息
                    String majorContent = majorName;
                    //第七行 专业名称,人数
                    HSSFRow row7 = sheet.createRow(6 + i);
                    //第八行最低分
                    HSSFRow row8 = sheet.createRow(7 + i);
                    //第九行线差
                    HSSFRow row9 = sheet.createRow(8 + i);
                    //第十行位次
                    HSSFRow row10 = sheet.createRow(9 + i);
                    //十一行分差
                    HSSFRow row11 = sheet.createRow(10 + i);
                    sheet.addMergedRegion(new CellRangeAddress(6 + i, 10 + i, 0, 0));
                    sheet.addMergedRegion(new CellRangeAddress(6 + i, 10 + i, 1, 1));
                    HSSFCell majorContentCell = row7.createCell(0);
                    majorContentCell.setCellValue(majorContent);
                    CellUtil.setAlignment(majorContentCell, HorizontalAlignment.CENTER);
                    CellUtil.setVerticalAlignment(majorContentCell, VerticalAlignment.CENTER);
                    //合并专业名称 2018年专业计划
                    //循环单个scoreInformation,写入不同年份的分数线
//                    Map<String, Object> map = maps.get(0);
                    for (Map<String, Object> map : maps) {
                        // 写2018年数据
                        row7.createCell(2).setCellValue("人数");
                        row8.createCell(2).setCellValue("最低分");
                        row9.createCell(2).setCellValue("线差");
                        row10.createCell(2).setCellValue("位次");
                        row11.createCell(2).setCellValue("分差");
                        if (map.get("year").equals(2018)) {
                            row7.createCell(1).setCellValue((Integer) map.get("enrollCount"));
                            row7.createCell(3).setCellValue((Integer) map.get("enrollCount"));
                            row8.createCell(3).setCellValue((Integer) map.get("minScore"));
                            row9.createCell(3).setCellValue((Integer) map.get("scoreLineDiff"));
                            row10.createCell(3).setCellValue((Integer) map.get("minRank"));
//                            row11.createCell(3).setCellValue((Integer) map.get("分差"));
                        } else if (map.get("year").equals(2017)) {
                            row7.createCell(4).setCellValue((Integer) map.get("enrollCount"));
                            row8.createCell(4).setCellValue((Integer) map.get("minScore"));
                            row9.createCell(4).setCellValue((Integer) map.get("scoreLineDiff"));
                            row10.createCell(4).setCellValue((Integer) map.get("minRank"));
//                            row11.createCell(4).setCellValue((Integer) map.get("分差"));
                        } else if (map.get("year").equals(2016)) {
                            row7.createCell(5).setCellValue((Integer) map.get("enrollCount"));
                            row8.createCell(5).setCellValue((Integer) map.get("minScore"));
                            row9.createCell(5).setCellValue((Integer) map.get("scoreLineDiff"));
                            row10.createCell(5).setCellValue((Integer) map.get("minRank"));
//                            row11.createCell(5).setCellValue((Integer) map.get("分差"));
                        } else if (map.get("year").equals(2015)) {
                            row7.createCell(6).setCellValue((Integer) map.get("enrollCount"));
                            row8.createCell(6).setCellValue((Integer) map.get("minScore"));
                            row9.createCell(6).setCellValue((Integer) map.get("scoreLineDiff"));
                            row10.createCell(6).setCellValue((Integer) map.get("minRank"));
//                            row11.createCell(6).setCellValue((Integer) map.get("分差"));
                        }
                    }
                    i = i + 5;
                }
            }
        }
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    /**
     * 生成当前的时间戳
     *
     * @return
     */
    public static String getDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

}

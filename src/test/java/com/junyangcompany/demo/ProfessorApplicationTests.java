package com.junyangcompany.demo;

import org.apache.logging.log4j.util.Strings;
import org.apache.poi.hslf.extractor.ImageExtractor;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFDocumentCore;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.UUID;

public class ProfessorApplicationTests {
    @Test
    public void contextLoads() throws Exception {
        String content = "";
        String[] strings;
        File file = new File("C:\\Users\\sunwukong\\Desktop\\2018年高考全国卷Ⅰ理综试题解析（精编版）（解析版）.doc");
        File file1 = new File("C:\\Users\\sunwukong\\Desktop\\2018年高考全国卷Ⅰ文综试题解析（精编版）（原卷版）.docx");
        InputStream inputStream = new FileInputStream(file);
        InputStream inputStream1 = new FileInputStream(file1);
//        WordExtractor wordExtractor = new WordExtractor(inputStream);
//        content = wordExtractor.getText();
//        System.out.println(content);
//
//        HWPFDocument document = new HWPFDocument(inputStream);
//        WordExtractor wordExtractor = new WordExtractor(document);
//        Range range = document.getRange();
//        Range range1 = new Range(0,1,document);
//
//
//        Picture picture = document.getPicturesTable().getAllPictures().get(0);
//        picture.writeImageContent(new FileOutputStream(new File("aaa.jpg")));
//        Paragraph paragraph = range.getParagraph(0);
//        System.out.println(range.numParagraphs());
//        range.getParagraph(0)

//        XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
//        List<XWPFParagraph> paragraphs = xwpfDocument.getParagraphs();
//        for (XWPFParagraph xwpfParagraph : paragraphs) {
//            System.out.println(xwpfParagraph.getPictureText());
//        }

//        HWPFDocument document = new HWPFDocument(inputStream);
//        Range range = document.getRange();
//        List<Picture> allPictures = document.getPicturesTable().getAllPictures();
//        for (Picture allPicture : allPictures) {
//        }

        XWPFDocument xwpfDocument = new XWPFDocument(inputStream1);
        List<XWPFPictureData> allPictures = xwpfDocument.getAllPictures();

//
////        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
////        BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);
////        ImageIO.write(bufferedImage,"jpg",new File("aaaa.jpg"));
//
        for (XWPFParagraph paragraph : xwpfDocument.getParagraphs()) {
            for (XWPFRun run : paragraph.getRuns()) {
                for (XWPFPicture embeddedPicture : run.getEmbeddedPictures()) {
                    XWPFPictureData pictureData = embeddedPicture.getPictureData();
                    byte[] data1 = pictureData.getData();
                    String fileName = pictureData.getFileName();
                    System.out.println(fileName);
                    ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(data1);
                    BufferedImage bufferedImage1 = ImageIO.read(byteArrayInputStream1);
                    ImageIO.write(bufferedImage1,"jpg",new File("C:\\Users\\sunwukong\\Desktop\\新建文件夹 (2)\\" +UUID.randomUUID()+"aaaa.jpg"));
                }
            }
        }

//        HWPFDocument document = new HWPFDocument(inputStream);
//        for (Picture allPicture : document.getPicturesTable().getAllPictures()) {
//            allPicture.writeImageContent(new FileOutputStream(new File("professors.txt")));
//        }
//        document.write(new File("C:\\Users\\sunwukong\\Desktop\\2018年高考全国卷.doc"));
//        document.close();


    }
}

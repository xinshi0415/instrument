package com.gjx.instrument;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.gjx.instrument.entity.GjxFile;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@SpringBootTest
class InstrumentApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) throws IOException {
        File file = new File("E:\\gjx");
        List<GjxFile> list = new ArrayList<>();
        showLayers(file, list);
        //处理数据并进行生成文件
        createFile(list);
    }

    public static void showLayers(File file, List<GjxFile> list) throws IOException {

        //判断是文件还是文件夹【文件就不需要在向下查找了，文件夹需要继续查找】
        File[] files = file.listFiles();
        if (files == null) {
            return;
        }
        for (File tempFile : files) {
            //如果是目录 就向内部查找
            if (tempFile.isDirectory()) {
                showLayers(tempFile, list); //递归遍历文件夹
            } else {
                //处理需要处理的文件
                //System.out.println(tempFile.getAbsolutePath());
                System.out.println(tempFile.getName() + "正在处理中！！！");
                handleInputFile(tempFile, list);
            }
        }
    }

    /**
     * 处理各种文件
     *
     * @param file
     */
    public static void handleInputFile(File file, List<GjxFile> list) throws IOException {
        if (file.getName().contains("empty")) {
            return;
        } else if (file.getName().endsWith(".csv")) {
            handleCsvFile(file, list);
        } else if (file.getName().endsWith(".xlsx")) {//此处也可以用else
            handleXlsxFile(file, list);
        }

    }

    public static void handleCsvFile(File file, List<GjxFile> list) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String content = null;
        //readLine这个方法的时候需要注意，判断读取到文件末尾使用 null
        if (null == br.readLine()) {
            return;
        }
        while ((content = br.readLine()) != null) {
            //这里处理集合
            //System.out.println(content);
            String[] split = content.split(",");
            GjxFile gjxFile = new GjxFile();
            gjxFile.setIndex(split[0]);
            gjxFile.setAqi(split[1]);
            gjxFile.setCo(split[2]);
            gjxFile.setNo2(split[3]);
            gjxFile.setO3(split[4]);
            gjxFile.setPm10(split[5]);
            gjxFile.setPm2_5(split[6]);
            gjxFile.setQuality(split[7]);
            gjxFile.setRank(split[8]);
            gjxFile.setSo2(split[9]);
            gjxFile.setTimePoint(split[10]);
            gjxFile.setCity(split[11]);
            list.add(gjxFile);
        }
        br.close();
    }

    public static void handleXlsxFile(File file, List<GjxFile> list) throws IOException {
        //获取文件
        FileInputStream fileInputStream = new FileInputStream(file);
        //获取工作薄
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        //得到表
        Sheet sheet = workbook.getSheetAt(0);

        //获取标题内容
        Row rowTitle = sheet.getRow(0);
        //获取表中的内容
        int rowCount = sheet.getPhysicalNumberOfRows();
        for (int rowNum = 1; rowNum < rowCount; rowNum++) {
            Row rowData = sheet.getRow(rowNum);
            if (rowData != null) {
                //读取列
                int cellCout = rowTitle.getPhysicalNumberOfCells();
               // List<String> cells = new ArrayList<>();
                GjxFile gjxFile = new GjxFile();
                for (int cellNum = 0; cellNum < cellCout; cellNum++) {
                    //System.out.print("【" + (rowNum+1) + "-" + (cellNum+1) + "】");

                    Cell cell = rowData.getCell(cellNum);
                    String cellValue = "";
                    //匹配列的数据类型
                    if (cell != null) {

                        int cellType = cell.getCellType();


                        switch (cellType) {
                            case HSSFCell.CELL_TYPE_STRING://字符串
                                //System.out.print("【STRING】");
                                cellValue = cell.getStringCellValue();
                                break;
                            case HSSFCell.CELL_TYPE_BOOLEAN://布尔值
                                //System.out.print("【BOOLEAN】");
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case HSSFCell.CELL_TYPE_NUMERIC://数字类型
                                //System.out.print("【NUMERIC】");

                                if (HSSFDateUtil.isCellDateFormatted(cell)) {//日期
                                    //System.out.print("【日期】");
                                    Date date = cell.getDateCellValue();
                                    cellValue = DateUtil.format(date, "yyyy/MM/dd");
                                } else {
                                    // 不是日期格式，则防止当数字过长时以科学计数法显示
                                    //System.out.print("【转换成字符串】");
                                    //cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                                    cellValue = String.valueOf(cell.getNumericCellValue());
                                }
                                break;
                            case HSSFCell.CELL_TYPE_BLANK://空
                                //System.out.print("【BLANK】");
                                break;
                            case Cell.CELL_TYPE_ERROR:
                                //System.out.print("【数据类型错误】");
                                break;
                        }
                        //cells.add(cellValue);
                    } /*else {
                        //如果为空值 送空串
                        cells.add("");
                    }*/
                    //这里是为了模板的排序不一致需要进行转换，进行匹配列名
                    switch (rowTitle.getCell(cellNum).getStringCellValue()) {
                        case "日期" :
                            gjxFile.setTimePoint(cellValue);
                            break;
                        case "AQI" :
                            gjxFile.setAqi(cellValue);
                            break;
                        case "质量等级" :
                            gjxFile.setQuality(cellValue);
                            break;
                        case "PM2.5":
                            gjxFile.setPm2_5(cellValue);
                            break;
                        case "PM10":
                            gjxFile.setPm10(cellValue);
                            break;
                        case "SO2":
                            gjxFile.setSo2(cellValue);
                            break;
                        case "NO2":
                            gjxFile.setNo2(cellValue);
                            break;
                        case "CO":
                            gjxFile.setCo(cellValue);
                            break;
                        case "O3_8h":
                            gjxFile.setO3(cellValue);
                            break;
                    }
                }
                //有一个不为空 都要保留数据
                if (StrUtil.isNotBlank(gjxFile.getO3()) ||
                        StrUtil.isNotBlank(gjxFile.getTimePoint()) ||
                        StrUtil.isNotBlank(gjxFile.getQuality()) ||
                        StrUtil.isNotBlank(gjxFile.getPm2_5()) ||
                        StrUtil.isNotBlank(gjxFile.getPm10()) ||
                        StrUtil.isNotBlank(gjxFile.getSo2()) ||
                        StrUtil.isNotBlank(gjxFile.getNo2()) ||
                        StrUtil.isNotBlank(gjxFile.getCo()) ||
                        StrUtil.isNotBlank(gjxFile.getAqi())) {
                    gjxFile.setCity(file.getName().substring(0, file.getName().indexOf("20")));
                    list.add(gjxFile);
                }
                /*if (CollUtil.isNotEmpty(cells) && cells.size() != 0) {
                    GjxFile gjxFile = new GjxFile();
                    if (cells.stream().anyMatch(StrUtil::isNotBlank)) {
                        //保留真实数剧，有一个不空就传值
                        gjxFile.setCity(file.getName().substring(0, file.getName().indexOf("20")));
                    } else {
                        continue;
                    }
                    gjxFile.setIndex("");
                    gjxFile.setAqi(cells.get(1));
                    gjxFile.setCo(cells.get(7));
                    gjxFile.setNo2(cells.get(6));
                    gjxFile.setO3(cells.get(8));
                    gjxFile.setPm10(cells.get(4));
                    gjxFile.setPm2_5(cells.get(3));
                    gjxFile.setQuality(cells.get(2));
                    gjxFile.setRank("");
                    gjxFile.setSo2(cells.get(5));
                    gjxFile.setTimePoint(cells.get(0));
                    list.add(gjxFile);
                }*/
            }
        }
        fileInputStream.close();
    }

    /**
     * 排序并生成文件
     *
     * @param list
     */
    public static void createFile(List<GjxFile> list) throws IOException {
        long begin = System.currentTimeMillis();
        Collator instance = Collator.getInstance(Locale.CHINA);
        List<GjxFile> sortList = list.stream().sorted((p1, p2) -> instance.compare(p1.getCity(), p2.getCity())).collect(Collectors.toList());
        //创建簿
        Workbook workbook = new SXSSFWorkbook();
        //创建表
        Sheet sheet = workbook.createSheet();

        //创建行(0:第一行)
        Row row1 = sheet.createRow(0);
        //创建单元格(0:第一行的第一个格子)
        Cell cell00 = row1.createCell(0);
        cell00.setCellValue("date");
        //第一行的第二个格子
        Cell cell01 = row1.createCell(1);
        cell01.setCellValue("city");
        //第一行的第三个格子
        Cell cell02 = row1.createCell(2);
        cell02.setCellValue("aqi");
        //第一行的第四个格子
        Cell cell03 = row1.createCell(3);
        cell03.setCellValue("co");
        //第一行的第五个格子
        Cell cell04 = row1.createCell(4);
        cell04.setCellValue("no2");
        //第一行的第六个格子
        Cell cell05 = row1.createCell(5);
        cell05.setCellValue("o3");
        //第一行的第七个格子
        Cell cell06 = row1.createCell(6);
        cell06.setCellValue("pm10");
        //第一行的第八个格子
        Cell cell07 = row1.createCell(7);
        cell07.setCellValue("pm2_5");
        //第一行的第九个格子
        Cell cell08 = row1.createCell(8);
        cell08.setCellValue("so2");
        //第一行的第十个格子
        Cell cell09 = row1.createCell(9);
        cell09.setCellValue("quality");

        //写数据
        for (int rowNum = 0; rowNum < list.size(); rowNum++) {
            Row row = sheet.createRow(rowNum + 1);
            Cell cellX0 = row.createCell(0);
            cellX0.setCellValue(list.get(rowNum).getTimePoint());
            Cell cellX1 = row.createCell(1);
            cellX1.setCellValue(list.get(rowNum).getCity());
            Cell cellX2 = row.createCell(2);
            cellX2.setCellValue(Double.parseDouble(list.get(rowNum).getAqi()));
            Cell cellX3 = row.createCell(3);
            cellX3.setCellValue(Double.parseDouble(list.get(rowNum).getCo()));
            Cell cellX4 = row.createCell(4);
            cellX4.setCellValue(Double.parseDouble(list.get(rowNum).getNo2()));
            Cell cellX5 = row.createCell(5);
            cellX5.setCellValue(Double.parseDouble(list.get(rowNum).getO3()));
            Cell cellX6 = row.createCell(6);
            cellX6.setCellValue(Double.parseDouble(list.get(rowNum).getPm10()));
            Cell cellX7 = row.createCell(7);
            cellX7.setCellValue(Double.parseDouble(list.get(rowNum).getPm2_5()));
            Cell cellX8 = row.createCell(8);
            cellX8.setCellValue(Double.parseDouble(list.get(rowNum).getSo2()));
            Cell cellX9 = row.createCell(9);
            cellX9.setCellValue(list.get(rowNum).getQuality());
        }
        FileOutputStream fileOutputStream = new FileOutputStream("E:\\insertFile\\airTest.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        //清除临时文件
        ((SXSSFWorkbook) workbook).dispose();

        long end = System.currentTimeMillis();
        System.out.println((double) (end - begin) / 1000);
    }
}

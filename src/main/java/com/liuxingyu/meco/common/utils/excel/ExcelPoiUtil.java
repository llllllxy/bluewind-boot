package com.liuxingyu.meco.common.utils.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.prefs.BackingStoreException;

/**
 * @author liuxingyu01
 * @date 2021-03-14-0:29
 * @description 简单excel导入导出工具类
 * @reference https://zhuanlan.zhihu.com/p/274993419
 **/
public class ExcelPoiUtil {
    final static Logger log = LoggerFactory.getLogger(ExcelPoiUtil.class);


    /**
     * @param sheetName  工作表名，文件名，头部信息
     * @param listName   列名
     * @param list       需要写入的数据
     * @param listBottom 底部写入信息：<列位置，数据>
     * @param response   返回
     */
    public static void excelPort(String sheetName, List<String> listName, List<Map<String, String>> list, List<Map<Integer, String>> listBottom, HttpServletResponse response) {
        try {
            if (list.size() == 0) {
                throw new BackingStoreException("数据为空");
            }
            // 声明一个工作簿
            XSSFWorkbook wb = new XSSFWorkbook();
            // 创建sheet页
            XSSFSheet sheet = wb.createSheet(sheetName);
            sheet.setDefaultColumnWidth(19);

            // 表头
            XSSFRow rowReportTitle = sheet.createRow(0);
            Cell cell1 = rowReportTitle.createCell(0); // 0列
            // 设置值
            cell1.setCellValue(sheetName);

            // 合并表头
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, listName.size() - 1));
            rowReportTitle.setHeight((short) 600); // 行高

            //设置表头字体
            Font headFont = wb.createFont();
            headFont.setFontName("宋体");
            headFont.setFontHeightInPoints((short) 18);// 字体大小

            CellStyle headStyle = wb.createCellStyle();
            headStyle.setFont(headFont);
            headStyle.setAlignment(HorizontalAlignment.CENTER);// 左右居中
            headStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中
            // 头部样式添加
            cell1.setCellStyle(headStyle);

            // 全局加线样式
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
            cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
            cellStyle.setBorderTop(BorderStyle.THIN);//上边框
            cellStyle.setBorderRight(BorderStyle.THIN);//右边框
            cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

            // 记录标题信息
            TreeMap<String, Integer> headMap = new TreeMap<>();

            // 标题写入
            XSSFRow row = sheet.createRow(1);
            for (int i = 0; i < listName.size(); i++) {
                row.setHeight((short) 450);
                XSSFCell cell = row.createCell(i);
                String headName = listName.get(i);
                cell.setCellValue(headName); // 写入列名
                headMap.put(headName, i);
                cell.setCellStyle(cellStyle);
            }

            // 写入内容数据
            int ind = 2;
            for (Map<String, String> map : list) {
                XSSFRow r = sheet.createRow(ind++);
                for (Map.Entry<String, Integer> m : headMap.entrySet()) {
                    String name = m.getKey(); // 列名
                    String value = map.get(name); // value 不一定存在
                    XSSFCell cell2 = r.createCell(m.getValue());
                    if (value != null) {
                        cell2.setCellValue(value);
                    }
                    cell2.setCellStyle(cellStyle);
                }
            }

            // 底部样式
            CellStyle bottomStyle = wb.createCellStyle();
            bottomStyle.setBorderBottom(BorderStyle.THIN); //下边框
            bottomStyle.setBorderLeft(BorderStyle.THIN);//左边框
            bottomStyle.setBorderTop(BorderStyle.THIN);//上边框
            bottomStyle.setBorderRight(BorderStyle.THIN);//右边框
            bottomStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 上下居中

            // 写入底部
            if (listBottom != null) {
                int columnNum = listName.size(); // 当前表有多少列
                for (Map<Integer, String> map : listBottom) {
                    XSSFRow bottom = sheet.createRow(ind);
                    bottom.setHeight((short) 400); // 行高
                    // 当前行分几列
                    int size = map.size();
                    if (columnNum % 2 == 0 & size % 2 == 0) {
                        // 都是偶数执行
                        int c = columnNum / size;  // 列大小
                        for (int i = 0; i < size; i++) {
                            CellRangeAddress cellAddresses0 = new CellRangeAddress(ind, ind, i * c, i * c + c - 1);
                            sheet.addMergedRegion(cellAddresses0);
                            XSSFCell c0 = bottom.createCell(cellAddresses0.getFirstColumn());
                            c0.setCellValue(map.get(i));
                            c0.setCellStyle(bottomStyle);
                            for (int n = cellAddresses0.getFirstColumn() + 1; n <= cellAddresses0.getLastColumn(); n++) {
                                XSSFCell cn = bottom.createCell(n);
                                cn.setCellStyle(bottomStyle);
                            }
                        }
                    } else if (!(columnNum % 2 == 0) & !(size % 2 == 0)) {
                        // 都是奇数执行
                        int c = columnNum - 1 / size;
                        for (int i = 0; i < size; i++) {
                            CellRangeAddress cellAddresses1;
                            if (size - i <= 1) {
                                cellAddresses1 = new CellRangeAddress(ind, ind, i * c, i * c + c);
                            } else {
                                cellAddresses1 = new CellRangeAddress(ind, ind, i * c, i * c + c - 1);
                            }
                            sheet.addMergedRegion(cellAddresses1);
                            XSSFCell c0 = bottom.createCell(cellAddresses1.getFirstColumn());
                            c0.setCellValue(map.get(i));
                            c0.setCellStyle(bottomStyle);
                            for (int n = cellAddresses1.getFirstColumn() + 1; n <= cellAddresses1.getLastColumn(); n++) {
                                XSSFCell cn = bottom.createCell(n);
                                cn.setCellStyle(bottomStyle);
                            }
                        }
                    } else {
                        // 奇偶不同
                        int c = (columnNum + 1) / size;
                        for (int i = 0; i < size; i++) {
                            CellRangeAddress cellAddresses2;
                            if (size - i <= 1) {
                                cellAddresses2 = new CellRangeAddress(ind, ind, i * c, i * c + c - 2);
                            } else {
                                cellAddresses2 = new CellRangeAddress(ind, ind, i * c, i * c + c - 1);
                            }
                            sheet.addMergedRegion(cellAddresses2);
                            XSSFCell c0 = bottom.createCell(cellAddresses2.getFirstColumn());
                            c0.setCellValue(map.get(i));
                            c0.setCellStyle(bottomStyle);
                            for (int n = cellAddresses2.getFirstColumn() + 1; n <= cellAddresses2.getLastColumn(); n++) {
                                XSSFCell cn = bottom.createCell(n);
                                cn.setCellStyle(bottomStyle);
                            }
                        }
                    }
                    ind++;
                }
            }

            // 输出Excel文件
            OutputStream output = response.getOutputStream();
            response.reset();
            // 设置响应头
            response.setHeader("Content-Disposition", "attchement; filename=" + URLEncoder.encode(sheetName + ".xls", "UTF-8"));
            response.setContentType("application/msexcel;charset=utf-8");
            wb.write(output);
            wb.close();
            // 关闭输出流
            output.close();
        } catch (Exception e) {
            log.error("ExcelPoiUtil -- excelPort Exception = {e}", e);
        }
    }


    /**
     * 使用示例
     * @param dateMonth
     * @param stationId
     * @param response
     */
//    public void exportFixed(String dateMonth, String stationId, HttpServletResponse response) {
//        // 列名
//        List<String> listName = Arrays.asList("时间", "入库数量", "使用", "库存", "运行状况", "DPD更换", "运维人员", "站点");
//        String[] split = dateMonth.split("-");
//        String startTime = getStartTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
//        String endTime = getEndTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
//        // 查询数据
//        List<DrugVO> drugVOS = this.statisticAnalysisMapper.getDrugPage(startTime, endTime, stationId);
//        // 列名 数据
//        List<Map<String, String>> list = new ArrayList<>();
//        for (DrugVO vo : drugVOS) {
//            Map<String, String> map = new HashMap<>();
//            map.put("时间", sdf.format(vo.getDrugTime()));
//            map.put("入库数量", vo.getDrugDepositNum() + "");
//            map.put("使用", vo.getDrugUseNum() + "");
//            map.put("库存", vo.getDrugInventoryNum() + "");
//            map.put("运行状况", vo.getDeviceState() == 1 ? "正常" : "异常");
//            map.put("DPD更换", vo.getDrugReplace());
//            map.put("运维人员", vo.getOperationName());
//            map.put("站点", vo.getStationName());
//            list.add(map);
//        }
//
//        ArrayList<Map<Integer, String>> bottomList = new ArrayList<>();
//        Map<Integer, String> p = new HashMap<>();
//
//        p.put(0, "月总用药量：150");
//        p.put(1, "月入库药量：166");
//        p.put(2, "月处理水量：166吨");
//        bottomList.add(p);
//        HashMap<Integer, String> map = new HashMap<>();
//        map.put(0,"ceshi");
//        bottomList.add(map);
//
//        // 将需要写入Excel的数据传入
//        ExcelPortUtil.excelPort("管理记录", listName, list, bottomList, response);
//    }

}

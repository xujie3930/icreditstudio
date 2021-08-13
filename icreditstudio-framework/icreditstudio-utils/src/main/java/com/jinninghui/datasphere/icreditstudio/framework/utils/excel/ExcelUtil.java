package com.jinninghui.datasphere.icreditstudio.framework.utils.excel;

import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import static com.alibaba.excel.EasyExcelFactory.read;
import static com.alibaba.excel.EasyExcelFactory.write;

@Slf4j
@UtilityClass
public class ExcelUtil {

    /**
     * 导入Excel
     *
     * @param file         excel文件
     * @param readListener 处理每条数据的监听器
     * @throws IOException
     */
    public <T> void importExcel(MultipartFile file, ReadListener<T> readListener, Class<T> clazz) {
        try {
            read(file.getInputStream(), clazz, readListener).sheet().doRead();
        } catch (IOException e) {
            log.error("importExcel error", e);
        }
    }

    /**
     * 导出Excel
     *
     * @param response HttpServletResponse
     * @param fileName 导出文件名称
     * @param data     导出List
     * @param clazz    导出类型
     * @throws IOException
     */
    public <T> void exportExcel(HttpServletResponse response, String fileName, List<T> data, Class<T> clazz) {
        setHeader(response, fileName);
        try {
            write(response.getOutputStream(), clazz).sheet().doWrite(data);
        } catch (IOException e) {
            log.error("exportExcel error", e);
        }
    }

    /**
     * 导出Excel
     *
     * @param response HttpServletResponse
     * @param fileName 导出文件名称
     * @param data     导出List
     * @param clazz    导出类型
     * @param columnFiledNamesList    需要导出的列的字段集合
     * @throws IOException
     */
    public <T> void exportExcelColumnFiledNames(HttpServletResponse response, String fileName, List<T> data,
                                                Class<T> clazz,List<String> columnFiledNamesList) {
        setHeader(response, fileName);
        try {
            write(response.getOutputStream(), clazz).excludeColumnFiledNames(columnFiledNamesList).sheet().doWrite(data);
        } catch (IOException e) {
            log.error("exportExcel error", e);
        }
    }

    /**
     * 填充Excel
     *
     * @param response         HttpServletResponse
     * @param templateFilePath 模板路径
     * @param exportFileName   导出文件名称
     * @param data             填充数据 单个对象传Map,多条传List
     */
    public void fillExcel(HttpServletResponse response, String templateFilePath, String exportFileName, Object data) throws IOException {
        setHeader(response, exportFileName);
        write(response.getOutputStream()).withTemplate(templateFilePath).excelType(templateFilePath.endsWith("xls") ? ExcelTypeEnum.XLS : ExcelTypeEnum.XLSX).sheet().doFill(data);
    }


    /**
     * 填充Excel
     *
     * @param response         HttpServletResponse
     * @param templateFilePath 模板路径
     * @param exportFileName   导出文件名称
     * @param data             填充数据 单个对象传Map,多条传List
     */
    public void fillExcelBySheetNum(HttpServletResponse response, String templateFilePath, String exportFileName, Object data, List<Integer> sheetnumList) throws IOException {
        setHeader(response, exportFileName);
        for (Integer integer : sheetnumList) {
            write(response.getOutputStream()).withTemplate(templateFilePath).sheet(integer).doFill(data);
        }
    }

    public void setHeader(HttpServletResponse response, String exportFileName) {
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // response.setContentType("application/vnd.ms-excel");
        // response.setContentType("application/x-www-form-urlencoded");
        // response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        // 下载文件能正常显示中文
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(exportFileName, "UTF-8") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


}

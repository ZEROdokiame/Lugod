package com.ruoyi.common.core.utils.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Excel数据格式处理适配器
 * 
 * @author ruoyi
 */
@SuppressWarnings("unused")
public interface ExcelHandlerAdapter {

    /**
     * 格式化
     *
     * @deprecated 弃用，使用两个方法分别处理导入导出数据格式化
     *
     * @param value 单元格数据值
     * @param args excel注解args参数组
     * @param cell 单元格对象
     * @param wb 工作簿对象
     * @return 处理后的值
     */
    @Deprecated
    default Object format(Object value, String[] args, Cell cell, Workbook wb) {
        return value;
    }

    /**
     * 导入时自定义数据处理器
     *
     * @param value 单元格数据
     * @param args excel注解args参数组
     * @param cell 单元格对象
     * @param wb 工作簿对象
     * @return 处理后的值
     */
    default Object importFormat(Object value, String[] args, Cell cell, Workbook wb) {
        return value;
    }

    /**
     * 导出时自定义数据处理器
     *
     * @param value 单元格数据
     * @param args excel注解args参数组
     * @param cell 单元格对象
     * @param wb 工作簿对象
     * @return 处理后的值
     */
    default Object exportFormat(Object value, String[] args, Cell cell, Workbook wb) {
        return value;
    }

}

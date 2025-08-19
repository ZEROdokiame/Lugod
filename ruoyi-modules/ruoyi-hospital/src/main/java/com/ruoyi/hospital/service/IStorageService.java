package com.ruoyi.hospital.service;

import java.util.List;
import com.ruoyi.hospital.domain.Storage;

/**
 * 药材库存管理Service接口
 * 
 * @author lugod
 * @date 2025-08-04
 */
public interface IStorageService 
{
    /**
     * 查询药材库存管理
     * 
     * @param id 药材库存管理主键
     * @return 药材库存管理
     */
     Storage selectStorageById(Long id);

    /**
     * 查询药材库存管理列表
     * 
     * @param storage 药材库存管理
     * @return 药材库存管理集合
     */
     List<Storage> selectStorageList(Storage storage);

    /**
     * 新增药材库存管理
     * 
     * @param storage 药材库存管理
     * @return 结果
     */
     int insertStorage(Storage storage);

    /**
     * 修改药材库存管理
     * 
     * @param storage 药材库存管理
     * @return 结果
     */
     int updateStorage(Storage storage);

    /**
     * 批量删除药材库存管理
     * 
     * @param ids 需要删除的药材库存管理主键集合
     * @return 结果
     */
     int deleteStorageByIds(Long[] ids);

    /**
     * 删除药材库存管理信息
     *
     * @param id 药材库存管理主键
     * @return 结果
     */
     int deleteStorageById(Long id);

    /**
     * 批量导入药材库存数据
     *
     * @param storageList 药材库存数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果信息
     */
     String importStorage(List<Storage> storageList, Boolean updateSupport, String operName);

    /**
     * 根据药材编码查询药材库存
     *
     * @param medicineCode 药材编码
     * @return 药材库存信息
     */
     Storage selectStorageByMedicineCode(String medicineCode);
}

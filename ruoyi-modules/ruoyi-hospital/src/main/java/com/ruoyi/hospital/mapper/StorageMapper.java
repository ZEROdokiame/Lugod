package com.ruoyi.hospital.mapper;

import java.util.List;
import com.ruoyi.hospital.domain.Storage;

/**
 * 药材库存管理Mapper接口
 * 
 * @author lugod
 * @date 2025-08-04
 */
public interface StorageMapper 
{
    /**
     * 查询药材库存管理
     * 
     * @param id 药材库存管理主键
     * @return 药材库存管理
     */
    public Storage selectStorageById(Long id);

    /**
     * 查询药材库存管理列表
     * 
     * @param storage 药材库存管理
     * @return 药材库存管理集合
     */
    public List<Storage> selectStorageList(Storage storage);

    /**
     * 新增药材库存管理
     * 
     * @param storage 药材库存管理
     * @return 结果
     */
    public int insertStorage(Storage storage);

    /**
     * 修改药材库存管理
     * 
     * @param storage 药材库存管理
     * @return 结果
     */
    public int updateStorage(Storage storage);

    /**
     * 删除药材库存管理
     * 
     * @param id 药材库存管理主键
     * @return 结果
     */
    public int deleteStorageById(Long id);

    /**
     * 批量删除药材库存管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStorageByIds(Long[] ids);
}

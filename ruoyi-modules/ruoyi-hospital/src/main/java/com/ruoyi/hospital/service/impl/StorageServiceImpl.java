package com.ruoyi.hospital.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.hospital.mapper.StorageMapper;
import com.ruoyi.hospital.domain.Storage;
import com.ruoyi.hospital.service.IStorageService;

/**
 * 药材库存管理Service业务层处理
 * 
 * @author lugod
 * @date 2025-08-04
 */
@Service
public class StorageServiceImpl implements IStorageService 
{
    @Autowired
    private StorageMapper storageMapper;

    /**
     * 查询药材库存管理
     * 
     * @param id 药材库存管理主键
     * @return 药材库存管理
     */
    @Override
    public Storage selectStorageById(Long id)
    {
        return storageMapper.selectStorageById(id);
    }

    /**
     * 查询药材库存管理列表
     * 
     * @param storage 药材库存管理
     * @return 药材库存管理
     */
    @Override
    public List<Storage> selectStorageList(Storage storage)
    {
        return storageMapper.selectStorageList(storage);
    }

    /**
     * 新增药材库存管理
     * 
     * @param storage 药材库存管理
     * @return 结果
     */
    @Override
    public int insertStorage(Storage storage)
    {
        storage.setCreateTime(DateUtils.getNowDate());
        return storageMapper.insertStorage(storage);
    }

    /**
     * 修改药材库存管理
     * 
     * @param storage 药材库存管理
     * @return 结果
     */
    @Override
    public int updateStorage(Storage storage)
    {
        storage.setUpdateTime(DateUtils.getNowDate());
        return storageMapper.updateStorage(storage);
    }

    /**
     * 批量删除药材库存管理
     * 
     * @param ids 需要删除的药材库存管理主键
     * @return 结果
     */
    @Override
    public int deleteStorageByIds(Long[] ids)
    {
        return storageMapper.deleteStorageByIds(ids);
    }

    /**
     * 删除药材库存管理信息
     * 
     * @param id 药材库存管理主键
     * @return 结果
     */
    @Override
    public int deleteStorageById(Long id)
    {
        return storageMapper.deleteStorageById(id);
    }
}

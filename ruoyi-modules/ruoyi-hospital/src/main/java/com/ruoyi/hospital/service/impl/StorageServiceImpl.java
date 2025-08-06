package com.ruoyi.hospital.service.impl;

import java.util.List;
import java.util.Date;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /**
     * 批量导入药材库存数据
     *
     * @param storageList 药材库存数据列表
     * @param updateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importStorage(List<Storage> storageList, Boolean updateSupport, String operName)
    {
        if (StringUtils.isNull(storageList) || storageList.size() == 0)
        {
            throw new RuntimeException("导入药材库存数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        int skipNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        StringBuilder skipMsg = new StringBuilder();

        for (Storage storage : storageList)
        {
            try
            {
                // 验证必填字段
                String validateResult = validateStorage(storage);
                if (StringUtils.isNotEmpty(validateResult))
                {
                    failureNum++;
                    failureMsg.append("<br/>").append(failureNum).append("、药材编码 ").append(storage.getMedicineCode()).append(" 导入失败：").append(validateResult);
                    continue;
                }

                // 验证这个药材编码是否存在
                Storage existStorage = this.selectStorageByMedicineCode(storage.getMedicineCode());
                if (StringUtils.isNull(existStorage))
                {
                    storage.setCreateBy(operName);
                    storage.setCreateTime(new Date());
                    this.insertStorage(storage);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、药材编码 ").append(storage.getMedicineCode()).append(" 导入成功");
                }
                else if (updateSupport)
                {
                    storage.setId(existStorage.getId());
                    storage.setUpdateBy(operName);
                    storage.setUpdateTime(new Date());
                    this.updateStorage(storage);
                    successNum++;
                    successMsg.append("<br/>").append(successNum).append("、药材编码 ").append(storage.getMedicineCode()).append(" 更新成功");
                }
                else
                {
                    // 跳过重复数据，不算失败
                    skipNum++;
                    skipMsg.append("<br/>").append(skipNum).append("、药材编码 ").append(storage.getMedicineCode()).append(" 已存在，跳过导入");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、药材编码 " + storage.getMedicineCode() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
            }
        }

        // 只有真正的失败才抛出异常回滚，跳过重复数据不算失败
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new RuntimeException(failureMsg.toString());
        }
        else
        {
            StringBuilder resultMsg = new StringBuilder();
            resultMsg.append("恭喜您，数据导入完成！");
            resultMsg.append("成功导入 " + successNum + " 条");
            if (skipNum > 0) {
                resultMsg.append("，跳过重复数据 " + skipNum + " 条");
            }
            resultMsg.append("。");

            if (successNum > 0) {
                resultMsg.append("成功导入的数据如下：").append(successMsg);
            }
            if (skipNum > 0) {
                resultMsg.append("跳过的数据如下：").append(skipMsg);
            }

            return resultMsg.toString();
        }
    }

    /**
     * 根据药材编码查询药材库存
     *
     * @param medicineCode 药材编码
     * @return 药材库存信息
     */
    @Override
    public Storage selectStorageByMedicineCode(String medicineCode)
    {
        return storageMapper.selectStorageByMedicineCode(medicineCode);
    }

    /**
     * 验证药材库存数据
     *
     * @param storage 药材库存数据
     * @return 验证结果
     */
    private String validateStorage(Storage storage)
    {
        if (StringUtils.isEmpty(storage.getMedicineName()))
        {
            return "药材名称不能为空";
        }
        if (StringUtils.isEmpty(storage.getMedicineCode()))
        {
            return "药材编码不能为空";
        }
        if (storage.getQuantity() == null || storage.getQuantity() < 0)
        {
            return "库存数量不能为空且不能小于0";
        }
        if (storage.getUnitPrice() != null && storage.getUnitPrice().doubleValue() < 0)
        {
            return "单价不能小于0";
        }
        if (storage.getMinStock() != null && storage.getMinStock() < 0)
        {
            return "最小库存预警值不能小于0";
        }
        return "";
    }
}

package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.MerchantMapper;
import com.ruoyi.system.domain.Merchant;
import com.ruoyi.system.service.IMerchantService;

/**
 * 商户Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
@Service
public class MerchantServiceImpl implements IMerchantService 
{
    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 查询商户
     * 
     * @param id 商户主键
     * @return 商户
     */
    @Override
    public Merchant selectMerchantById(Long id)
    {
        return merchantMapper.selectMerchantById(id);
    }

    /**
     * 查询商户列表
     * 
     * @param merchant 商户
     * @return 商户
     */
    @Override
    public List<Merchant> selectMerchantList(Merchant merchant)
    {
        return merchantMapper.selectMerchantList(merchant);
    }

    /**
     * 新增商户
     * 
     * @param merchant 商户
     * @return 结果
     */
    @Override
    public int insertMerchant(Merchant merchant)
    {
        merchant.setCreateTime(DateUtils.getNowDate());
        return merchantMapper.insertMerchant(merchant);
    }

    /**
     * 修改商户
     * 
     * @param merchant 商户
     * @return 结果
     */
    @Override
    public int updateMerchant(Merchant merchant)
    {
        merchant.setUpdateTime(DateUtils.getNowDate());
        return merchantMapper.updateMerchant(merchant);
    }

    /**
     * 批量删除商户
     * 
     * @param ids 需要删除的商户主键
     * @return 结果
     */
    @Override
    public int deleteMerchantByIds(Long[] ids)
    {
        return merchantMapper.deleteMerchantByIds(ids);
    }

    /**
     * 删除商户信息
     * 
     * @param id 商户主键
     * @return 结果
     */
    @Override
    public int deleteMerchantById(Long id)
    {
        return merchantMapper.deleteMerchantById(id);
    }
}

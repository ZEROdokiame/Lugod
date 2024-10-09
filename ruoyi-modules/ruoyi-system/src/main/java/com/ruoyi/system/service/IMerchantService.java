package com.ruoyi.system.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.common.core.domain.http.Merchant;
import com.ruoyi.common.core.web.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;

/**
 * 商户Service接口
 * 
 * @author ruoyi
 * @date 2024-09-15
 */
public interface IMerchantService extends IService<Merchant>
{
    /**
     * 查询商户
     * 
     * @param id 商户主键
     * @return 商户
     */
    public Merchant selectMerchantById(Long id);

    /**
     * 查询商户列表
     * 
     * @param merchant 商户
     * @return 商户集合
     */
    public List<Merchant> selectMerchantList(Merchant merchant);

    /**
     * 新增商户
     * 
     * @param merchant 商户
     * @return 结果
     */
    public int insertMerchant(Merchant merchant);

    /**
     * 修改商户
     * 
     * @param merchant 商户
     * @return 结果
     */
    public int updateMerchant(Merchant merchant);

    /**
     * 批量删除商户
     * 
     * @param ids 需要删除的商户主键集合
     * @return 结果
     */
    public int deleteMerchantByIds(Long[] ids);

    /**
     * 删除商户信息
     * 
     * @param id 商户主键
     * @return 结果
     */
    public int deleteMerchantById(Long id);

    /**
     * 获取基本合适的商户
     * @return
     */
    R<List<Merchant>> getMerchantList();

    /**
     * H5获取合适产品
     * @param request
     * @return
     */
    AjaxResult getMatchMerchantList(HttpServletRequest request);
    /**
     * 获取所有的商户列表
     * @return
     */
    public List<Merchant> findAllMerchantList();

    /**
     * H5申请订单
     * @param merchantId
     * @param request
     * @return
     */
    AjaxResult H5applyMerchant(Long merchantId, HttpServletRequest request);

    AjaxResult getMatchMerchantNew();
}

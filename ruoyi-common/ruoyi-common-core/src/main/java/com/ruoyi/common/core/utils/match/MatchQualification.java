package com.ruoyi.common.core.utils.match;

import com.ruoyi.common.core.domain.http.Customer;
import com.ruoyi.common.core.domain.http.Merchant;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class MatchQualification {

    /**
     * 是否可以匹配上产品
     * @param customer 用户信息
     * @param merchant 产品前筛
     *        openInfoFilter  开启资质筛选  0:不筛选, 1:满足1, 2:满足全部
     *
     * @return 结构
     */
    public static Boolean doesItMatchProduct(Customer customer, Merchant merchant) {
//        if (Objects.isNull(merchant)){
//            return true;
//        }
//
//        if (Objects.isNull(merchant.getCustomerInfoFilterType()) || merchant.getCustomerInfoFilterType() == 0){
//            return true;
//        }
//
//        //满足1个
//        boolean openOne = merchant.getCustomerInfoFilterType() == 1;
//        //满足所有
//        boolean openAll = merchant.getCustomerInfoFilterType() == 2;
//        //结果
//        boolean result = false;
//
//        String userInfo = customer.getId() + "-" + customer.getChannelId() + "-" + merchant.getId();
//
//        //社保
//        if (merchant.getSocialSecurityHigh() || merchant.getSocialSecurityNo() ||merchant.getSocialSecurityLow()) {
//            if (openAll && Objects.isNull(customer.getSocialSecurity())){
//                log.info("资质匹配筛选, 配置[满足所有]-社保为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            Integer infoByCode = customer.getSocialSecurity();
//            if (Objects.nonNull(customer.getSocialSecurity())) {
//                if (merchant.getSocialSecurityNo()) {
//                    if (customer.getSocialSecurity()==merchant.getSocialSecurityNo())) {
//                        result = true;
//                    }
//                }
//
//                if (merchant.getSocialSecurityLow()) {
//                    if (customer.getSocialSecurity().equals(CustomerSocialSecurityType.GREAT_SOCIAL_SECURITY.getCode())) {
//                        result = true;
//                    }
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
////                    log.info("资质匹配筛选, 配置[满足其一]-社保:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getSocialSecurity() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-社保:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getSocialSecurity() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //公积金
//        if (productLimit.getSmallProvidentFund() || productLimit.getGreatProvidentFund()) {
//            if (openAll && Objects.isNull(userDetailsDTO.getProvidentFund())){
//                log.info("资质匹配筛选, 配置[满足所有]-公积金为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = CustomerProvidentFundType.getInfoByCode(userDetailsDTO.getProvidentFund());
//            if (Objects.nonNull(userDetailsDTO.getProvidentFund())) {
//                if (productLimit.getSmallProvidentFund()) {
//                    if (userDetailsDTO.getProvidentFund().equals(CustomerProvidentFundType.SMALL_PROVIDENT_FUND.getCode())) {
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getGreatProvidentFund()) {
//                    if (userDetailsDTO.getProvidentFund().equals(CustomerProvidentFundType.GREAT_PROVIDENT_FUND.getCode())) {
//                        result = true;
//                    }
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
////                    log.info("资质匹配筛选, 配置[满足其一]-公积金:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getProvidentFund() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
////                log.info("资质匹配筛选, 配置[满足所有]-公积金:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getProvidentFund() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //车产
//        if (productLimit.getHavaCar()) {
//            if (openAll && Objects.isNull(userDetailsDTO.getCarProduction())){
//                log.info("资质匹配筛选, 配置[满足所有]-车产为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = CustomerCarProductionType.getInfoByCode(userDetailsDTO.getCarProduction());
//            if (Objects.nonNull(userDetailsDTO.getCarProduction())) {
//                if (userDetailsDTO.getCarProduction().equals(CustomerCarProductionType.HAVE_CAR.getCode())) {
//                    result = true;
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
//                    log.info("资质匹配筛选, 配置[满足其一]-车产:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getCarProduction() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-车产:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getCarProduction() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //房产
//        if (productLimit.getHaveMortgageRoom() || productLimit.getHaveFullRoom()) {
//            if (openAll && Objects.isNull(userDetailsDTO.getEstate())){
//                log.info("资质匹配筛选, 配置[满足所有]-房产为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = CustomerEstateType.getInfoByCode(userDetailsDTO.getEstate());
//            if (Objects.nonNull(userDetailsDTO.getEstate())) {
//                if (productLimit.getHaveMortgageRoom()){
//                    if (userDetailsDTO.getEstate().equals(CustomerEstateType.HAVE_PROPERTY.getCode())){
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getHaveFullRoom()){
//                    if (userDetailsDTO.getEstate().equals(CustomerEstateType.FULL_PAYMENT_FOR_HOUSING.getCode())){
//                        result = true;
//                    }
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
////                    log.info("资质匹配筛选, 配置[满足其一]-房产:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getEstate() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-房产:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getEstate() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //保单
//        if (productLimit.getPolicyLessThanOneYear() || productLimit.getPolicyPaymentForOneYear() || productLimit.getPolicyPaymentForTwoYear()) {
//            if (openAll && Objects.isNull(userDetailsDTO.getPersonalInsurance())){
//                log.info("资质匹配筛选, 配置[满足所有]-保单为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = CustomerInsurancePolicyType.getInfoByCode(userDetailsDTO.getPersonalInsurance());
//            if (Objects.nonNull(userDetailsDTO.getPersonalInsurance())) {
//                if (productLimit.getPolicyLessThanOneYear()) {
//                    if (userDetailsDTO.getPersonalInsurance().equals(CustomerInsurancePolicyType.POLICY_LESS_THAN_ONE_YEAR.getCode())) {
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getPolicyPaymentForOneYear()) {
//                    if (userDetailsDTO.getPersonalInsurance().equals(CustomerInsurancePolicyType.POLICY_PAYMENT_FOR_ONE_YEAR.getCode())) {
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getPolicyPaymentForTwoYear()) {
//                    if (userDetailsDTO.getPersonalInsurance().equals(CustomerInsurancePolicyType.POLICY_PAYMENT_FOR_TWO_YEAR.getCode())) {
//                        result = true;
//                    }
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
//                    log.info("资质匹配筛选, 配置[满足其一]-保单:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getPersonalInsurance() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-保单:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getPersonalInsurance() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //芝麻分
//        if (Objects.nonNull(productLimit.getSesame())){
//            if (openAll && Objects.isNull(userDetailsDTO.getSesame())){
//                log.info("资质匹配筛选, 配置[满足所有]-芝麻分为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            if (Objects.nonNull(userDetailsDTO.getSesame())) {
//                if (userDetailsDTO.getSesame() >= productLimit.getSesame()){
//                    result = true;
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
//                    log.info("资质匹配筛选, 配置[满足其一]-芝麻分:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getSesame(), userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-芝麻分:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getSesame(), userInfo);
//                return false;
//            }
//        }
//
//        //职业
//        if (productLimit.getOfficeWorker() ||
//                productLimit.getCivilServant() ||
//                productLimit.getPrivateOwners() ||
//                productLimit.getSmallPrivateBusiness() ||
//                productLimit.getOtherOccupations()) {
//
//            if (openAll && Objects.isNull(userDetailsDTO.getProfessionalIdentity())){
//                log.info("资质匹配筛选, 配置[满足所有]-职业为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = CustomerProfessionalIdentityType.getInfoByCode(userDetailsDTO.getProfessionalIdentity());
//            if (Objects.nonNull(userDetailsDTO.getProfessionalIdentity())) {
//                if (productLimit.getOfficeWorker()){
//                    if (userDetailsDTO.getProfessionalIdentity().equals(CustomerProfessionalIdentityType.OFFICE_WORKER.getCode())) {
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getCivilServant()){
//                    if (userDetailsDTO.getProfessionalIdentity().equals(CustomerProfessionalIdentityType.CIVIL_SERVANT.getCode())) {
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getPrivateOwners()){
//                    if (userDetailsDTO.getProfessionalIdentity().equals(CustomerProfessionalIdentityType.PRIVATE_OWNERS.getCode())) {
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getSmallPrivateBusiness()){
//                    if (userDetailsDTO.getProfessionalIdentity().equals(CustomerProfessionalIdentityType.SMALL_PRIVATE_BUSINESS.getCode())) {
//                        result = true;
//                    }
//                }
//
//                if (productLimit.getOtherOccupations()){
//                    if (userDetailsDTO.getProfessionalIdentity().equals(CustomerProfessionalIdentityType.OTHER_OCCUPATIONS.getCode())) {
//                        result = true;
//                    }
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
//                    log.info("资质匹配筛选, 配置[满足其一]-职业:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getProfessionalIdentity() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-职业:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getProfessionalIdentity() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //学历   字典映射 1: "初中及以下,2: "高中",3: "中专",4: "大专",5: "本科",6: "研究生及以上"
//        if (productLimit.getJuniorMiddleSchool() ||
//                productLimit.getSeniorMiddleSchool() ||
//                productLimit.getMiddleSchool() ||
//                productLimit.getCollege() ||
//                productLimit.getUndergraduate() ||
//                productLimit.getPostgraduate()) {
//
//            if (openAll && Objects.isNull(userDetailsDTO.getEducation())){
//                log.info("资质匹配筛选, 配置[满足所有]-学历为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = getEducationByCode(userDetailsDTO.getEducation());
//            if (Objects.nonNull(userDetailsDTO.getEducation())) {
//                if (productLimit.getJuniorMiddleSchool() && userDetailsDTO.getEducation() == 1) {   //初中
//                    result = true;
//                }
//
//                if (productLimit.getSeniorMiddleSchool() && userDetailsDTO.getEducation() == 2) {   //高中
//                    result = true;
//                }
//
//                if (productLimit.getMiddleSchool() && userDetailsDTO.getEducation() == 3) {   //中专
//                    result = true;
//                }
//
//                if (productLimit.getCollege() && userDetailsDTO.getEducation() == 4) {   //大专
//                    result = true;
//                }
//
//                if (productLimit.getUndergraduate() && userDetailsDTO.getEducation() == 5) {   //本科
//                    result = true;
//                }
//
//                if (productLimit.getPostgraduate() && userDetailsDTO.getEducation() == 6) {   //研究生
//                    result = true;
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
//                    log.info("资质匹配筛选, 配置[满足其一]-学历:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getEducation() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-学历:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getEducation() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //白条可用额度   字典映射 1: 无额度,2: 2000以下,3: 2000-10000,4: 大于10000
//        if (productLimit.getBaiTiaoLevelOne() ||
//                productLimit.getBaiTiaoLevelTwo() ||
//                productLimit.getBaiTiaoLevelThree()) {
//
//            if (openAll && Objects.isNull(userDetailsDTO.getBaiTiaoQuota())){
//                log.info("资质匹配筛选, 配置[满足所有]-白条为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = getBaiTiaoByCode(userDetailsDTO.getBaiTiaoQuota());
//            if (Objects.nonNull(userDetailsDTO.getBaiTiaoQuota())) {
//                if (productLimit.getBaiTiaoLevelOne() && userDetailsDTO.getBaiTiaoQuota() == 2) {   //2000以下
//                    result = true;
//                }
//
//                if (productLimit.getBaiTiaoLevelTwo() && userDetailsDTO.getBaiTiaoQuota() == 3) {   //2000-10000
//                    result = true;
//                }
//
//                if (productLimit.getBaiTiaoLevelThree() && userDetailsDTO.getBaiTiaoQuota() == 4) {   //大于10000
//                    result = true;
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
//                    log.info("资质匹配筛选, 配置[满足其一]-白条:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getBaiTiaoQuota() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-白条:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getBaiTiaoQuota() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //花呗可用额度   字典映射 1: 无额度,2: 2000以下,3: 2000-10000,4: 大于10000
//        if (productLimit.getHuaBeiLevelOne() ||
//                productLimit.getHuaBeiLevelTwo() ||
//                productLimit.getHuaBeiLevelThree()) {
//
//            if (openAll && Objects.isNull(userDetailsDTO.getHuaBeiQuota())){
//                log.info("资质匹配筛选, 配置[满足所有]-花呗为空, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//                return false;
//            }
//
//            //判断结果置默认
//            result = false;
//            String infoByCode = getBaiTiaoByCode(userDetailsDTO.getHuaBeiQuota());
//            if (Objects.nonNull(userDetailsDTO.getHuaBeiQuota())) {
//                if (productLimit.getHuaBeiLevelOne() && userDetailsDTO.getHuaBeiQuota() == 2) {   //2000以下
//                    result = true;
//                }
//
//                if (productLimit.getHuaBeiLevelTwo() && userDetailsDTO.getHuaBeiQuota() == 3) {   //2000-10000
//                    result = true;
//                }
//
//                if (productLimit.getHuaBeiLevelThree() && userDetailsDTO.getHuaBeiQuota() == 4) {   //大于10000
//                    result = true;
//                }
//
//                //条件为  满足一个即可, 恰巧现在就有满足的, 则返回true（通过）
//                if (openOne && result) {
//                    log.info("资质匹配筛选, 配置[满足其一]-花呗:{}, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getHuaBeiQuota() + "-" + infoByCode, userInfo);
//                    return result;
//                }
//            }
//
//            //条件为  满足全部, 恰巧现在这个不满足, 则返回false(不通过)
//            if (openAll && !result) {
//                log.info("资质匹配筛选, 配置[满足所有]-花呗:{}, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userDetailsDTO.getHuaBeiQuota() + "-" + infoByCode, userInfo);
//                return false;
//            }
//        }
//
//        //满足其一, 但结果为false, 则不通过
//        if (openOne && !result){
////            log.info("资质匹配筛选, 配置[满足其一]-未匹配成功, 匹配不通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//            return result;
//        }
//
//        if (openAll && result){
//            log.info("资质匹配筛选, 配置[满足所有]-匹配成功, 匹配通过, 结束匹配, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
//            return result;
//        }
//
//        log.info("资质匹配筛选, 此行日志我觉得不会输出, 用户id-渠道id-渠道名称-产品id:{}", userInfo);
        return true;
    }
}

package com.ruoyi.common.core.utils;


import com.ruoyi.common.core.domain.GuestProbabilityReq;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

/**
 * 计算助贷计划概率
 * @Author: daisi
 * @Date: 2022/4/2 11:10
 */
@Slf4j
public class ProbitUtil {
    public static GuestProbabilityReq calculatePlanTheProbability(List<GuestProbabilityReq> guestProbabilityReqs) {
        //按排序价格排序
        Collections.sort(guestProbabilityReqs, new Comparator<GuestProbabilityReq>() {
            @Override
            public int compare(GuestProbabilityReq o1, GuestProbabilityReq o2) {
                return o2.getOrderPrice().compareTo(o1.getOrderPrice());
            }
        });
        List<GuestProbabilityReq> list = new ArrayList<>(5);
        for (GuestProbabilityReq guestProbabilityReq : guestProbabilityReqs) {
//            if (guestProbabilityReq.getOrderPrice().compareTo(guestProbabilityReqs.get(0).getOrderPrice()) == 0) {
//                list.add(guestProbabilityReq);
//            }
            list.add(guestProbabilityReq);
        }
       log.info("排序后的数据:{}",list);
        //重置概率
        resetTranslate(list);
        int index = drawGift(list);
        return guestProbabilityReqs.get(index);
    }

    private static List<GuestProbabilityReq> resetTranslate(List<GuestProbabilityReq> reqs) {
        if (reqs.size()!=1){
            //高值得一部分
            int count = 0;
            //获取计数
            BigDecimal orderPrice = reqs.get(0).getOrderPrice();
            for (int i = 0; i < reqs.size(); i++) {
                if (i+1<reqs.size()&&reqs.get(i).getOrderPrice().compareTo(reqs.get(i+1).getOrderPrice())==0&&orderPrice.compareTo(reqs.get(i).getOrderPrice())==0){
                    count++;
                }
            }
            ///获取最高的概率
            BigDecimal bigDecimal = new BigDecimal(reqs.get(0).getGuestProbability().toString());
            if (count!=0){
                //用最高的概率除以计数 得到最高价的平均概率
                BigDecimal divide = bigDecimal.divide(new BigDecimal(count+1),3,BigDecimal.ROUND_DOWN);
                for (int i = 0; i <= count; i++) {
                    //循环重设最高概率
                    reqs.get(i).setGuestProbability(divide.doubleValue());
                }
            }
            //低值得一部分
            BigDecimal remTotal = new BigDecimal(1).subtract(bigDecimal);
            BigDecimal b = remTotal.divide(new BigDecimal((reqs.size()-count-1)==0?1:(reqs.size()-count-1)),3,BigDecimal.ROUND_HALF_UP);

            for (int i = count+1; i < reqs.size(); i++) {
                reqs.get(i).setGuestProbability(b.doubleValue());
            }

        }
        //log.info("重置概率后的概率,{}",reqs);
        return reqs;
    }

    public static int drawGift(List<GuestProbabilityReq> guestProbabilityReqList) {
        if (null != guestProbabilityReqList && guestProbabilityReqList.size() > 0) {
            List<Double> orgProbList = new ArrayList<Double>(guestProbabilityReqList.size());
            for (GuestProbabilityReq guest : guestProbabilityReqList) {
                //按顺序将概率添加到集合中
                orgProbList.add(guest.getGuestProbability());
            }
            return draw(orgProbList);
        }
        return -1;
    }
    public static int draw(List<Double> giftProbList) {
        List<Double> sortRateList = new ArrayList<Double>();
        // 计算概率总和
        Double sumRate = 0D;
        for (Double prob : giftProbList) {
            sumRate += prob;
        }
        if (sumRate != 0) {
            double rate = 0D;   //概率所占比例
            for (Double prob : giftProbList) {
                rate += prob;
                // 构建一个比例区段组成的集合(避免概率和不为1)
                sortRateList.add(rate / sumRate);
            }
            // 随机生成一个随机数，并排序
            double random = Math.random();
            sortRateList.add(random);
            Collections.sort(sortRateList);
            // 返回该随机数在比例集合中的索引
            return sortRateList.indexOf(random);
        }
        return -1;
    }

//    public static void main(String[] args) {
////        System.out.println(LocalDateTimeUtils.getStringFromLocalDateTime(LocalDateTimeUtil.beginOfDay(LocalDateTimeUtil.offset(LocalDateTime.now(), -7, ChronoUnit.DAYS))));
////        System.out.println(LocalDateTimeUtils.getStringFromLocalDateTime(LocalDateTimeUtil.endOfDay(LocalDateTimeUtil.offset(LocalDateTime.now(), -1, ChronoUnit.DAYS))));
//        int a=0;
//        List<GuestProbabilityReq> guestProbabilityReqs = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            List<GuestProbabilityReq> list = new ArrayList<>();
//            GuestProbabilityReq req = new GuestProbabilityReq();
//            req.setPlanId(1L).setOrderPrice(new BigDecimal(104.00)).setGuestProbability(0.8D);
//
//            GuestProbabilityReq req1 = new GuestProbabilityReq();
//            req1.setPlanId(2L).setOrderPrice(new BigDecimal(120)).setGuestProbability(0.8D);
//
//            GuestProbabilityReq req2 = new GuestProbabilityReq();
//            req2.setPlanId(3L).setOrderPrice(new BigDecimal(90)).setGuestProbability(0.8D);
//
//            GuestProbabilityReq req3 = new GuestProbabilityReq();
//            req3.setPlanId(4L).setOrderPrice(new BigDecimal(110)).setGuestProbability(0.4D);
//
//            GuestProbabilityReq req4 = new GuestProbabilityReq();
//            req4.setPlanId(5L).setOrderPrice(new BigDecimal(110)).setGuestProbability(0.6D);
////
////            GuestProbabilityReq req5 = new GuestProbabilityReq();
////            req5.setPlanId(6L).setOrderPrice(new BigDecimal(80)).setGuestProbability(0.6D);
//
//            list.add(req);
//            list.add(req1);
//            list.add(req2);
//            list.add(req3);
//            list.add(req4);
////            list.add(req5);
//
//            guestProbabilityReqs.add(calculatePlanTheProbability(list));
//
//        }
//        int b = 0;
//        int c = 0;
//        for (GuestProbabilityReq req:guestProbabilityReqs) {
//            if (req.getPlanId()==2){
//                b++;
//            }else {
//                c++;
//            }
//        }
//        System.out.println("几率："+c+"    "+b);
//    }

}

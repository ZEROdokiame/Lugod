package com.ruoyi.system;

import cn.hutool.json.JSONUtil;
import com.ruoyi.common.sms.component.SmsComponent;
import com.ruoyi.common.sms.entity.response.SmsResponse;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static cn.hutool.extra.spring.SpringUtil.getActiveProfile;

@SpringBootTest
public class RuoYiSystemApplicationTest {

    @Autowired
    SmsComponent smsComponent;

    @Test
    public void testGroupMsg(){

        String mobile = "15826189779,15826189779,17382317154";
        String content = "123456";

        SmsResponse groupMsg = smsComponent.sendGroupMsg(mobile, content);
        System.out.println(JSONUtil.toJsonStr(groupMsg));
    }

    @Test
    public void testP2pMsg(){

        String content = "123456";
        Map<String, String> params = new HashMap<>();
        params.put("15826189779", content);
        SmsResponse sendP2PMsg = smsComponent.sendP2PMsg(params);
        System.out.println(JSONUtil.toJsonStr(sendP2PMsg));
    }

    @Test
    public void getActivePro(){

        String activeProfile = getActiveProfile();
        System.out.println(activeProfile);
    }
}

package com.ruoyi.common.sms.component;

import com.ruoyi.common.sms.entity.response.SmsEntity;
import com.ruoyi.common.sms.entity.response.SmsResponse;
import com.ruoyi.common.sms.properties.XunDaYunXinProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
public class SmsComponent {
    @Autowired
    private XunDaYunXinProperties properties;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 点对点
     *
     * @param mobileContentKvp 号码内容键值对   示例 {"15100000000":"【测试】test1","15100000001":"【测试】test2"}
     * @return SmsResponse<SmsEntity>
     */
    public SmsResponse sendP2PMsg(Map<String, String> mobileContentKvp) {
        Map<String, Object> extraParams = new HashMap<>();

        for (Map.Entry<String, String> entry : mobileContentKvp.entrySet()) {
            String oldValue = entry.getValue();
            String newValue = replaceCode(properties.getTemplate(), oldValue);
            entry.setValue(newValue);
        }
        extraParams.put("mobileContentKvp", mobileContentKvp);
        return sendSmsRequest("p2p", extraParams, new ParameterizedTypeReference<SmsResponse>() {
        });
    }


    /**
     * 发送群发信息
     *
     * @param mobile  电话号码按照,分割 示例 "15100000000,15100000001"
     * @param content 内容 一般为验证码
     * @return SmsResponse<SmsEntity>
     */
    public SmsResponse<SmsEntity> sendGroupMsg(String mobile, String content) {
        Map<String, Object> extraParams = new HashMap<>();
        extraParams.put("mobile", removeDuplicates(mobile));
        extraParams.put("content", replaceCode(properties.getTemplate(), content));
        return sendSmsRequest("send", extraParams, new ParameterizedTypeReference<SmsResponse<SmsEntity>>() {
        });
    }


    public SmsResponse<?> checkBalance() {
        String url = properties.getBaseUrl() + "/smsv2";
        Map<String, Object> params = new HashMap<>();
        params.put("action", "balance");
        params.put("account", properties.getAccount());
        params.put("password", properties.getPassword());
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params);
        ResponseEntity<SmsResponse<?>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<SmsResponse<?>>() {
                }
        );
        return responseEntity.getBody();
    }

    /**
     * 发送短信请求的通用方法，适用于不同的短信操作。
     *
     * @param action       短信操作的类型，例如 "p2p"、"send" "balance"。
     * @param extraParams  附加的请求参数，具体取决于短信操作的需求。可以为 null。
     * @param responseType 返回值的泛型类型，用于指定响应的具体类型。
     * @param <T>          响应体中泛型的类型参数。
     * @return 包含响应结果的 SmsResponse 对象，响应体中可能包含不同类型的数据。
     */
    private <T> T sendSmsRequest(String action, Map<String, Object> extraParams, ParameterizedTypeReference<T> responseType) {
        // 拼接请求的 URL 地址
        String url = properties.getBaseUrl() + "/smsv2";

        // 创建请求参数 Map，并填充必需的账户信息和操作类型
        Map<String, Object> params = new HashMap<>();
        params.put("action", action);  // 设置请求的操作类型，如发送短信或查询余额
        params.put("account", properties.getAccount());  // 设置账户名
        params.put("password", properties.getPassword());  // 设置密码
        params.put("extno", properties.getExtno()); // 虚拟接入码

        // 如果有额外的参数，则将它们添加到请求参数中
        if (extraParams != null) {
            params.putAll(extraParams);
        }
        // 构建 HttpEntity 实体，包含请求参数
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(params);

        // 使用 RestTemplate 的 exchange 方法发送 POST 请求，并指定返回的泛型类型
        ResponseEntity<T> responseEntity = restTemplate.exchange(
                url,                               // 请求 URL
                HttpMethod.POST,                   // HTTP 方法类型为 POST
                requestEntity,                     // 请求体包含请求参数
                responseType                       // 指定返回类型的泛型引用，用于保留泛型信息
        );

        // 返回响应体（根据调用方法传入的类型）
        return responseEntity.getBody();
    }


    /**
     * 相同号码去重
     *
     * @param input 字符串电话号码 逗号分隔符分割
     * @return String 去重之后的手机电话号码
     */
    private String removeDuplicates(String input) {
        // 使用 LinkedHashSet 保持插入顺序并去重
        Set<String> uniqueSet = new LinkedHashSet<>(Arrays.asList(input.split(",")));
        if (uniqueSet.size() >= 1000) {
            throw new IllegalArgumentException("群发不建议超过1000个电话号码");
        }
        // 将去重后的集合转换回字符串
        return String.join(",", uniqueSet);
    }

    /**
     * 模板替换占位符
     *
     * @param template 模板
     * @param code     实际值
     * @return 替换之后的模板
     */
    public String replaceCode(String template, String code) {
        return template.replace("{code}", code);
    }


}

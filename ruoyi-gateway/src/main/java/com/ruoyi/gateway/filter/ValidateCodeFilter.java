package com.ruoyi.gateway.filter;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.utils.ServletUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.gateway.config.properties.CaptchaProperties;
import com.ruoyi.gateway.service.ValidateCodeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 验证码过滤器
 *
 * @author ruoyi
 */
@Component
public class ValidateCodeFilter extends AbstractGatewayFilterFactory<Object>
{
    private final static String[] VALIDATE_URL = new String[] { "/auth/login", "/auth/register" };

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private CaptchaProperties captchaProperties;

    private static final String CODE = "code";

    private static final String UUID = "uuid";

    @Override
    public GatewayFilter apply(Object config)
    {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            // 非登录/注册请求或验证码关闭，不处理
            if (!StringUtils.equalsAnyIgnoreCase(request.getURI().getPath(), VALIDATE_URL) || !captchaProperties.getEnabled())
            {
                return chain.filter(exchange);
            }

            return DataBufferUtils.join(request.getBody())
                .flatMap(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    String bodyString = new String(bytes, StandardCharsets.UTF_8);
                    
                    try {
                        JSONObject obj = JSON.parseObject(bodyString);
                        if (obj != null) {
                            validateCodeService.checkCaptcha(obj.getString(CODE), obj.getString(UUID));
                        } else {
                            return ServletUtils.webFluxResponseWriter(exchange.getResponse(), "验证码参数获取失败");
                        }
                    } catch (Exception e) {
                        return ServletUtils.webFluxResponseWriter(exchange.getResponse(), e.getMessage());
                    }
                    
                    // 重新包装请求，将请求体放回
                    DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
                    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(request) {
                        @Override
                        public Flux<DataBuffer> getBody() {
                            DataBuffer buffer = bufferFactory.wrap(bytes);
                            return Mono.just(buffer).flux();
                        }
                    };
                    
                    // 使用修改后的请求，继续过滤器链
                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
                })
                .onErrorResume(err -> {
                    return ServletUtils.webFluxResponseWriter(exchange.getResponse(), "验证码检查失败：" + err.getMessage());
                });
        };
    }
}

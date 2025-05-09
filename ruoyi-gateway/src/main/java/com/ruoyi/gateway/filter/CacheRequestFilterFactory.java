package com.ruoyi.gateway.filter;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 请求缓存过滤器，用于缓存请求数据
 * 
 * @author ruoyi
 */
@Component
public class CacheRequestFilterFactory extends AbstractGatewayFilterFactory<CacheRequestFilterFactory.Config> {

    private static final String[] EXCLUDE_URL = new String[] { "/auth/login", "/auth/register" };

    public CacheRequestFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("enabled");
    }

    public static class Config {
        private boolean enabled = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            // 判断是否启用缓存
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();
            
            // 登录和注册接口不应用此过滤器，避免与ValidateCodeFilter冲突
            for (String url : EXCLUDE_URL) {
                if (path.contains(url)) {
                    return chain.filter(exchange);
                }
            }
            
            // 只缓存POST、PUT等类型的请求体
            if (request.getMethod() == null || !request.getMethod().matches("POST|PUT|PATCH")) {
                return chain.filter(exchange);
            }

            // 获取响应的DataBufferFactory
            DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

            return DataBufferUtils.join(request.getBody())
                    .flatMap(dataBuffer -> {
                        byte[] bytes = new byte[dataBuffer.readableByteCount()];
                        dataBuffer.read(bytes);
                        DataBufferUtils.release(dataBuffer);
                        
                        // 创建缓存的请求实例
                        ServerHttpRequest cachedRequest = prepareRequest(exchange.getRequest(), bytes, bufferFactory);
                        
                        // 使用缓存的请求继续过滤器链
                        return chain.filter(exchange.mutate().request(cachedRequest).build());
                    });
        };
    }

    private ServerHttpRequest prepareRequest(ServerHttpRequest request, byte[] body, DataBufferFactory bufferFactory) {
        return new ServerHttpRequestDecorator(request) {
            @Override
            public Flux<DataBuffer> getBody() {
                if (body == null || body.length == 0) {
                    return Flux.empty();
                }
                DataBuffer buffer = bufferFactory.wrap(body);
                return Mono.just(buffer).flux();
            }
        };
    }
    
    /**
     * 重要：这个方法用于控制过滤器在配置中的名称
     * 根据错误信息，配置中使用的名称是CacheRequestFilter
     */
    @Override
    public String name() {
        return "CacheRequestFilter";
    }
}

package com.hmall.gateway.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class PrintAnyGatewayFilterFactory extends AbstractGatewayFilterFactory<PrintAnyGatewayFilterFactory.Config> {
    @Override
    public GatewayFilter apply(Config config) {
        return new OrderedGatewayFilter(new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                String name = config.getName();
                String value = config.getValue();
                System.out.println(name);
                System.out.println(value);
                return chain.filter(exchange);
            }
        },100);

    }

    @Data
    static class Config{
        private String name;
        private String value;
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("name", "value");
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }
}

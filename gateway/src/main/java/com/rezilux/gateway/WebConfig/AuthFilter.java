package com.rezilux.gateway.WebConfig;

import com.rezilux.gateway.Dto.UserDto;
import org.apache.http.HttpHeaders;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new RuntimeException("Missing token");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length != 2 || !"Bearer".equals(parts[0])) {
                throw new RuntimeException("Bad token format");
            }

            return webClientBuilder.build()
                    .post()
                    .uri("http://AUTH-SERVICE/auth/validateToken")
                    .header(HttpHeaders.AUTHORIZATION,"Bearer ",parts[1])
                    .retrieve()
                    .bodyToMono(UserDto.class)
                    .flatMap(userDto -> {
                        exchange.getRequest()
                                .mutate()
                                .header("x-auth-user-id", String.valueOf(userDto.getId()));
                        return chain.filter(exchange);
                    });
        };
    }
}

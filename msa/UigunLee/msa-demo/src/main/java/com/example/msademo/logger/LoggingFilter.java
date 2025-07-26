package com.example.msademo.logger;


import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/*
    1. 클라이언트가 요청을 보냄
    2. Gateway가 LoggingFilter를 호출함
    3. 요청 URI 출력하고 다음 필터(chain.filter)로 넘김
    4. 응답 상태 코드 출력
    5. 응답 전송
 */

// Gateway는 어디로 갈 것인지를 결정하는데 도움을 주는데
// 집 환경으로 생각하면 공유기의 역할을 한다고 생각하면 됩니다.

// Spring Cloud Gateway 에서
// Request(요청)과 Response(응답)에 대한 정보를 찍어주는 로그 필터입니다.
@Component
public class LoggingFilter implements GatewayFilter {
    // GatewayFilter 라는 인터페이스를 Implements 함으로서
    // 실제 GatewayFilter에 있는 filter 메서드를 구현해줘야 합나다.
    // 해당 인터페이스는 Spring Cloud Gateway 에서 Filter(필터)로 동작합니다.
    // 네트워크 라우팅 시 요청과 응답을 중간에 가로채서 다른 뭔가를 할 수 있게 만들어 줍니다.

    // filter 라는 녀석은 Gateway가 요청을 처리할 때 실행됨
    // ServerWebExchange 라는 녀석은 HTTP Request(요청)과 Response(응답)을 모두 관리함

    // GatewayFilterChain 이라는 녀석은 다음의 처리가 있을 경우
    // 다음 처리를 진행할 수 있도록 지원하는 녀석입니다.
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 실제 요청을 제대로 했는지 체크하기 위해 요청(Request) URI를 확인하는 작업입니다.
        // 요청과 응답을 모두 관리하는 'exchange' 에게 Request()를 획득하라고 합니다.
        // getRequest()로 요청(Request)를 획득한 이후 getURI()를 통해 요청(Request) URI를 확인할 수 있습니다.
        // '"Request URI: http://localhost:8080/api/user/1"' 형태로 결과가 출력됩니다.
        System.out.println("Request URI: " + exchange.getRequest().getURI());

        // 위에서는 Request(요청)에 대한 로깅을 했다면,
        // 이 파트에서는 Response(응답)에 대한 로깅을 진행합니다.
        // 실제 Response(응답)을 변환하기 전에 뭔가를 동작시키고 싶은 경우
        // 'beforeCommit90' 을 구동시키면 응답 변환 직전에 작업이 가능합니다.
        exchange.getResponse().beforeCommit(() -> {
            // 응답의 상태값을 확인하기 위해 'Response status' 를 확인합니다.
            // exchange는 위에서도 정리했듯이 Request(요청)과 Response(응답)을 관리합니다.
            // 그러므로 'getResponse()' 를 통해서 응답을 가져올 수 있습니다.
            // 상태값이라는 것은 postman을 실행하면 200 OK  , 404, 500 등등이 나타나는데,
            // 위의 상태값에 해당하는 정보를 의미합니다.
            System.out.println("Response status: " + exchange.getResponse().getStatusCode());
            return Mono.empty();
        });

        // Filter Chain을 호출하는 파트입니다.
        // 이 파트를 호출해야 다음 필터가 호출되거나 혹은 실제 서비스 코드로 요청이 전달되어 동작이 가능해 집니다.
        // 이 부분이 없으면 Gateway 에서 요청이 전달되지 않기 때문에 아무런 응답이 없어지게 됩니다.
        return chain.filter(exchange);
    }
}

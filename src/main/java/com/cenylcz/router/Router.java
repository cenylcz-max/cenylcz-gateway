package com.cenylcz.router;

import com.cenylcz.handler.TicketDispatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class Router {

    @Bean
    public RouterFunction<ServerResponse> RouterConfiguration(TicketDispatcher ticketDispatcher) {
        return RouterFunctions.nest(path("/cenylcz-gateway"),ticketRouterFunctions(ticketDispatcher));
    }

    private RouterFunction<ServerResponse> ticketRouterFunctions(TicketDispatcher ticketDispatcher) {
        return RouterFunctions.route(POST("/tickets/{entity}").and(contentType(MediaType.APPLICATION_JSON)), ticketDispatcher::create);
    }
}

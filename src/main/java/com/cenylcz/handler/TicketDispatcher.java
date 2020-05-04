package com.cenylcz.handler;

import com.cenylcz.domain.business.Ticket;
import com.cenylcz.service.KafkaService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TicketDispatcher {

    private final KafkaService kafkaService;

    public TicketDispatcher(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    public Mono<ServerResponse> create(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Ticket.class)
                .doOnNext(model -> kafkaService.createTicketEvent(model))
                .flatMap(result -> ServerResponse.status(HttpStatus.CREATED).body(Mono.just("Kafka Message created."), String.class));
    }
}

package com.cenylcz.handler;

import com.cenylcz.domain.business.Ticket;
import com.cenylcz.service.KafkaService;
import org.apache.commons.lang3.StringUtils;
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
        String entity = Ticket.class.getPackage().getName().concat(".").concat(StringUtils.capitalize(serverRequest.pathVariable("entity")));
        try {
            Class<? extends Ticket> modelClass = (Class<? extends Ticket>) Class.forName(entity);
            return serverRequest.bodyToMono(modelClass)
                    .doOnNext(model -> kafkaService.createTicketEvent(model))
                    .flatMap(result -> ServerResponse.status(HttpStatus.CREATED).body(Mono.just(true), Boolean.class));
        } catch (ClassNotFoundException e) {
            return ServerResponse.badRequest().build();
        }
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        String entity = Ticket.class.getPackage().getName().concat(".").concat(StringUtils.capitalize(serverRequest.pathVariable("entity")));
        try {
            Class<? extends Ticket> modelClass = (Class<? extends Ticket>) Class.forName(entity);
            return serverRequest.bodyToMono(modelClass)
                    .doOnNext(model -> kafkaService.updateTicketEvent(model))
                    .flatMap(result -> ServerResponse.status(HttpStatus.NO_CONTENT).body(Mono.just(true), Boolean.class));
        } catch (ClassNotFoundException e) {
            return ServerResponse.badRequest().build();
        }
    }
}

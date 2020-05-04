package com.cenylcz.service;

import com.cenylcz.Model;
import com.cenylcz.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {

    private final String topic;

    private final KafkaPublisher kafkaPublisher;

    public KafkaService(@Value("${kafka.topics.ticket}") String topic, KafkaPublisher kafkaPublisher) {
        this.topic = topic;
        this.kafkaPublisher = kafkaPublisher;
    }

    public void createTicketEvent(Model model) {
        this.kafkaPublisher.publishEvent(this.topic, model);
    }
}

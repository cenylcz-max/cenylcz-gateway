package com.cenylcz.kafka;

import com.cenylcz.event.Event;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

    private final KafkaTemplate<String, Event> kafkaTemplate;

    public KafkaPublisher(final KafkaTemplate<String, Event> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(final String topic, final Event event) {
        this.kafkaTemplate.send(topic, event);
    }
}

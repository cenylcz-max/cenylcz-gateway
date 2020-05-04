package com.cenylcz.kafka;

import com.cenylcz.Model;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaPublisher {

    private final KafkaTemplate<String, Model> kafkaTemplate;

    public KafkaPublisher(final KafkaTemplate<String, Model> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(final String topic, final Model model) {
        this.kafkaTemplate.send(topic, model);
    }
}
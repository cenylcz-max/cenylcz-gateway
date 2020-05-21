package com.cenylcz.service;

import com.cenylcz.domain.business.StockTrade;
import com.cenylcz.domain.business.Ticket;
import com.cenylcz.event.EventInfo;
import com.cenylcz.event.MetaData;
import com.cenylcz.event.Trade.StockTradeCreate;
import com.cenylcz.event.Trade.StockTradeUpdate;
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

    public void createTicketEvent(Ticket ticket) {
        MetaData metaData = new MetaData(EventInfo.GATEWAY_EVENT_SOURCE, EventInfo.GATEWAY_EVENT_VERSION);
        if (ticket instanceof StockTrade) {
            this.kafkaPublisher.publishEvent(this.topic, new StockTradeCreate(metaData, (StockTrade) ticket));
        }
    }

    public void updateTicketEvent(Ticket ticket) {
        MetaData metaData = new MetaData(EventInfo.GATEWAY_EVENT_SOURCE, EventInfo.GATEWAY_EVENT_VERSION);
        if (ticket instanceof StockTrade) {
            this.kafkaPublisher.publishEvent(this.topic, new StockTradeUpdate(metaData, (StockTrade) ticket));
        }
    }
}

package io.nadkarni.streamsproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {

    @Value("${topic}")
    private String topicName;

    @Value("${numberOfEvents}")
    private Integer numEvents;

    private final KafkaTemplate<String, String> kafkaTemplate;


    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send() {
        logger.info("Sending: " + numEvents);
        for (Integer ii = 0; ii < numEvents; ii++) {
            this.kafkaTemplate.send(topicName, ii.toString(), "Message " + ii.toString());
            logger.info("Sending " + ii);
        }
    }
}

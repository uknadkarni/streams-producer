package io.nadkarni.streamsproducer;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StreamsProducerApplication implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(StreamsProducerApplication.class);

    @Value("${topic}")
    private String topicName;

    @Value("${numPartitions}")
    private Integer numPartitions;

    @Value(("${replicationFactor}"))
    private Short replicationFactor;

    private final Producer producer;

    public StreamsProducerApplication(Producer producer) {
        this.producer = producer;
    }

    @Bean
    public NewTopic newTopic() {
        return new NewTopic(topicName, numPartitions, replicationFactor);
    }

    public static void main(String[] args) {
        SpringApplication.run(StreamsProducerApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        producer.send();
    }
}

package io.nadkarni.streamsproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka message producer service responsible for sending string messages to a Kafka topic.
 *
 * <p>This service encapsulates the logic for producing messages to Apache Kafka using
 * Spring Kafka's {@link KafkaTemplate}. It sends a configurable number of sequential
 * string messages with incremental keys and formatted values.</p>
 *
 * <h3>Message Format:</h3>
 * <ul>
 *   <li><strong>Key:</strong> Sequential integers starting from 0 (e.g., "0", "1", "2", ...)</li>
 *   <li><strong>Value:</strong> Formatted strings (e.g., "Message 0", "Message 1", "Message 2", ...)</li>
 * </ul>
 *
 * <h3>Configuration:</h3>
 * <p>The service is configured through application properties:</p>
 * <ul>
 *   <li>{@code topic} - The Kafka topic name to send messages to</li>
 *   <li>{@code numberOfEvents} - The total number of messages to produce</li>
 * </ul>
 *
 * <h3>Logging:</h3>
 * <p>The service provides comprehensive logging to track message production progress,
 * including the total number of messages to be sent and individual message confirmations.</p>
 *
 * @author Utkarsh Nadkarni
 * @version 1.0
 * @since 1.0
 */
@Service
public class Producer {

    /**
     * The name of the Kafka topic to send messages to.
     * Configured via the {@code topic} property in application.yaml.
     * Default value: "strings"
     */
    @Value("${topic}")
    private String topicName;

    /**
     * The total number of messages to produce and send to Kafka.
     * Configured via the {@code numberOfEvents} property in application.yaml.
     * Can be overridden via command line argument: --numberOfEvents=&lt;number&gt;
     * Default value: 100,000
     */
    @Value("${numberOfEvents}")
    private Integer numEvents;

    /**
     * Spring Kafka template for sending messages to Kafka topics.
     * Configured to use String serializers for both keys and values.
     * Handles connection management, serialization, and error handling automatically.
     */
    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Logger instance for tracking message production progress and debugging.
     */
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    /**
     * Constructor for dependency injection of the KafkaTemplate.
     *
     * <p>The KafkaTemplate is automatically configured by Spring Boot based on
     * the properties defined in application.yaml, including bootstrap servers,
     * key/value serializers, and other producer configurations.</p>
     *
     * @param kafkaTemplate The Spring Kafka template for message production,
     *                     configured with String key and value serializers
     */
    @Autowired
    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a configured number of sequential messages to the specified Kafka topic.
     *
     * <p>This method produces messages in a simple loop, sending each message synchronously
     * to the Kafka topic. Each message consists of:</p>
     * <ul>
     *   <li><strong>Key:</strong> String representation of the message index (0, 1, 2, ...)</li>
     *   <li><strong>Value:</strong> Formatted message string ("Message 0", "Message 1", ...)</li>
     * </ul>
     *
     * <p>The method logs the total number of messages to be sent at the beginning,
     * and logs each individual message as it's being sent for progress tracking.</p>
     *
     * <h3>Message Distribution:</h3>
     * <p>Messages are distributed across topic partitions based on the key hash,
     * ensuring even distribution when multiple partitions are configured.</p>
     *
     * <h3>Error Handling:</h3>
     * <p>The KafkaTemplate handles retries and error scenarios automatically based on
     * the producer configuration. Failed messages will be retried according to the
     * configured retry policy.</p>
     *
     * <h3>Performance Considerations:</h3>
     * <p>This implementation sends messages synchronously in a loop. For high-throughput
     * scenarios, consider implementing asynchronous sending with callbacks or batching.</p>
     *
     * @throws org.springframework.kafka.KafkaException if there are issues connecting to Kafka
     *         or sending messages after all retry attempts are exhausted
     */
    public void send() {
        logger.info("Sending: " + numEvents);
        for (Integer ii = 0; ii < numEvents; ii++) {
            this.kafkaTemplate.send(topicName, ii.toString(), "Message " + ii.toString());
            logger.info("Sending " + ii);
        }
    }
}

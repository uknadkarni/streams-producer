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

/**
 * Main Spring Boot application class for the Kafka Producer demonstration.
 *
 * <p>This application demonstrates basic Kafka message production using Spring Boot and Spring Kafka.
 * It automatically creates a Kafka topic with configurable parameters and sends a specified number
 * of string messages to that topic.</p>
 *
 * <p>The application implements {@link ApplicationRunner} to execute the message production logic
 * immediately after the Spring Boot application context is fully initialized.</p>
 *
 * <h3>Key Features:</h3>
 * <ul>
 *   <li>Automatic Kafka topic creation with configurable partitions and replication factor</li>
 *   <li>Configurable number of messages to produce</li>
 *   <li>Simple string message format with sequential keys and values</li>
 *   <li>Comprehensive logging of message production progress</li>
 * </ul>
 *
 * <h3>Configuration Properties:</h3>
 * <ul>
 *   <li>{@code topic} - Name of the Kafka topic to create and produce to</li>
 *   <li>{@code numPartitions} - Number of partitions for the topic</li>
 *   <li>{@code replicationFactor} - Replication factor for the topic</li>
 * </ul>
 *
 * @author Utkarsh Nadkarni
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication
public class StreamsProducerApplication implements ApplicationRunner {

    /**
     * Logger instance for this class to track application startup and execution flow.
     */
    private Logger logger = LoggerFactory.getLogger(StreamsProducerApplication.class);

    /**
     * The name of the Kafka topic to create and produce messages to.
     * Configured via the {@code topic} property in application.yaml.
     * Default value: "strings"
     */
    @Value("${topic}")
    private String topicName;

    /**
     * The number of partitions to create for the Kafka topic.
     * More partitions allow for better parallelism and throughput.
     * Configured via the {@code numPartitions} property in application.yaml.
     * Default value: 5
     */
    @Value("${numPartitions}")
    private Integer numPartitions;

    /**
     * The replication factor for the Kafka topic.
     * Determines how many copies of each partition are maintained across brokers.
     * Configured via the {@code replicationFactor} property in application.yaml.
     * Default value: 1
     */
    @Value(("${replicationFactor}"))
    private Short replicationFactor;

    /**
     * The Producer service responsible for sending messages to Kafka.
     * Injected via constructor dependency injection.
     */
    private final Producer producer;

    /**
     * Constructor for dependency injection.
     *
     * @param producer The Producer service that will handle message production to Kafka
     */
    public StreamsProducerApplication(Producer producer) {
        this.producer = producer;
    }

    /**
     * Creates a Spring Bean for the Kafka topic configuration.
     *
     * <p>This method automatically creates a Kafka topic with the specified configuration
     * when the application starts. If the topic already exists, this operation is idempotent
     * and will not cause any issues.</p>
     *
     * @return A {@link NewTopic} instance configured with the topic name, partition count,
     *         and replication factor from the application properties
     */
    @Bean
    public NewTopic newTopic() {
        return new NewTopic(topicName, numPartitions, replicationFactor);
    }

    /**
     * Main entry point for the Spring Boot application.
     *
     * <p>This method starts the Spring Boot application context and triggers the
     * execution of the {@link #run(ApplicationArguments)} method once the context
     * is fully initialized.</p>
     *
     * @param args Command line arguments passed to the application.
     *             Supports standard Spring Boot arguments including:
     *             <ul>
     *               <li>--numberOfEvents=&lt;number&gt; - Override the number of messages to send</li>
     *               <li>--spring.profiles.active=&lt;profile&gt; - Activate specific Spring profiles</li>
     *             </ul>
     */
    public static void main(String[] args) {
        SpringApplication.run(StreamsProducerApplication.class, args);
    }

    /**
     * Executes the main application logic after Spring Boot startup is complete.
     *
     * <p>This method is called automatically by Spring Boot after the application context
     * is fully initialized. It triggers the message production process by calling the
     * {@link Producer#send()} method.</p>
     *
     * <p>The application will terminate after all messages have been sent successfully.</p>
     *
     * @param args Application arguments passed from the command line
     * @throws Exception If any error occurs during message production
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        producer.send();
    }
}

/**
 * Kafka Producer demonstration application using Spring Boot and Spring Kafka.
 * 
 * <p>This package contains a complete Spring Boot application that demonstrates
 * basic Kafka message production patterns. The application automatically creates
 * a Kafka topic and sends a configurable number of string messages to that topic.</p>
 * 
 * <h2>Package Overview</h2>
 * 
 * <p>The package is organized into the following main components:</p>
 * 
 * <h3>Core Classes:</h3>
 * <ul>
 *   <li>{@link io.nadkarni.streamsproducer.StreamsProducerApplication} - Main Spring Boot application class</li>
 *   <li>{@link io.nadkarni.streamsproducer.Producer} - Kafka message producer service</li>
 * </ul>
 * 
 * <h2>Application Architecture</h2>
 * 
 * <p>The application follows a simple layered architecture:</p>
 * <pre>
 * ┌─────────────────────────────────────┐
 * │     StreamsProducerApplication      │  ← Main application entry point
 * │     (ApplicationRunner)             │
 * └─────────────────┬───────────────────┘
 *                   │ delegates to
 *                   ▼
 * ┌─────────────────────────────────────┐
 * │           Producer                  │  ← Message production service
 * │         (@Service)                  │
 * └─────────────────┬───────────────────┘
 *                   │ uses
 *                   ▼
 * ┌─────────────────────────────────────┐
 * │        KafkaTemplate                │  ← Spring Kafka abstraction
 * │    (Auto-configured)                │
 * └─────────────────┬───────────────────┘
 *                   │ connects to
 *                   ▼
 * ┌─────────────────────────────────────┐
 * │        Apache Kafka                 │  ← Message broker
 * │         Cluster                     │
 * └─────────────────────────────────────┘
 * </pre>
 * 
 * <h2>Message Format</h2>
 * 
 * <p>The application produces messages with the following structure:</p>
 * <ul>
 *   <li><strong>Topic:</strong> Configurable (default: "strings")</li>
 *   <li><strong>Key:</strong> Sequential integers as strings ("0", "1", "2", ...)</li>
 *   <li><strong>Value:</strong> Formatted message strings ("Message 0", "Message 1", ...)</li>
 *   <li><strong>Partitioning:</strong> Based on key hash for even distribution</li>
 * </ul>
 * 
 * <h2>Configuration</h2>
 * 
 * <p>The application is configured through {@code application.yaml} with the following key properties:</p>
 * 
 * <h3>Kafka Configuration:</h3>
 * <ul>
 *   <li>{@code spring.kafka.producer.bootstrap-servers} - Kafka broker addresses</li>
 *   <li>{@code spring.kafka.producer.key-serializer} - Key serialization strategy</li>
 *   <li>{@code spring.kafka.producer.value-serializer} - Value serialization strategy</li>
 * </ul>
 * 
 * <h3>Application Configuration:</h3>
 * <ul>
 *   <li>{@code topic} - Target Kafka topic name</li>
 *   <li>{@code numberOfEvents} - Number of messages to produce</li>
 *   <li>{@code numPartitions} - Topic partition count</li>
 *   <li>{@code replicationFactor} - Topic replication factor</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * 
 * <h3>Default Execution:</h3>
 * <pre>
 * java -jar streams-producer-0.0.1-SNAPSHOT.jar
 * </pre>
 * <p>Sends 100,000 messages to the "strings" topic.</p>
 * 
 * <h3>Custom Message Count:</h3>
 * <pre>
 * java -jar streams-producer-0.0.1-SNAPSHOT.jar --numberOfEvents=1000
 * </pre>
 * <p>Sends 1,000 messages to the "strings" topic.</p>
 * 
 * <h3>Maven Execution:</h3>
 * <pre>
 * mvn spring-boot:run -Dspring-boot.run.arguments="--numberOfEvents=5000"
 * </pre>
 * 
 * <h2>Dependencies</h2>
 * 
 * <p>Key dependencies used by this application:</p>
 * <ul>
 *   <li><strong>Spring Boot:</strong> Application framework and auto-configuration</li>
 *   <li><strong>Spring Kafka:</strong> Kafka integration and KafkaTemplate</li>
 *   <li><strong>Apache Kafka Streams:</strong> Kafka client libraries</li>
 *   <li><strong>SLF4J/Logback:</strong> Logging framework</li>
 *   <li><strong>Lombok:</strong> Code generation for boilerplate reduction</li>
 * </ul>
 * 
 * <h2>Error Handling</h2>
 * 
 * <p>The application includes basic error handling through Spring Kafka's built-in mechanisms:</p>
 * <ul>
 *   <li><strong>Connection Failures:</strong> Automatic retry with exponential backoff</li>
 *   <li><strong>Serialization Errors:</strong> Logged and application continues</li>
 *   <li><strong>Broker Unavailability:</strong> Retries until timeout or success</li>
 * </ul>
 * 
 * <h2>Performance Considerations</h2>
 * 
 * <p>Current implementation characteristics:</p>
 * <ul>
 *   <li><strong>Synchronous Sending:</strong> Messages sent one at a time in a loop</li>
 *   <li><strong>Logging Overhead:</strong> Each message is logged individually</li>
 *   <li><strong>Single-threaded:</strong> No parallel message production</li>
 * </ul>
 * 
 * <p>For high-throughput scenarios, consider:</p>
 * <ul>
 *   <li>Asynchronous message sending with callbacks</li>
 *   <li>Batch message production</li>
 *   <li>Reduced logging frequency</li>
 *   <li>Multi-threaded producer implementation</li>
 * </ul>
 * 
 * <h2>Testing</h2>
 * 
 * <p>The package includes basic integration tests that verify:</p>
 * <ul>
 *   <li>Spring Boot application context loading</li>
 *   <li>Bean creation and dependency injection</li>
 *   <li>Configuration property binding</li>
 * </ul>
 * 
 * <p>For comprehensive testing, consider adding:</p>
 * <ul>
 *   <li>Unit tests with mocked dependencies</li>
 *   <li>Integration tests with embedded Kafka</li>
 *   <li>Performance and load testing</li>
 *   <li>Error scenario testing</li>
 * </ul>
 * 
 * @author Utkarsh Nadkarni
 * @version 1.0
 * @since 1.0
 */
package io.nadkarni.streamsproducer;

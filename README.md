# streams-producer

A Spring Boot Kafka producer application that demonstrates basic Kafka message publishing functionality.

## Overview

This application is a simple Kafka producer built with Spring Boot and Spring Kafka. It automatically creates a Kafka topic and sends a configurable number of string messages to that topic. The application is designed as a demonstration tool for Kafka message production patterns.

## Features

- **Automatic Topic Creation**: Creates a Kafka topic with configurable partitions and replication factor
- **Configurable Message Volume**: Send a customizable number of messages (default: 100,000)
- **String Message Format**: Sends simple string messages with incremental keys and values
- **Spring Boot Integration**: Uses Spring Kafka for seamless Kafka integration
- **Logging**: Comprehensive logging of message sending progress

## Technical Details

- **Framework**: Spring Boot 2.3.2
- **Java Version**: 11
- **Kafka Client**: Apache Kafka Streams and Spring Kafka
- **Default Topic**: `strings`
- **Default Message Count**: 100,000 messages
- **Topic Configuration**: 5 partitions, replication factor of 1
- **Message Format**: Key: sequential integers (0, 1, 2...), Value: "Message 0", "Message 1", etc.

## Configuration

The application can be configured via `application.yaml`:

- `topic`: Kafka topic name (default: "strings")
- `numberOfEvents`: Number of messages to send (default: 100,000)
- `numPartitions`: Number of topic partitions (default: 5)
- `replicationFactor`: Topic replication factor (default: 1)
- `spring.kafka.producer.bootstrap-servers`: Kafka broker addresses (default: localhost:9092)

## Prerequisites

- Java 11 or higher
- Apache Kafka running on localhost:9092 (or configure different broker addresses)
- Maven 3.6+ (or use included Maven wrapper)

For local Kafka setup, see [Confluent Platform Documentation](https://docs.confluent.io/current/cli/command-reference/confluent-local/confluent_local_start.html)

## Usage

### Build the Application

Using Maven:
```bash
mvn clean package
```

Using Maven wrapper:
```bash
./mvnw clean package
```

### Run the Application

**Default configuration (100,000 messages):**
```bash
java -jar target/streams-producer-0.0.1-SNAPSHOT.jar
```

**Custom number of events:**
```bash
java -jar target/streams-producer-0.0.1-SNAPSHOT.jar --numberOfEvents=1000
```

**Using Spring Boot Maven plugin:**
```bash
mvn spring-boot:run
```

**With custom parameters:**
```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--numberOfEvents=5000"
```

## Application Behavior

1. **Startup**: Application starts and connects to Kafka
2. **Topic Creation**: Automatically creates the configured topic if it doesn't exist
3. **Message Production**: Sends the specified number of messages sequentially
4. **Logging**: Logs progress for each message sent
5. **Shutdown**: Application terminates after all messages are sent

## Project Structure

```
src/
├── main/
│   ├── java/io/nadkarni/streamsproducer/
│   │   ├── StreamsProducerApplication.java  # Main application class
│   │   └── Producer.java                    # Kafka producer service
│   └── resources/
│       └── application.yaml                 # Application configuration
└── test/
    └── java/io/nadkarni/streamsproducer/
        └── StreamsProducerApplicationTests.java  # Basic tests
```

## Dependencies

Key dependencies include:
- Spring Boot Starter Actuator
- Apache Kafka Streams
- Spring Kafka
- Spring Boot DevTools
- Lombok
- Spring Boot Test Starter


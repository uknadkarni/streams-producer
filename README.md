# streams-producer

A basic sample Spring Boot KStreams application.
This application sends 10000 events to Kafka. The number of events sent can be overriden by providing the option `--numberOfEvents` at the command line.This expects Confluent Platform to be running on the localhost on port 9092. See [Confluent Docs](https://docs.confluent.io/current/cli/command-reference/confluent-local/confluent_local_start.html) to set up Confluent Platform locally.

### Usage
To Build
`mvn package`

To Run
`java -jar target/streams-producer-0.0.1-SNAPSHOT.jar`
OR
`java -jar target/streams-producer-0.0.1-SNAPSHOT.jar --numberOfEvents=<number of events>`

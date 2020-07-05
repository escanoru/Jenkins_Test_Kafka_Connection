Kafka Simple Producer Pipeline
=========================================
Simple Jenkins pipeline which uses a golang kafka producer to test send messages to a kafka topic. The Jenkinsfile is configured to send 10 messages, this of course can be modified.

You must compile the golang kafka producer before trying to use this job, in this Jenkinsfile it's assumed that the the producer has the name "GO_kafka-simpler-producer_linux" and it's placed under /opt/.

Golang's producer code:
https://github.com/Shopify/sarama/blob/master/tools/kafka-console-producer/kafka-console-producer.go
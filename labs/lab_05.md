# Spark Streaming and Apache Kafka
### Sơ đồ tương quan giữa Kafka và Spark.
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/Kafka-Spark-Streaming.png?raw=true)

### Sơ đồ Apache Kafka.
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/KafkaSparkStreamingDiagram.png?raw=true)

### Sơ đồ Apache Spark.
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/SparkClusterDiagram1.png?raw=true)

## Build code 
```
cd SparkStreamingKafka
mvn clean compile assembly:single
```

## Chạy ứng dụng Consumer Spark Streaming.
### Truy cập [GoogleCloud-EC](https://github.com/anhtp4495/spark-lab/blob/main/h%C6%B0%E1%BB%9Bng-d%E1%BA%ABn/H%C6%B0%E1%BB%9Bng%20d%E1%BA%ABn%20s%E1%BB%AD%20d%E1%BB%A5ng%20SSH%20t%E1%BB%9Bi%20Server.md), mở 2 shell
### Tại shell 1 chạy Consumer

```
source spark-environment.env
spark-submit --master spark://spark-master-app.com:7077 /apps/KafkaConsumers.jar 34.125.182.252:9092 0 spark-transaction
```

### Tại shell 2 chạy Producer
Chạy Producer
```
java -jar KafkaTransactionProducer-0.0.1-SNAPSHOT-jar-with-dependencies.jar 34.125.182.252:9092 spark-transaction 5 100 100 Producer_001
java -jar KafkaTransactionProducer-0.0.1-SNAPSHOT-jar-with-dependencies.jar 34.125.182.252:9092 spark-transaction 5 100 100 Producer_002
```

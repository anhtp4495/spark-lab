# Thao tác với Apache Kafka
Dựa vào hệ thống Apache Kafka cài đặt tại [hướng dẫn](https://github.com/anhtp4495/spark-lab/blob/main/hướng-dẫn/H%C6%B0%E1%BB%9Bng%20d%E1%BA%ABn%20c%C3%A0i%20%C4%91%E1%BA%B7t%20Apache%20Kafka.md), chúng ta tạo **Producer** và **Consumer** tương tác với hệ thống Apache Kafka.

Sơ đồ thực nghiệm.
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/KafkaDiagram1.jpg?raw=true)


Mở 3 shell, trong đó 2 shell sẽ làm Producer và 1 shell làm Consumer.
Nếu chưa có máy chạy Linux OS thì kết nối đến GoogleCloud EC để thực hành [làm theo hướng dẫn](https://github.com/anhtp4495/spark-lab/blob/main/h%C6%B0%E1%BB%9Bng-d%E1%BA%ABn/H%C6%B0%E1%BB%9Bng%20d%E1%BA%ABn%20s%E1%BB%AD%20d%E1%BB%A5ng%20SSH%20t%E1%BB%9Bi%20Server.md)

## Build ứng dụng Java chạy Producer
```
cd spark-lab/src/KafkaTransactionProducer
mvn clean compile assembly:single
```

java -jar KafkaTransactionProducer-0.0.1-SNAPSHOT-jar-with-dependencies.jar <kafka-server-ip>:9092 <kafka-topic> <số message gửi 1 lần> <số lần lặp> <thời gian chờ mỗi lần lặp> <producer-name>
  
Tạo Producer 1
```
java -jar KafkaTransactionProducer-0.0.1-SNAPSHOT-jar-with-dependencies.jar 34.125.182.252:9092 kafka-test 5 100 100 Producer_001
```
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/Kafka-Producer-1.png?raw=true)

Tạo Producer 2
```
java -jar KafkaTransactionProducer-0.0.1-SNAPSHOT-jar-with-dependencies.jar 34.125.182.252:9092 kafka-test 5 100 100 Producer_002
```
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/Kafka-Producer-2.png?raw=true)
  
Tạo Consumer
```
kafka-environment.env
kafka-console-consumer.sh --topic kafka-test --bootstrap-server 34.125.182.252:9092
```
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/Kafka-Consumer.png?raw=true)

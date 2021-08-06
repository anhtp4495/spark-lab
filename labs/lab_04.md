# Thực hành Spark Streaming
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/Spark-Streaming-Basic.png?raw=true)


## Build code
```
cd spark-lab/src/SparkStreamingBasic
mvn clean compile assembly:single
```
## Chạy ứng dụng Spark MLLib
SparkStreaming-0.0.1-SNAPSHOT-jar-with-dependencies.jar <host> <port> <duration>
```
spark-submit --master local[*] target\SparkStreaming-0.0.1-SNAPSHOT-jar-with-dependencies.jar 127.0.0.1 9999 5
```

Mở shell script và listen port 9999
```
nc -l -p 9999
```
Nhập một vài đoạn text, SparkStreaming App sẽ tiến hành Workcount.
  
![alt-text](https://github.com/anhtp4495/spark-lab/blob/main/resources/Spark-Streaming-Basic-Example.png?raw=true)

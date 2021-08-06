# Hướng dẫn cài đặt Apache Kafka
Truy cập đường dẫn https://kafka.apache.org/downloads và chọn bản cài đặt tương ứng
```
wget https://www.apache.org/dyn/closer.cgi?path=/kafka/2.7.1/kafka-2.7.1-src.tgz
```
Giải nén 
```
tar -xzvf kafka-2.7.1-src.tgz
```
Kafka đã cài đặt xong! Tiếp đến thiết lập biến môi trường để chạy ứng dụng.
Tạo file kafka-environment.env
```
echo "export KAFKA_HOME/home/atr_pt_vn/kafka_2.12-2.7.1" >> ~/kafka-environment.env
echo "export PATH=$PATH:$KAFKA_HOME/bin" >> ~/kafka-environment.env
```
Nạp biến môi trường vào session hiện tại
```
source kafka-environment.env
```

# Thiết lập Kafka với 3 brokers
![alt text](https://github.com/anhtp4495/spark-lab/blob/main/resources/KafkaDiagram.png?raw=true)

Khởi động zookeeper
```
zookeeper-server-start.sh -daemon $KAFKA_HOME/config/zookeeper.properties
```
zookeeper sẽ lắng nghe tại port 2181, xem file zookeeper.properties
```
# the directory where the snapshot is stored.
dataDir=/tmp/zookeeper
# the port at which the clients will connect
clientPort=2181
```

Khởi động broker1, broker2, broker3
Đầu tiên tạo config file tương ứng cho broker. Chúng ta copy từ config có sẵn thành 3 files
```
cp $KAFKA_HOME/config/server.properties $KAFKA_HOME/config/server1.properties
cp $KAFKA_HOME/config/server.properties $KAFKA_HOME/config/server2.properties
cp $KAFKA_HOME/config/server.properties $KAFKA_HOME/config/server3.properties
```
Chỉnh sửa file config của broker, trong này có 3 thông tin quan trọng cần lưu ý
broker.id: Là id của broker, nó là duy nhất
port: port của broker lắng nghe
log.dirs: thư mục chứa log của broker
 
$KAFKA_HOME/config/server1.properties
```
############################# Server Basics #############################

# The id of the broker. This must be set to a unique integer for each broker.
broker.id=1

############################# Socket Server Settings #############################
port=9092

############################# Log Basics #############################
# A comma separated list of directories under which to store log files
log.dirs=/tmp/kafka-logs-1
```

$KAFKA_HOME/config/server2.properties
```
############################# Server Basics #############################

# The id of the broker. This must be set to a unique integer for each broker.
broker.id=2

############################# Socket Server Settings #############################
port=9093

############################# Log Basics #############################
# A comma separated list of directories under which to store log files
log.dirs=/tmp/kafka-logs-2
```

$KAFKA_HOME/config/server3.properties
```
############################# Server Basics #############################

# The id of the broker. This must be set to a unique integer for each broker.
broker.id=3

############################# Socket Server Settings #############################
port=9094

############################# Log Basics #############################
# A comma separated list of directories under which to store log files
log.dirs=/tmp/kafka-logs-3
```

Khởi động Broker1, Broker2, Broker3
```
kafka-server-start.sh -daemon $KAFKA_HOME/config/server1.properties
kafka-server-start.sh -daemon $KAFKA_HOME/config/server2.properties
kafka-server-start.sh -daemon $KAFKA_HOME/config/server3.properties
```
Liệt kê các brokers đang chạy trong zookeeper
```
zookeeper-shell.sh localhost:2181 ls /brokers/ids
```

Tạo topic kafkatest 1 broker, 1 partition
```
kafka-topics.sh --create --zookeeper localhost:2181 --topic kafka-test --replication-factor 1 --partitions 1
```

Tạo topic kafka-transaction 3 brokers, 3 partitions
```
kafka-topics.sh --create --zookeeper localhost:2181 --topic kafka-transaction --replication-factor 3 --partitions 3
```

Hiển thị các danh sách topics
```
kafka-topics.sh --list --zookeeper localhost:2181
```
Hiển thị các thông tin partitions, brokers của các topics
```
kafka-topics.sh --describe --zookeeper localhost:2181
```

![alt text](https://github.com/anhtp4495/spark-lab/blob/main/resources/Kafka-List-Describe.png?raw=true)

# Hướng dẫn cài đặt Apache Spark
Truy cập đường dẫn https://spark.apache.org/downloads.html và chọn bản cài đặt tương ứng
```
wget https://mirror.downloadvn.com/apache/spark/spark-3.1.2/spark-3.1.2-bin-hadoop2.7.tgz
```
Giải nén 
```
tar -xzvf spark-3.1.2-bin-hadoop2.7.tgz
```
Apache Spark đã cài đặt xong! Tiếp đến thiết lập biến môi trường để chạy ứng dụng.
Tạo file spark-environment.env
```
echo "export SPARK_HOME=/home/atr_pt_vn/spark-3.1.2-bin-hadoop2.7" >> ~/spark-environment.env
echo "export PYTHONPATH=$SPARK_HOME/python:$PYTHONPATH" >> ~/spark-environment.env
echo "export PYSPARK_PYTHON=python3" >> ~/spark-environment.env
echo "export PATH=$PATH:$SPARK_HOME/bin:$SPARK_HOME/sbin" >> ~/spark-environment.env
```
Tạo file spark-env.sh từ mẫu có sẵn và chỉnh sửa
```
cp $SPARK_HOME/config/spark-env.sh.template $SPARK_HOME/config/spark-env.sh
```
Thêm vào cuối file
```
SPARK_MASTER_HOST=spark-master-app.com
SPARK_PUBLIC_DNS=spark-master-app.com
SPARK_MASTER_PORT=7077
SPARK_MASTER_WEBUI_PORT=8080
SPARK_WORKER_INSTANCES=2
SPARK_WORKER_CORES=2
SPARK_WORKER_MEMORY=2G
```
Chỉnh file /etc/hosts
```
sudo vim /etc/hosts
#<IP address của máy> spark-master-app.com
10.182.0.10 spark-master-app.com
```

Nạp biến môi trường vào session hiện tại
```
source spark-environment.env
```
# Khởi động Apache Spark với 4 workers
![alt text](https://github.com/anhtp4495/spark-lab/blob/main/resources/SparkClusterDiagram2.png?raw=true)

Tiến hành cài đặt cho 2 máy nữa các bước tương tự như trên

Tại Host 1: Khởi động Spark Master
```
start-master.sh
```

Tại Host 2 và 3: Khởi động workers
```
start-workers.sh --master spark://spark-master-app.com:7077
```

![alt text](https://github.com/anhtp4495/spark-lab/blob/main/resources/SparkMaster.png?raw=true)

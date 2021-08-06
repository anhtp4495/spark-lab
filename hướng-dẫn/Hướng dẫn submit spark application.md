# Hướng dẫn submit spark application

Câu lệnh 
```
./bin/spark-submit \
  --class <main-class> \
  --master <master-url> \
  --deploy-mode <deploy-mode> \
  --conf <key>=<value> \
  ... # other options
  <application-jar> \
  [application-arguments]
```

## Truy cập [GoogleCloud-EC](https://github.com/anhtp4495/spark-lab/blob/main/h%C6%B0%E1%BB%9Bng-d%E1%BA%ABn/H%C6%B0%E1%BB%9Bng%20d%E1%BA%ABn%20s%E1%BB%AD%20d%E1%BB%A5ng%20SSH%20t%E1%BB%9Bi%20Server.md)
## Build ứng dụng
```
cd spark-lab/src/SparkWorkCount
mvn clean compile assembly:single
```

Submit ứng dụng Workcount.jar <filename> lên Spark standalone cluster với deploy-mode client
```
./bin/spark-submit --master spark://spark-master-app.com:7077 /apps/Workcount.jar /apps/cluster-overview.html
```
Đối với deploy-mode cluster. Tạo file ở đường dẫn /apps/ ở master, workers và máy submit application.
```
./bin/spark-submit --master spark://spark-master-app.com:7077 --deploy-mode cluster /apps/Workcount.jar /apps/cluster-overview.html
```

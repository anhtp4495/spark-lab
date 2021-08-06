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

submit ứng dụng Workcount.jar <filename> lên Spark standalone cluster với deploy-mode client
```
./bin/spark-submit \
  --master spark://spark-master-app.com:7077 \
  /apps/Workcount.jar \
  /apps/cluster-overview.html
```
Đối với deploy-mode cluster. Tạo file ở đường dẫn /apps/ ở master, workers và máy submit application.
```
./bin/spark-submit \
  --master spark://spark-master-app.com:7077 \
  --deploy-mode cluster \
  /apps/Workcount.jar \
  /apps/cluster-overview.html
```

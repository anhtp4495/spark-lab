# Hướng dẫn build ứng dụng Spark bằng Java

build ứng dụng từ command line bằng lệnh
```
cd src/SparkStreamingKafka
mvn clean compile assembly:single
```

build ứng dụng bằng eclipse.
Load project vào elipse.
right-click và chọn "Run Configurations ...", tại goal nhập: clean compile assembly:single
![alt text](https://github.com/anhtp4495/spark-lab/blob/main/resources/EclipseBuildJavaSpark.PNG?raw=true)

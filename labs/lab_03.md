# Thực hành Spark MLLib

## Build code
```
cd spark-lab/src/SparkMLLib
mvn clean compile assembly:single
```
## Chạy ứng dụng Spark MLLib
```
spark-submit --master local[*] target\SparkMLLib-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
DataSource | Ví dụ | Chú thích |
------------- | ------------- | -------------
DataSource | JavaRDD<Vector> vectors = jsc.parallelize(random(rows, dim)); | Load Model từ RDDs
KMeansModel | KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations); | Traning thuật toán KMeans
DataSource | KMeansModel.load(jsc.sc(), "KMeansModel") | Load Model từ datasource

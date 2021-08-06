# Thực hành Spark SQL

## Build code
```
cd spark-lab/src/SparkSQL
mvn clean compile assembly:single
```
```
cd spark-lab/src/SparkSQLDataSource
mvn clean compile assembly:single
```
## Spark SQL và DataFram
Thao tác DataFrames và truy vấn SQL
```
spark-submit --master local[*] target\SparkSQL-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```
## Load từ DataSource vào Spark SQL
DataSource | Example
------------- | -------------
BasicDataSource | runBasicDataSourceExample(spark);
GenericFileSource| runGenericFileSourceOptionsExample(spark);
ParquetSchemaMerging | runParquetSchemaMergingExample(spark);
JsonDataset | runJsonDatasetExample(spark);
JdbcDataset | runJdbcDatasetExample(spark);
```
spark-submit --master local[*] target\SparkSQLDataSource-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

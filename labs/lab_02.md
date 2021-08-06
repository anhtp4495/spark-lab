# Thực hành Spark DataFrames và Spark SQL

## DataFrames
Tạo DataFrames từ file json, csv, txt
```
Dataset<Row> df = spark.read().json("examples/src/main/resources/people.json");
Dataset<Row> df = spark.read().csv("examples/src/main/resources/people.csv");
Dataset<Row> df = spark.read().load("examples/src/main/resources/users.parquet");
JavaRDD<String> peopleRDD = spark.sparkContext()
  .textFile("examples/src/main/resources/people.txt", 1)
  .toJavaRDD();
Dataset<Row> df = spark.createDataFrame(peopleRDD);
```
Apply Schema
```
// The schema is encoded in a string
String schemaString = "name age";

// Generate the schema based on the string of schema
List<StructField> fields = new ArrayList<>();
for (String fieldName : schemaString.split(" ")) {
  StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
  fields.add(field);
}
StructType schema = DataTypes.createStructType(fields);

// Convert records of the RDD (people) to Rows
JavaRDD<Row> rowRDD = peopleRDD.map((Function<String, Row>) record -> {
  String[] attributes = record.split(",");
  return RowFactory.create(attributes[0], attributes[1].trim());
});

// Apply the schema to the RDD
Dataset<Row> peopleDataFrame = spark.createDataFrame(rowRDD, schema);
```
Truy vấn DataFrames
```
// Print the schema in a tree format
df.printSchema();
// root
// |-- age: long (nullable = true)
// |-- name: string (nullable = true)

// Select only the "name" column
df.select("name").show();
// +-------+
// |   name|
// +-------+
// |Michael|
// |   Andy|
// | Justin|
// +-------+

// Select everybody, but increment the age by 1
df.select(col("name"), col("age").plus(1)).show();
// +-------+---------+
// |   name|(age + 1)|
// +-------+---------+
// |Michael|     null|
// |   Andy|       31|
// | Justin|       20|
// +-------+---------+

// Select people older than 21
df.filter(col("age").gt(21)).show();
// +---+----+
// |age|name|
// +---+----+
// | 30|Andy|
// +---+----+

// Count people by age
df.groupBy("age").count().show();
// +----+-----+
// | age|count|
// +----+-----+
// |  19|    1|
// |null|    1|
// |  30|    1|
// +----+-----+
```


## TempView, Global Temporary View
Tạo TempView, Global Temporary View
```
// Register the DataFrame as a temporary view
peopleDF.createOrReplaceTempView("people")
// Register the DataFrame as a global temporary view
df.createGlobalTempView("people");
```
Truy vấn TempView
```
// SQL statements can be run by using the sql methods provided by spark
Dataset<Row> teenagersDF = spark.sql("SELECT name FROM people WHERE age BETWEEN 13 AND 19");
// SQL can be run over a temporary view created using DataFrames
Dataset<Row> results = spark.sql("SELECT name FROM people");
Dataset<Row> results = spark.sql("SELECT myAverage(salary) as average_salary FROM employees");
```
Truy vấn Global Temporary View
```
// Global temporary view is tied to a system preserved database `global_temp`
spark.sql("SELECT * FROM global_temp.people").show();
// Global temporary view is cross-session
spark.newSession().sql("SELECT * FROM global_temp.people").show();
```
Truy vấn Parquet
```
Dataset<Row> sqlDF = spark.sql("SELECT * FROM parquet.`examples/src/main/resources/users.parquet`");
```

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

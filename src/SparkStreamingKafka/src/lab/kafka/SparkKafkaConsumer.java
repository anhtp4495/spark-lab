package lab.kafka;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import kafka.serializer.StringDecoder;

public class SparkKafkaConsumer {

        public static void main(String[] args) throws InterruptedException {
                if (args.length < 2) {
                        System.err.println("Usage: JavaDirectKerberizedKafkaWordCount <brokers> <topics>\n"
                                        + "  <brokers> is a list of one or more Kafka brokers\n"
                                        + "  <topics> is a list of one or more kafka topics to consume from\n\n");
                        System.exit(1);
                }

                String brokers = args[0];
                String groupId = args[1];
                String topics = args[2];

                // Create context with a 2 seconds batch interval

                Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
                Map<String, Object> kafkaParams = new HashMap<>();
                kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
                kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                SparkConf conf = new SparkConf().setAppName("Kafka.SparkStreaming");
                JavaSparkContext jsc = new JavaSparkContext(conf);
                JavaStreamingContext ssc = new JavaStreamingContext(jsc, new Duration(2000));

                // Create direct kafka stream with brokers and topics
                JavaInputDStream<ConsumerRecord<String, String>> directKafkaStream = KafkaUtils.createDirectStream(ssc,
                                LocationStrategies.PreferConsistent(),
                              ConsumerStrategies.Subscribe(topicsSet, kafkaParams));


                directKafkaStream.foreachRDD(rdd -> {
                        System.out.println("RDD " + rdd.count() + " records");
                        rdd.foreach(record -> {
                                System.out.println("Record: " + record.value().toString());
                        });
                });

                ssc.start();
                ssc.awaitTermination();
        }
}

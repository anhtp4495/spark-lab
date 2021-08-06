package lab.twitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.codec.language.Soundex;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;

import scala.Tuple2;
import twitter4j.Status;
import twitter4j.auth.Authorization;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class SparkStreamingTwitter {

	private static final Pattern SPACE = Pattern.compile(" ");

	public static void main(String[] args) throws InterruptedException {
		if (args.length < 5) {
			System.err.println(
					"Usage: TransactionProducer <consumerKey> <consumerSerect> <accessToken> <accessTokenSerect>\n");
			System.exit(1);
		}

		String consumerKey = args[0];
		String consumerSerect = args[1];
		String accessToken = args[2];
		String accessTokenSerect = args[3];

		SparkConf conf = new SparkConf().setAppName("JavaStreamingTwitter");
		JavaSparkContext jsc = new JavaSparkContext(conf);
		JavaStreamingContext ssc = new JavaStreamingContext(jsc, Durations.seconds(1));

		// Prepare configuration for Twitter authentication and authorization
		Configuration twitterConf = new ConfigurationBuilder().setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSerect).setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSerect).build();
		// Create Twitter authorization object by passing prepared configuration
		// containing consumer and access keys and tokens
		Authorization twitterAuth = new OAuthAuthorization(twitterConf);
		String[] filters = new String[] {};

		// Create a data stream using streaming context and Twitter authorization
		JavaReceiverInputDStream<Status> inputDStream = TwitterUtils.createStream(ssc, twitterAuth, filters);

		JavaDStream<String> words = inputDStream
				.flatMap((status) -> Arrays.asList(SPACE.split(status.getText())).iterator());
		JavaDStream<String> hashTags = words.filter((s) -> s.startsWith("#") && s.length() > 3);
		JavaPairDStream<String, Integer> hashTagsOnes = hashTags.mapToPair((s) -> new Tuple2<String, Integer>(s, 1));
		JavaPairDStream<String, Integer> topCount60 = hashTagsOnes.reduceByKey((a, b) -> a + b);
		JavaPairDStream<String, Integer> topCount60Sorted = topCount60.mapToPair((s) -> s.swap())
				.transformToPair((rdd) -> rdd.sortByKey(false)).mapToPair((s) -> s.swap());

		topCount60Sorted.foreachRDD((rdd) -> {
			List<Tuple2<String, Integer>> topList = rdd.take(10);
			Map<String, Tuple2<String, Integer>> resultmap = new HashMap<>(10);
			System.out.println("----------------------------------------------------");
			System.out.println(String.format("Popular topics out of %s total topics received:\n", rdd.count()));
			Soundex sx = new Soundex();
			for (int i = 0; i < topList.size(); i++) {
				String key = sx.encode(topList.get(i)._1);
				if (!resultmap.containsKey(key)) {
					resultmap.put(key, topList.get(i));
				} else {
					Tuple2<String, Integer> old = resultmap.get(key);
					Tuple2<String, Integer> newval = new Tuple2<>(topList.get(i)._1 + "/" + old._1,
							topList.get(i)._2 + old._2);
					resultmap.put(key, newval);
				}

			}

			List<Tuple2<String, Integer>> result = new ArrayList<Tuple2<String, Integer>>(resultmap.values());
			result.sort((a, b) -> b._2.compareTo(a._2));
			for (Tuple2<String, Integer> list : result) {
				System.out.println(String.format("%s (%s tweets)", list._1, list._2));
			}
		});

		ssc.start();
		ssc.awaitTermination();
	}
}

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package lab.spark;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

// $example on$
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.mllib.clustering.KMeans;
import org.apache.spark.mllib.clustering.KMeansModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
// $example off$

public class JavaKMeansExample {
	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("JavaKMeansExample");
		JavaSparkContext jsc = new JavaSparkContext(conf);

		int rows = 1000;
		int dim = 2;
		// $example on$
		// Load and parse data
		JavaRDD<Vector> parsedData = jsc.parallelize(random(rows, dim));
		parsedData.cache();

		// Cluster the data into two classes using KMeans
		int numClusters = 2;
		int numIterations = 20;
		KMeansModel clusters = KMeans.train(parsedData.rdd(), numClusters, numIterations);

		ArrayList<String> clusterCenters = new ArrayList<String>();
		for (Vector center : clusters.clusterCenters()) {
			clusterCenters.add(center.toString());
		}

		double cost = clusters.computeCost(parsedData.rdd());
		System.out.println("Cost: " + cost);

		// Evaluate clustering by computing Within Set Sum of Squared Errors
		double WSSSE = clusters.computeCost(parsedData.rdd());
		System.out.println("Within Set Sum of Squared Errors = " + WSSSE);

		// Save and load model
		try {
			FileUtils.deleteDirectory(new File("KMeansModel"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		clusters.save(jsc.sc(), "KMeansModel");
		KMeansModel sameModel = KMeansModel.load(jsc.sc(), "KMeansModel");
		// $example off$

		
		System.out.println("Cluster centers:");
		for (String center : clusterCenters) {
			System.out.println(" " + center);
		}
		
		jsc.stop();
	}

	private static List<Vector> random(int rows, int dim) {
		Vector[] vectors = new Vector[rows];
		for (int i = 0; i < rows; i++) {
			vectors[i] = random(dim);
		}

		return Arrays.asList(vectors);
	}

	private static Vector random(int dim) {
		double[] values = new double[dim];
		for (int i = 0; i < dim; i++) {
			values[i] = Math.random();
		}
		return Vectors.dense(values);
	}
}

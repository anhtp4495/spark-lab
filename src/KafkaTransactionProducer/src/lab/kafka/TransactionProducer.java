package lab.kafka;

import java.util.ArrayList;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class TransactionProducer {
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		if (args.length < 3) {
			System.err.println("Usage: TransactionProducer <boostrap> <topic> <length_record> <repeat_time> <repeat_delay>\n"
					+ "  <boostrap> is a list of one or more Kafka boostrap\n"
					+ "  <topic> is a list of one or more kafka topics to consume from\n\n"
					+ "  <length_record> is a length of generate records\n\n"
					+ "  <repeat_time> optional\n\n"
					+ "  <repeat_delay> optional\n\n"
					+ "  <producer_name> optional\n\n");
			System.exit(1);
		}

		String boostrap = args[0];
		String procedureTopic = args[1];
		String producer_name = "producer";
		if (args.length > 4) {
			producer_name = args[5]; 
		}

		TransactionFactory.PRODUCER_NAME = producer_name;
		// create instance for properties to access producer configs
		Properties props = new Properties();
		
		// Assign localhost id
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrap);

		// Set acknowledgements for producer requests.
		props.put(ProducerConfig.ACKS_CONFIG, "all");

		// If the request fails, the producer can automatically retry,
		props.put(ProducerConfig.RETRIES_CONFIG, 0);

		// Specify buffer size in config
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

		// The buffer.memory controls the total amount of memory available to the
		// producer for buffering.
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

		props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "org.apache.kafka.clients.producer.internals.DefaultPartitioner");
		
		KafkaProducer<String, Transaction> producer = new KafkaProducer<String, Transaction>(
				props, 
				new StringKeySerializer(), 
				new TransactionSerializer());
		System.out.println("KafkaProducer is created.");
		
		Long repeat_time = 0L;
		Long deley_time = 0L;
		if (args.length > 3) {
			repeat_time = Long.valueOf(args[3]);
		}
		if (args.length > 4) {
			deley_time = Long.valueOf(args[4]); 
		}

		repeat_time = repeat_time > 0 ? repeat_time : 1L;
		deley_time = deley_time > 0 ? deley_time : 10L;

		while (repeat_time > 0) {
			ArrayList<Transaction> trans = TransactionFactory.random(Integer.valueOf(args[2]));
			for (Transaction tran : trans) {
				ProducerRecord<String, Transaction> record = new ProducerRecord<String, Transaction>(procedureTopic, tran.getKey(), tran);
				producer.send(record);
				System.out.println("Producer '" + producer_name +"' send a Transaction to KafkaTopic.");
			}
			
			Thread.sleep(deley_time);
			repeat_time--;
			TransactionFactory.useRandomNgayGiaoDich = false;
		}
		
	}

}

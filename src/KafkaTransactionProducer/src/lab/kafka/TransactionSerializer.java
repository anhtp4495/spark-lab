package lab.kafka;

import org.apache.kafka.common.serialization.Serializer;

public class TransactionSerializer implements Serializer<Transaction> {

	@Override
	public byte[] serialize(String topic, Transaction data) {
		return data.toString().getBytes();
	}

}

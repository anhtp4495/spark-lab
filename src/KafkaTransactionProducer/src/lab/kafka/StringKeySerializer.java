package lab.kafka;

import org.apache.kafka.common.serialization.Serializer;

public class StringKeySerializer implements Serializer<String>{

	@Override
	public byte[] serialize(String topic, String data) {
		return data.toString().getBytes();
	}

}

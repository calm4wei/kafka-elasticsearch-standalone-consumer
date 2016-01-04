package org.elasticsearch.kafka.consumer.messageHandlers;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.kafka.consumer.ConsumerConfig;
import org.elasticsearch.kafka.consumer.MessageHandler;
import org.elasticsearch.kafka.consumer.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class RawMessageStringHandler extends MessageHandler {

	private static final Logger logger = LoggerFactory.getLogger(RawMessageStringHandler.class);

	public RawMessageStringHandler(TransportClient client,ConsumerConfig config) throws Exception{
		super(client, config);
		logger.info("Initialized RawMessageStringHandler");
	}
		
	@Override
	public byte[] transformMessage( byte[] inputMessage, Long offset) throws Exception{
		byte[] outputMessage;
		// do necessary transformation here
		// in the simplest case - post as is
		outputMessage = inputMessage;		
		return outputMessage; 		
	}

	@Override
	public String getId(byte[] inputMessage, Long offset) {
		String id = null;
		try {
			String rawmsg = new String(inputMessage, "UTF-8");
			JSONObject jsonObject = new JSONObject(rawmsg);
			id = jsonObject.getString("deviceid");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return id;
	}

}

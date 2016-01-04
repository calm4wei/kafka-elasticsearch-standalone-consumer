package org.elasticsearch.kafka.consumer.messageHandlers;

import org.codehaus.jackson.map.ObjectMapper;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.kafka.consumer.ConsumerConfig;
import org.elasticsearch.kafka.consumer.MD5;
import org.elasticsearch.kafka.consumer.MessageHandler;
import org.elasticsearch.kafka.consumer.json.JSONObject;
import org.elasticsearch.kafka.consumer.mappers.AccessLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class EventMessageHandler extends MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(EventMessageHandler.class);

    public EventMessageHandler(TransportClient client, ConsumerConfig config) throws Exception {
        super(client, config);
        logger.info("Initialized org.elasticsearch.kafka.consumer.messageHandlers.AccessLogMessageHandler");
    }

    @Override
    public byte[] transformMessage(byte[] inputMessage, Long offset) throws Exception {
        // String outputMessageStr = this.convertToJson(new String(inputMessage, "UTF-8"), offset);
        return inputMessage;
    }

    @Override
    public String getId(byte[] inputMessage, Long offset) {
        String id = null;
        try {
            String rawMsg = new String(inputMessage, "UTF-8");
            logger.info("rawMsg=" + rawMsg);

            JSONObject jsonObject = new JSONObject(rawMsg);
            String deviceid = jsonObject.getString("deviceid");
            String event_identifier = jsonObject.getString("event_identifier");
            String localtime = jsonObject.getString("localtime");
            id = MD5.md5Hashing(deviceid + event_identifier + localtime);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return id;
    }

}

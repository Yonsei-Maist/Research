/**
 * Publish and subscribe to AWS iot core topic.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2021.01.07
 */
package kr.ac.yonsei.maist.module.aws;

import com.amazonaws.services.iot.client.*;

public class PublishSubscribe {

    public static final AWSIotQos TopicQos = AWSIotQos.QOS0;

    /**
     * Create AWS iot mqtt client.
     * @param id Client id
     * @return AWSIotMqttClient
     */
    public AWSIotMqttClient init(String id) {
        String clientEndpoint = "ajqa4269qyk6b-ats.iot.ap-northeast-2.amazonaws.com";
        String clientId = id;
        String awsAccessKeyId = "AKIA5EKINKSUXGD3YDCC";
        String awsSecretAccessKey = "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";
        AWSIotMqttClient awsIotMqttClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey);

        return awsIotMqttClient;
    }

    /**
     * Subscribe to a topic.
     * @param awsIotMqttClient AWSIotMqttClient
     * @param topic Topic
     */
    public void subscribe(AWSIotMqttClient awsIotMqttClient, String topic) {
        try {
            awsIotMqttClient.connect();
            AWSIotTopic awsIotTopic = new TopicListener(topic, TopicQos);
            awsIotMqttClient.subscribe(awsIotTopic); //topic
        }
        catch (AWSIotException e) {
            e.getStackTrace();
        }
    }

    /**
     * Publishing topics and sending messages.
     * @param awsIotMqttClient AWSIotMqttClient
     * @param topic topic
     * @param payload payload(message)
     */
    public void publish(AWSIotMqttClient awsIotMqttClient, String topic, String payload) {
        try {
            awsIotMqttClient.publish(topic, payload, 3000);
        }
        catch (Exception e) {
            e.getStackTrace();
        }
    }

    /**
     * Disconnect the AWS iot mqtt client.
     * @param awsIotMqttClient AWSIotMqttClient
     */
    public void disconnect(AWSIotMqttClient awsIotMqttClient) {
        try {
            awsIotMqttClient.disconnect();
        }
        catch (Exception e) {
            e.getStackTrace();
        }

    }
}

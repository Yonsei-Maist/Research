/**
 * This class extends {@link AWSIotTopic} to receive messages from a subscribed.
 * @author Mina Kim, Yonsei Univ. Researcher, since 2020.08~
 * @Date 2020.12.28
 */
package kr.ac.yonsei.maist.module.aws;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.yonsei.maist.global.config.ApplicationContextProvider;
import kr.ac.yonsei.maist.domain.machine.service.MachineService;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.HashMap;

public class TopicListener extends AWSIotTopic {

    public TopicListener(String topic, AWSIotQos qos) {
        super(topic, qos);
    }

    /**
     * Receive a callback message from a subscribed topic.
     * Receives status messages from NX machines.
     * Calls a method that saves the state of the NX machine in DB.
     * @param awsIotMessage AWSIotMessage
     */
    @Override
    public void onMessage(AWSIotMessage awsIotMessage) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            HashMap<String, String> map = new HashMap<String, String>();
            map = mapper.readValue(awsIotMessage.getPayload(), new TypeReference<HashMap<String, String>>(){});
            String machineId = map.get("id_nx_code");
            String status = map.get("status");

            ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
            MachineService machineService = (MachineService) applicationContext.getBean("machineService");
            machineService.updateOperateDate(machineId); // 작동 시간 update
        }
        catch (IOException e) {
            e.getStackTrace();
        }
    }
}

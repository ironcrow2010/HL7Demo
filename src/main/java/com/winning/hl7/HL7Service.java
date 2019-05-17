package com.winning.hl7;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v251.message.ACK;
import ca.uhn.hl7v2.model.v251.message.ADT_A01;
import ca.uhn.hl7v2.model.v251.message.ADT_A05;
import ca.uhn.hl7v2.model.v251.segment.MSA;
import ca.uhn.hl7v2.model.v251.segment.MSH;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.Parser;
import com.winning.common.FileUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.UUID;

public class HL7Service {
    private Logger logger = null;
    private GenericParser parser = null;
    private Message message;

    public HL7Service() {
        logger = LogManager.getLogger(HL7Service.class);
        DefaultHapiContext context = new DefaultHapiContext();
        parser = context.getGenericParser();
    }

    public void initACKMessage() {
        try {
            ACK ack = new ACK();
            ack.initQuickstart("ACK", null, "P");

            logger.debug("设置MSH");

            MSH msh = ack.getMSH();
            msh.getSendingApplication().getNamespaceID().setValue("发送系统ID");
            msh.getSendingFacility().getNamespaceID().setValue("发送机构ID");
            msh.getReceivingApplication().getNamespaceID().setValue("接收系统ID");
            msh.getReceivingFacility().getNamespaceID().setValue("接收机构ID");
            msh.getMessageControlID().setValue(UUID.randomUUID().toString());

            logger.info("设置MSA");
            MSA msa = ack.getMSA();


            // 结果标志：AA-成功 AE-失败
            msa.getAcknowledgmentCode().setValue("AA");
            // 获取接收消息的MSH-10值
            String requestMessageControlId = UUID.randomUUID().toString();

            // 原始消息控制ID
            msa.getMessageControlID().setValue(requestMessageControlId);

            msa.getTextMessage().setValue("第一行\r第二行");

            HapiContext context = new DefaultHapiContext();
            Parser parser = context.getPipeParser();
            String encodedMessage = parser.encode(ack);

            logger.info(encodedMessage);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void parseHL7Message() throws HL7Exception {
        String inputHL7Message = FileUtil.readToString("E:\\Temp\\Debug\\ADT^A28^ADT_A05.txt");

        message = parser.parse(inputHL7Message);

        ADT_A05 hl7Msg = (ADT_A05) message;
        System.out.printf("MessageStructure:%s,MessageControlID:%s", hl7Msg.getMSH().getMessageType().getMessageStructure().getValue(),
                hl7Msg.getMSH().getMessageControlID().getValue());
    }

    public void parseHL7MessageFromText(String inputHL7Message) throws HL7Exception {
        message = parser.parse(inputHL7Message);

        String patientId;
        String patientName = null;
        String patientClass;

        ADT_A01 hl7Msg = (ADT_A01) message;
        patientId = hl7Msg.getPID().getPatientID().getIDNumber().getValue();
        if (hl7Msg.getPID().getPatientNameReps() > 0) {
            patientName = hl7Msg.getPID().getPatientName(0).getFamilyName().getSurname().getValue();
        }
        patientClass = hl7Msg.getPV1().getPatientClass().getValue();

        System.out.printf("PatientId:%s, PatientName:%s, PatientClass:%s", patientId, patientName, patientClass);
    }
}

package com.winning.hl7;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v251.message.ACK;
import ca.uhn.hl7v2.model.v251.message.ADT_A01;
import ca.uhn.hl7v2.model.v251.segment.MSA;
import ca.uhn.hl7v2.model.v251.segment.MSH;
import ca.uhn.hl7v2.model.v251.segment.PID;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.Parser;
import com.winning.model.Patient;
import com.winning.service.MultiThreadingPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

public class PatientService {
    private static Logger logger = LoggerFactory.getLogger(PatientService.class);
    private GenericParser parser = null;
    private Message message;
    private MultiThreadingPatientService multiThreadPatientService = null;
    private ArrayList<Patient> currentPatient = null;

    public PatientService() {
        DefaultHapiContext context = new DefaultHapiContext();
        parser = context.getGenericParser();
        multiThreadPatientService = new MultiThreadingPatientService();
    }

    public void initACK() {
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

    public void parseHL7(String hl7Text) throws HL7Exception {

        String patientId;
        String patientName = null;
        String patientClass;

        ADT_A01 hl7Msg = this.parse(hl7Text);

        // parsePID
        PID pid = hl7Msg.getPID();
        patientId = pid.getPatientID().getIDNumber().getValue();
        if (pid.getPatientNameReps() > 0) {
            patientName = hl7Msg.getPID().getPatientName(0).getFamilyName().getSurname().getValue();
        }

        pid.getPatientAddress(0).getStreetAddress().getSad1_StreetOrMailingAddress().getValue();

        // parsePV1
        patientClass = hl7Msg.getPV1().getPatientClass().getValue();

        System.out.printf("PatientId:%s, PatientName:%s, PatientClass:%s", patientId, patientName, patientClass);
    }

    /**
     * @param hl7Text HL7输入文本字符串
     * @return ADT_A01
     * @throws HL7Exception HL7 异常类
     */
    private ADT_A01 parse(String hl7Text) throws HL7Exception {
        message = parser.parse(hl7Text);

        ADT_A01 hl7Msg = (ADT_A01) message;
        return hl7Msg;
    }

    /**
     * @param jsonText JSON字符串
     * @return HL7文本
     * @throws HL7Exception HL7异常类
     */
    public String parseHL7FromJson(String jsonText) throws HL7Exception {
        logger.info("parseHL7FromJson ");
        return "abc";
    }

    /**
     * @param status 患者状态：0-未登记 1-已登记 2-已分床 3-已出院
     */
    public void QueryAdmitPatientList(int status) {
        multiThreadPatientService.run();
    }
}

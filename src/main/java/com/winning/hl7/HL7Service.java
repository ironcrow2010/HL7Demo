package com.winning.hl7;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v251.group.OML_O21_ORDER;
import ca.uhn.hl7v2.model.v251.group.OML_O21_SPECIMEN;
import ca.uhn.hl7v2.model.v251.message.ACK;
import ca.uhn.hl7v2.model.v251.message.ADT_A01;
import ca.uhn.hl7v2.model.v251.message.OML_O21;
import ca.uhn.hl7v2.model.v251.message.ORM_O01;
import ca.uhn.hl7v2.model.v251.segment.*;
import ca.uhn.hl7v2.parser.GenericParser;
import ca.uhn.hl7v2.parser.Parser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.winning.common.FileUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

import com.winning.model.RequestModel;

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
        String inputHL7Message = FileUtil.readToString("E:\\Temp\\Debug\\检验申请单hl7.txt");

        message = parser.parse(inputHL7Message);

        OML_O21 hl7Msg = (OML_O21) message;
        for (OML_O21_ORDER order : hl7Msg.getORDERAll()) {
            ORC orc = order.getORC();
            OBR obr = order.getOBSERVATION_REQUEST().getOBR();
            SPM spm = null;

            List<OML_O21_SPECIMEN> specimenList = order.getOBSERVATION_REQUEST().getSPECIMENAll();
            for (OML_O21_SPECIMEN specimen : specimenList) {
                spm = specimen.getSPM();
            }

            System.out.printf("PlacerOrderNumber:%s", orc.getPlacerOrderNumber().
                    getEntityIdentifier().getValue());
        }


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

    public void parseHL7MessageFromOrignialFile() throws HL7Exception {
        String inputHL7Message = FileUtil.readToString("E:\\Temp\\Debug\\检查申请单json.txt");
        ORM_O01 orm = null;
        RequestModel requestModel = JSON.parseObject(inputHL7Message, RequestModel.class);
        String[] hl7List = requestModel.Request.Body;
        String nteCommentText = null;

        for(String hl7 : hl7List){
            message = parser.parse(hl7);
            if(message instanceof ORM_O01){
                orm = (ORM_O01)message;
                NTE nte = orm.getNTE();
                if(nte.getCommentReps() > 0){
                    nteCommentText = nte.getComment(0).getValue();
                    JSONArray commentList = JSONObject.parseArray(nteCommentText);
                    String valueText = null;
                    for(Object comment : commentList){
                        JSONObject obj = (JSONObject)comment;
                        valueText = (String)obj.get("value");
                        System.out.println(valueText);
                    }
                }
                break;
            }

        }

    }
}

package com.winning.hl7;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.model.Message;
import ca.uhn.hl7v2.model.v251.message.OML_O21;
import ca.uhn.hl7v2.model.v251.segment.NTE;
import ca.uhn.hl7v2.model.v251.segment.PID;
import ca.uhn.hl7v2.parser.GenericParser;
import com.alibaba.fastjson.JSON;
import com.winning.model.RequestModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ironcrow on 2019-5-23.
 */
public class LaborApplyService {
    private static Logger logger = LoggerFactory.getLogger(LaborApplyService.class);
    private GenericParser parser = null;
    private Message message;

    public LaborApplyService() {
        DefaultHapiContext context = new DefaultHapiContext();
        parser = context.getGenericParser();
    }



    public void parseHL7(String hl7Text) throws HL7Exception {
        message = parser.parse(hl7Text);

        OML_O21 hl7Msg = (OML_O21) message;
        PID pid = hl7Msg.getPATIENT().getPID();
        String idNumber = null;
        String idType = null;
        if(pid.getPatientIdentifierListReps() > 0){
            idNumber = pid.getPatientIdentifierList(0).getIDNumber().getValue();
            idType = pid.getPatientIdentifierList(0).getIdentifierTypeCode().getValue();

            logger.debug("IDType:{}, IDNumber:{}", idType, idNumber);
        }

        List<NTE> nteList = null;
        if (hl7Msg.getORDER(0).getOBSERVATION_REQUEST().getNTEReps()>0) {
            nteList = hl7Msg.getORDER(0).getOBSERVATION_REQUEST().getNTEAll();
            for(NTE nte : nteList){
                if(nte.getCommentReps() > 0){
                    logger.debug("CommentType:{},Value:{}",nte.getCommentType().getIdentifier().getValue(),nte.getComment(0).getValue());

                }
            }
        }
    }

    public void parseHL7FromJson(String jsonText) throws HL7Exception {
        OML_O21 hl7Msg = null;
        RequestModel requestModel = JSON.parseObject(jsonText, RequestModel.class);
        String[] hl7List = requestModel.Request.Body;
    }
}

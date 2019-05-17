import ca.uhn.hl7v2.HL7Exception;
import com.winning.hl7.HL7Service;

public class Main {
    public static void main(String[] args) throws HL7Exception {
        HL7Service hl7Service = new HL7Service();
        hl7Service.initACKMessage();
        hl7Service.parseHL7Message();
        String inputHL7Message = "MSH|^~\\&|||||||ADT^A01|a07749c8-ec04-4530-9065-46470ba0b4da||2.5.1\rEVN||20190516155145\rPID||abcde\\S\\f|||第一行\\X000d\\第二行\rPV1|1|有波浪\\R\\波浪\r";
        hl7Service.parseHL7MessageFromText(inputHL7Message);
    }

    private static String getInputHL7Message(){
        return "MSH|^~\\&|||||||ADT^A01|a07749c8-ec04-4530-9065-46470ba0b4da||2.5.1\rEVN||20190516155145\rPID||abcde\\S\\f|||第一行\\X000d\\第二行\rPV1|1|有波浪\\R\\波浪\r";
    }
}

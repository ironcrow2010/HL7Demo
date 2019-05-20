import ca.uhn.hl7v2.HL7Exception;
import com.winning.hl7.HL7Service;

public class Main {
    public static void main(String[] args) throws HL7Exception {
        HL7Service hl7Service = new HL7Service();
        //hl7Service.initACKMessage();
        //hl7Service.parseHL7Message();
        hl7Service.parseHL7MessageFromOrignialFile();
    }
}

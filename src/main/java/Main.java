import ca.uhn.hl7v2.HL7Exception;
import com.winning.hl7.HL7Service;

public class Main {
    public static void main(String[] args) throws HL7Exception {
        HL7Service service = new HL7Service();
        service.initACK();
    }
}

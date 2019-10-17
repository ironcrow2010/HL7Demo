import ca.uhn.hl7v2.HL7Exception;
import com.winning.hl7.PatientService;

public class Main {
    public static void main(String[] args) throws HL7Exception {
        // Push test1
        PatientService service = new PatientService();
        service.initACK();
    }
}

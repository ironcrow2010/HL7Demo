import ca.uhn.hl7v2.HL7Exception;
import com.winning.hl7.PatientService;

public class Main {
    public static void main(String[] args) throws HL7Exception {
        PatientService service = new PatientService();
        service.initACK();
    }
}

import ca.uhn.hl7v2.HL7Exception;
import com.winning.hl7.PatientService;

/**
 * Description:
 * <br>WebSite:<a href = "https://github.com/ironcrow2010/HL7Demo">HL7Demo</a>
 * <br>Copyright (C), 2009-2010, Balance.Chang</br>
 * <br>This program show you how to create and parse HL7 message.
 * <br>Program name:HL7Demo
 * <br>Date:2019-10-29
 * @author Balance.Chang
 * @version 1.0
 */
public class Main {
    /**
     * Simple test field
     */
    protected String name;

    /**
     * @param args 主函数方法
     * @throws HL7Exception HL7 exception
     */
    public static void main(String[] args) throws HL7Exception {
        PatientService service = new PatientService();
        service.initACK();
    }
}

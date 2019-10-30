package test.com.winning.hl7;

import com.winning.common.FileUtil;
import com.winning.hl7.PatientService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * PatientService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>May 20, 2019</pre>
 */
public class PatientServiceTest {

    private PatientService service = null;

    @Before
    public void before() throws Exception {
        service = new PatientService();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: initACKMessage()
     */
    @Test
    public void testInitACK() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: parseHL7Message()
     */
    @Test
    public void testParseHL7() throws Exception {
//TODO: Test goes here... 
    }

    /**
     * Method: parseHL7MessageFromOrignialFile()
     */
    @Test
    public void testParseHL7FromJson() throws Exception {
        String hl7Text = FileUtil.readToString("E:\\Temp\\Debug\\检查申请单json.txt");
        service.parseHL7FromJson(hl7Text);
    }

    @Test
    public void testQueryAdmitPatientList() throws Exception{
        service.QueryAdmitPatientList(0);
    }
}

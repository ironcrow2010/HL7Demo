package test.com.winning.hl7;

import com.winning.common.FileUtil;
import com.winning.hl7.HL7Service;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * HL7Service Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>May 20, 2019</pre>
 */
public class HL7ServiceTest {

    private HL7Service service = null;

    @Before
    public void before() throws Exception {
        service = new HL7Service();
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
}

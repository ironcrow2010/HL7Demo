package test.com.winning.hl7;

import com.winning.common.FileUtil;
import com.winning.hl7.LaborApplyService;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

/**
 * LaborApplyService Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>May 23, 2019</pre>
 */
public class LaborApplyServiceTest {
    private LaborApplyService service = null;

    @Before
    public void before() throws Exception {
        service = new LaborApplyService();
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: parseHL7(String hl7Text)
     */
    @Test
    public void testParseHL7() throws Exception {
        String hl7Text = FileUtil.readToString("E:\\Temp\\Debug\\OML_O21-sample.txt");
        service.parseHL7(hl7Text);
    }

    /**
     * Method: parseHL7FromJson(String jsonText)
     */
    @Test
    public void testParseHL7FromJson() throws Exception {
//TODO: Test goes here... 
    }


} 

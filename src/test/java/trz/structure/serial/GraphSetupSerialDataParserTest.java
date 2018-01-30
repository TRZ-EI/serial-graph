package trz.structure.serial;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 30/01/18
 * Time: 16.09
 */
public class GraphSetupSerialDataParserTest {
    private SerialDataReader sut;

    @DataProvider
    private Object[][] createDataForTest(){
        return new Object[][]{
                {"^S11004001010000500100100008002000130000000000000000000255"},
                {""}
        };
    }


    @BeforeMethod
    public void setup(){
        this.sut = GraphSetupSerialDataParser.getNewInstance();
    }

    @Test
    public void testGetNewInstance() throws Exception {
        assertNotNull(GraphSetupSerialDataParser.getNewInstance());
    }
    @Test
    public void testReadByteArray() throws Exception {
    }

}
package trz.structure.serial;

import org.testng.annotations.BeforeTest;
import trz.structure.Cell;
import trz.utils.ConfigurationHolder;
import trz.utils.FontAndColorSelector;

import java.io.UnsupportedEncodingException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 04/05/17
 * Time: 15.57
 */
public class SerialDataFacadeTest {
    private SerialDataFacade sut;
    private FontAndColorSelector fontAndColorSelector;


    @BeforeTest
    private void setup(){
        String configurationFile = this.getClass().getClassLoader().getResource("application.properties").getFile();
        ConfigurationHolder.createSingleInstanceByConfigUri(configurationFile);
        this.sut = SerialDataFacade.createNewInstance();
        this.fontAndColorSelector = FontAndColorSelector.getNewInstance();
    }
    //@Test
    public void testCreateNewInstance() throws Exception {
        assertNotNull(this.sut);
    }
    //@Test(dataProvider = "testDataForConfigurationVariable")
    public void testOnSerialDataParserForConfiguration(String dataToParse, Cell expectedValue) throws UnsupportedEncodingException {
        Cell actualValue = this.sut.onSerialDataParser(dataToParse.getBytes());
        assertEquals(actualValue, expectedValue);
    }
    //@Test(dataProvider = "testDataForVariable")
    public void testOnSerialDataParserForVariables(String dataToParse, Cell expectedValue) throws UnsupportedEncodingException {
        Cell actualValue = this.sut.onSerialDataParser(dataToParse.getBytes());
        String actualVariableValue = actualValue.getValue();
        String expectedVariableValue = expectedValue.getValue();
        assertEquals(actualValue, expectedValue);
        assertEquals(actualVariableValue, expectedVariableValue);
    }
    //@Test(dataProvider = "testDataForText")
    public void testOnSerialDataParserForText(String dataToParse, Cell expectedValue) throws UnsupportedEncodingException {
        Cell actualValue = this.sut.onSerialDataParser(dataToParse.getBytes());
        String actualVariableValue = actualValue.getValue();
        String expectedVariableValue = expectedValue.getValue();
        assertEquals(actualValue, expectedValue);
        assertEquals(actualVariableValue, expectedVariableValue);
    }
    //@Test(dataProvider = "testDataForNumber")
    public void testOnSerialDataParserForNumber(String dataToParse, Cell expectedValue) throws UnsupportedEncodingException {
        Cell actualValue = this.sut.onSerialDataParser(dataToParse.getBytes());
        String actualVariableValue = actualValue.getValue();
        String expectedVariableValue = expectedValue.getValue();
        assertEquals(actualValue, expectedValue);
        assertEquals(actualVariableValue, expectedVariableValue);
    }
    //@Test(dataProvider = "testDataForClear")
    public void testOnSerialDataParserForClear(String dataToParse, Cell expectedValue) throws UnsupportedEncodingException {
        assertEquals(this.sut.onSerialDataParser(dataToParse.getBytes()), expectedValue);
    }
    //@Test(dataProvider = "testDataForRowCleaner")
    public void testOnSerialDataParserForRowCleaner(String dataToParse, Cell expectedValue) throws UnsupportedEncodingException {
        assertEquals(this.sut.onSerialDataParser(dataToParse.getBytes()), expectedValue);
    }
    //@Test(dataProvider = "testDataForBar")
    public void testOnSerialDataParserForBar(String dataToParse, Cell expectedValue) throws UnsupportedEncodingException {
        assertEquals(this.sut.onSerialDataParser(dataToParse.getBytes()), expectedValue);
    }
}
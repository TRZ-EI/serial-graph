package trz.structure.serial;

import trz.structure.Cell;
import trz.utils.DataTypesConverter;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 01/05/17
 * Time: 14.26
 *
 * Interface between data received from a serial port
 * and structure to print to RaspBerry screen
 */
public class SerialDataFacade {

    //private final VariableCollector variableCollector;
    private DataTypesConverter dataTypesConverter;
    private Map<Character, SerialDataReader> parsersMap;

    public static SerialDataFacade createNewInstance(){
        return new SerialDataFacade();
    }
    private SerialDataFacade(){
        //this.variableCollector = VariableCollector.getSingleInstance();
        this.dataTypesConverter = DataTypesConverter.getNewInstance();
        this.fillParsersMap();
    }

    private void fillParsersMap() {
        this.parsersMap = new HashMap<>();
        this.parsersMap.put('S', GraphSetupSerialDataParser.getNewInstance());
        this.parsersMap.put('R', GraphRunningDataSerialDataParser.getNewInstance());
        this.parsersMap.put('F', GraphFinalDataSerialDataParser.getNewInstance());
    }

    public Cell onSerialDataInput(byte[] data) throws UnsupportedEncodingException {
        Cell dataParsed = onSerialDataParser(data);
        return dataParsed;
    }


    public Cell onSerialDataParser(byte[] data) throws UnsupportedEncodingException {
        Cell retValue = null;

        // first step: what type of action?
        char command = this.readCommandFromData(data);
        if (command != '0') {
            // second step: On command received, parse data
            retValue = this.parsersMap.get(Character.valueOf(command)).readByteArray(data);
        }
        return retValue;
    }

    private char readCommandFromData(byte[] data) {
        char command = this.dataTypesConverter.byteToChar(data[0]);
        final int commandPos = 1;
        final int commandLenght = 1;
        if (command == '^') {
            byte byteCommand = Arrays.copyOfRange(data, commandPos, commandPos + commandLenght)[0];
            command = this.dataTypesConverter.byteToChar(byteCommand);
        }
        return command;
    }
}

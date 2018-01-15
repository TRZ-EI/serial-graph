package trz.comunication;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import trz.crc.CRC16CCITT;
import trz.crc.CRCCalculator;
import trz.crc.Crc16CcittKermit;
import trz.structure.serial.MultipleCommandSplitter;
import trz.utils.ConfigurationHolder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 26/08/17
 * Time: 14.31
 */
public class SerialDataManager {
    private char NEW_LINE;

    private SerialPort serialPort;
    //private SerialCommunicator serialCommunicator;
    private MultipleCommandSplitter multipleCommandSplitter;
    private BlockingQueue<String> serialBuffer;
    private BooleanProperty isDataAvalaible = new SimpleBooleanProperty(false);



    public static SerialDataManager createNewInstance(){
        return new SerialDataManager();
    }

    public static SerialDataManager createNewInstanceBySerialBuffer(BlockingQueue<String> serialBuffer){
        return new SerialDataManager(serialBuffer);
    }

    private SerialDataManager(){
        this.serialBuffer = new LinkedBlockingQueue<>();
        this.multipleCommandSplitter = MultipleCommandSplitter.getNewInstance();
        this.NEW_LINE = this.readEndLineFromConfiguration();
    }
    private SerialDataManager(BlockingQueue<String> serialBuffer) {
        this.serialBuffer = serialBuffer;
        this.multipleCommandSplitter = MultipleCommandSplitter.getNewInstance();
        this.NEW_LINE = this.readEndLineFromConfiguration();
    }
    private char readEndLineFromConfiguration(){
        String value = ConfigurationHolder.getInstance().getProperties().getProperty(ConfigurationHolder.END_OF_LINE);
        return (char)Integer.parseInt(value, 16);
    }


    public SerialPort connectToSerialPort() throws IOException{
        this.serialPort = SerialPort.getCommPort("/dev/ttyUSB0");
        this.serialPort.setBaudRate(115200);
        this.serialPort.setComPortParameters(115200, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        this.serialPort.openPort();
        if (serialPort != null){
            System.out.println("Connected !!!");
        }
        StringBuilder message = new StringBuilder();
        try {

            serialPort.addDataListener(new SerialPortDataListener() {
                int data = 0;
                @Override
                public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
                @Override
                public void serialEvent(SerialPortEvent event)
                {
                    if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE){
                        return;
                    }
                    try {
                        while ( ( data = serialPort.getInputStream().read()) > -1 ){
                            if ( data == NEW_LINE ) {
                                break;
                            }
                            message.append((char) data);
                        }
                        if (message.toString().startsWith("^")) {

                            // ONLY ONE MESSAGE
                            boolean isValid = calculateCRC(new String[]{message.toString()});
                            if (isValid) {
                                // TO MANAGE MULTIPLE COMMANDS IN A SINGLE ROW
                                List<String> commands = multipleCommandSplitter.splitMultipleCommand(message.toString());
                                serialBuffer.addAll(commands);
                                serialPort.getOutputStream().write(new String("OK").getBytes());
                                serialPort.getOutputStream().flush();
                            }
                            else{
                                serialPort.getOutputStream().write(new String("KO: " + message.toString() + NEW_LINE).getBytes());
                                serialPort.getOutputStream().flush();
                            }
                        }
                        message.setLength(0);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.serialPort;
    }

    private boolean calculateCRC(String[] messages) {
        int score = 0;
        int crcDigits = 4; // 4 hex digits
        for (String message: messages){
            int size = message.length();
            if (size > crcDigits){
                String hexCrc = message.substring(size - crcDigits);
                String messageToCalculate = message.substring(0, size - crcDigits);
                CRCCalculator calculator = this.selectCalculator();
                long crc = calculator.calculateCRCForStringMessage(messageToCalculate);
                String crcHex = Long.toHexString(crc);
                if (crcHex.length() == 3){
                    crcHex = "0" + crcHex;

                }else if (crcHex.length() == 2){
                    crcHex = "00" + crcHex;
                }else if (crcHex.length() == 1){
                    crcHex = "000" + crcHex;
                }
                score += (hexCrc.equalsIgnoreCase(crcHex))? 1: 0;
            }
        }
        return (score == messages.length);
    }

    private CRCCalculator selectCalculator() {
        String crc = ConfigurationHolder.getInstance().getProperties().getProperty(ConfigurationHolder.CRC);
        return (crc.equalsIgnoreCase("kermit"))? Crc16CcittKermit.getNewInstance(): CRC16CCITT.getNewInstance();
    }

    public boolean disconnectFromSerialPort(){
        boolean retValue = true;
        if(this.serialPort != null){
            System.out.println("disconnectFromSerialPort()");
            this.serialPort.removeDataListener();
            try {
                this.serialPort.getOutputStream().close();
                this.serialPort.getInputStream().close();
                this.serialPort.closePort();
            } catch (Exception e) {
                retValue = false;
                e.printStackTrace();
            }
        }
        return retValue;
    }


    public BooleanProperty getIsDataAvalaible() {
        return isDataAvalaible;
    }

    public BlockingQueue<String> getSerialBuffer() {
        return serialBuffer;
    }

    public void setIsDataAvalaible(boolean value) {
        this.isDataAvalaible.set(value);
    }
}

package trz.comunication;


import com.fazecast.jSerialComm.SerialPort;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import trz.crc.CRC16CCITT;
import trz.crc.CRCCalculator;
import trz.crc.Crc16CcittKermit;
import trz.utils.ConfigurationHolder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 19/01/17
 * Time: 12.42
 */
public class SerialCommunicatorLocalClientForTest implements SerialCommunicatorInterface, Runnable {


    private Properties properties;

    //input and output streams for sending and receiving data
    private InputStream input;
    private OutputStream output;


    //the timeout value for connecting with the port
    final static int TIMEOUT = 2000;

    private char END_OF_LINE;

    //a string for recording what goes on in the program
    //this string is written to the GUI
    String logText = "";

    private SerialPort serialPort;
    private StringBuffer buffer;

    private SerialWriter writer;
    private DoubleProperty numericValue = new SimpleDoubleProperty();


    public SerialCommunicatorLocalClientForTest() throws IOException {
        this.init();
    }
    private void init() throws IOException {
        this.properties = ConfigurationHolder.getInstance().getProperties();
        this.END_OF_LINE = this.readNewLineValueFromConfiguration();
        this.buffer = new StringBuffer();
    }

    private char readNewLineValueFromConfiguration() {
        String value = this.properties.getProperty(ConfigurationHolder.END_OF_LINE);
        return (char)Integer.parseInt(value, 16);

    }

    public SerialPort connect() {
        try {
            this.serialPort = SerialDataManager.createNewInstance().connectToSerialPort();
            this.initIOStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.serialPort;
    }


    //open the input and output streams
    //pre: an open port
    //post: initialized intput and output streams for use to communicate data
    public boolean initIOStream(){
        input = serialPort.getInputStream();
        output = serialPort.getOutputStream();
        this.writer = new SerialWriter(output);
        Thread t = new Thread(this.writer);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
        return (input != null && output != null);
    }
    //starts the event listener that knows whenever data is available to be read
    //pre: an open serial port
    //post: an event listener for the serial port that knows when data is recieved
    public void disconnect(){
        try{
            serialPort.removeDataListener();
            input.close();
            output.close();
            serialPort.closePort();
            System.out.println("Disconnected.");
        }catch (Exception e){
            System.out.println("Failed to close " + serialPort.getDescriptivePortName() + "(" + e.toString() + ")");
        }
    }

    //what happens when data is received
    //pre: serial event is triggered
    //post: processing on the data it reads

    public void manageDataReceivedFromSerialPort(String s) throws IOException {
        long startNanoseconds = System.nanoTime();
//        if (this.calculateCRC(s)) {
//            byte[] b = "OK\n".getBytes();
//            this.output.write("OK\n".getBytes());
//            this.output.flush();
//            long endNanoSeconds = System.nanoTime();
//
//            System.out.println("Latency time(micros): " + (endNanoSeconds - startNanoseconds) / 1000);
//        }
          System.out.println(s);
          this.output.write(("OK" + this.END_OF_LINE).getBytes());
          this.output.flush();
          long endNanoSeconds = System.nanoTime();
          System.out.println("Latency time(micros): " + (endNanoSeconds - startNanoseconds) / 1000);

    }

    private boolean calculateCRC(String message) {
        boolean retValue = false;
        int crcDigits = 4; // 4 hex digits
        int size = message.length();
        if (size > crcDigits){
            String hexCrc = message.substring(size - crcDigits);
            String messageToCalculate = message.substring(0, size - crcDigits);
            String crcHex = calculateCrCForString(messageToCalculate);
            if (crcHex.length() < crcDigits){
                crcHex = "0" + crcHex;
            }
            retValue = hexCrc.equalsIgnoreCase(crcHex);
        }
        return retValue;
    }

    public String calculateCrCForString(String messageToCalculate) {
        long crc = this.selectCalculator().calculateCRCForStringMessage(messageToCalculate);
        return this.fillHexCodeToFourDigitIfNecessary(crc);
    }
    private CRCCalculator selectCalculator() {
        String crc = ConfigurationHolder.getInstance().getProperties().getProperty(ConfigurationHolder.CRC);
        return (crc.equalsIgnoreCase("kermit"))? Crc16CcittKermit.getNewInstance(): CRC16CCITT.getNewInstance();
    }

    private String fillHexCodeToFourDigitIfNecessary(long crc) {
        String hexCode = Long.toHexString(crc);
        int len = hexCode.length();
        int zeros = 4 -len;
        if (zeros > 0){
            for (int i = 0; i < zeros; i ++){
                hexCode = "0" + hexCode;
            }
        }
        return hexCode;
    }



    //method that can be called to send data
    //pre: open serial port
    //post: data sent to the other device
    public void writeData(byte[] data) {
        try {
            for (int i = 0; i < data.length; i++) {
                output.write(data[i]);
                output.flush();
            }
        } catch (Exception e) {
            logText = "Failed to write data. (" + e.toString() + ")";
            System.out.println(logText);
        }
    }
    public List<String> readTestScenaryAndProduceDataForTest(String scenary){
        List<String> list = new ArrayList<>();
        String realFileName = this.getClass().getClassLoader().getResource(scenary).getFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(realFileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    line += this.calculateCrCForString(line);
                    line += this.END_OF_LINE;
                    list.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return list;


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigurationHolder.createSingleInstanceByConfigUri(args[0]);

        SerialCommunicatorLocalClientForTest sc = new SerialCommunicatorLocalClientForTest();
        Thread runner = new Thread(sc);
        runner.start();

        System.out.println("********* EMBEDDED SSL BROKER STARTED *****************");
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        String input = "WRITE  'STOP' TO STOP SERVER ...";
        System.out.println(input);
        while ((input = reader.readLine()).length() != 0) {
            if (input.equalsIgnoreCase("s")) {
                runner.suspend();
                System.out.println("Service interrupted");
            }else{
                runner.resume();
            }
        }




    }

    public double convertStringDataToNumericValue(String s) {
        if (s.length() > 0){
            this.numericValue.set(Double.parseDouble(s));
        }
        return this.numericValue.get();
    }

    public DoubleProperty getNumericValue() {
        return numericValue;
    }

    @Override
    public void run() {
        this.connect();
        this.sendMessagesToRemoteClient("serialInputs/real-examples-prova3-fragment1-1-no-crc.txt");
        this.sendMessagesToRemoteClient("serialInputs/real-examples-prova3-fragment1-2-no-crc.txt");
        this.sendMessagesToRemoteClient("serialInputs/real-examples-prova3-fragment1-3-no-crc.txt");
        this.sendMessagesToRemoteClient("serialInputs/clean-row-before-cleaner-test-no-crc.txt");
        this.sendMessagesToRemoteClient("serialInputs/clean-row-after-cleaner-test-no-crc.txt");
        for (int i = 0; i < 200; i++){
            this.sendMessagesToRemoteClient("serialInputs/real-examples-prova3-fragment1-4-bars-no-crc.txt");
            String testHeader = "^t11215 cycle # " + i;
            testHeader += this.calculateCrCForString(testHeader);
            testHeader += this.END_OF_LINE;
            this.writeData(testHeader.getBytes());
            this.waitFor(500);
        }
    }

    private void sendMessagesToRemoteClient(String resource) {
        List<String> lines = this.readTestScenaryAndProduceDataForTest(resource);
        for (String line: lines){
            System.out.println(line);
            this.writeData(line.getBytes());
            this.waitFor(100);
        }
    }

    public void waitFor(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}



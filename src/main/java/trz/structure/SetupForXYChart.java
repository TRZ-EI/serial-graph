package trz.structure;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 27/01/18
 * Time: 17.42
 * Command = S
 */
public class SetupForXYChart {
    private int testId;             // # prova
    private int channels;           // # canali
    private double pressureMax;     // mBar
    private double immisionTime;    // decimi di secondo - tempo di immissione
    private double restTime;        // decimi di secondo - tempo di stabilizzazione
    private double testTime;        // decimi di secondo - tempo di prova
    private double s1Plus;
    private double s1Minus;
    private double s2Plus;
    private double s2Minus;
    private double s3Plus;
    private double s3Minus;
    private double s4Plus;
    private double s4Minus;

    public int getTestId() {
        return testId;
    }

    public SetupForXYChart setTestId(int testId) {
        this.testId = testId;
        return this;
    }

    public int getChannels() {
        return channels;
    }

    public SetupForXYChart setChannels(int channels) {
        this.channels = channels;
        return this;
    }

    public double getPressureMax() {
        return pressureMax;
    }

    public SetupForXYChart setPressureMax(double pressureMax) {
        this.pressureMax = pressureMax;
        return this;
    }

    public double getImmisionTime() {
        return immisionTime;
    }

    public SetupForXYChart setImmisionTime(double immisionTime) {
        this.immisionTime = immisionTime;
        return this;
    }

    public double getRestTime() {
        return restTime;
    }

    public SetupForXYChart setRestTime(double restTime) {
        this.restTime = restTime;
        return this;
    }

    public double getTestTime() {
        return testTime;
    }

    public SetupForXYChart setTestTime(double testTime) {
        this.testTime = testTime;
        return this;
    }

    public double getS1Plus() {
        return s1Plus;
    }

    public SetupForXYChart setS1Plus(double s1Plus) {
        this.s1Plus = s1Plus;
        return this;
    }

    public double getS1Minus() {
        return s1Minus;
    }

    public SetupForXYChart setS1Minus(double s1Minus) {
        this.s1Minus = s1Minus;
        return this;
    }

    public double getS2Plus() {
        return s2Plus;
    }

    public SetupForXYChart setS2Plus(double s2Plus) {
        this.s2Plus = s2Plus;
        return this;
    }

    public double getS2Minus() {
        return s2Minus;
    }

    public SetupForXYChart setS2Minus(double s2Minus) {
        this.s2Minus = s2Minus;
        return this;
    }

    public double getS3Plus() {
        return s3Plus;
    }

    public SetupForXYChart setS3Plus(double s3Plus) {
        this.s3Plus = s3Plus;
        return this;
    }

    public double getS3Minus() {
        return s3Minus;
    }

    public SetupForXYChart setS3Minus(double s3Minus) {
        this.s3Minus = s3Minus;
        return this;
    }

    public double getS4Plus() {
        return s4Plus;
    }

    public SetupForXYChart setS4Plus(double s4Plus) {
        this.s4Plus = s4Plus;
        return this;
    }

    public double getS4Minus() {
        return s4Minus;
    }

    public SetupForXYChart setS4Minus(double s4Minus) {
        this.s4Minus = s4Minus;
        return this;
    }
}

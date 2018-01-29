package trz.structure;

/**
 * Created with IntelliJ IDEA.
 * User: luigi
 * Date: 27/01/18
 * Time: 17.57
 *
 * Running data for XYGraph. Data trasmitted every 100mS
 * Command = R
 */
public class RunningTestDataForXYChart {

    private char phase;                     // fase (I/S/P)
    private double relativeToPhaseTimer;    // tempo relativo ad una certa fase
    private double totalTimer;              // tempo totale
    private double p1;                      // pressione P1
    private double p2;                      // pressione P2
    private double p3;                      // pressione P3
    private double p4;                      // pressione P4

    public char getPhase() {
        return phase;
    }
    public RunningTestDataForXYChart setPhase(char phase) {
        this.phase = phase;
        return this;
    }
    public double getRelativeToPhaseTimer() {
        return relativeToPhaseTimer;
    }
    public RunningTestDataForXYChart setRelativeToPhaseTimer(double relativeToPhaseTimer) {
        this.relativeToPhaseTimer = relativeToPhaseTimer;
        return this;
    }
    public double getTotalTimer() {
        return totalTimer;
    }
    public RunningTestDataForXYChart setTotalTimer(double totalTimer) {
        this.totalTimer = totalTimer;
        return this;
    }
    public double getP1() {
        return p1;
    }
    public RunningTestDataForXYChart setP1(double p1) {
        this.p1 = p1;
        return this;
    }
    public double getP2() {
        return p2;
    }
    public RunningTestDataForXYChart setP2(double p2) {
        this.p2 = p2;
        return this;
    }
    public double getP3() {
        return p3;
    }
    public RunningTestDataForXYChart setP3(double p3) {
        this.p3 = p3;
        return this;
    }
    public double getP4() {
        return p4;
    }
    public RunningTestDataForXYChart setP4(double p4) {
        this.p4 = p4;
        return this;
    }
}

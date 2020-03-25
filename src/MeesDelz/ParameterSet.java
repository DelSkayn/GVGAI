package MeesDelz;

public class ParameterSet {
    public double CI;
    public double MAX_ROLLOUT_DEPTH;

    ParameterSet(){
        this.CI = Math.sqrt(2.0);
        this.MAX_ROLLOUT_DEPTH = 10;
    }
}
